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
#endif
/*****************************************************
 Colocar aqui los include de los modulos creados para la
 practica, entre " " 
 *****************************************************/
 
#include "Rdp.h"
#define NTRANS 3
#define NLUGARES 3

void Inicializacion (void) ;

void Accion1 (void);
char Condicion1(unsigned char entrada);

void Accion2(void);
char Condicion2(unsigned char entrada);

void Accion3 (void);
char Condicion3(unsigned char entrada);

void Leer_Entradas(unsigned char *pEntrada);

 
int  main(void){
  
  static char T1_Lugares_Entrada[] ={1,0};
  static char T1_Lugares_Salida[]={2,0};

  static char T2_Lugares_Entrada[] ={2,0};
  static char T2_Lugares_Salida[]={3,0};

  static char T3_Lugares_Entrada[] ={3,0};
  static char T3_Lugares_Salida[]={1,0};

  static T_TRAN Tran[NTRANS+1];
  static unsigned char Mc[NLUGARES+1]={0,1,0,0};
  static unsigned char Sig_Mc[NLUGARES+1]={0,1,0,0};
  
  Tran[1].Lugares_Entrada = T1_Lugares_Entrada;
  Tran[1].Lugares_Salida  = T1_Lugares_Salida;
  Tran[1].Accion = Accion1;
  Tran[1].Condicion =Condicion1;

  
  Tran[2].Lugares_Entrada = T2_Lugares_Entrada;
  Tran[2].Lugares_Salida  = T2_Lugares_Salida;
  Tran[2].Accion = Accion2;
  Tran[2].Condicion =Condicion2;

  Tran[3].Lugares_Entrada = T3_Lugares_Entrada;
  Tran[3].Lugares_Salida  = T3_Lugares_Salida;
  Tran[3].Accion = Accion3;
  Tran[3].Condicion =Condicion3;
  
  Inicializacion ();
  
  while(1)
    Interpretar_RdP(Tran, NTRANS,Mc,Sig_Mc,NLUGARES,Leer_Entradas);

}

#define IZDA_1     0x01  /*0000_0001*/
#define DCHA_1     0x02  /*0000_0010*/
#define A          0x02  /*0000_0010*/
#define B          0x04  /*0000_0100*/
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
/*get current mode*/ 
newT=oldT;
newT.c_lflag &= ~ECHO; 
/* echo off */ 
newT.c_lflag &= ~ICANON; 
/*one char @ a time*/ 
ioctl(0,TCSETS,&newT); 
fcntl(0, F_SETFL, O_NONBLOCK);
#endif  
}  

void Accion1 (void)
{
#ifdef __AT_LAB__
PTB |= DCHA_1;
#else
printf("DERECHA CARRO 1\n");
#endif
}

char Condicion1(unsigned char entrada)
{
return entrada & PULSADOR_M;
}


void Accion2 (void)
{
#ifdef __AT_LAB__
  PTB &= ~(DCHA_1);
  PTB |= IZDA_1;
#else
printf("IZQUIERDA CARRO 1\n");
#endif
}

char Condicion2(unsigned char entrada)
{
  return entrada & B;
}

void Accion3 (void)
{
#ifdef __AT_LAB__
  PTB &= ~(IZDA_1);
#else
  printf("PARADO 1\n");
#endif
}

char Condicion3(unsigned char entrada)
{
  return entrada & A;
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
           case 'm':
		   *pEntrada = PULSADOR_M;
		   printf("PULSADOR_M\n");
		   break;
	   default:
		   *pEntrada = 0;
   }
   
#endif

}

