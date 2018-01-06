#include<math.h>
#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include<dirent.h>

int no_mu;
int no_mu_sin;

int *vectorxf1;
int *vectorxf2;
int *vector;


//*************************************************************
//
//
//		LEER FICHERO Y ORDENARLO
//
//
//**************************************************************
int leer_de_fichero(void){

	FILE *fp;
	int i;
	int cont,n;
	int j,aux,aux2;
        cont=0;
       
	if ((fp=fopen("./muestras.m","r"))<0){
		fprintf(stderr,"error al cargar fichero --> ");abort();
       	}	
	     
	//HALLAMOS NUMERO DE NUMERACOS //print 1
	      i=getc(fp);
              while(i!=EOF){
		   if (i!='\n'){
		   i-=48;
         	   cont++;
		   }
              i=getc(fp);
              }
	      
	fclose(fp);
	      
	vectorxf1=(int *)calloc(sizeof(int),cont);
	vectorxf2=(int *)calloc(sizeof(int),cont);
	vector=(int *)calloc(sizeof(int),cont);
       	
	if ((fp=fopen("./muestras.m","r"))<0){
		fprintf(stderr,"error al cargar fichero --> ");abort();
	}	
	//INSERTAMOS EN EL VECTOR
	n=0;
	i=getc(fp);
		while(i!=EOF){
		   if (i!='\n'){
		   i-=48;
		   vector[n]=i;
		   n++;
		   }
              i=getc(fp);
              }
	      
	if (n<=0){
               fprintf(stderr,"archivo muestras.m vacio --> ");abort();
	}	
       		fclose(fp);

		//BURBU
		
  for(i=0;i<cont-1;){
	j=i+1;
	if(vector[i]>vector[j]){
		aux=vector[j];
		vector[j]=vector[i];
		vector[i]=aux;
		i=0;
	}else{
		i++;
	}
  }
  
			//print 1 
  		
/*printf("\nVector Inicial Ordenado con el interesante metodo de la burbu\n");

	for(i=0;i<cont;i++){
		printf("posicion %i %i \n",i,vector[i]);
	}*/
  			return cont;
}

/////////////////////////////////////////////////////////////////////
//
//	FRECUENCIA ABSOLUTA  (RECOGE N//DEVUELVE n)
//
//////////////////////////////////////////////////////////////////////

int frecuencia_absoluta(int n){
int i,k;
vectorxf1[0]=vector[0];
vectorxf2[0]=vectorxf2[0]+1;
k=0;

for(i=1;i<n;i++){
	if(vectorxf1[k]!=vector[i]){
		k++;
		vectorxf1[k]=vector[i];
		vectorxf2[k]=vectorxf2[k]+1; 
	}else{
		vectorxf2[k]=vectorxf2[k]+1;
	}
}

	vectorxf1 = (int *)realloc(vectorxf1,sizeof(int)*(k+1));
	vectorxf2 = (int *)realloc(vectorxf2,sizeof(int)*(k+1));

	//reasignacion de memoria
			//print 1

printf("\n ...FRECUENCIA ABSOLUTA...\n");
for(i=0;i<k+1;i++){
printf("	muestra %i: f absoluta %i \n",vectorxf1[i],vectorxf2[i]);
}
		return k+1;

}

/////////////////////////////////////////////////////////////////////
//
//	FRECUENCIA RELATIVA (RECOGE N y K)
//
//////////////////////////////////////////////////////////////////////

frecuencia_relativa(int n,int k){
int i;
float fr;	
printf("\v ...FRECUENCIA RELATIVA...\n");
	for(i=0;i<k;i++){
		fr=(float)vectorxf2[i]/n;
		printf("	muestra %i: f relativa %f\n",vectorxf1[i],fr);
	}
}

/////////////////////////////////////////////////////////////////////
//
//	MEDIA ARITMETICA (RECOGE N y K//DEVUELVE media)
//
//////////////////////////////////////////////////////////////////////


float media_aritmetica(int n, int k){
int i;
float fr;
float media;
media=0.0;
fr=0.0;
printf("\v ...MEDIA ARITMETICA...\n");
	for(i=0;i<k;i++){
		fr=(float)vectorxf2[i]/n;//nuse no sale de otra forma
		fr=(float)fr*vectorxf1[i];
		media=(float)media + fr;
//		printf("	muestra %i: medieta  %f\n",vectorxf1[i],fr);
	}
		printf("     media aritmetica %f\n\n",media);
			return media;
}
/////////////////////////////////////////////////////////////////////
//
//	MEDIA ARMONICA (RECOGE N y K)
//
//////////////////////////////////////////////////////////////////////


media_armonica(int n, int k){
int i,bool;
float fr,media;
media = 0.0;
printf("\v ...MEDIA ARMONICA...\n");
	for(i=0;i<k;i++){
		fr=(float)vectorxf1[i];
		if (fr==0)
		{
		  bool=1;
		}
		
		fr=(float)fr*vectorxf2[i];
		media=(float)media + fr;
//		printf("	muestra %i: medieta  %f\n",vectorxf1[i],fr);
	}
	media = (float)media / n;
	if (bool!=1)
	{
	printf("     media armonica %f\n\n",media);
	}else{
	printf("     media armonica infinita (Valor de Xi=0)\n\n");
	}
}
/////////////////////////////////////////////////////////////////////
//
//	DESVIACION TIPICA (RECOGE N y K y media//DEVUELVE desv tipica)
//
//////////////////////////////////////////////////////////////////////

float desviacion_tipica(int n, int k, float media){
int i;
float fr,desviacion;
fr = 0.0;
desviacion = 0.0;
printf("\v ...DESVIACIONN TIPICA...\n");
for(i=0;i<k;i++){
	fr = (float)vectorxf1[i]-media;		
	fr = (float)fr*fr;
	fr = (float)fr*vectorxf2[i];
	desviacion = (float)desviacion+fr;
//	printf("	muestra %i: desviacioncita %f\n",vectorxf1[i],fr);
}
		desviacion = (float) sqrt(desviacion);
		desviacion = (float) desviacion/ n;
		printf("    desviacion tipica %f\n\n",desviacion);

		return desviacion;
}
/////////////////////////////////////////////////////////////////////
//
//	DESVIACION MEDIA (RECOGE N y K y media)
//
//////////////////////////////////////////////////////////////////////


desviacion_media(int n, int k, float media){
int i;
float fr,desviacion;
fr = 0.0;
desviacion = 0.0;
printf("\v ...DESVIACIONN MEDIA...\n");
for(i=0;i<k;i++){
	fr = (float)vectorxf1[i]-media;	
	if (fr<0){
		fr = (float)fr*(-1);
	}
	fr = (float)fr*vectorxf2[i];		
	desviacion = (float)desviacion+fr;
//	printf("	muestra %i: desviacioncita %f\n",vectorxf1[i],fr);
}
	desviacion = (float)desviacion / n;
	printf("    desviacion media %f\n\n",desviacion);
}
/////////////////////////////////////////////////////////////////////
//
//	COEFICIENTE DE VARRIACION (RECOGE desv tipica y media)
//
//////////////////////////////////////////////////////////////////////


coeficiente_variacion(float S, float media ){
double fr,variacion;

printf("\v ...COEFICIENTE DE VARIACION...\n");
variacion = (float)S/media;
		printf("    coeficiente de variacion %f\n\n",variacion);

}
/////////////////////////////////////////////////////////////////////
//
//	COEFICIENTE DE ASIMETRIA (RECOGE N y K)
//
//////////////////////////////////////////////////////////////////////


coeficiente_asimetria(int n, float S, float media){
int a,b;
float mediana,f;
printf("\v ...COEFICIENTE DE ASIMETRIA...\n");
	if (n%2==0){//par
		a=n/2-1;
		b=n/2;
		mediana = (float) vector[b]+vector[a];
		mediana = (float) mediana/2;
	}else{		
		a=n/2-1;
		mediana = (float) vector[a];
	}

	
	f = (float) media - mediana;
	f = (float) f/ S;
	printf("   coeficiente de asimetria  %f\n\n",f);
	

}
	
/////////////////////////////////////////////////////////////////////
//
//	COEFICIENTE DE APUNTAMIENTO (RECOGE N y K y MEDIA y desv tipica)
//
//////////////////////////////////////////////////////////////////////

coeficiente_apuntamiento(int n, int k, float media, float S){
int i;
float parentesis,fr,fr2,apuntamiento;
fr = 0.0;
fr2 = 0.0;
parentesis = 0.0;
apuntamiento = 0.0;
printf("\v ...COEFICIENTE DE APUNTAMIENTO...\n");
for(i=0;i<k;i++){
	fr = (float) vectorxf1[i]- media;		
	fr = (float) fr*vectorxf2[i];		
	fr2 = (float) fr2 + fr;
//	printf("	muestra %i: apuntamiento %f\n",vectorxf1[i],fr);
}
parentesis = (float) fr2 / n;
S = (float)S*S;
S = (float)S*S;
fr2 = (float)1/S;
apuntamiento = (float) parentesis*fr2;
		printf("   coeficiente de apuntamiento  %f\n\n",apuntamiento);
		

}


////////////////////////////////////////////////////////////////////////
//
//     MAIN
//
/////////////////////////////////////////////////////////////////////////


main(void){
int n,k;
float S,media;
n=leer_de_fichero();
media = 0.0;
//OPERACIONES ESTADISTICAS

k=frecuencia_absoluta(n);
frecuencia_relativa(n,k);
media = media_aritmetica(n,k);
media_armonica(n,k);
S = desviacion_tipica(n,k,media);
desviacion_media(n,k,media);
coeficiente_variacion(S,media);
coeficiente_asimetria(n,S,media);
coeficiente_apuntamiento(n,k,S,media);
printf("\n\n");

//	PARA COMPILAR //	GCC PRACTICA4.C -O P4 -LM	//	PA QUE COJA LA LIB MATH

}
