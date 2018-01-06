import java.applet.*;
import java.awt.*;

class metodos{
   //coordenadas que relacionan las dos garrafas.
   int x,y;
   //Dimensiones de las garrafas (constantes)
   final static int ancho=40;
   final static int alto3=30;
   final static int alto4=40;
   //Separacion entre las garrafas
   final static int separacion=50;
   //A partir de estas medidas se puede sacar todas los contenidos que tienen las garrafas
        
   metodos(int a,int b){
        x=a;
        y=b;
   }
   //Para cambiar el punto de referencia!!
   void setRef(int a,int b){
        x=a;
        y=b;
   }
   
   void DibujaGarrafaVacia(Graphics g,int tipo){
        //importante!!!
        //gr=g;
        int x2,y2;
        if(tipo==3){
            y2=y+10;
            g.drawLine(x,y2,x,y2+alto3);
            g.drawLine(x,y2+alto3,x+ancho,y2+alto3);
            g.drawLine(x+ancho,y2+alto3,x+ancho,y2);
            //perimetro dibujado
        }else if(tipo==4){
            x2=x+ancho+separacion;
            g.drawLine(x2,y,x2,y+alto4);
            g.drawLine(x2,y+alto4,x2+ancho,y+alto4);
            g.drawLine(x2+ancho,y+alto4,x2+ancho,y);
        }
    }
    
    void DibujarGarrafa(Graphics g,int i,int contenido){
        g.setColor(Color.blue);
        if(i==3){
            g.fillRect(x,((alto4/10)-(contenido))*10+y,ancho,(contenido*10));
            g.setColor(Color.black);
            this.DibujaGarrafaVacia(g,i);
        }else if(i==4){
            g.fillRect(x+ancho+separacion,((alto4/10)-(contenido))*10+y,ancho,(contenido*10));
            g.setColor(Color.black);
            this.DibujaGarrafaVacia(g,i);
        }
    }
    
    void Garrafas(Graphics g,int c1,int c2){
        DibujarGarrafa(g,3,c1);   
        DibujarGarrafa(g,4,c2);
    }
}
public class grafgarrafa extends Applet{
    //La columna de las x en la cual se programa!!!
    final static int mainx1=30;
    final static int mainx2=400;
    final static int fila1=50;
    final static int fila2=130;
    final static int fila3=210;
    final static int fila4=290;
    final static int fila5=370; 
        
    public void init(){
        //Para poner el tamaño deseado.
        resize(1000,1000);   
    }
        
    public void paint (Graphics g){      
        garrafa g3=new garrafa(3);
        garrafa g4=new garrafa(4);
        metodos m=new metodos(mainx1,fila1);
        //Solucion 1!!!
        g.drawString("***SOLUCION 1***",mainx1+25,20);
        m.setRef(mainx1+25,fila1);
        g3.LlenarG3();
        g.drawString("Hemos llenado la garrafa de 3 litros.", mainx1,fila1-10);
        m.Garrafas(g,g3.cantidad,g4.cantidad);
        g3.EcharG3enG4(g4);
        g.drawString("Hemos echado la garrafa de 3 litros en la de 4.", mainx1,fila2-10);
		m.setRef(mainx1+25,fila2);
		m.Garrafas(g,g3.cantidad,g4.cantidad);
        g3.LlenarG3();
        g.drawString("Hemos llenado la garrafa de 3 litros.", mainx1,fila3-10);
        m.setRef(mainx1+25,fila3);
        m.Garrafas(g,g3.cantidad,g4.cantidad);
		g3.EcharG3enG4(g4);
		g.drawString("Hemos echado la garrafa de 3 litros en la de 4.", mainx1,fila4-10);
		m.setRef(mainx1+25,fila4);
		m.Garrafas(g,g3.cantidad,g4.cantidad);
        g4.VaciarGarrafa4();
        g.drawString("Hemos vaciado la garrafa de 4 litros.", mainx1,fila5-10);
        m.setRef(mainx1+25,fila5);
        m.Garrafas(g,g3.cantidad,g4.cantidad);
        
        //Solucion 2
        g3=new garrafa(3);//Tambien se pueden vaciar!!!
        g4=new garrafa(4);
        g.drawString("***SOLUCION 2***",mainx2+25,20);
        g4.LlenarG4();
        g.drawString("Hemos llenado la garrafa de 4 litros.", mainx2,fila1-10);
        m.setRef(mainx2+30,fila1);
        m.Garrafas(g,g3.cantidad,g4.cantidad);
		g4.EcharG4enG3(g3);
		g.drawString("Hemos echado la garrafa de 4 litros en la de 3.", mainx2,fila2-10);
		m.setRef(mainx2+30,fila2);
		m.Garrafas(g,g3.cantidad,g4.cantidad);
		g3.VaciarGarrafa3();
		g.drawString("Hemos vaciado la garrafa de 3 litros.", mainx2,fila3-10);
		m.setRef(mainx2+30,fila3);
		m.Garrafas(g,g3.cantidad,g4.cantidad);
		g4.EcharG4enG3(g3);
		g.drawString("Hemos echado la garrafa de 4 litros en la de 3.", mainx2,fila4-10);
		m.setRef(mainx2+30,fila4);
		m.Garrafas(g,g3.cantidad,g4.cantidad);
		g4.LlenarG4();
		g.drawString("Hemos llenado la garrafa de 4 litros.", mainx2,fila5-10);
		m.setRef(mainx2+30,fila5);
		m.Garrafas(g,g3.cantidad,g4.cantidad);
		g4.EcharG4enG3(g3);
		g.drawString("Hemos echado la garrafa de 4 litros en la de 3.", mainx2,fila5+70);
		m.setRef(mainx2+30,fila5+80);
		m.Garrafas(g,g3.cantidad,g4.cantidad);
        
    }
    
}