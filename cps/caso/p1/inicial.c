#include <stdio.h>
#define MINIMO 1L
#define MAXIMO 100000L

long cuenta_primos(long min, long max);
long encuentra_primos(long min, long max, long *vector);
int esprimo(long n);

/*Escribe la lista de numeros primos entre 1 y 1000000*/
main(){
	long i, cuantos, primos[1000];
	printf("El numero de primos es: %ld\n", cuenta_primos(MINIMO, MAXIMO));
	cuantos = encuentra_primos(MINIMO, MAXIMO, primos);
	for(i = 0; i < cuantos; i++){
		printf("%ld es primo\n", primos[i]);
	}
}

/*Encuentra los primos entre min y max, y los devolvera en un vector*/
long cuenta_primos(long min, long max){
	long i, contador = 0;
	for (i = min; i<=max; i++)
		if(esprimo(i))
			contador++;
	return contador;
}

/*Encuentra los primos entre min y max, y los devolvera en un vector*/
long encuentra_primos(long min, long max, long *vector){
	long i, contador = 0;
	for(i = min; i<=max; i++)
		vector[contador++] = i;
	return contador;
}

/*Devuelve TRUE si n es primo*/
int esprimo(long n){
	long i;
	for(i = 2; i*i <= n; i++){
		if ((n%i) == 0)
			return 0;
	}
	return 1;
}
