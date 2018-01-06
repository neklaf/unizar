/*Es una prueba para ver lo q devuelve la funcion strlen(char *)
Aunque todavia sigo sin entender pq no funcionaba el sizeof*/
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]){
	int i;
	if(argc != 2){
		fprintf(stderr,"Uso %s cadena1\n",argv[0]);
		exit(1);
	}
	// i tendra el nº de caracteres q tenga la cadena sin contar el \0 
	i=strlen(argv[1]);
	printf("La longitud del parametro es: %d\n",i);
	printf("El tamaño q ocupa arg[1] es: %d\n",sizeof(argv[1]));
	return 0;
}
