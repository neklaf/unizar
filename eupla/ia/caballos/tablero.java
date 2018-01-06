/*Esta es la pr�ctica de los caballos blancos y los caballos negros*/

/**
    @author Aitor Acedo
    
    Comentario:
        El problema que se resuelve en esta codificaci�n es el de un tablero de 3x3 en el
        se encuentran tres caballos negros y otros tantos blancos, los unos est�n en la
        parte inferior del tablero y los otros en la parte superior, y se trata de hacer
        que los caballos que se encuentran en la parte superior se coloquen en la parte inferior
        y viceversa.
*/

/**
    Para realizar con �xito el problema se ha ideado una jerarqu�a de clases que comienza por 
    la clase m�s baja de la jerarqu�a que en este caso es la clase posici�n, dicha clase se 
    utilizar� para conocer la posici�n de cada caballo en el tablero.
    
    Se le han implementado una serie de m�todos que ayudar�n a trabajar con la clase, como puede
    ser el m�todo set que lo que hace ahce es escribir los enteros que se le pasan como
    par�metros en cada una de las variables que representan las coordenadas.
    
    
*/
class posicion{
	int[] coord=new int[2];
	
	posicion(){
		coord[0]=4;
		coord[1]=4;
		//System.out.println("Posici�n incializada.");
	}

	void set(int i, int j){
		coord[0]=i;
		coord[1]=j;
	}	
}


/**
    Siguiendo con nuestra jerarqu�a de clases nos encontramos con la clase caballo, en ella 
    se han implementado dos constructores que difieren en que en uno de ellos se le pasa
    la posici�n al caballo y en el otro no.
    
    Despu�s se implementan todos los movimientos que cada caballo puede realizar a lo largo
    del tablero para poder conseguir llegar a las casillas deseadas.
    
    Tambi�n implementamos un m�todo que nos dar� las coordenadas de un caballo, el cual
    ser� buscado por el tablero y al cual se le introducir�n las coordenadas en el mismo 
    objeto.
    
*/
class caballo{
	//probablemente la variable numero no nos haga falta.	
	String tipo;
	int numero;
	posicion p;
	
	//Constructores

	caballo(){
		tipo="";
		numero=0;
		p=new posicion();
		//System.out.println("Inicializaci�n simple del caballo.");
	}

	caballo(String t,int n){
		tipo=t;
		numero=n;
		p=new posicion();
		//System.out.println("Caballo incializado del tipo:" +tipo+" y el numero es: "+numero);
	}
	
	//Nos devuelve las coordenas de un caballo RECUERDA QUE PASAMOS EL TABLERO!!
	void SituacionCaballo(tablero t){
		for(int i=0;i<t.tabla.length;i++){
			for(int j=0;j<t.tabla.length;j++){
			if((t.tabla[i][j].ficha.tipo==this.tipo)&&(t.tabla[i][j].ficha.numero==this.numero)){
					p.set(i,j);
			}
			}
		}
	}

	void Mov1(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]-1][c.p.coord[1]+2].vacia=false;
		t.tabla[c.p.coord[0]-1][c.p.coord[1]+2].ficha=c;
		c.p.coord[0]=c.p.coord[0]-1;
		c.p.coord[1]=c.p.coord[1]+2;
	}

	void Mov2(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]+2][c.p.coord[1]-1].vacia=false;
		t.tabla[c.p.coord[0]+2][c.p.coord[1]-1].ficha=c;
		c.p.coord[0]=c.p.coord[0]+2;
		c.p.coord[1]=c.p.coord[1]-1;
	}

	void Mov3(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]-2][c.p.coord[1]-1].vacia=false;
		t.tabla[c.p.coord[0]-2][c.p.coord[1]-1].ficha=c;
		c.p.coord[0]=c.p.coord[0]-2;
		c.p.coord[1]=c.p.coord[1]-1;
	}

	void Mov4(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]+1][c.p.coord[1]-2].vacia=false;
		t.tabla[c.p.coord[0]+1][c.p.coord[1]-2].ficha=c;
		c.p.coord[0]=c.p.coord[0]+1;
		c.p.coord[1]=c.p.coord[1]-2;
	}

	void Mov5(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]-2][c.p.coord[1]+1].vacia=false;
		t.tabla[c.p.coord[0]-2][c.p.coord[1]+1].ficha=c;
		c.p.coord[0]=c.p.coord[0]-2;
		c.p.coord[1]=c.p.coord[1]+1;
	}

	void Mov6(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]+2][c.p.coord[1]+1].vacia=false;
		t.tabla[c.p.coord[0]+2][c.p.coord[1]+1].ficha=c;
		c.p.coord[0]=c.p.coord[0]+2;
		c.p.coord[1]=c.p.coord[1]+1;
	}

	void Mov7(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]-1][c.p.coord[1]-2].vacia=false;
		t.tabla[c.p.coord[0]-1][c.p.coord[1]-2].ficha=c;
		c.p.coord[0]=c.p.coord[0]-1;
		c.p.coord[1]=c.p.coord[1]-2;
	}

	void Mov8(tablero t,String s, int n){
		caballo c=new caballo(s,n);
		caballo c1=new caballo();
		c.SituacionCaballo(t);
		t.tabla[c.p.coord[0]][c.p.coord[1]].vacia=true; 
		t.tabla[c.p.coord[0]][c.p.coord[1]].ficha=c1; //Casilla vacia y sin caballo.
		t.tabla[c.p.coord[0]+1][c.p.coord[1]+2].vacia=false;
		t.tabla[c.p.coord[0]+1][c.p.coord[1]+2].ficha=c;
		c.p.coord[0]=c.p.coord[0]+1;
		c.p.coord[1]=c.p.coord[1]+2;
	}
}

/**
    La clase casilla se ha implementado ya que un tablero est� formado por casillas y es 
    bastante �til que una clase lleve constancia del estado en el cual se encuentran cada
    una de las posic�ones del tablero.
    
    Esta clase implementa un contructor, y varios m�todos como el CambiaEstado que har� 
    que la casilla pase a estar ocupada si estaba libre y libre si estaba ocupada.
    
    Despu�s tambi�n implementamos un m�todo que nos sacar� por pantalla el estado de la misma
    y el tipo de caballo que est� en ella.
*/
class casilla{
	
	boolean vacia;
	caballo ficha;
	
	//Constructor
	casilla(){
		vacia=true;
		ficha=new caballo();
	}
	
	void CambiaEstado(){
		if (vacia==true){
			vacia=false;
		}
		else{
			vacia=true;
		}
	}
	void MuestraCasilla(){
		if (vacia==true){
			System.out.println("Casilla vacia");
		}else{
			System.out.println("Casilla ocupada con caballo: "+ficha.tipo);
		}
	}
}

/**
    Y por �ltimo la clase tablero que contendr� una matriz de 3x3 de casillas las cuales se inicializar�n 
    debidamente gracias al constructor de la clase, desde este constructor se llamar� a los constructores 
    de las clases anteriores, para inicializar debidamente todos los campos de las clases.
    
    Aparte del constructor �nicamente nos har� falta un m�todo que nos represente en la pantalla
    el contenido actual del tablero y con esto quedar�a el problema perfilado para que desde el
    applet realizando una serie de llamadas a los diferentes m�todos aqu� implentados, se pudiera d
    dar soluci�n al problema.
*/
class tablero{
	
	casilla tabla[][]=new casilla[3][3];
	//Con esto no estar�a inicializado la matriz de casillas sino que se deber�a inicializar cada una de 
	//las casillas.
 
	tablero(){
		//Esto es para la situaci�n incial
		for(int i=0;i<tabla.length;i++){
			for(int j=0;j<tabla.length;j++){
				tabla[i][j]=new casilla();
				if (i==0){
					tabla[i][j].vacia=false;
					tabla[i][j].ficha=new caballo("negro",j+1);
					tabla[i][j].ficha.p.coord[0]=i;
					tabla[i][j].ficha.p.coord[1]=j;
				}else if(i==2){
					tabla[i][j].vacia=false;
					tabla[i][j].ficha=new caballo("blanco",j+1);
					tabla[i][j].ficha.p.coord[0]=i;
					tabla[i][j].ficha.p.coord[1]=j;
				}
			}
		}
	}

	void MuestraTablero(){
		for(int i=0;i<tabla.length;i++){
			for(int j=0;j<tabla.length;j++){
				System.out.println("Posicion: "+i+","+j);
				tabla[i][j].MuestraCasilla();
			}
		}
	}

	void solucion(){
		caballo c=new caballo();
		c.Mov1(this,"blanco",1);
		c.Mov2(this,"negro",2);
		c.Mov3(this,"blanco",3);
		c.Mov4(this,"negro",3);
		c.Mov5(this,"blanco",2);
		c.Mov6(this,"negro",1);
		c.Mov7(this,"blanco",1);
		c.Mov8(this,"negro",3);
	}

	/*public static void main(String args[]){
		tablero t=new tablero();
		System.out.println();
		System.out.println("***SITUACI�N INICIAL***");
		t.MuestraTablero();
		System.out.println();
		System.out.println("****SOLUCI�N****");
		t.solucion();
		t.MuestraTablero();
	}*/
}
