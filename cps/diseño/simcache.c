#include <stdio.h>
#include <stdlib.h>
#include <string.h>

main(argc,argv)
int argc;
char *argv[];
{

   int size_cache=14, size_bloque=5, num_max_refer=10000;
   char politica='c';

   char linea[20],*otra;
   unsigned int direccion,dirAux,set;
   int  i, tipo_acceso,long_cache;
   int marca;

   for (i=1;i<argc;i++)
	switch (argv[i][1]){

		case 'U':
                 size_cache=atoi(argv[i]+2);
                 break;                        /*Guardamos size en bytes */
		case 'B':
                 size_bloque=atoi(argv[i]+2);
                 break;
		case 'w':
                 politica=*(argv[i]+2);
                 break;
		case 'z':
                 num_max_refer=atoi(argv[i]+2);
                 break;
		default :  printf("Parametros incorrectos\n");
                 exit(1);

	} /* del switch */
	
   long_cache=2<<(size_cache-size_bloque);
 
/*	Comprobando recepcion de parametros */	
		printf("U=%d, B=%d, W=%c, MAX=%d\n", 
			size_cache, size_bloque, politica, num_max_refer);
   
   otra=gets(linea);
   for (i=0;(i<num_max_refer)&&(otra!=NULL);i++){
	sscanf(linea,"%d%x",&tipo_acceso,&direccion);

/*	Comprobando lectura de traza */	
		printf("acc:%d, dir:%xi, ", tipo_acceso, direccion);
		dirAux=direccion;
		marca=dirAux>>size_cache;
		dirAux=direccion;
		set=dirAux<<(32-size_cache);
		set=set>>(32-(size_cache-size_bloque));
		printf("marca:%x ",marca);
		printf("set:%x\n ",set);

/* Ahora que tenemos la marca y el set de la direccion */
/* Tendremos que ir escribiendo y leyendo de cache anotando fallos y aciertos*/

	otra=gets(linea);
   }
 } 
