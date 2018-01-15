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
#define NTRANS 12
#define NLUGARES 15 

void Inicializacion (void) ;

void empty(void);

void Izquierda_1 (void);
void Izquierda_2 (void);
void Izquierda_3 (void);
void Izquierda_12(void);


void Derecha_1 (void);
void Derecha_2 (void);
void Derecha_3 (void);
void Derecha_123(void);

void Parado_1 (void);
void Parado_2 (void);
void Parado_3 (void);

void Put_Time(void);
char  time_over(unsigned char);

char CondicionTrue (unsigned char entrada);
char CondicionA (unsigned char entrada);
char CondicionB (unsigned char entrada);
char CondicionC (unsigned char entrada);
char CondicionD (unsigned char entrada);
char CondicionE (unsigned char entrada);
char CondicionF (unsigned char entrada);
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
  

   static char T9_Lugares_Entrada[] = { 11, 0 }; // Tercer carro
   static char T9_Lugares_Salida[]  = { 12, 0 };

   static char T10_Lugares_Entrada[] = { 12, 0 };
   static char T10_Lugares_Salida[]  = { 13, 0 };
  
   static char T3_Lugares_Entrada[] = { 3, 8, 13, 0};//Sincronizacion a la izquierda
   static char T3_Lugares_Salida[] =  { 4, 9, 14,  0};

   static char T4_Lugares_Entrada[] = { 4, 0};//Primer carro
   static char T4_Lugares_Salida[] =  { 5, 0};

   static char T7_Lugares_Entrada[] = { 9, 0};//Segundo carro
   static char T7_Lugares_Salida[] =  { 10, 0};
  
     static  char T8_Lugares_Entrada[] = { 5, 10, 0}; //Sincronizacion a la derecha
   static char T8_Lugares_Salida[] =  { 2, 7, 0};

   static char T11_Lugares_Entrada[] = { 14, 0}; // Tercer carro
   static char T11_Lugares_Salida[] = { 15, 0 };
  
   static char T12_Lugares_Entrada[] = { 15, 0};
   static char T12_Lugares_Salida[] = { 11, 0 };

  static T_TRAN Tran[NTRANS+1];
  static unsigned char Mc[NLUGARES+1] = {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0};
  static unsigned char Sig_Mc[NLUGARES+1] = {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0} ;
  
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
  Tran[3].Accion = Derecha_123;
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

  Tran[9].Lugares_Entrada = T9_Lugares_Entrada; // Carro 3
  Tran[9].Lugares_Salida = T9_Lugares_Salida;
  Tran[9].Accion = Izquierda_3;
  Tran[9].Condicion = CondicionTrue;

  Tran[10].Lugares_Entrada = T10_Lugares_Entrada;
  Tran[10].Lugares_Salida = T10_Lugares_Salida;
  Tran[10].Accion = Parado_3;
  Tran[10].Condicion = CondicionE;

  Tran[11].Lugares_Entrada = T11_Lugares_Entrada;
  Tran[11].Lugares_Salida = T11_Lugares_Salida;
  Tran[11].Accion = Put_Time;
  Tran[11].Condicion = CondicionF;

  Tran[12].Lugares_Entrada = T12_Lugares_Entrada;
  Tran[12].Lugares_Salida = T12_Lugares_Salida;
  Tran[12].Accion = empty;
  Tran[12].Condicion = time_over;
  
  Inicializacion ();
  
  while(1)
    Interpretar_RdP(Tran, NTRANS,Mc,Sig_Mc,NLUGARES,Leer_Entradas);

}

#define IZDA_1     0x01  /*0000_0001*/
#define DCHA_1     0x02  /*0000_0010*/
#define IZDA_2	   0x04  /* Es correcto ? */
#define DCHA_2     0x08  /* Es correcto ? */
#define IZDA_3	   0x10  /* Es correcto ? */
#define DCHA_3     0x20  /* Es correcto ? */
#define A          0x02  /*0000_0010*/
#define B          0x04  /*0000_0100*/
#define C	   0x08  /* Es correcto ? */
#define D	   0x10  /* Es correcto ? */
#define PULSADOR_M 0x20  /*0010_0000*/ 
#define E	   0x40  /* Correcto ? */
#define F	   0x80  /* Correcto ? */
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


#ifdef __AT_HOME__
void Set_Timer(int segundos){
	
	gettimeofday(&time, NULL);

	timer = time.tv_sec + segundos;
	
}


int Time_Out(void) {

	gettimeofday(&time, NULL);

	return  time.tv_sec > timer ;

}
#endif 

void empty(void){

}

void Put_Time(void ){


	Set_Timer(5);
	Parado_3();
}


char   time_over(unsigned char foo){

	return Time_Out();
}

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

void Izquierda_3(void)
{
#ifdef __AT_LAB__
  PTB &= ~(DCHA_3);
  PTB |= IZDA_3;
#else
printf("IZQUIERDA CARRO 3\n");
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

void Derecha_3(void)
{
#ifdef __AT_LAB__
  PTB &= ~(IZDA_3);
  PTB |= DCHA_3;
#else
printf("DERECHA CARRO 3\n");
#endif
}

void Derecha_123(void)
{
	Derecha_1();
	Derecha_2();
	Derecha_3();
	
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

void Parado_3 (void)
{
#ifdef __AT_LAB__
  PTB &= ~(IZDA_3);
  PTB &= ~(DCHA_3);
#else
  printf("PARADO 3\n");
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

char CondicionE(unsigned char entrada)
{
  return entrada & E;
}
char CondicionF(unsigned char entrada)
{
  return entrada & F;
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
	   case 'e':
		   *pEntrada = E;
		   printf("E\n");
		   break;
	   case 'f':
		   *pEntrada = F;
		   printf("F\n");
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

