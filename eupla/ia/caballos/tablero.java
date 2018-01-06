/*Esta es la práctica de los caballos blancos y los caballos negros*/

/**
    @author Aitor Acedo
    
    Comentario:
        El problema que se resuelve en esta codificación es el de un tablero de 3x3 en el
        se encuentran tres caballos negros y otros tantos blancos, los unos están en la
        parte inferior del tablero y los otros en la parte superior, y se trata de hacer
        que los caballos que se encuentran en la parte superior se coloquen en la parte inferior
        y viceversa.
*/

/**
    Para realizar con éxito el problema se ha ideado una jerarquía de clases que comienza por 
    la clase más baja de la jerarquía que en este caso es la clase posición, dicha clase se 
    utilizará para conocer la posición de cada caballo en el tablero.
    
    Se le han implementado una serie de métodos que ayudarán a trabajar con la clase, como puede
    ser el método set que lo que hace ahce es escribir los enteros que se le pasan como
    parámetros en cada una de las variables que representan las coordenadas.
    
    
*/
class posicion{
	int[] coord=new int[2];
	
	posicion(){
		coord[0]=4;
		coord[1]=4;
		//System.out.println("Posición incializada.");
	}

	void set(int i, int j){
		coord[0]=i;
		coord[1]=j;
	}	
}


/**
    Siguiendo con nuestra jerarquía de clases nos encontramos con la clase caballo, en ella 
    se han implementado dos constructores que difieren en que en uno de ellos se le pasa
    la posición al caballo y en el otro no.
    
    Después se implementan todos los movimientos que cada caballo puede realizar a lo largo
    del tablero para poder conseguir llegar a las casillas deseadas.
    
    También implementamos un método que nos dará las coordenadas de un caballo, el cual
    será buscado por el tablero y al cual se le introducirán las coordenadas en el mismo 
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
		//System.out.println("Inicialización simple del caballo.");
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
    La clase casilla se ha implementado ya que un tablero está formado por casillas y es 
    bastante útil que una clase lleve constancia del estado en el cual se encuentran cada
    una de las posicíones del tablero.
    
    Esta clase implementa un contructor, y varios métodos como el CambiaEstado que hará 
    que la casilla pase a estar ocupada si estaba libre y libre si estaba ocupada.
    
    Después también implementamos un método que nos sacará por pantalla el estado de la misma
    y el tipo de caballo que está en ella.
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
    Y por último la clase tablero que contendrá una matriz de 3x3 de casillas las cuales se inicializarán 
    debidamente gracias al constructor de la clase, desde este constructor se llamará a los constructores 
    de las clases anteriores, para inicializar debidamente todos los campos de las clases.
    
    Aparte del constructor únicamente nos hará falta un método que nos represente en la pantalla
    el contenido actual del tablero y con esto quedaría el problema perfilado para que desde el
    applet realizando una serie de llamadas a los diferentes métodos aquí implentados, se pudiera d
    dar solución al problema.
*/
class tablero{
	
	casilla tabla[][]=new casilla[3][3];
	//Con esto no estaría inicializado la matriz de casillas sino que se debería inicializar cada una de 
	//las casillas.
 
	tablero(){
		//Esto es para la situación incial
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
		System.out.println("***SITUACIÓN INICIAL***");
		t.MuestraTablero();
		System.out.println();
		System.out.println("****SOLUCIÓN****");
		t.solucion();
		t.MuestraTablero();
	}*/
}
