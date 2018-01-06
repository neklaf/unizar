/*Problema del granjero y la barca*/

/**
    @author Aitor Acedo
    Comentario:
    La parte de la aplicación que realmente resuelve el problema del barquero 
    es el código que se va a comentar a continuación:
*/

/**
    La clase ser es la clase madre de las clases oveja, lechuga y lobo, únicamente instrumenta
    un método que es su propio constructor, lo cual nos hace pensar que únicamente se utiliza como 
    receptor de las clases derivadas, utilizando así un concepto importante de la programación 
    orientada a objetos como es el polimorfismo.
    
    Dicha clase tiene dos variables internas que son localizacion y tipo, localizacion se utliza
    para obtener la orilla en la que se encuentra el ser en cuestión, y tipo indica de que
    clase es la variable, es decir, si es una lechuga, un lobo o una oveja.
*/
class ser{
	String localizacion,tipo;

	//abstract boolean come(ser s);

	public ser(){
		localizacion="";
		tipo="";
	}
}

/**
    La clase orilla se implenta para poder contar con una estructura en la que ubicar a los 
    animales, se ha optado por un array de tres posiciones de la clase ser, en el cual se podrán 
    ubicar cualquiera de los animales que creemos, bien sea una oveja, una lechuga o un lobo.
    
    Posee un constructor que a cada posición del array le asigna un animal en cuestión.
    
    Además se ha implementado un método denominado quitaSer(ser s) que se utilizará para eliminar
    a un ser de la orilla que invoque dicho método, el ser eliminado se pasa como parámetro.
    
    El método muestraOrilla() se implementó para la representación en modo texto del contenido 
    de la orilla que lo invoque, este método sería inútil en la actualidad ya que se ha utilizado 
    un interface gráfico para representar las evoluciones de las orillas.
    Pero se ha dejado por motivos de depuración.
    
    Por último el método colocaEnOrilla(ser s), que como su propio nombre indica ubicará un ser en la
    orilla especificada.
*/
class orilla{
	String nombre;
	ser huespedes[]=new ser[3];
	boolean barquero;
	
	public orilla(String m,ser o, ser l, ser le){
		nombre=m;
		huespedes[0]=o;
		huespedes[1]=l;	
		huespedes[2]=le;
	}
	
	void quitaSer(ser s){
		int a=0,b;
		//ser ser=new ser();
		while ((huespedes[a]!=null)&&(s.tipo!=huespedes[a].tipo)){
			a++;
		}		
		if(huespedes[a]==null){
			System.out.println("No se encuentra al animal en la orilla.");
		}else{
			System.out.println("El animal ha embarcado con éxito.");
			huespedes[a]=null;//Animal quitado!!!
			b=a+1;
			while(b<huespedes.length){
			    huespedes[a]=huespedes[b];//hemos echado uno hacia atrás!!
			    huespedes[b]=null;
			    a++;
			    b++;
			}
		}
		
	}
//He cambiado el parametro a private por si falla!! 
//private==solo visible en la clase!!
/*Muestra el contenido de una orilla por pantalla.*/
	private void muestraOrilla(){
		System.out.println();
		for (int i=0;i<this.huespedes.length;i++){
			if((huespedes[i]!=null)&&(huespedes[i] instanceof lechuga)){
				System.out.println("La lechuga está en la posición: "+(i+1));
			}else if((huespedes[i]!=null)&&(huespedes[i] instanceof oveja)){
				System.out.println("La oveja está en la posición: "+(i+1));
			}else if((huespedes[i]!=null)&&(huespedes[i] instanceof lobo)){
				System.out.println("El lobo está en la posición: "+(i+1));
			}else{
				System.out.println("Animal desconocido.");
			}
		}
	}
	    
	void colocaEnOrilla(ser s){
		int b=0;
		while((huespedes[b]!=null)&&(b<huespedes.length)){
			b++;
		}
		if(huespedes[b]==null){
			huespedes[b]=s;
			System.out.println("Animal ubicado en orilla.");
		}else{
			System.out.println("Orilla completa.");
		}
	}

}

/**

    La clase lechuga se extiende de la clase ser y únicamente añade el método come que será una de las
    reglas que el programa debe tener en cuenta ya que no puede estar un animal al lado de otro 
    que se lo pueda comer si no está el barquero en la misma orilla.
*/
class lechuga extends ser{
	lechuga(String nombre){
		localizacion=nombre;
		tipo="lechuga";
		System.out.println("Lechuga inicializada.");
	}

	boolean come(ser s){
		System.out.println("La lechuga no se puede comer a nadie.");
		return false;
	}

	
}

/** 
    La clase oveja es también otra extensión de la clase ser y al igual que la lechuga implenta 
    un come para definir a quien se puede comer la oveja.
    
    Implementa como se puede ver su propio constructor pasándole como parámetro una cadena.
    @param nombre
*/
class oveja extends ser{
	oveja(String nombre){
		localizacion=nombre;
		tipo="oveja";
		System.out.println("Oveja inicializada.");
	}

	boolean come(ser s){
		if(s instanceof lechuga){
			System.out.println("La oveja se ha comido la lechuga de la orilla: "+s.localizacion);
			return true;
		}
		System.out.println("A la oveja no le gusta este animal.");
		return false;
	}

}
/**
    Con la clase lobo ocurre lo mismo que con las dos anteriores.
*/
class lobo extends ser{
	lobo(String nombre){
		localizacion=nombre;
		tipo="lobo";
		System.out.println("Lobo incializado.");
	}
	
	void muestraLobo(){
	     System.out.println("Tipo de animal: "+tipo);  
	     System.out.println("Localizacion de animal: "+localizacion);  
	}
	boolean come(ser s){
		if (s instanceof oveja){
			System.out.println("El lobo se ha comido a la oveja de la orilla: "+s.localizacion);
		}
		System.out.println("Al lobo no le gusta este animal.");
		return false;
	}
}

class problema{
	
	/*public static void main(String args[]){
		oveja doly;
		lechuga le;
		lobo l;
		String o1="orilla A";
		String o2="orilla B";
		le=new lechuga(o1);
		doly=new oveja(o1);
		l=new lobo(o1);
		orilla a,b;
		a=new orilla(o1,doly,l,le);
		b=new orilla(o2,null,null,null);*/
		/*SOLUCION*/
		/*a.quitaSer(doly);
		b.colocaEnOrilla(doly);
		//granjero vuelve a la orilla "a"
		a.quitaSer(l);
		b.colocaEnOrilla(l);
		b.quitaSer(doly);
		a.colocaEnOrilla(doly);
		a.quitaSer(le);
		b.colocaEnOrilla(le);
		a.quitaSer(doly);
		b.colocaEnOrilla(doly);
		b.muestraOrilla();
	}*/
}
