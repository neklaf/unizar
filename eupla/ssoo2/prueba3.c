/*Probar a capturar el EAGAIN como error del pthread_create*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

void *tarea2(void *arg){
	char *m1=(char *)malloc((strlen((char *)arg))*2);
	int i=0;
	strcpy(m1,(char *)arg);
	printf("Ejecutando el segundo hilo...\n");
	while (i<5){
		printf("Mensaje_%d: %s\n",i,m1);
		i++;
	}
	printf("\n");
	pthread_exit(NULL);	//Para la finalizacion explicita del hilo
}

void *tarea1(void *arg){
	pthread_t hilo2;	
	char *m1=(char *)malloc(sizeof(arg));
	char *m2=(char *)malloc(sizeof(arg));
	int i=0;

	m2=strchr(arg,' ');
	*m2='\0';	//!!
	m1=arg;
	m2=m2++;	

	//Creacion del hilo secundario
	if(pthread_create(&hilo2,NULL,tarea2,(void *)m2) < 0){
		fprintf(stderr,"Error al crear el segundo hilo");
	}

	while (i<5){
		printf("Mensaje_%d: %s\n",i,m1);
		i++;
	}
	printf("\n");
	pthread_join(hilo2,NULL);
}

int main(int argc, char *argv[]){
	pthread_t hilo,hilo2;
	char *argumento;	

	if (argc != 3){
		fprintf(stderr,"Uso %s mensaje1 mensaje2\n",argv[0]);
	}
	
	argumento=(char *)malloc((strlen(argv[1])*2) + (strlen(argv[2])*2) + 6);
	strcat(argumento,argv[1]);
	strcat(argumento," ");
	strcat(argumento,argv[2]);

	printf("Ejecutando primer hilo... \n");
	if(pthread_create(&hilo,NULL,tarea1,(void *)argumento) < 0){
		fprintf(stderr,"Error al crear el primer hilo");
	}	
	pthread_join(hilo,NULL);	

	//printf("Ejecutando segundo hilo... \n");
	
	return 0;
}
