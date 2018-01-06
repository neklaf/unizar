/*Esta es la   practica del 8-puzzle*/

/**
    @author Aitor Acedo
*/

/**
    La clase array es una clase auxiliar definida en este archivo para poder gestionar las coordenadas
    de los números que irán moviéndose por el tablero.
    
    Dicha clase proporciona los métodos necesarios para devolver las coordenadas y para poder modificarlas.
    
*/

class array {
	int[] a=new int[2];
	
	array(){
		//Esto lo que hace es reservar memoria para el vector de dos enteros.!!
		//a=new int[2];
		a[0]=4;
		a[1]=4;
		//System.out.println("Variable inicializada.");
	}

	void set(int x, int y){
		a[0]=x;
		a[1]=y;
		//System.out.println("Los valores se han cargado en el vector.");
	}
	
	int get1(){
		return a[0];
	}

	int get2(){
		return a[1];
	}
}


/**
    La clase puzzle es la clase principal del archivo, en ella tenemos definida la tabla
    que llevará registradas en sus posiciones los números que irán moviendose con ayuda 
    de unas simples reglas que se implementan dentro de la clase.
    
    El problema se puede abordar de muchas maneras aunque la más sencilla es que únicamente 
    la casilla que se puede mover es la que tiene un cero, que representará un espacio vacío.
    
    Por lo tanto lo que debemos hacer es que con el constructor de la clase puzzle el tablero
    se inicialice en un estado en el cual aplicando una secuencia de reglas, o movimientos del
    entero cero, se pueda llegar a la posición en la que los número del tablero estén ordenados en
    el sentido de las agujas del reloj.
    
    Entonces lo único que se ha hecho ha sido implementar los movimientos permitidos para el
    'hueco' y aplicando una secuencia de movimientos recogidos en el método solución de la
    clase se consigue llegar a la situación deseada.
*/
public class puzzle{

//int[filas][columnas]

int tabla[][]=new int[3][3];

	//Contructor
	puzzle(){
		//Lo que tiene que hacer el contructor es poner el puzzle en su estado inicial.
		tabla[0][0]=1;
		tabla[0][1]=4;
		tabla[0][2]=2;
		tabla[1][0]=7;
		tabla[1][1]=0;
		tabla[1][2]=3;
		tabla[2][0]=6;
		tabla[2][1]=8;
		tabla[2][2]=5;
		//System.out.println("Incializada la tabla!!");
		//También se podría poner lo mismo pero this.tabla{...}
	}
    
    
    
	void MostrarTabla(){
		int i,j;
		for(i=0;i<tabla.length;i++){
			System.out.println(tabla[i][0]+ "    "+tabla[i][1]+"    "+tabla[i][2]);
		}
	}
	
	public array EncuentraVacio(){
		array vector=new array();
		int i,j;
		for(i=0;i<tabla.length;i++){
			for(j=0;j<tabla.length;j++){
				if(tabla[i][j]==0){
					vector.set(i,j);
				}
			}
		}
		return vector;
	}

	//Reglas!!
	
	void MoverArriba(int i, int j){
		tabla[i][j]=tabla[i-1][j];
		tabla[i-1][j]=0;
		System.out.println("El hueco se ha movido arriba.");
	}

	void MoverAbajo(int i, int j){
		tabla[i][j]=tabla[i+1][j];
		tabla[i+1][j]=0;
		System.out.println("El hueco se ha movido abajo.");
	}

	void MoverIzquierda(int i, int j){
		tabla[i][j]=tabla[i][j-1];
		tabla[i][j-1]=0;
		System.out.println("El hueco se ha movido a la izquierda.");
	}

	void MoverDerecha(int i, int j){
		tabla[i][j]=tabla[i][j+1];
		tabla[i][j+1]=0;
		System.out.println("El hueco se ha movido a la derecha.");
	}

	void solucion(){
		array aux=new array();
		//int x=0,y=0;
		aux=EncuentraVacio();
		MoverArriba(aux.a[0],aux.a[1]);
		aux=EncuentraVacio();
		MoverDerecha(aux.a[0],aux.a[1]);
		aux=EncuentraVacio();
		MoverAbajo(aux.a[0],aux.a[1]);
		aux=EncuentraVacio();
		MoverIzquierda(aux.a[0],aux.a[1]);
		aux=EncuentraVacio();
		MoverAbajo(aux.a[0],aux.a[1]);
		aux=EncuentraVacio();
		MoverIzquierda(aux.a[0],aux.a[1]);
		aux=EncuentraVacio();
		MoverArriba(aux.a[0],aux.a[1]);
		aux=EncuentraVacio();
		MoverDerecha(aux.a[0],aux.a[1]);
	}

	public static void main(String args[]){
		puzzle p=new puzzle();
		array auxiliar=new array();
		System.out.println();
		System.out.println("El estado incial es:");
		System.out.println();
		p.MostrarTabla();
		System.out.println();
		System.out.println("****SOLUCIÓN****");
		p.solucion();
		System.out.println();
		System.out.println("El estado final es:");
		System.out.println();
		p.MostrarTabla();
		System.out.println();
	}	
}
