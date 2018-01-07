//Vamos a implementar un algoritmo para encontrar a la celebridad dentro
//de la matriz de conocidos.

#include<stdio.h>

//Definimos una constante!!
#define NUM 4

/*Enunciado:
 *Tenemos una entrada de datos que será una matriz, en la que el elemento [i][j]
 *será 1 si la persona i conoce a la persona j y cero en caso contrario.
 *La celebridad será aquella persona que la conozca todo el mundo pero que no
 *conoce a nadie.
 * */

//CONCLUSIONES!!

/*No podrá existir otra persona que conozca a todo el mundo*/
/*En nuestro grupo de personas solamente tendremos una celebridad*/
main(){
	//Aquí estarán alamacenadas las personas que no puedan ser celebridades
	int eliminado[NUM];

	//Definicion estatica de una matriz de enteros
	//Tomamos el protocolo de C, 0 -> falso, 1 -> verdadero
	int mat[NUM][NUM]={
		{1,1,1,1},
		{1,1,1,1},
		{1,1,1,1},
		{0,0,0,1},};
	//NOTA: Tener mucho cuidado de no olvidarnos NINGUNA coma!!
	
	//Sabemos que la notacion es mat[i][j], i filas y j columnas
	int encontrado=0;
	int i=0;
	int j=0;
	int cont=0; //Contara las preguntas que hacemos
	//Para el ejercicio en cuestion es mejor recorrer al revés la matriz
	/*for(i=0; i<NUM; i++){
		for(j=0; j<NUM; j++){
			//printf("%d ",mat[i][j]); 
			//if (mat[i][j]) printf("%d conoce a %d\n", i, j);
			//else printf("%d NO conoce a %d\n", i, j);
			cont++;
			//Ponemos el elemento de la matriz y un espacio
		}
		printf("\n");
	}
	printf("\nContador: %d\n",cont);*/

	//Esta es la mejor manera para ver si una columna es todo 1's
	
	/*if ((i==0) && (j==0) && (cont == 0) && (encontrado == 0)) 
		printf("Inicializacion CORRECTA\n\n");
	else
		printf("Inicializacion INCORRECTA\n\n");
	*/
	/**
	 * Este es un primer acercamiento al algoritmo
	while((j<NUM) && (~encontrado)){
		//cont++;
		//Con la condicion mat[i][j] suponemos que ii tiene que ser 1
		while((i<NUM) && (mat[i][j])){
			if (i!=j) cont++;
			i++;
		}
		if (mat[i][j]) {
			encontrado=1;
			printf("Le celebridad es la persona %d\n", j+1);
		}
		j++;
		i=0;
	}
	printf("\nContador: %d\n",cont-1);
	*/

	/*
	 *Esta es la siguiente idea del algoritmo
	 * */
	//Miramos la primera columna para ver quien tiene un cero
	for(j=0; j<NUM; j++){
		for(i=0; i<NUM; i++){
			if(~eliminado[i]){
				if(mat[i][j]) eliminado[i]=1;
				cont++;
			}
		}
	}
	printf("\nContador: %d\n",cont-1);
	
}
