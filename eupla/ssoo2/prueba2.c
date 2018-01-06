/*Esta es una prueba para ver era capaz de ejecutar dos hilos a la vez,
ahora tendremos q reproducir las mismas condiciones q en el fuente de la
practica para intentar descubrir q es lo q pasa*/

/*Probar a capturar el EAGAIN como error del pthread_create*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
//#include <>

void *tarea1(void *arg){
	char *m1=(char *)malloc((strlen((char *)arg))*2);
	int i=0;
	strcpy(m1,(char *)arg);
	while (i<5){
		printf("Mensaje1: %s\n",m1);
		i++;
	}
	printf("\n");
}

void *tarea2(void *arg){
	char *m1=(char *)malloc((strlen((char *)arg))*2);
	int i=0;
	strcpy(m1,(char *)arg);
	while (i<5){
		printf("Mensaje2: %s\n",m1);
		i++;
	}
	printf("\n");
}

int main(int argc, char *argv[]){
	pthread_t hilo,hilo2;	

	if (argc != 3){
		fprintf(stderr,"Uso %s mensaje1 mensaje2\n",argv[0]);
	}
	printf("Ejecutando primer hilo... \n");
	if(pthread_create(&hilo,NULL,tarea1,(void *)argv[1]) < 0){
		fprintf(stderr,"Error al crear el primer hilo");
	}	
	pthread_join(hilo,NULL);	

	printf("Ejecutando segundo hilo... \n");
	
	if(pthread_create(&hilo2,NULL,tarea2,(void *)argv[2]) < 0){
		fprintf(stderr,"Error al crear el primer hilo");
	}	
	
	return 0;
}
