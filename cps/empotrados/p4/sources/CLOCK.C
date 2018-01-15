#ifdef __AT_LAB_
#define __DECL__68HC08GP32_H__ extern
#include <68hc08gp32.h>
#include "clock.h"



/* Variables del modulo */

static unsigned int tick_counter ;
static unsigned int Timer ;
static char TO=0;
static char Active_Timer=0;


/* Implementacion servicios */
void interrupt 17  Tick (void) {

  TACK=1;
  tick_counter ++ ;
  if (Active_Timer)
    if (Timer == tick_counter){
      TO = 1 ;
      Active_Timer = 0 ;
     }

  return ;
}



void Reset_Clock (void) {
 
  TBON=0;
  
  /*Tick de 1.66mS, 600Hz */
  TBR2=0;
  TBR1=0;
  TBR0=1;

  TBON=1;
  

  asm {
    cli
  }
  
  tick_counter = 0 ;
  return;
}


void Start_Clock (void) {


   TBIE=1;

  
  return ;
  
}

void Stop_Clock (void) {

  TBIE=0;
  return;

}

unsigned int Get_Time (void) {
  
  return tick_counter;
}

void Delay_Until(unsigned int T){

  while(!(T==tick_counter))
     ;
}


void Set_Timer (unsigned int Ticks) {

  Timer = tick_counter + Ticks ;
  TO = 0 ;
  Active_Timer = 1 ;
}


char Time_Out (void) {
  return TO ;
}


void Remove_Timer (void) {
  Active_Timer = 0 ;
  TO = 0 ;
}
