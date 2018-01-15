/*
 * SERVIDOR.C
 * Servidor de numeros primos v0.1
 * (c) Sergio Paracuellos Gutierrez
 */ 

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include "servidor.h"

#define MAXCONEXIONES 100
#define MAX_TAM_BUFFER 256
#define TRUE 0
#define FALSE 1


/* TRUE si es primo */
int esprimo (long n){
    long i;
    for (i = 2; i * i <= n; i++){      
    	if ((n % i) == 0)
            return TRUE;
	}
    return FALSE;
}
                      
/* cuenta primos entre MAX Y MIN */
long cuenta_primos (long min, long max) {
    long i, contador = 0;
    for (i = min; i < max; i++)
        if (esprimo (i))
            contador++;
    return contador;
}

/* devuelve un vector con los primos */
long encuentra_primos(long min, long max, long *vector) {
    long i, contador = 0;
    for (i = min; i < max; i++)
        if (esprimo (i))
            vector[contador++] = i;
    return contador;
}

/* MAIN */
main (int argc, char *argv[]) {
  int fd, fd2;		      /* los ficheros descriptores */
  pid_t pid;
  struct sockaddr_in server;  /* para la información de la dirección del servidor */
  struct sockaddr_in client;  /* para la información de la dirección del cliente */
  int sin_size;
  int port;                   /*puerto para conectarse*/  

  FILE *log_file;
  long buffer [MAX_TAM_BUFFER];
  long longitud,i;

  long max, min, num_primos=0;
  int numbytes;
  char peticion[MAX_TAM_BUFFER];

  /* comprobacion de argumentos */
  if (argc != 3) {
      perror ("Error en el numero de argumentos!!!\n");
      exit (1);
  }
                                
  /* recogemos el puerto de linea de comandos */
  port = atoi (argv[2]);               
  
  /* abrimos el fichero de log */
  if((log_file = fopen ("log.txt", "a"))<0) {
    perror("Error abriendo el fichero\n");
    exit(-1);
  }
  
  fprintf (log_file, "%s \t %s\n", argv[1], argv[2]);
  
  fclose (log_file);
            
  
  /* socket() */
  if ((fd = socket (AF_INET, SOCK_STREAM, 0)) == -1) {
      perror ("error en socket()\n");
      exit (-1);
  }

  
  server.sin_family = AF_INET;
  server.sin_port = htons (port);
  server.sin_addr.s_addr = INADDR_ANY;
  bzero (&(server.sin_zero), 8);


  /* bind() */
  if (bind (fd, (struct sockaddr *) &server, sizeof (struct sockaddr)) == -1) {
      perror ("error en bind() \n");
      exit (-1);
  }
  
  /* Listen() */
  if (listen (fd, MAXCONEXIONES) == -1) {				
      perror ("error en listen()\n");
      exit (-1);
  }
   
  printf ("Aceptando conexion...........\n");
  /* bucle indefinido de aceptacion de conexiones */
  while (1) {
      sin_size = sizeof (struct sockaddr_in);
    
      fd2 = accept (fd, (struct sockaddr *) &client, &sin_size);
          
      pid=fork();
      switch (pid) {
          case -1:
            perror("Error creando proceso\n");
            exit(1);
            break;
          case 0:
             /* accept */
            if (fd2 < 0) {
                perror("error en accept gestionado port proceso\n");
                exit(1);
            }

            printf ("Se obtuvo una conexión desde %s gestionada por un proceso con pid %d\n", inet_ntoa (client.sin_addr),getpid());

            /* recibimos la peticion deseada del cliente */
            if ((numbytes = read (fd2,peticion,MAX_TAM_BUFFER)) == -1) {
                perror("Error recibiendo la petición\n");
                exit(1);
            }

            /* recibimos el minimo */
            if ((numbytes = read (fd2, &min, sizeof (long))) == -1) {
                perror ("Error en revc\n");
                exit (1);
            }      
                                                                                                                            
            /* recivimos el maximo */
            if ((numbytes = read(fd2, &max, sizeof (long),0)) == -1) {
                perror ("Error en revc\n");
                exit (1);
            }
                                     
            if ((strcmp(peticion,"cuenta_primos"))==TRUE) {
                /* contamos los primos en ese intervalo */
                num_primos = cuenta_primos (min, max);
      
                /* mandamos los primos al cliente */
                if ((write (fd2, &num_primos , sizeof (long))) == -1) {
                    perror ("Error en el envio\n");
                    exit (1);
                }
                close (fd2);		
            } else if ((strcmp(peticion,"encuentra_primos"))==TRUE) {
                /* encontramos la lista de primos en ese intervalo */
                longitud=encuentra_primos(min,max,buffer);

                /* mandamos la longitud del vector de primos al cliente */
                if ((numbytes = write (fd2,&longitud, sizeof(long))) == -1) {
                    perror("Error mandando la longitud del vector de primos\n");
                    exit(-1);
                }
      
                /* mandamos la lista de primos al cliente */
                for (i=0;i<longitud;i++) {
                    if ((numbytes = write(fd2,&buffer[i], sizeof(long))) == -1) {
                        perror("Error en el envio de la lista de primos\n");
                        exit(1);
                    }
                }
                close (fd2);		
            } else {
                printf("Peticion no disponible de momento\n");
                close (fd2);		
            }

          default:
                break;
      }
    }
}

