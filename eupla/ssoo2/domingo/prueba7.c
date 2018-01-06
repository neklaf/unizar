#include <stdio.h>
#include <pthread.h>
#include "lista.h"

void *mensaje(void *arg){
	printf("El mensaje es: %s\n",(char *)arg);
	pthread_exit(NULL);
}

void *reproducir(void *arg){
	int i,j;
	char *m;
	pthread_t hilo2,hilo3;
	copyinfo_t *nodo,*aux;

	for(i=0; i<10; i++){
		m=(char *)malloc(10);
		sprintf(m,"hola %d",i);
		if(pthread_create(&hilo2, NULL, mensaje, (void *)m) < 0)
			fprintf(stderr,"Error al crear el hilo2 %d\n",hilo2);

		nodo=(copyinfo_t *)malloc(sizeof(copyinfo_t));
		nodo->tid = hilo2;
		nodo->fuente_fd = 0;
		nodo->destino_fd = 0;
		nodo->nombre = m;
		nodo->next = NULL; 
		inserta(nodo);
	}
	
	for(i=0; i<10;i++){
		printf("***************************");
		if(pthread_create(&hilo3, NULL, reproducir, NULL) < 0)
			fprintf(stderr,"Error al crear el hilo3\n");
		pthread_join(hilo3, NULL);
	}

	aux=primero();
	while (aux != NULL){
		pthread_join(nodo->tid,NULL);
		aux = aux->next;
	}
	
}

int main(int argc,char *argv[]){
	pthread_t hilo;
	
	if(pthread_create(&hilo, NULL, reproducir, (void *)argv[0]) < 0)
		fprintf(stderr, "Error al crear 'hilo'\n");	
	pthread_join(hilo,NULL);
}
