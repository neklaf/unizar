/*Vamos a realizar una prueba para el manejo de listas de estructuras 
en C*/

#include <stdio.h>
#include <pthread.h>
#include <string.h>
#include <stdlib.h>

/*void viewNodo(struct copy_struct n);
void viewLista(struct copy_struct *p);
void inserta(copy_struct *nodo);*/

/*
NOTA:
Sintaxis de la instruccion 'typedef':
	typedef tipo nombre_del_tipo;

Para poder declarar variables de la siguiente manera:
	nombre_del_tipo 	nombre_de_variable;
Al igual q se hace en los tipos basicos.
Un ejemplo real es por ejemplo:
	typedef struct copy_struct{
		char *nombre;
		int fuente_fd;
		int destino_fd;
		pthread_t tid;
		struct copy_struct *next;
	}copyinfo_t;

Por lo q ahora podremos definir las variables asi:
	copyinfo_t	nodo;
*/

//Poner esto hemos declarado el tipo como copyinfo_t
typedef struct copy_struct{
	char *nombre;
	int fuente_fd;
	int destino_fd;
	pthread_t tid;
	struct copy_struct *next;
}copyinfo_t;

copyinfo_t *lista;	//Variable estatica (o global) q hara de ancla para la lista

int esIgual(copyinfo_t *n1,copyinfo_t *n2){
	//Devolveremos un 0 si no es igual y un 1 si es igual
	if (((strcmp(n1->nombre,n2->nombre) == 0)) 
		&& (n1->fuente_fd == n2->fuente_fd) 
		&& (n1->destino_fd == n2->destino_fd) 
		&& (n1->tid == n2->tid)){
		return 0;
	}else{
		return 1;
	}	
}

//PROBADO!!
void viewNodo(copyinfo_t *n){
	printf("Nombre: %s\n",n->nombre);
	printf("Descriptor fuente: %d\n",n->fuente_fd);
	printf("Descriptor destino: %d\n",n->destino_fd);
	printf("Hilo: %d\n",n->tid);
	printf("********************\n");
}

//Comprobar si funciona!!
void viewLista(){
	copyinfo_t *aux = lista; //p apuntara al primer elemento de la lista
	printf("Listando...\n");
	while (aux != NULL){
		viewNodo(aux);
		aux = aux->next;
	}
}

/*Es mejor pasarle como parametro un puntero ya q ocupa menos 
y si le pasaramos un copyinfo_t probablemente se haria una copia*/
void borraNodo(copyinfo_t *nodo){
	//Tenemos q decidir q campo/s utilizaremos para identificar el nodo
	copyinfo_t *aux;
	//Se puede optimizar el codigo del eliminar!!
	if (esIgual(lista,nodo) == 0){
		lista = lista->next;
		printf("Primer nodo eliminado\n");
	}
	else{
		aux = lista;
		while ((aux->next != NULL) && (esIgual(aux->next,nodo) != 0)){
			aux = aux->next;
		}
		if (aux->next == NULL){
			printf("Nodo NO borrado.\n");
		}else{
			//Eliminar
			aux->next = aux->next->next;
			printf("Nodo eliminado\n");
		}
	}
}
//Probado
void inserta(copyinfo_t *nodo){
	//Se puede implementar de una manera mas eficiente
	//sacando las partes iguales fuera del if
	nodo->next = lista;
	lista = nodo;
	printf("Nodo insertado\n");
}
//Hay q inicializar todos los punteros Q HAGAN FALTA!!!
/*Faltan por probar algunos casos del borraNodo*/
int main(int argc,char *argv[]){
	int i;
	copyinfo_t *n,*n2;
	for (i=0; i < 4; i++){
		//n=(struct copy_struct *)malloc(sizeof(struct copy_struct));
		//Creando nodo para la insercion
		n=(copyinfo_t *)malloc(sizeof(copyinfo_t));
		n->nombre=(char *)malloc(20);
		sprintf(n->nombre,"aitor%d",i);
		n->fuente_fd = 55;
		n->destino_fd = 55;
		n->tid = 100; //??
		n ->next = NULL;
		inserta(n);
	}	
	n2=(copyinfo_t *)malloc(sizeof(copyinfo_t));
	n2->nombre = "aitor10";
	n2->fuente_fd = 55;
	n2->destino_fd = 55;
	n2->tid = 100;
	n2->next = NULL;
	borraNodo(n2);
	viewLista();
	return 0;
}
