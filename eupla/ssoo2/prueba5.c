/*Esta prueba es para comprobar como maneja C los punteros 
me imagino q los pasara como parametros por referencia, pero 
tengo q comprobarlo*/

/*NOTA: Es para evitar la chapuza q hacia en el programa anterior*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*Comprobaremos q la cadena pasada tenga al final el carater '/'
y si no lo tuviera deberiamos introducirlo*/
void retocaDir(char *cad){
	char *aux;
	aux=strchr(cad,'\0');	//aux apuntara la final de la cadena
	aux=aux--;	//aux apuntara al ultimo caracter de la cadena cad
	if (*aux == '/'){
		*aux='\0';
	}
}

int main(int argc, char *argv[]){
	
	if (argc != 3){
		fprintf(stderr,"Uso %s directorio1 directorio2.\n",argv[0]);
		exit(1);
	}
	printf("Antes.....\n");
	printf("Primer parametros: %s\n",argv[1]);
	printf("Segundo parametros: %s\n",argv[2]);
	retocaDir(argv[1]);
	retocaDir(argv[2]);
	printf("Despues....\n");
	printf("Primer parametros: %s\n",argv[1]);
	printf("Segundo parametros: %s\n",argv[2]);
}
