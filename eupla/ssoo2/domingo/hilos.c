/*Los permisos obviamente tal y como creamos los permisos no se
mantienen en relacion a como los tenian*/

#include <stdio.h>
#include <stdlib.h> //Para poder usar el malloc
#include <string.h>
#include <pthread.h>	//Para los hilos
#include <sys/types.h>
#include <sys/stat.h>	//Para la funcion stat
#include <unistd.h>		//Para la funcion stat
#include <dirent.h>		//Para los directorios
#include <fcntl.h>		//Para abrir ficheros
#include <sched.h>
#include <errno.h>
#include "lista.h"		//Para trabjar con listas

#define TAMANNOBUFFER	100

/*PROTOTIPOS DE LAS FUNCIONES*/
//void mensaje(void *arg);	//Solo es de debugueo!!!
void *copy_fichero(void *arg);
void *copy_directorio(void *arg);	//Esta es una función q propone el Joselo!!

//Quita la barra del final del nombre del directorio
void retocaDir(char *cad){
	char *aux;
	aux=strchr(cad,'\0');	//aux apuntara la final de la cadena
	aux=aux--;	//aux apuntara al ultimo caracter de la cadena cad
	if (*aux == '/'){
		*aux='\0';
	}
}

/*La función 'sizeof' si la cadena es muy grande no funciona bien como 
argumento del malloc*/
/*No entiendo pq?? no funciona bien el sizeof.
Creo q no era el sizeof lo q no funcionaba sino q lo q ocurre es q no libero
los punteros en ningun momento??, pq sino ta,poco debería funcionar con el 
strlen no?*/

void *copia_fichero(copyinfo_t *arg){
	int bytes_leidos=0;
	int bytes_escritos=0;
	int *bytes_copiados;
	char *file1,*file2;
	char buffer[TAMANNOBUFFER];
	char *buffer_p;
	
	/*file2=strchr(arg,' ');
	*file2='\0';
	file1=arg;
	file2++;*/

	/*printf("Fichero origen: %s\n",file1);
	printf("Fichero destino: %s\n",file2);*/

	if ((bytes_copiados = (int *)malloc(sizeof(int))) == NULL)
		pthread_exit(NULL);
	
	
	for( ; ; ){
		bytes_leidos = read(arg->fuente_fd, buffer, TAMANNOBUFFER);
		
		//printf("Los bytes leidos son: %d\n",bytes_leidos);
		
		if ((bytes_leidos == 0) || ((bytes_leidos < 0) && (errno != EINTR)))
				break;
		
		
		else if ((bytes_leidos == 0) && (errno == EINTR)){
			continue;
		}

		buffer_p = buffer;	//Actua como indice para el vector
		
		while (bytes_leidos > 0){
			bytes_escritos = write(arg->destino_fd, buffer_p, bytes_leidos);
			//printf("bytes escritos: %d\n",bytes_escritos);
			if ((bytes_escritos < 0) && (errno != EINTR))
				break;
			else if (bytes_escritos < 0)
				continue;
			*bytes_copiados += bytes_escritos;
			bytes_leidos -= bytes_escritos;
			//printf("bytes leidos: %d\n",bytes_leidos);
			buffer_p += bytes_escritos;

		}
		if (bytes_escritos == -1)
			break;
	}
	/*close(fd1);
	close(fd2);*/
	pthread_exit(bytes_copiados);
}

//Solo sirve para probar si funciona el paso de parametros
/*void mensaje(void *arg){
	char *men=(char *)arg;
	printf("%s\n",men);
}*/	

void *copy_directorio(void *arg){
	DIR *dirp,*dirp2;	//Puntero a directorio
	struct dirent *direntp;	//Puntero a estructura dirent
	//Es muy importante trabajar con los punteros inicializados
	struct stat buffer;	
	//Estructura donde se guarda info sobre el fichero

	pthread_t hilo2, hilo3;

	char *origen=(char *)malloc(sizeof(arg));
	char *destino=(char *)malloc(sizeof(arg));
	char *nb, *arg2, *rutac, *ruta2, *arg3, *ancla;
	int tipo,fd1,fd2;

	copyinfo_t *aux,*nodo = (copyinfo_t *)malloc(sizeof(copyinfo_t));	//Nodo de la lista
	
	//separamos los directorios
	destino=strchr(arg,' ');
	*destino='\0';
	origen=arg;
	destino=destino++;	//!!
	printf("Origen: %s\nDestino: %s\n",origen,destino);
	
	if ( (dirp = opendir(origen)) == NULL){
		fprintf(stderr,"No se puede abrir el directorio %s\n",origen);
		exit(1);
	}

	if ( (dirp2 = opendir(destino)) == NULL){
		fprintf(stderr,"No se puede abrir el directorio %s\n",destino);
		if (errno == ENOENT){
			printf("Creando directorio %s\n",destino);
			mkdir(destino,0777);	
		}
	}
	direntp = readdir(dirp);	//Para saltar el .
	direntp = readdir(dirp);	//Para saltar el ..
	while ( (direntp=readdir(dirp)) != NULL){
		nb=(char *)malloc(strlen((char *)&(direntp->d_name))*2);
		strcpy(nb,direntp->d_name);
		rutac=(char *)malloc((strlen(nb)*2) + (strlen(origen)*2) + 6);
		ruta2=(char *)malloc((strlen(nb)*2) + (strlen(destino)*2) + 6);
		strcpy(rutac,origen); strcpy(ruta2,destino);
		strcat(rutac,"/");	strcat(ruta2,"/");
		strcat(rutac,nb);	strcat(ruta2,nb);
		printf("Ruta origen: %s\nRuta destino: %s\n",rutac,ruta2);
		free(nb);	//Aqui ya no utilizamos nb
		stat(rutac, &buffer); //Recibimos info del fichero
		tipo = buffer.st_mode & S_IFMT;	//Aplicamos la mascara
		switch (tipo){
			case S_IFREG:
				/*arg2=(char *)malloc((strlen(rutac)*2) + (strlen(ruta2)*2) + 6);
				strcpy(arg2,rutac);
				strcat(arg2," ");
				strcat(arg2,ruta2);*/

				if ((fd1=open(rutac, O_RDONLY)) < 0){
					fprintf(stderr,"Error al abrir el fichero %s\n",rutac);
				}

				if ((fd2=open(ruta2, O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR)) < 0){
					fprintf(stderr,"Error al crear el fichero %s\n",ruta2);
				}
				//printf("Argumento para copia_fichero: \n%s\n",arg2);	
				
				if (pthread_create(&hilo2, NULL, (void *)copia_fichero, (void *)nodo) < 0)
					fprintf(stderr,"Error al crear el hilo2");
			
				nodo->nombre = rutac;
				nodo->fuente_fd = fd1;
				nodo->destino_fd = fd2;
				nodo->tid = hilo2;
				nodo->next = NULL;
				
				//viewNodo(nodo);	
				inserta(nodo);
				nodo = (copyinfo_t *)malloc(sizeof(copyinfo_t));
				pthread_join(hilo2,NULL);
				break;
			case S_IFDIR:
				//Tendremos q crear un hilo para q se ejecute copy_directorio
			        arg3=(char *)malloc((strlen(rutac)*2) + (strlen(ruta2)*2) + 6);	
			  	strcpy(arg3,rutac);
				strcat(arg3," ");
				strcat(arg3,ruta2);
				printf("Argumento 3 si es dir: %s\n",arg3);

				if ((pthread_create(&hilo3, NULL, copy_directorio, (void *)arg3)) < 0)
					fprintf(stderr,"Error al crear el hilo en %s\n",arg3);

				pthread_join(hilo3,NULL); //No se si seria asi
				break;
			case S_IFSOCK:
				printf("socket no copiado\n");
				break;
			case S_IFBLK:
				printf("dispositivo de bloques no copiado\n");
				break;
			case S_IFCHR:
				printf("dispositivo de caracteres no copiado\n");
				break;
			case S_IFIFO:
				printf("tuberia no copiada\n");
				break;
			default: printf("Tipo desconocido"); break;
		}
	}
	aux = primero(); 
	printf("while terminado\n");	
	while (aux != NULL){
		pthread_join(aux->tid,NULL);
		printf("Hilo numero %d comienza a copiar archivo %s\n",aux->tid,aux->nombre);
		close(aux->fuente_fd);
		close(aux->destino_fd);
		aux = aux->next;
	}
	printf("while 2 terminado\n");
	closedir(dirp);
}

int main(int argc, char *argv[]){
	pthread_t hilo;		//Nuestro primer hilo!!
	char *arg;

	if (argc != 3){
		fprintf(stderr,"Uso %s dir_origen dir_destino\n",argv[0]);
		exit(1);
	}
	retocaDir(argv[1]);	
	retocaDir(argv[2]);
	//Directorios retocados!!
	arg=(char *)malloc((strlen(argv[1])*2) + (strlen(argv[2])*2) + 6);
	// Concatenamos los parametros
	strcat(arg,argv[1]);
	strcat(arg," ");	
	strcat(arg,argv[2]);

	if (pthread_create(&hilo, NULL, copy_directorio, (void *)arg) < 0){
		return 0;
	}
	pthread_join(hilo,NULL); //hilo se ejecutara hasta q termine
}
