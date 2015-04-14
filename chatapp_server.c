#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <pthread.h>

#define PORT 9999

void *connection_handler(void *);

struct list_user
{
    int sock;
    char nama_user[100];
    struct list_user * next;
};

typedef struct list_user user;

user * active, * head, * tmp;

int main(int argc, char *argv[])
{
    int socket_desc, client_socket, c, *new_sock, sd, max_sd;
	struct sockaddr_in server, client;
	//char client_message[2000];

    head = NULL;
	socket_desc = socket(AF_INET, SOCK_STREAM, 0);

	//membuat socket
	if (socket_desc == -1)
	{
		printf("Could not create socket\n");
	}
	printf("Socket created\n");

	//deklarasi ip version dan port yang digunakan
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(PORT);

	//Bind
	if(bind(socket_desc,(struct sockaddr *)&server, sizeof(server)) < 0)
	{
		printf("Bind failed\n");
		return 1;
	}
	printf("Bind done\n");

	listen(socket_desc, 3);

	//menunggu koneksi dari client
	printf("Waiting for incoming connections\n");
	c = sizeof(struct sockaddr_in);

	//menerima koneksi dari client
	while( (client_socket = accept(socket_desc, (struct sockaddr *)&client, (socklen_t*)&c)) )
	{
        //printf("client_sock : %d socket_desc : %d\n", client_socket, socket_desc);
        printf("Connection accepted\n");

        //printf("1-- > ip client : %s port : %d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));
        pthread_t sniffer_thread;
        pthread_t list_user;
        new_sock = malloc(1);
        *new_sock = client_socket;

        active = (user *)malloc(sizeof(user));
        active->next = head;
        active->sock = client_socket;
        //strcpy(active->nama_user, "");
        head = active;

        if(pthread_create( &sniffer_thread, NULL, connection_handler, (void*) new_sock) < 0)
        {
            printf("Could not create thread\n");
            return 1;
        }
        printf("Handler assigned\n");

	}

	if(client_socket < 0)
	{
        printf("Accept failed");
        return 1;
	}
	return 0;
}

void *connection_handler(void *socket_desc)
{
    //membuat socket decriptor
    int sock = *(int*)socket_desc;
    int read_size;
    int i = 0;
    char *message, client_message[2000], temp_message[2000], msg[2000], body_msg[2000], user[100], user_tujuan[100];

    active = head;
    read_size = read(sock, client_message, 2000);
    active = head;

    while(active)
    {
        if(active->sock == sock)
        {
            client_message[read_size] = '\0';
            strtok(client_message,"\r\n");
            strcpy(active->nama_user,client_message);
            strcpy(user, client_message);
        }
        active = active->next;
    }

    //mengirimkan pesan ke client bahwa dia sudah berhasil terhubung dengan server
    message = "Selamat datang di Secure Chat\n";
    write(sock, message, strlen(message));

    int flag_receive = 0;
    int active_user = 0;
    //printf("2-->ip client : %s port : %d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));
    while(read_size = recv(sock, client_message, 2000, 0))
    {
        client_message[read_size] = '\0';
        strtok(client_message, "\r\n");

        active = head;
        char list[2000];
        strcpy(list, ";");
        while(active)
        {
            strcat(list, active->nama_user);
            strcat(list, ";");
            active = active->next;
        }
        flag_receive = 0;
        printf("ini list %s \n", list);
        send(sock, list, strlen(list), 0);
        send(sock, "\r\n", strlen("\r\n"), 0);

        if(client_message[0] == ':')
        {
            printf("masuk ke titik 2\n");
            strcpy(user_tujuan, strtok(client_message, ":"));
            strcpy(body_msg, strtok(NULL, ":"));
            //printf("body_msg %s \n", body_msg);
            printf("user_tujuan %s ", user_tujuan);
            flag_receive = 1;
        }

        else
        {
            flag_receive = 2;
            strcpy(body_msg, client_message);
        }

        if(flag_receive == 1)
        {
			active = head;
			while(active)
			{
				if(strcmp(user_tujuan, active->nama_user) == 0)
				{
					active_user = active->sock;
					printf("user %s dan body_msg %s ", user, body_msg);
					sprintf(msg,"%s: %s\r\n",user, body_msg);
					printf("msg is %s", msg);
					send(active_user , msg , strlen(msg),0);
					send(active_user , "\r\n" , strlen("\r\n"),0);

				}
				active = active->next;
			}
		}
		else if(flag_receive==2)
		{
			sprintf(msg,"%s: %s\r\n",user,body_msg);
			write(active_user , msg , strlen(msg));
		}

		strcpy(client_message,"");

	}

    if(read_size == 0)
    {
        printf("Client disconnect\n");
        active = head;
		while(active)
		{
			if(active->sock == sock)
			{
				tmp = active;
				head = active->next;
				free(tmp);
				break;
			}
			else if(active->next!=NULL && active->next->sock==sock)
			{
				tmp = active->next;
				active->next = active->next->next;
				free(tmp);
				break;
			}

			printf("%d\n", active->sock);
			active = active->next;
		}
        fflush(stdout);
    }
    else if(read_size == -1)
    {
        printf("recv failed");
    }

    free(socket_desc);
    return 0;
}
