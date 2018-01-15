/*******************************************************************************
*             Departamento de Informatica e Ingenieria de Sistemas             
*                          Universidad de Zaragoza                            
********************************************************************************
* Fichero     : carros1.c
********************************************************************************
* Proyecto    : Practicas Sistemas Empotrados / Sistemas Control con Micropro
* Programador : J.L. Villarroel & J.M Martinez Montiel
* Proposito   : Plantilla para la implementacion de un punto de una practica
* Lenguaje    : Ansi c                                                         
* Modulo      : RdP con unico carro                                                      
* Fecha       : Octubre 2002
* Historia    : 2 octubre creacion (JMMM) 
*             : 24 noviembre modificacion (JLV)
*             : 29 Diciembrei 2003 adaptacion por Javier Uruen Val:
*             :    Modificado para compilar sobre UNIX
*             :    simulando las entradas de pulsadores del PTB
*             :    por lectura de teclado. De esta manera testeamos
*             :    la implementación de la red de petri antes de 
*             :    llevarla al laboratorio.
*******************************************************************************/

/**************************************************
 Este bloque de defines e includes debe ser contenido
 por todos los programas principales
****************************************************/ 
#ifdef __AT_LAB__
#define __DECL__68HC08GP32_H__

#include <68hc08gp32.h>
#endif

#ifdef __AT_HOME__
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>
#include <asm/ioctls.h>
short int PTB;
struct timeval time;
long timer;
#endif
/*****************************************************
 Colocar aqui los include de los modulos creados para la
 practica, entre " " 
 *****************************************************/
 
#include "Rdp.h"
#define NTRANS 8
#define NLUGARES 10 

void Inicializacion (void) ;

void Izquierda_1 (void);
void Izquierda_2 (void);
void Izquierda_12(void);

void Derecha_1 (void);
void Derecha_2 (void);
void Derecha_12 (void);

void Parado_1 (void);
void Parado_2 (void);

char CondicionTrue (unsigned char entrada);
char CondicionA (unsigned char entrada);
char CondicionB (unsigned char entrada);
char CondicionC (unsigned char entrada);
char CondicionD (unsigned char entrada);
char CondicionM (unsigned char entrada);



void Leer_Entradas(unsigned char *pEntrada);

 
int  main(void){
  
  static char T1_Lugares_Entrada[] = { 1, 0}; // Primer Carro
  static char T1_Lugares_Salida[] =  { 2, 0};

  static char T2_Lugares_Entrada[] = { 2, 0};
  static char T2_Lugares_Salida[] =  { 3, 0};

    
  
  static char T5_Lugares_Entrada[] = { 6, 0}; // Segundo Carro
  static char T5_Lugares_Salida[] =  { 7, 0};
  
  static char T6_Lugares_Entrada[] = { 7, 0};
  static char T6_Lugares_Salida[] =  { 8, 0};
  
  static char T3_Lugares_Entrada[] = { 3, 8, 0};//Sincronizacion a la izquierda
  static char T3_Lugares_Salida[] =  { 4, 9, 0};

  static char T4_Lugares_Entrada[] = { 4, 0};//Primer carro
  static char T4_Lugares_Salida[] =  { 5, 0};

  static char T7_Lugares_Entrada[] = { 9, 0};//Segundo carro
  static char T7_Lugares_Salida[] =  { 10, 0};
  
  static char T8_Lugares_Entrada[] = { 5, 10, 0}; //Sincronizacion a la derecha
  static char T8_Lugares_Salida[] =  { 2, 7, 0};


  static T_TRAN Tran[NTRANS+1];
  static unsigned char Mc[NLUGARES+1] = {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0};
  static unsigned char Sig_Mc[NLUGARES+1] = {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0} ;
  
  Tran[1].Lugares_Entrada = T1_Lugares_Entrada; //Carro 1
  Tran[1].Lugares_Salida  = T1_Lugares_Salida;
  Tran[1].Accion = Izquierda_1;
  Tran[1].Condicion = CondicionTrue;

  
  Tran[2].Lugares_Entrada = T2_Lugares_Entrada;
  Tran[2].Lugares_Salida  = T2_Lugares_Salida;
  Tran[2].Accion = Parado_1;
  Tran[2].Condicion = CondicionA;

  Tran[3].Lugares_Entrada = T3_Lugares_Entrada; // Sincronizacion Izquierda
  Tran[3].Lugares_Salida  = T3_Lugares_Salida;
  Tran[3].Accion = Derecha_12;
  Tran[3].Condicion = CondicionM;
  
  Tran[5].Lugares_Entrada = T5_Lugares_Entrada; // Carro 2
  Tran[5].Lugares_Salida  = T5_Lugares_Salida;
  Tran[5].Accion = Izquierda_2;
  Tran[5].Condicion = CondicionTrue;

  
  Tran[6].Lugares_Entrada = T6_Lugares_Entrada;
  Tran[6].Lugares_Salida  = T6_Lugares_Salida;
  Tran[6].Accion = Parado_2;
  Tran[6].Condicion = CondicionC;
  

  Tran[4].Lugares_Entrada = T4_Lugares_Entrada; // Carro 1
  Tran[4].Lugares_Salida  = T4_Lugares_Salida;
  Tran[4].Accion = Parado_1;
  Tran[4].Condicion = CondicionB;

  
  Tran[7].Lugares_Entrada = T7_Lugares_Entrada; // Carro 2
  Tran[7].Lugares_Salida  = T7_Lugares_Salida;
  Tran[7].Accion = Parado_2;
  Tran[7].Condicion = CondicionD;

 
  Tran[8].Lugares_Entrada = T8_Lugares_Entrada; // Sincronizacion derecha
  Tran[8].Lugares_Salida  = T8_Lugares_Salida;
  Tran[8].Accion = Izquierda_12;
  Tran[8].Condicion = CondicionTrue;

  
  Inicializacion ();
  
  while(1)
    Interpretar_RdP(Tran, NTRANS,Mc,Sig_Mc,NLUGARES,Leer_Entradas);

}

#define IZDA_1     0x01  /*0000_0001*/
#define DCHA_1     0x02  /*0000_0010*/
#define IZDA_2	   0x04  /* Es correcto ? */
#define DCHA_2     0x08  /* Es correcto ? */
#define A          0x02  /*0000_0010*/
#define B          0x04  /*0000_0100*/
#define C	   0x08  /* Es correcto ? */
#define D	   0x10  /* Es correcto ? */
#define PULSADOR_M 0x20  /*0010_0000*/ 
#define RESET_PTA_NO_CONECTADAS 0x3E /*0011_1110*/

void Inicializacion (void) {
#ifdef __AT_LAB__
  PTB = 0x00 ;
  DDRB=0xFF;
  DDRA=0x00;
#else
struct termios oldT, newT; 
char c; ioctl(0,TCGETS,&oldT); 
/*
 * Básicamente lo que hacemos aquí
 * es poner la terminal en modo no
 * canónico y no bloqueante para
 * hacer read sobre stdin caracter
 * a carcter sin bloquear
 */
newT=oldT;
newT.c_lflag &= ~ECHO; 
newT.c_lflag &= ~ICANON; 
ioctl(0,TCSETS,&newT); 
fcntl(0, F_SETFL, O_NONBLOCK);
#endif  
}  


#ifdef __AT_LAB__
void Set_Timer(int segundos){
	
	gettimeofday(&time, NULL);

	timer = time.tv_sec + segundos;
	
}


int Time_Out(void) {

	gettimeofday(&time, NULL);

	return  time.tv_sec > timer ;

}
#endif 

void Izquierda_1(void)
{
#ifdef __AT_LAB__
  PTB &= ~(DCHA_1);
  PTB |= IZDA_1;
#else
printf("IZQUIERDA CARRO 1\n");
#endif
}

void Izquierda_2(void)
{
#ifdef __AT_LAB__
  PTB &= ~(DCHA_2);
  PTB |= IZDA_2;
#else
printf("IZQUIERDA CARRO 2\n");
#endif
}

void Izquierda_12(void)
{
 Izquierda_1();
 Izquierda_2();
}
char CondicionTrue(unsigned char entrada)
{
return 1;
}


void Derecha_1(void)
{
#ifdef __AT_LAB__
  PTB &= ~(IZDA_1);
  PTB |= DCHA_1;
#else
printf("DERECHA CARRO 1\n");
#endif
}

void Derecha_2(void)
{
#ifdef __AT_LAB__
  PTB &= ~(IZDA_2);
  PTB |= DCHA_2;
#else
printf("DERECHA CARRO 2\n");
#endif
}

void Derecha_12(void)
{
	Derecha_1();
	Derecha_2();
	
}
char Condicion2(unsigned char entrada)
{
  return entrada & B;
}

void Parado_1 (void)
{
#ifdef __AT_LAB__
  PTB &= ~(IZDA_1);
  PTB &= ~(DCHA_1);
#else
  printf("PARADO 1\n");
#endif
}

void Parado_2 (void)
{
#ifdef __AT_LAB__
  PTB &= ~(IZDA_2);
  PTB &= ~(DCHA_2);
#else
  printf("PARADO 2\n");
#endif
}

char CondicionA(unsigned char entrada)
{
  return entrada & A;
}

char CondicionB(unsigned char entrada)
{
  return entrada & B;
}

char CondicionC(unsigned char entrada)
{
  return entrada & C;
}
char CondicionD(unsigned char entrada)
{
  return entrada & D;
}

char CondicionM(unsigned char entrada)
{
  return entrada & PULSADOR_M;
}

void Leer_Entradas(unsigned char *pEntrada)
{  
#ifdef __AT_LAB_
   *pEntrada=PTA&RESET_PTA_NO_CONECTADAS;
#else
   char c;
   read(0, &c, 1);
   switch ( c ) {

	   case 'a':
		   *pEntrada = A;
		   printf("A\n");
		   break;
	   case 'b':
		   *pEntrada = B;
		   printf("B\n");
		   break;
	   case 'c':
		   *pEntrada = C;
		   printf("C\n");
		   break;
	   case 'd':
		   *pEntrada = D;
		   printf("D\n");
		   break;
           case 'm':
		   *pEntrada = PULSADOR_M;
		   printf("PULSADOR_M\n");
		   break;
	   default:
		   *pEntrada = 0;
   }
   
#endif

}

