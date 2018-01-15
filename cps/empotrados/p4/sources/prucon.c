/******************************************************************
* Area Ingenieria de Sistemas y Automatica
* Sistemas Empotrados y Sistemas de control microprocesador
*******************************************************************
* nombre fichero : prucon.c  prueba conexiones I/O practica 1
*******************************************************************
* proyecto       : Practicas asignatura
* descripcion    : Prueba de las conexiones de las I/O 
* programador    : JL Villarroel (JLV) y JMM Montiel (JMM)
* lenguaje       : ANSI C para ICC11 ver 5.0
* fecha          : 25 noviembre 1998
* version        :
* historia       : 25-11-98 Creacion (JMM)(JLV)
********************************************************************/
#define __DECL__68HC08GP32_H__

#include <68hc08gp32.h>

/* Las entradas se leen del puerto A
     PTA1 --> B0
     PTA2 --> B1
     PTA3 --> B2
     PTA4 --> S0
     PTA5 --> S1
     PTA6 --> S2
 Las salidas se envian por el puerto C 
     PTC0 --> P0
     PTC1 --> P1
     PTC2 --> P2
     PTC3 --> SUBIR
     PTC4 --> BAJAR */
     

#define B0      0x01   /* 0000_0001*/
#define B1      0x02   /* 0000_0010*/
#define B2      0x04   /* 0000_0100*/
#define S0      0x08   /* 0000_1000*/
#define S1      0x10   /* 0001_0000*/
#define S2      0x20   /* 0010_0000*/

#define O_P0      0x01   /* 0000_0001*/
#define O_P1      0x02   /* 0000_0010*/
#define O_P2      0x04   /* 0000_0100*/
#define O_SUBIR   0x08   /* 0000_1000*/
#define O_BAJAR   0x10   /* 0001_0000*/
#define O_PARADO  0x00   /* 0000_0000*/



static void ConfigurarEntradas(void);
static unsigned char LeerEntrada(void);
static void ConfigurarSalidas(void);
static void GenerarSalida(unsigned char Salida);
static void Temporizar(void);


void main (void){
  
  unsigned char Entrada;
  int i;
  
  ConfigurarEntradas();
  ConfigurarSalidas();
 
  for(i=0; i < 3; i++){
    GenerarSalida(O_P0);
    Temporizar();
    GenerarSalida(O_P1);
    Temporizar();
    GenerarSalida(O_P2);
    Temporizar();
    GenerarSalida(O_SUBIR);
    Temporizar();
    GenerarSalida(O_BAJAR);
    Temporizar();
  }
  
    
 
  while(1){
    Entrada = LeerEntrada();
    GenerarSalida(Entrada);
  }
}    

static void Temporizar(void){
  unsigned int i ;
  
     
 // T1MODL = 0x9C ;
 // T1MODH = 0x95 ;  /* 38300 ticks x 26.042 us = 1 segundo */  
 // PS2_1 = 1 ; PS1_1 = 1 ; PS0_1 = 0 ; 
//  T1SC &= 0x0F ;
//  while (TOF_1 == 0) ;
 // T1SC = 0x20 ;    // STOP the timer
 
 for (i=0; i < 0xFFFF; i++) asm NOP ;
}

static void ConfigurarEntradas(void){
    DDRA = 0x00;  /*Configurar todos los pines del PTA como
                    pines de entrada */
}


static unsigned char LeerEntrada(void){
   return PTA>>1;
}


static void ConfigurarSalidas(void){
    DDRC=0xFF;
}

static void GenerarSalida(unsigned char Salida){
  PTC = Salida;
}
