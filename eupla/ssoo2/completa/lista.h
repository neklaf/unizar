/*Vamos a realizar una prueba para el manejo de listas de estructuras 
en C*/

#ifndef LISTA_H
#define LISTA_H 

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

copyinfo_t *primero();

int esIgual(copyinfo_t *n1,copyinfo_t *n2);

//PROBADO!!
void viewNodo(copyinfo_t *n);

//Comprobar si funciona!!
void viewLista();
/*Es mejor pasarle como parametro un puntero ya q ocupa menos 
y si le pasaramos un copyinfo_t probablemente se haria una copia*/
void borraNodo(copyinfo_t *nodo);

void inserta(copyinfo_t *nodo);

#endif
