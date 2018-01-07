#include<stdio.h>
#define NUM 4
main(){

	//Definicion estatica de una matriz de enteros
	//Tomamos el protocolo de C, 0 -> falso, 1 -> verdadero
	int mc[NUM][NUM]={
		{1,0,0,0},
		{1,1,1,1},
		{1,1,1,1},
		{1,1,1,1},};
	//NOTA: Tener mucho cuidado de no olvidarnos NINGUNA coma!!
	
	//Sabemos que la notacion es mc[i][j], i filas y j columnas
	int fila=0; //indica si hemos encontrado una posible fila candidata!!

	//Ahora sabemos que la idea la tenemos bien pero únicamente falta que 
	//implementemos la idea de manera adecuada!!
	//int cont=0; //Contara las preguntas que hacemos
	
	/*INICIALIZACION*/
	int i=0; //Fila
	int j=1; //Columna
	/*La inicializacion se hace así para evitar leer el elemento 00*/
		
	/*Con esta parte del algoritmo eliminamos n-1 personas de ser 
	 * celebridad, solo nos queda comprobar la columna*/

	/*Tenemos que resolver el problema de que ninguna fila satisfaga la 
	 * condicion de celebridad!!*/
	//printf("El valor de la variable fila es: %d \n", fila);
	while ((i<NUM)&&(!fila)){
		while ((j<NUM)&&(!mc[i][j])){
			j++;
		}
		if ((j>=NUM) && (i!=NUM-1)) { fila=1; j=i; i=0;}
		else { i=j; j++; }
	}
	if (fila) {
		//printf("El valor de la variable fila es: %d \n", fila);
		while ((i<NUM)&&(mc[i][j])){
			i++;
		}
		if (i>=NUM) printf("La celebridad es la persona %d\n", j+1);
		else printf("No hay celebridad entre las %d personas\n",NUM);
	}
	else printf("No hay celebridad!!\n");
}
