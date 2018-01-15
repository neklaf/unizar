/*Posibles mejoras implementar una funcion error en la que esten contemplados dentro de un 
 * switch un monton mas de posibilidades de error, como por ejemplo las que se mencionan en 
 * el libro*/


#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h> /*struct sockaddr_in, htons, htonl*/

#define PORT 4321 /*Lo mas flexible es pasar el puerto como parametro*/
#define NUM_CLI_MAX 100
#define MAX_TAM_BUFFER 1000
#define MAX_TAM_BUFFER2 256


/*Funciones de ordenacion de bytes*/
/*Esto pretende resolver la problematica de la ordenacion en memoria de los bytes, recordemos
 * que tenemos dos posibles ordenaciones de la memoria little endian y big endian*/

/*Nosotros como programadores que somos debemos de tratar con la ordenacion de bytes de red*/

/*Para convertir pues del la ordenacion de bytes en el host a la ordenacion de bytes de la red
 * utilizarmos las funciones siguientes htons, htonl. La h viene de host, la n de network y la 
 * s de short (entero de 16 bits) y la l de long (entero de 32 bits)*/

/*Ahora tendremos la explicacion de porque utilizamos la funcion bzero, es utilizada para resolver el problema de la ordenacion de bytes en las variables que representan los datos que van
 * por la red. La funcion 'bzero' lo que hace es poner un numero de bytes especificados en un
 * parametro a cero en el destino, que es su primer parametro.*/

/*La familia del protocolo utilizado por el socket será AF_INET implica protocolo IPv4*/
/*Y el tipo de socket será un stream socket SOCK_STREAM*/

/*Cuando tenemos en una funcion un parametro que es un puntero a algo y delante lleva la etiqueta 'const' significara que el contenido de esa direccion de memoria no es modificable en la funcion (o no es modificado en el interior de la funcion) Esto se lo debemos al estandar ANSI C*/

main (int argc, char *argv[]){
	int escfd, confd;	/*descriptores de ficheros*/
	socklen_t len;
	struct sockaddr_in servaddr, cliaddr;	/*direcciones del servidor y del cliente*/
	long buffer[MAX_TAM_BUFFER];
	int port;	/*puerto al que se conecta*/

	long max, min, n_prim=0;
	pid_t pid;
	
	/*Por ahora para facilitar la implementacion un poco lo haremos con el puerto estatico*/

	/*socket()*/
	if ((escfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("Error en la creacion del socket\n");
		exit(-1);
	}

	bzero(&servaddr, sizeof(struct sockaddr));
	/*segun el libro esto es igual sizeof(struct sockaddr) = sizeof(servaddr) ?*/
	/*Yo creo que si pq no son punteros*/
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(PORT);	/*Puerto del servidor*/

	/*bind()*/
	if (bind (escfd, (struct sockaddr *) &servaddr, sizeof(struct sockaddr)) == -1){
		perror("Error en el bind\n");
		exit(-1);
	}

	/*listen()*/
	if (listen(escfd, NUM_CLI_MAX) == -1){
		perror("Error en el listen\n");
		exit(-1);
	}

	printf("Aceptando conexion.............\n");
	for( ; ; ){
		len = sizeof(cliaddr);
		confd = accept(escfd, (struct sockaddr *) &cliaddr, &len);
		printf("Conexion desde el puerto %d\n", ntohs(cliaddr.sin_port));
		
		pid = fork();
		switch(pid){
			case -1:
				perror("Error creando el proceso\n");
				exit(-1);
				break;
			case 0:
				/*Comprobacion del accept*/
				if (confd < 0){
					perror("Error en el accept\n");
					exit(-1);
				}
		}
	}
}
