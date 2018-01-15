#ifndef clock_h
#define clock_h

extern void Reset_Clock (void) ;
extern void Start_Clock (void) ;
extern void Stop_Clock (void) ;
extern unsigned int Get_Time (void) ;
extern void Delay_Until(unsigned int T);

extern void Set_Timer (unsigned int Ticks) ;
extern char Time_Out (void) ;
extern void Remove_Timer (void) ;

#endif