/*Ya funciona la funcion para copiar un solo archivo ahora habra 
 q aplicarla al programa principal*/
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <pthread.h>
#include <errno.h>


//Hemos definido esta constante
#define TAMANNOBUFFER	100

void *copia_fichero(void *arg){
	int fd1,fd2;
	int bytes_leidos=0;
	int bytes_escritos=0;
	int *bytes_copiados;
	char *file1,*file2;
	char buffer[TAMANNOBUFFER];
	char *buffer_p;
	
	file2=strchr(arg,' ');
	*file2='\0';
	file1=arg;
	file2++;

	printf("Fichero origen: %s\n",file1);
	printf("Fichero destino: %s\n",file2);

	if ((bytes_copiados = (int *)malloc(sizeof(int))) == NULL)
		pthread_exit(NULL);
	
	if ((fd1=open(file1, O_RDONLY)) < 0){
		fprintf(stderr,"Error al abrir el fichero %s\n",file1);
	}

	if ((fd2=open(file2, O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR)) < 0){
		fprintf(stderr,"Error al crear el fichero %s\n",file2);
	}

	//Archivos abiertos!!
	
	for( ; ; ){
		bytes_leidos = read(fd1, buffer, TAMANNOBUFFER);
		
		//printf("Los bytes leidos son: %d\n",bytes_leidos);
		
		if ((bytes_leidos == 0) || ((bytes_leidos < 0) && (errno != EINTR)))
				break;
		
		
		else if ((bytes_leidos == 0) && (errno == EINTR)){
			continue;
		}

		buffer_p = buffer;	//Actua como indice para el vector
		
		while (bytes_leidos > 0){
			bytes_escritos = write(fd2, buffer_p, bytes_leidos);
			//printf("bytes escritos: %d\n",bytes_escritos);
			if ((bytes_escritos < 0) && (errno != EINTR))
				break;
			else if (bytes_escritos < 0)
				continue;
			*bytes_copiados += bytes_escritos;
			bytes_leidos -= bytes_escritos;
			//printf("bytes leidos: %d\n",bytes_leidos);
			buffer_p += bytes_escritos;

		}
		if (bytes_escritos == -1)
			break;
	}
	close(fd1);
	close(fd2);
	pthread_exit(bytes_copiados);
}

int main(int argc,char *argv[]){
	pthread_t hilo;
	char *argumento;
	
	if (argc != 3){
		fprintf(stderr,"Uso %s fichero1 fichero2\n",argv[0]);
	}	

	argumento=(char *)malloc((strlen(argv[1])*2) + (strlen(argv[2])*2) + 4);

	strcat(argumento,argv[1]);
	strcat(argumento," ");
	strcat(argumento,argv[2]);

	//printf("Argumento pasado: %s\n",argumento);
	
	if (pthread_create(&hilo, NULL, copia_fichero, (void *)argumento) != 0){
		fprintf(stderr,"Error al crear el hilo, fichero no copiado");
		
	}

	pthread_join(hilo,NULL);
	return 0;
}
