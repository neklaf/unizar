/**
    @author Aitor Acedo
    
    Comentario:
        El problema de las garrafas trata de que se tienen dos garrafas de 3 y 4 litros 
        respectivamente y gracias a una serie de reglas que se deberán implementar
        tendremos que hacer que en una de las dos garrafas quede con una cantidad de liquido
        de 2 litros.
*/

/**
    Tenemos la clase garrafa que será la única clase que nos hace falta, en ella estarán
    reflejadas la cantidad máxima de líquido que puede soportar la garrafa, la cantidad 
    de líquido que hay en la garrafa actualmente y también se llevará la cantidad de líquido
    que se puede hechar todavía en la garrafa.
    
    La clase constará de un constructor que incializará las variables mencionadas anteriormente
    gracias a un parámetro que se le pasa al constructor.
    
    También tendremos implementados todas las reglas que le pueden aplicar a las garrafas
    como por ejemplo llenarla de liquido, vaciarla, ... Estos métodos son los que se utilizarán
    para que combinandose entre sí podamos llegar a la solución del problema.
    
    En los métodos de solución se han implementado los pasos para resolver de las dos maneras más
    sencillas posibles el problema de las garrafas.
*/
public class garrafa{
	public int cantidad_maxima;
	public int cantidad;
	public int sobra;//Cambiar nombre 
	
	//El constructor de nuestra clase!!
 	public garrafa(int litros){
		cantidad_maxima=litros;
		cantidad=0;
		sobra=litros;
		//Si garrafa esta vacia => sobra=cantidad
	}
	
	//El conjunto de reglas para resolver el problema!!
	public void LlenarG3(){
		//Cambiar!!
		cantidad=3;
		sobra=0;
		//System.out.println("La garrafa de 3 litros se ha llenado.");
	}

	public void LlenarG4(){
		//No me convence!!! 
		cantidad=4;
		sobra=0;
		//System.out.println("La garrafa de 4 litros se ha llenado.");
	}
	
	public void VaciarGarrafa3(){
		//Este sera igual para las dos!!
		cantidad=0;
		sobra=3;
		//System.out.println("La garrafa de 3 litros se ha vaciado por completo.");
	}

	public void VaciarGarrafa4(){
		cantidad=0;
		sobra=4;
		//System.out.println("La garrafa de 4 litros se ha vaciado por completo.");
	}

	public void EcharG3enG4(garrafa garrafa4){
		if(garrafa4.sobra<cantidad){
			this.cantidad=this.cantidad-garrafa4.sobra;
			this.sobra=this.sobra+garrafa4.sobra;
			garrafa4.cantidad=4;
			garrafa4.sobra=0;
		}
		//Deberia ser un else!!!
		else if(garrafa4.sobra>=cantidad){
		    //sobra=+cantidad;
		    garrafa4.cantidad=+cantidad;
		    garrafa4.sobra=garrafa4.sobra-cantidad;
		    this.VaciarGarrafa3();
		}
		//System.out.println("El contenido de la garrafa de 3 litros esta en la de 4.");
	}

	public void MostrarContenido(){
		//Mirar mas a fondo.!!!
		System.out.println("El contenido de la garrafa es: " + cantidad);
		//System.out.println("Lo que sobra de la garrafa es: " + sobra);
	}

	public void EcharG4enG3(garrafa garrafa3){
		if(garrafa3.sobra<cantidad){
		    cantidad=cantidad-garrafa3.sobra;
		    sobra=sobra+garrafa3.sobra;
		    garrafa3.cantidad=3;
		    garrafa3.sobra=0;
		}else{
		    sobra=sobra+cantidad;
		    garrafa3.cantidad=garrafa3.cantidad+cantidad;
		    garrafa3.sobra=garrafa3.sobra-cantidad;  
		}
		//System.out.println("El contenido de la garrafa de 4 litros esta en la de 3.");
		//System.out.println("El contenido final de la garrafa de 4 litros es: "+cantidad);
		//System.out.println("sobra de g4: "+sobra);
		//garrafa3.MostrarContenido();
	}
	
	void solucion1(garrafa g3,garrafa g4){
		//Aplicar las reglas necesarias!!!     
		g3.LlenarG3();
		g3.EcharG3enG4(g4);
		g3.LlenarG3();
		g3.EcharG3enG4(g4);
		//El resultado esta en la garrafa 3.
		//g3.MostrarContenido();
	}

    
	void solucion2(garrafa g3,garrafa g4){
		//Esta mal pq tendriamos que tener un solo vaciar garrafa y q dinamicamente cojiera
		//el tipo de garrafa que estamos vaciando.!!!! MIRAR MAÑANA!!!
		g4.LlenarG4();
		g4.EcharG4enG3(g3);
		g3.VaciarGarrafa3();
		g4.EcharG4enG3(g3);
		g4.LlenarG4();
		g4.EcharG4enG3(g3);
		//g4.MostrarContenido();
	 }

	
	/*public static void main(String args[]){
		garrafa g1=new garrafa(3);
		garrafa g2=new garrafa(4);
		garrafa g3=new garrafa(3);
		garrafa g4=new garrafa(4);
		
		//Esto es una prueba!!!
		System.out.println("***SOLUCION UNO***");
		//Da igual que variable se ponga como recceptor en el mensaje "solucion1"!!!
		g2.solucion1(g1,g2);
		System.out.println();
		System.out.println();
		System.out.println("***SOLUCION DOS***");
		g2.solucion2(g3,g4);    
	}*/
	
}
