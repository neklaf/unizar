/**EL PROBLEMA DE LAS GARRAFAS CON EL MÉTODO DE BÚSQUEDA EN PROFUNDIDAD*/

import java.io.*;
import java.util.*;
import java.awt.*;

/*Solucion al problema de las garrafas Busqueda en profundidad, añadiendo en abiertos por el principio*/
//Implementado con 2 soluciones: 2 litros en la garrafa de 3 litros o 2 litros en la garrafa de 4 litros

/**En este problema de las garrafas tendremos dos posibles soluciones que la garrafa de 3 litros acabe con
dos litros de capacidad o que la garrafa de 4 litros acabe con dos litros ambas las consideraremos como 
buenas*/

/**Tenemos la clase Nodo de obligada implementación debido a los algoritmos de búsqueda que vamos a utilizar*/
/**La clase Nodo sólo tiene sus variables internas, el constructor y una serie de métodos:
	estado:
	padre: nos indicará el nodo padre del nodo actual.
	profundidad: nos indica el nivel de profundidad en el cual nos encontramos!!
*/


/**###################CLASE NODO !!!########################*/


class Nodo{
	Testado estado;
	Nodo	padre;
	int profundidad;
	
	//int x=30,y=50;

 Nodo(Testado estado,Nodo padre,int profundidad){//Constructor del nodo a partir de un estado
	this.estado=new Testado(estado.g3,estado.g4);
	this.padre=padre;
	this.profundidad=profundidad;
 }

 Vector Expandir(){
	Vector nuevos=new Vector(); //cjto de nodos expandidos
	Nodo nodo; //cada nodo expandido
	Testado aux;
	//ejecutar todas las funciones y se expanden las que sean posibles
	//System.out.println("Estado.g3 "+estado.g3+"Estado.g4 "+estado.g4+" antes de expandir VACIA3");
	aux=estado.vacia3();
 	
	if (aux!=null){
 		 nodo=new Nodo(aux,this,profundidad+1);
 		 //System.out.println("Nodo expandido en vacia3: "+nodo);
 		 //nodo.describe();
 		 nuevos.addElement(nodo);
 	}
    //System.out.println("Estado.g3 "+estado.g3+"Estado.g4 "+estado.g4+" antes de expandir VACIA4");	
    aux=estado.vacia4();
    if (aux!=null)
    {
 	    nodo=new Nodo(aux,this,profundidad+1);
 	    //System.out.println("Nodo expandido en vacia4: "+nodo);
        //nodo.describe();
         nuevos.addElement(nodo);	
    }
    //System.out.println("Estado.g3 "+estado.g3+"Estado.g4 "+estado.g4+" antes de expandir LLENA3");
    aux=estado.llena3();
    if (aux!=null)
    {
 	 nodo=new Nodo(aux,this,profundidad+1);
 	 //System.out.println("Nodo expandido en llena3: "+nodo);
	 //nodo.describe();
 	 nuevos.addElement(nodo);
    }
 
    //System.out.println("Estado.g3 "+estado.g3+"Estado.g4 "+estado.g4+" antes de expandir LLENA4");
    aux=estado.llena4();
    if (aux!=null)
    { 
 	    nodo=new Nodo(aux,this,profundidad+1);
 	    //System.out.println("Nodo expandido en llena4: "+nodo);
        //nodo.describe();
 	    nuevos.addElement(nodo);
    }
    //System.out.println("Estado.g3 "+estado.g3+"Estado.g4 "+estado.g4+" antes de expandir DERRAMA3_4");
    aux=estado.derrama3sobre4(); 
    nodo=new Nodo(aux,this,profundidad+1);
    //System.out.println("Nodo expandido en derrama3_4: "+nodo);
    //nodo.describe();
    nuevos.addElement(nodo);	
 
    //System.out.println("Estado.g3 "+estado.g3+"Estado.g4 "+estado.g4+" antes de expandir DERRAMA4_3");
    aux=estado.derrama4sobre3(); 
    nodo=new Nodo(aux,this,profundidad+1);
    //System.out.println("Nodo expandido en derrama4_3: "+nodo);
    //nodo.describe();
    nuevos.addElement(nodo);
 
    //System.out.println("Vector nuevos despues de expandir: "+nuevos);
    return nuevos;		
 }
 
 void describe(Graphics g){
 	System.out.println("profundidad: "+profundidad);
 	g.drawString("profundidad: "+profundidad,x,y);
 	Integer i=new Integer(profundidad);
 	g.drawString(i.toString(),x+30,y);
 	//tenemos que incrementar losvalores de x e y!!!
 	
 	//estado.describe(g,a,b);
 }
 boolean objetivo4()
 {
 	return (this.estado.g4==2);
 }	
 boolean objetivo3(){
 	return (this.estado.g3==2&&this.estado.g4==0);
 }
 /*void escribe(Graphics g){
 	estado.describe(g);
 }//escribe*/
}


/**###################CLASE T-ESTADO !!!########################*/

/*La clase Testado tiene las dos garrafas que serán dos enteros!!!*/

class Testado{
   /**Las variables que representan las garrafas!!!*/
    int g3,g4;
    /**Medidas necesarias para la representación de las garrafas!!*/
    final static int ancho=40;
    final static int alto3=30;
    final static int alto4=40;
    //Separacion entre las garrafas
    final static int separacion=50;
    //A partir de estas medidas se puede sacar todas los contenidos que tienen las garrafas
    
    /**Coordenadas de referencia*/
    int x,y;
 
 /**Constructor primero de la clase Testado!!*/
 public Testado(){this.g3=0;this.g4=0;}
 
 /**Constructor segundo de la clase Testado!!*/
 public Testado(int g3,int g4){
  this.g3=g3;this.g4=g4;
 }

public Testado vacia3() {
	if (g3==0) return null;
	else{
		//System.out.println("G3: "+g3+"G4: "+g4+"antes de vaciar3");
		return new Testado(0,g4);
	}
	
}
public Testado vacia4(){
	if (g4==0) return null;
	else{
		//System.out.println("G3: "+g3+"G4: "+g4+"antes de vaciar4");
		return new Testado(g3,0);
	}
}
public Testado llena3(){
	if (g3==3) return null;
	else{
		//System.out.println("G3: "+g3+"G4: "+g4+"antes de llena3");
		return new Testado(3,g4);
	}
}
public Testado llena4(){
	if (g4==4) return null;
	else{
		//System.out.println("G3: "+g3+"G4: "+g4+"antes de llena4");
		return new Testado(g3,4);
	}
}

public Testado derrama3sobre4(){
	//System.out.println("G3: "+g3+"G4: "+g4+"antes de derrama3_4");
 if(g3<=(4-g4)){
 	return new Testado(0,g3+g4);
 }else{
 	return new Testado(g3-(4-g4),4);
 }
}

public Testado derrama4sobre3(){
	//System.out.println("G3: "+g3+"G4: "+g4+"antes de derrama4_3");
 if(g4<=(3-g3)){
	return new Testado(g3+g4,0);
 }else{
	return new Testado(3,g4-(3-g3));
 }
}

/**Añadido para la representación de las garrafas!!!*/

void setRef(int a,int b){
        x=a;
        y=b;
}


/**Este método dibuja el esqueleto de la garrafa!!!*/
   void DibujaGarrafaVacia(Graphics g,int tipo){
        //importante!!!
        //gr=g;
        int x2,y2;
        if(tipo==3){
            y2=y+10;
            g.drawLine(x,y2,x,y2+alto3);
            g.drawLine(x,y2+alto3,x+ancho,y2+alto3);
            g.drawLine(x+ancho,y2+alto3,x+ancho,y2);
            //perimetro dibujado de la garrafa de tres litros
        }else if(tipo==4){
            x2=x+ancho+separacion;
            g.drawLine(x2,y,x2,y+alto4);
            g.drawLine(x2,y+alto4,x2+ancho,y+alto4);
            g.drawLine(x2+ancho,y+alto4,x2+ancho,y);
            //perimetro dibujado de la garrafa de cuatro litros
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

/**Este es el método utilizado para representar las garrafas!!!*/
public void describe(Graphics g){
  /**Ahora tenemos que dibujar las garrafas de los */
  //System.out.println("G3l:"+g3+" G4l:"+g4);
  setRef(x,y+20);
  g.drawString("G3l:"+g3+" G4l:"+g4,x,y);//?????
  //setRef();???? Para bajar las posición en la cual vamos a escribir
  //Garrafas(g,g3,g4);//???????
 }
} 


/**###################CLASE GARRPROF !!!########################*/


public class garrprof{
	
	/**Coordenadas de referencia*/
	//int x,y;
	
	/**El constructor de la clase pública*/
	garrprof(){}
		
	//compara las garrafas del estado est con las garrafas de cada estado del vector cerrados y si estan no se expanden
	static boolean estaencerrados(Testado est, Vector ce)
	{
		int i=0, sw=0;
		Testado el_estado=new Testado();
		int garr31, garr41, garr32, garr42;
		while((i<ce.size())&&(sw==0))
		{
			el_estado=(Testado)ce.elementAt(i);
			garr31=est.g3; //estado que viene pasado por param y que es el estado de los que se han expandidos abajo
			garr41=est.g4;
			garr32=el_estado.g3;
			garr42=el_estado.g4;
			if ((garr31==garr32)&&(garr41==garr42)) //si son iguales los valores de las garrafas
			{
				sw=1;
				//System.out.println("HAY UN NODO IGUAL EN CERRADOS");
			}//if
			i++;
		}//while
		 if (sw==0) //no esta en cerrados
		 	return false;
		 else return true; //si que esta en cerrados (sw==1)
	}//estaencerrados	
	
	
	/**Tenemos que hacer un método que realice todas las operaciones como las hace el main y 
	después desde el paint del applet dibujar los resultados!!!*/
	
public Stack main2(Graphics g){
    Vector abiertos=new Vector();
	Vector cerrados=new Vector();
	Stack resultado=new Stack();
	Nodo nodo=null, el_nodo;
	Testado estado=new Testado(0,0);
	Testado el_estado;
	boolean fin=false, res;
	int iteraciones=0, i;
	Vector aux=new Vector();

    abiertos.addElement(new Nodo(estado,null,0));//El nodo inicial responde al estado inicial, profundidad 0
						  //y padre null
	 cerrados.addElement(estado);
		//System.out.println("Vector cerrados: "+cerrados);
    while (!fin){
	    try{
		    	nodo=(Nodo)abiertos.firstElement(); //coger el primer elemento de abiertos
	    }catch(NoSuchElementException e){
	 	    System.out.println("Nodo imposible de alcanzar");
	 	}
	 	
	 	el_estado=nodo.estado;
	 	cerrados.addElement(el_estado);
	 	//System.out.println("Vector cerrados: "+cerrados);
	    abiertos.removeElementAt(0);
	    //System.out.println("Nodo elegido: "+nodo+" y su contenido es: ");
	    //nodo.escribe();
	    
	    //controlar si el nodo sacado de abierto es el objetivo
	    if (nodo.objetivo4())
	    {
	    	System.out.println("OBJETIVO ENCONTRADO");
	    	fin=true;//si es el objetivo ->fin
	    }//if
	    else{
			//Expandir el nodo, tomar lista de nodos expandidos y añadirlos en abiertos
			aux=nodo.Expandir();
	    	//System.out.println("Vector aux: "+aux);
	    	//Despues de expandir se inserta en abiertos los nodos cuyo estado no este en cerrados porque ya habian 
	    	//sido expandidos, y asi nos los volveremos a expandir	    	
	    	//System.out.println("ANADIENDO EN ABIERTOS");
			i=0;
			//inserta en abiertos los estados que no estan en cerrados para no volver a expandir los ya expandidos
			while(i<aux.size())
	    	{
					el_nodo=(Nodo)aux.elementAt(i);
					el_estado=el_nodo.estado;
					//System.out.println("Nodo a insertar en abiertos: ");
					//el_estado.describe();
					res=estaencerrados(el_estado, cerrados);
					if (res==false) //si el_estado enviado no esta en cerrados->se añade a abiertos
	    		{
		    			//añade al final de abiertos
							//abiertos.addElement(el_nodo); //añade el valor al vector abiertos
							abiertos.insertElementAt(el_nodo,0);
							//System.out.println("SE ANADE EN ABIERTOS "+el_nodo);
							//aux.removeElementAt(0);
					}//if.  Si el_estado esta en cerrados no se inserta en abiertos
	    		i++;
	    	}//while
	    	//System.out.println("Vector abiertos despues de añadir los expandidos: "+abiertos); 
	    	aux.removeAllElements();
	    }//else
	    iteraciones++;
	    if (iteraciones==100)	fin=true;
    }//while
//System.out.println("Abiertos size:"+abiertos.size());
	//ordena desde objetivo hasta raiz 
while(nodo!=null){
	resultado.addElement(nodo);
	nodo=nodo.padre;
}

System.out.println();
System.out.println("Objetivo alcanzado en "+iteraciones+" iteraciones");
//g.drawString("Objetivo alcanzado en "+iteraciones+" iteraciones", x, y);
System.out.println("Secuencia de pasos");
//y=+20;
//g.drawString("Secuencia de pasos",x,y);
return resultado;
 /*while(!resultado.isEmpty()){
	nodo=(Nodo)resultado.pop();//almacena en pila (desapila)
	nodo.describe(g);
	//setRef(a,b+20);
 } */   
}
}