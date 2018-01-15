/*
 * CLIENTE.C
 * Cliente de números primos v0.1
 * (c) Sergio Paracuellos Gutiérrez
 */ 

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>


#define MAX_SIZE_BUFFER 256
#define TRUE 0
#define FALSE 1


int main (int argc, char *argv[]) {
    
  int fd, numbytes; /* ficheros descriptores */
  struct hostent *he;  /* estructura que recibirá información sobre el nodo remoto */
  struct sockaddr_in server;  /* información sobre la dirección del servidor */
  long i,longitud=0;

  FILE *log_file; /* fichero de log */
  
  int puerto;

  long min=1L;
  long max=100L;
  long num_primos=0;

  long buffer[MAX_SIZE_BUFFER];
  
  char *hostname;
  char *port_number;
  char peticion[MAX_SIZE_BUFFER];
  
  
  /* comprobacion de argumentos */
   if (argc < 2) {
        perror ("Error en el paso de parametros.\n");
        exit (1);
   }
                                                                                                                 
   /* apertura del fichero */
    if((log_file = fopen ("log.txt", "r"))<0) {
        perror("Error abriendo el fichero\n");
        exit(-1);
    }

    /* reservamos memoria para el hostname y el puerto */
    hostname=(char *)malloc(sizeof(hostname));
    port_number=(char *)malloc(sizeof(port_number));

    /* leemos los valores del fichero */
    while (fscanf (log_file, "%s\t %s \n", hostname, port_number) != EOF) {
        puerto = atoi (port_number);
    }
      
  /* estructura del host en he */
  if ((he = gethostbyname (hostname)) == NULL) {
      /* llamada a gethostbyname() */
      printf ("gethostbyname() error\n");
      exit (-1);
  }

  /* socket() */
  if ((fd = socket (AF_INET, SOCK_STREAM, 0)) == -1) {
      printf ("socket() error\n");
      exit (-1);
  }

  server.sin_family = AF_INET;
  server.sin_port = htons (puerto);/* htons() es necesaria nuevamente ;-o */
  server.sin_addr = *((struct in_addr *) he->h_addr); /*he->h_addr pasa la información de *he a "h_addr" */
  bzero (&(server.sin_zero), 8);

  /* connect() */
  if (connect (fd, (struct sockaddr *) &server, sizeof (struct sockaddr)) == -1) {
      printf ("connect() error\n");
      exit (-1);
    }
   
  printf ("Mandando datos al servidor...\n");

  strcpy(peticion,argv[1]);

  /* mandamos al servidor el valor minimo del intervalo */
  if (numbytes = write (fd, peticion, MAX_SIZE_BUFFER) == -1) {
    perror ("Envío de la peticion.\n");
    exit (1);
  }
  
  /* mandamos al servidor el valor minimo del intervalo */
  if (numbytes = write (fd, &min, sizeof (long)) == -1) {
    perror ("Envío del mínimo.\n");
    exit (1);
  }
  
  /* mandamos al servidor el valor maximo del intervalo */
  if (numbytes = write (fd, &max, sizeof (long)) == -1) {
    perror ("Envío del maximo.\n");
    exit (1);
  }
                                    
  if ((strcmp(peticion,"cuenta_primos"))==TRUE) {
    /* recibimos la respuesta del servidor con el numero de primos */
    if ((numbytes = read(fd, &num_primos, sizeof (long))) == -1) {
        perror ("Error en revc\n");
        exit (1);
    }
  
     printf ("El numero de primos que me ha mandao el servidor es: %d\n", num_primos);
     close(fd);
  } else if ((strcmp(peticion,"encuentra_primos"))==TRUE) {
    /* recibimos la longitud del vector de primos */
    if ((numbytes = read(fd, &longitud, sizeof (long))) == -1) {
          perror ("Error en revc\n");
          exit (1);
    }

    printf("La lista de primos obtenida del servidor es: ");

    /* recogemos la lista de primos que nos manda el servidor */
    for (i=0;i<longitud;i++) {
      if ((numbytes = read(fd,&buffer[i],sizeof(long))) == -1) {
        perror("Error recibiendo la lista de primos\n");
        exit(1);
      }
    printf(" %d ", buffer[i]);
    }
    printf("\n");
    close(fd);
  } else {
    printf("No existe esa opcion\n");
    close(fd);
    exit(1);
  }

  free(hostname);
  free(port_number);

}
