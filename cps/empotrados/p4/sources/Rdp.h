/*******************************************************************************
*                             Grupo de Robotica                                *
*             Departamento de Informatica e Ingenieria de Sistemas             *
*                          Universidad de Zaragoza                             *
********************************************************************************
* Filename    : rdpROM.h                                                  *
********************************************************************************
* Proyect     : Practicas Sistemas Empotrados / Sistemas Control con Micropro
* Programmer  : J.L. Villarroel & J.M Martinez Montiel
* Purpose     : Implementacion de la interpretacion de una RdP generica en ROM
* Language    : Ansi c                                                         *
* Module      : RdPROM                                                               *
* Date        : Noviembre 1998
* History     : 26/Nov/98 Creacion (JMMM,JLV)
*******************************************************************************/

#ifndef RdP_h
#define RDP_h

typedef struct{
  char *Lugares_Entrada;
  char *Lugares_Salida;
  void (*Accion) (void);
  char (*Condicion)(unsigned char entrada);
}T_TRAN;
                                
void Interpretar_RdP(const T_TRAN Trans[], int nTrans,
                     unsigned char Mc[], unsigned char Sig_Mc[],
                     int nLugares, 
                     void (*Leer_Entrada)(unsigned char *pEntrada));

#endif
