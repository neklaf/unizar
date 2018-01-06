/*Tenemos q tener en cuenta q alguno de los campos de Obj ahora seran
innecesarios ya q hemos a¤adido la clase Struct q est  implementada
de una manera totalmente diferente.!!!*/

class Obj {
        String name;  // nombre del objeto
        Struct type;  // tipo del objeto en cuestion
        Obj next;     // el siguiente objeto del mismo ambiente
        int kind;     // Para decir si es una variable...
        int adr;      // direccion de memoria para el procedimiento
        int level;    // nivel de anidamiento para la declaracion !!
        Obj locals;   // scopes: para los objetos declarados localmente
        int nextAdr;  // scopes: la siguiente direccion libre en este ambiente
	Object val;   // Representa el valor q poseerá la variable.
	boolean block; //Para bloquear el valor de la variable!! default=false

        public void view(){
                System.out.println("	Nombre: "+name);
		System.out.println("	Tipo: "+type.id);
		System.out.println("	Val: "+((Integer)val).intValue());
		if(block==true){
			System.out.println("	BLOQUEADA");
		}else{
			System.out.println("	DESBLOQUEADO");
		}
                //System.out.println("	Tipo2: "+kind);
                //System.out.println("	Anidamiento: "+level);
                //System.out.println("	SigDireccion: "+nextAdr);
                System.out.println("	*********************");
		System.out.println();
        }
}

class Struct{
        Struct baseTyp;         // Este campo es para los array's!!!
        //Tb tiene utilidad en los registros extendidos de otros
        int form;               // ?? Es para indicar un tipo construido, registro....
        String id;
	int size;               // El tamano en bytes de los tipos
        Obj link;               // ?? Para indicar el comienzo de las variables en un proc
	//El campo link sirve para apuntar los campos del registro n cuestion!!!	
	Struct sig; 
	
  /*Constructor de uso interno*/ 
   public Struct(String a, int b){
	id=a;
	size=b;	
	form=0;
   }

   /*Constructor para utilizar en el oberon.atg!!!*/
   public Struct(String b){
	id=b;
	size=0;
	form=0;
   }
   
   public void view(){
	Obj li=link;
	System.out.println("Id de tipo: "+id);
	System.out.println("Size: "+size);
	if(form==1){
		System.out.println("Forma: REGISTRO");
		System.out.println();
		while(li!=null){
			li.view();
			li=li.next;
		}
	}else if(form==2){
		System.out.println("Forma: VECTOR");
	}else{
		System.out.println("Forma: PROCEDIMIENTO");
	}	
	System.out.println();
	while(li!=null){
		li.view();
		li=li.next;
	}
	//System.out.println("*********************");
	//System.out.println();
   }
}

class Scope{
	//int nextAdr;
	Obj locals;		
	int level;	//Para ayudar al ambito creado por los registros!!
	Scope sig;
	Scope ant;
	
	public Scope(int a){
		level=a;
		locals=null;
		sig=null;
	}

	void view(){
		Obj var=this.locals;
		System.out.println("Nivel: "+level);
		System.out.println("Variables: ");
		while(var!=null){
			System.out.println("Nombre Var: "+var.name);
			var=var.next;
		}
		System.out.println("****************");
		System.out.println();
	}
}


/*Recordar q tenemos q generar una lista con las estructuras de los tipos
basicos!!!*/

//Tenemos q crear un metodo para asignar el tipo de la variable!!!

class tabla{
        // posibles tips de las variables!!
	static final int undef = 0;
	static final int bool = 1;
	static final int integer = 2;
	static final int flotante = 3;
	static final int cte = 4;

        // formas para los tipos (forms)
    	static final int vars = 0;
        static final int reg = 1;
        static final int vector = 2;
        static final int proc = 3;      //???

        // VARIABLES INTERNAS
        static Scope topScope;    // El ambito actual 
        private static Obj undefObj;        // Nodo objeto para los simbolos incorrectos
        static int curLevel;      // Representa el nivel actual de anidamiento
        
		static Obj aux; //Lista de variables del mismo tipo!!!
		static Struct lt;  //Lista d los tipos del fichero fuente!!!
		//static Scope Scopes; //Lista de ambitos del fichero fuente!!!

/***********************************OBJ**********************************************/
	
	/*static Obj buscaO(String nb){
		Obj aux2=topScope.locals;
		while(aux2!=null){
			
		}
	}*/

	/*Lo inserto asi por rapidez!!!*/	
        static void add(String a){
        	Obj aux2=new Obj();
                aux2.name=new String(a);
		aux2.kind=vars;
		aux2.block=false;
		aux2.val=new Integer(0);
		//aux.nextAdr=0; ??????????
		aux2.next=aux;
		aux=aux2; 
        }

        static void recorre(){
		Obj aux3=aux;
                while(aux3 != null){
                        aux3.view();
                        aux3 = aux3.next;
                }
        }

	/*Habra q buscar en la lista de tipos d q tipo son las variables en cuestion
	y despues asignar el campo 'type' del obj a ese tipo!!!*/

	static void pontipo(String t){
		Struct s=new Struct(t,0);
		Struct auxt=buscaT(s);
		/*He encontrado el tipo q tengo q insertar*/
		if(aux==null){
			//Parse.SemError(1);
			System.out.println("ERROR TIPO NO ENCONTRADO!!");
		}else{
			Obj aux2=aux;
			while (aux2 != null){
				aux2.type=auxt;
				aux2=aux2.next;
			}
		}
	} 

 	static void insTablaS(){
		/*Insertamos las variables creadas en la tabla de simbolos*/
		Obj aux2=aux;
		while(aux2.next!=null){
			aux2=aux2.next;
		}
		aux2.next=topScope.locals;
		topScope.locals=aux;
		aux=null;
	}

	/*Creo q es el modo indicado de insertar aux en cualquier lugar*/	
	static void insAuxR(){
		Obj aux2=aux;
		while(aux2.next!=null){
			aux2=aux2.next;
		}
		aux2.next=lt.link;
		lt.link=aux;
		//aux=null;  //Reseteamos la lista de variables!!
	}
	static void bloquea(boolean y){
		Obj aux2=aux;
		while(aux2!=null){
			aux2.block=y;
			aux2=aux2.next;
		}
	}
/************************************************************************************/
       


/**********************************PRUEBAS***********************************************/

/*Y la posterior asignacion de un nuevo tipo*/
/*Creacion de ambitos diferentes!!!*/

/****************************************************************************************/


/**************************************STRUCT****************************************/
  
  static void recorreT(){
	Struct aux=lt;
	while(aux!=null){
		aux.view();
		aux=aux.sig;
	}
  }
	
  /*Se puede optimizar el codigo!!*/	
  static Struct buscaT(Struct t){
	if(lt.id.equals(t.id)==true){
		return(lt);
	}else{
		Struct aux=lt;
		while((aux!=null)&&(!aux.id.equals(t.id))){
			aux=aux.sig;
		}
		return(aux);
	}
  }

  static boolean igualT(Struct a, Struct b){
	return(a.id.equals(b.id));
  }

  static void addT(Struct t){
	t.sig=lt;
	lt=t;
  }

  static void creaTiposB(){
	Struct aux=new Struct("REAL",4);
	addT(aux);
	aux=new Struct("INTEGER",2);
	addT(aux);
	aux=new Struct("BOOLEAN",1);
	addT(aux);
  }
  static int tamReg(){
	int i=0;
	Obj aux2=lt.link;
	if(aux2==null){
		/*El registro no tiene campos!!*/
		return 0;
	}else{
		while(aux2!=null){
			i+=aux2.type.size;
			aux2=aux2.next;
		}
	}
	return (i);
  }

/***********************************************************************************/

	
/********************************SCOPE**********************************************/ 

	/*Lo que hacemos al entrar a un nuevo ambito*/
        static void EnterScope(){
		curLevel++;
		Scope scope = new Scope(curLevel);
		//scope.nextAdr = 3;
		topScope.ant = scope;
		scope.sig = topScope; 
		topScope = scope;
        }

	static void LeaveScope(){
		topScope = topScope.sig; curLevel--;
	}

	static void verScopes(){
		Scope aux=topScope;
		while(aux!=null){
			aux.view();
			aux=aux.sig;
		}			
	}

        /*Para crear un objeto variable*/
        static Obj NewObj (String name,int kind) {
		Obj p, obj = new Obj();
                obj.name = new String(name); 
		obj.type = null; obj.kind = kind;
		obj.level = curLevel;
		p = topScope.locals;
		/*Para buscar si el nb de la variable nueva ya esta!!!*/
		while (p != null) { 
    	        	if (p.name.equals(name)) Parser.SemError(1);
			p = p.next;
		}
         	//FILO!!
                obj.next = topScope.locals; 
		topScope.locals = obj;
           	//if (kind == vars) {}
            	//obj.adr = topScope.nextAdr; topScope.nextAdr++;
            	//obj.view();
		return obj;
        }

        static void Init(){
		topScope =new Scope(0);  //Inicializa el scope actual
		curLevel = 0;  //Inicializa el nivel de anidamiento
		/*No me haría falta para la tabla pero para la generacion??*/
		undefObj = new Obj();
		undefObj.name  =  "";
                undefObj.type = null;
		undefObj.val = null;
		undefObj.kind  = vars;
                undefObj.adr = 0;
		undefObj.level = 0;
                undefObj.next = null;  //Inicaliza el objeto no definido
                creaTiposB();	//Inicializa la lista de tipos
        }
}
