#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <pthread.h>

#define PORT 8888

void *connection_handler(void *);

int main(int argc, char *argv[])
{
    int socket_desc, client_sock, c, *new_sock;
	struct sockaddr_in server, client;
	//char client_message[2000];

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
	printf("sizeof sockaddr_in : %d", c);

	//menerima koneksi dari client
	while( (client_sock = accept(socket_desc, (struct sockaddr *)&client, (socklen_t*)&c)) )
	/*if(client_sock < 0)
	{
		printf("Accept failed\n");
		return 1;
	}*/
	{
        printf("client_sock : %d socket_desc : %d", client_sock, socket_desc);
        printf("Connection accepted\n");
        printf("ip client : %s port : %d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));
        pthread_t sniffer_thread;
        new_sock = malloc(1);
        *new_sock = client_sock;
        //printf();
        if(pthread_create( &sniffer_thread, NULL, connection_handler, (void*) new_sock) < 0)
        {
            printf("Could not create thread\n");
            return 1;
        }
        printf("Handler assigned\n");

	}

	if(client_sock < 0)
	{
        printf("Accept failed");
        return 1;
	}
	return 0;
	/*printf("Connection accepted");

	while((read_size == recv(client_sock, client_message, 2000, 0)) > 0)
	{
		write(client_sock, client_message, strlen(client_message));
	}

	if(read_size == 0)
	{
		printf("Client disconnected");
	}

	else if(read_size == -1)
	{
		printf("Receive failed");
	}
	return 0;*/
}

void *connection_handler(void *socket_desc)
{
    //membuat socket decriptor
    int sock = *(int*)socket_desc;
    int read_size;
    char *message, client_message[2000];

    //mengirimkan pesan ke client bahwa dia sudah berhasil terhubung dengan server
    message = "Selamat, saya sudah menghandel koneksi Anda\n";
    write(sock, message, strlen(message));

    while( (read_size = recv(sock, client_message, 2000, 0)) >0 )
    {
        write(sock, client_message, strlen(client_message));
    }

    if(read_size == 0)
    {
        printf("Client disconnect\n");
        fflush(stdout);
    }
    else if(read_size == -1)
    {
        printf("recv failed");
    }

    free(socket_desc);
    return 0;
}
