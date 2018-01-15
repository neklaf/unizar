/*******************************************************************************
*                             Grupo de Robotica                                *
*             Departamento de Informatica e Ingenieria de Sistemas             *
*                          Universidad de Zaragoza                             *
********************************************************************************
* Filename    : rdp.c                                                   *
********************************************************************************
* Proyect     : Practicas Sistemas Empotrados / Sistemas Control con Micropro
* Programmer  : J.L. Villarroel & J.M Martinez Montiel
* Purpose     : Implementacion de la interpretacion de una RdP generica en
* Language    : Ansi c                                                         *
* Module      : RdP                                                               *
* Date        : Diciembre 1998
* History     : 13/09/2000 Modificacion para manejo redes con arcos peso unitario
                4/Ene/1998 Creacion (JMMM,JLV)
*******************************************************************************/

#include "Rdp.h"
#include <string.h>
static char Sensibilizada(T_TRAN Tran, const unsigned char Mc[]);
static void Retirar_Marcas(T_TRAN Tran, unsigned char Mc[]);
static void Poner_Marcas(T_TRAN Tran, unsigned char Mc[]);



void Interpretar_RdP(const T_TRAN Trans[], int nTrans,
                            unsigned char Mc[], unsigned char Sig_Mc[], 
                            int nLugares, void (*Leer_Entrada)(unsigned char *pEntrada))
{ 
  int t;
  unsigned char entrada;


  (*Leer_Entrada)(&entrada);

      
  memcpy (Mc, Sig_Mc, nLugares+1) ; /*El tamanyo del char es 1, se evita la multiplicacion,
                                      se evitan 63 bytes de codigo*/
  for(t=1; t<=nTrans; t++){
    
     //printf("Sensibilizada transicion %i:%i condicion entrada %i\n", t, Sensibilizada(Trans[t],Mc),  (*Trans[t].Condicion)(entrada));
     if(Sensibilizada(Trans[t],Mc) && (*Trans[t].Condicion)(entrada)){
//     printf("Disparo transicion %i\n", t );     
     Retirar_Marcas(Trans[t],Sig_Mc) ;
     Retirar_Marcas(Trans[t],Mc) ;  /* Si existen conflictos efectivos*/
     (*Trans[t].Accion)();
     Poner_Marcas(Trans[t],Sig_Mc);
    }        
   }
}/*fin Interpretar_RdP_UnPaso */

static char Sensibilizada(T_TRAN Tran, const unsigned char Mc[])
{
  int p;
  char Ret_Value=1;
  

  p=0;
  while((Tran.Lugares_Entrada[p] !=0) && Ret_Value==1){
    if (Mc[Tran.Lugares_Entrada[p]] < 1)
      Ret_Value = 0;
    p++;
  }
  
  return Ret_Value;
  
}

static void Retirar_Marcas(T_TRAN Tran, unsigned char Mc[])
{
  int p;
  
  p=0;
 // printf("Retirar marcas:\n");
  while((Tran.Lugares_Entrada[p] !=0)){
    Mc[Tran.Lugares_Entrada[p]]--;
   // printf("%i de %i\n",  Mc[Tran.Lugares_Entrada[p]], Tran.Lugares_Entrada[p] );
    p++;
  }
}

static void Poner_Marcas(T_TRAN Tran, unsigned char Mc[]) 
{
  int p;
  
  p=0;
// printf("Poner marcas:\n");
  while((Tran.Lugares_Salida[p] !=0)){
    Mc[Tran.Lugares_Salida[p]]++;
  //  printf("%i en %i\n",  Mc[Tran.Lugares_Salida[p]], Tran.Lugares_Salida[p]);
    p++;
  }
}

  












