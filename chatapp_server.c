#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>
 
int main(int argc, char *argv[])
{
	int socket_desc, client_sock, c, read_size;
	struct sockaddr_in server, client;
	char client_message[2000];
	
	socket_desc = socket(AF_INET, SOCK_STREAM, 0);
	
	//Create socket
	if (socket_desc == -1)
	{
		printf("Could not create socket\n");
	}
	printf("Socket created\n");
	
	//Prepare the sockaddr_in structure
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(9000);

	//Bind
	if(bind(socket_desc,(struct sockaddr *)&server, sizeof(server)) < 0)
	{
		printf("Bind failed\n");
		return 1;
	}
	printf("Bind done");
	
	listen(socket_desc, 3);

	//Accetp and incoming connection
	printf("Waiting for incoming connections\n");
	c = sizeof(struct sockaddr_in);
	
	//Accept connection from an incoming cllient
	client_sock = accept(socket_desc, (struct sockaddr *)&client, (socklen_t*)&c);
	if(client_sock < 0)
	{
		printf("Accept failed\n");
		return 1;
	}
	printf("Connection accepted");
	
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
	return 0;
}
