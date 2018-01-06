
/*Hay q hacerlo con valores conocidos para comprobarlo!!*/

/*Contemplar la posibilidad de q la muestra sea vacia
 * de q la muestra unicamente tenga un valor....
 * Y demas situaciones q puedan ser problematicas*/


#include <stdio.h>
#include <math.h>
#include <unistd.h>
#include <stdlib.h>

/*0 es falso y verdadero el resto de los valores*/

struct lista{
	int val,Fi;
	struct lista *sig;

}*l,*lo;

void error(char *s){
	printf("%s",s);
	exit(1);
}

void add(struct lista *nodo){
	struct lista *aux;
	if (l==NULL){
		l=nodo;
		//printf("Insertado primero\n");
	}else{
		aux=l;
		while(aux->sig!=NULL){
			aux=aux->sig;
		}
		aux->sig=nodo;
		//printf("Insertado\n");
	}
}


void addOrd(struct lista *nodo){
	struct lista *aux;
	if (lo==NULL){
		lo=nodo;
		//printf("Insertado primero, lista vacia\n");
	}else{
		aux=lo;

		if(aux->val>nodo->val){
			nodo->sig=aux;
			aux=nodo;
			printf("Primero insertado, lista vacia\n");
		}
		while((aux->sig!=NULL)&&(nodo->val>=aux->sig->val)){
			aux=aux->sig;
		}
		nodo->sig=aux->sig;
		aux->sig=nodo;
		//printf("Insertado ordenadamente\n");
	}
}

void lista (struct lista *list){
	struct lista *aux;

	aux=list;

	while(aux!=NULL){
		printf("Numero nodo: %i\n",aux->val);
		printf("Frecuencia absoluta: %i\n",aux->Fi);
		printf("****************\n");
		aux=aux->sig;
	}
}

struct lista *DevuelveDato (int x){
	struct lista *aux;

	if (l!=NULL){	
		aux=l;
		while((aux!=NULL)&&(aux->val!=x)) {
			aux=aux->sig;
		}

		if(aux!=NULL){
			return aux;
		}else{
			return NULL;
		}
	}else{
		return NULL;
	}
}

int EstaRepetido(struct lista *lis,int n){
	struct lista *aux;
	if (lis!=NULL){	
		aux=lis;
		while((aux!=NULL)&&(aux->val!=n)) {
			aux=aux->sig;
		}

		if(aux!=NULL){
			aux->Fi++;
			return 1;
		}else{
			return 0;
		}
	}else{
		//printf("lista vacia!!\n");
		return 0;
	}
}

//Leemos el fichero completo
void Read_Ln (void) {
	FILE *fich;
	int c;
	struct lista *aux;
	if((fich=fopen("./muestras.dat","r"))<0)
		error("No se ha podido abrir el fichero\n");
	
	c=getc(fich);
	while (c!=EOF){
		if(c!='\n') {
			c-=48;
			//printf("hola!!\n");
			//OPTIMIZAR!!!
			if(!EstaRepetido(l,c)){
	 		    	aux=(struct lista*)malloc(sizeof(struct lista));
				aux->val=c;
				aux->Fi=1;
				add(aux);
			}
		}
		c=getc(fich);
	}
	fclose(fich);
}

//Inicializar todos las variables
int n (struct lista *lis){
	struct lista *aux;
	int a=0;
	if(lis!=NULL){
		aux=lis;
		while(aux!=NULL){
			a++;
			aux=aux->sig;
		}
	}else{
		printf("La lista pasada esta vacia\n");
	}
	return a;
}

int n2 (void){
	struct lista *aux;
	int a=0;
	
	if(l!=NULL){
		aux=l;
		while(aux!=NULL){
			a+=aux->Fi;
			aux=aux->sig;
		}
	}else{
		printf("La lista de desordenados esta vacia\n");
	}
	return a;
}

float fi(int x){
	struct lista *aux;
	float fi=0.0;
	aux=DevuelveDato(x);
	if (aux !=NULL)	fi=(float)aux->Fi/n2();
	return fi;
}

double MArit (void){
	struct lista *aux;
	int aux2=0;
	double ma=0.0;
	if(l!=NULL){
		aux=l;
	
		while(aux!=NULL){
			aux2+=aux->val * aux->Fi;
			aux=aux->sig;
		}
	}else{
		printf("LISTA VACIA!!!\n");
	}
	ma=(double)aux2/n2();
	return ma;
}

double MArm (void){
	struct lista *aux;
	double mar=0.0, aux2=0.0, aux3=0.0;
	aux=l;

	while(aux!=NULL){
		if(!aux->val) printf("Un valor de la muestra es cero\n");
		aux2+=((double) 1/aux->val) * aux->Fi;
		aux=aux->sig;
	}

	mar=(double)aux2/n2();
	return mar;
}

//PROBAR CON UNA MUESTRA Q SEPAMOS LOS VALORES!!
double Dt (void){
	struct lista *aux;
	double dt=0.0,ma=0.0,aux2=0.0,aux3=0.0;
	
	ma=MArit();
	printf("media aritmetica %f\n",ma);
	aux=l;

	while(aux!=NULL){
		aux2=aux->val - ma;
		aux2=aux2*aux2;
		//aux2=pow(aux->val - ma,2);
		aux2=aux->Fi * aux2;
		aux3+=aux2;
		aux=aux->sig;
	}

	aux3=(double)aux3/n2();
	aux3=sqrt(aux3);
	return aux2;
}


//PROBAR CON UNA MUESTRA Q SEPAMOS LOS VALORES!!
double Cv (void){
	return ((double)Dt()/MArit());
}

//PROBAR
double Dm (void){
	struct lista *aux;
	double dt=0.0,ma=0.0,aux2=0.0;
	
	ma=MArit();
	aux=l;

	while(aux!=NULL){
		aux2=fabs(aux->val - ma);
		aux2=aux->Fi * aux2;
		aux2+=aux2;
		aux=aux->sig;
	}

	aux2=(double)aux2/n2();
	return aux2;
}

double Mediana (){
	struct lista *aux;
	int cont=0,cont2=0;
	double res=0.0;
	if(lo!=NULL){
		cont=n2();
		//printf("Numero de elementos de lo: %i\n",cont);
		cont2=cont/2;
		aux=lo;
		while((aux!=NULL)&&(cont2-1>0)){
			aux=aux->sig;
			cont2--;
		}
		if((cont%2==0)&&(aux!=NULL)){
			res=(double)(aux->val + aux->sig->val)/2;
			return res;
		}else{
			return (double)aux->sig->val;
		}
	}
	
}


//PROBAR
double Ca (void){
	FILE *fich;
	int c;
	struct lista *aux;
	double ma=0.0;
	//ma=MArit();
	if((fich=fopen("./muestras.dat","r"))<0)
		error("No se ha podido abrir el fichero\n");
	
	c=getc(fich);
	//lo=(struct lista *)malloc(sizeof(struct lista));
	while (c!=EOF){
		if(c!='\n') {
			c-=48;
	 		aux=(struct lista*)malloc(sizeof(struct lista));
			aux->val=c;
			aux->Fi=0;
			addOrd(aux);
		}
		c=getc(fich);
	}
	fclose(fich);

	//lista(lo);
	return ((MArit()-Mediana())/Dt());
}

//PROBAR
double Cap(){
	struct lista *aux;
	double dt=0.0,ma=0.0,aux2=0.0,inv=0.0;
	
	ma=MArit();
	aux=l;

	while(aux!=NULL){
		aux2=((double)aux->val - ma);
		aux2=aux->Fi * aux2;
		aux2+=aux2;
		aux=aux->sig;
	}

	aux2=(double)aux2/n2();
	inv=((double)1/pow(Dt(),4));
	aux2=inv * aux2;
	return aux2;
}

/*void comprueba (void){
	if (!n(l)-n2()){
		printf("Los indices son iguales\n");
	}else{
		printf("ERROR\n");
	}
	
}*/


Fi(){
	struct lista *aux;
	aux=l;

	while(aux!=NULL){
		printf("Xi es: %i   	Y la Fi: %i\n",aux->val,aux->Fi);
		aux=aux->sig;
	}
}

int main (int argc, char *argv[]) {
	int a=2;
	Read_Ln();
	/*lista(l);
	printf("Frecuencia relativa de %i: %f\n",a,fi(a));
	printf("Media aritmetica: %f\n",MArit());
	printf("Media armonica %f\n",MArm());*/
	//printf("Desviacion tipica: %f\n",Dt());
	/*printf("Coeficiente de asimetria: %f\n",Ca());
	printf("Coeficiente de variacion: %f\n",Cv());
	printf("Desviacion media: %f\n",Dm());
	printf("Coeficiente de apuntamiento: %f\n",Cap());
	printf("Mediana: %f\n",Mediana());*/
	Fi();
	free(l);
	
	return 0;
}
