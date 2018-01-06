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

        public void view(){
                System.out.println("Nombre: "+name);
                System.out.println("Tipo2: "+kind);
                System.out.println("Anidamiento: "+level);
                System.out.println("SigDireccion: "+nextAdr);
                System.out.println("*********************");
        }
}

class Struct{
        Struct baseTyp;         // Este campo es para los array's!!!
        //Tb tiene utilidad en los registros
        int form;               // Es para indicar un tipo construido, registro....
        int size;               // El tama¤o en bytes de los tipos
        Obj link;               // Para indicar el comienzo de las variables en un procedimiento
}

/*Es una clase auxiliar creada por mi!!!*/
class List{
        String s;
        List sig;

        void view(){
                System.out.println("Cadena: "+s);
        }
}

//Recordar q tenemos q generar una lista con las estructuras de los tipos
//b sicos!!!

//Tenemos q crear un metodo para asignar el tipo de la variable!!!

class tabla{
        // posibles tips de las variables!!
	static final int undef = 0;
	static final int integer = 1;
	static final int bool = 2;
        static final int flotante = 3;

        // formas para los tipos  (modes)
        static final int vars = 0;
        static final int reg = 1;
        static final int vector = 2;
        static final int proc = 3;      //???
        static final int head = 4;

        // VARIABLES INTERNAS
        private static Obj topScope;    // El ambito actual 
        private static Obj undefObj;        // Nodo objeto para los simbolos incorrectos
        static int curLevel;            // Representa el nivel actual de anidamiento
        private static List l;          // Para almacenar una lista de identificadores

        /*No se como hay q a¤adirlas*/
        static void add(String a){
                if(l==null){
                        l=new List();
                        l.s=new String(a);
                        l.sig=null;
                }else{
                        List aux2,aux = new List();
                        aux.s=new String(a);
                        aux.sig=null;
                        aux2=l;
                        while(aux2.sig != null){
                                aux2 = aux2.sig;
                        }
                        aux2.sig=aux;
                }
        }

        static void recorre(List a){
                List aux;
                aux=l;
                while(aux != null){
                        aux.view();
                        aux = aux.sig;
                }
        }

        /*Lo que hacemos al entrar a un nuevo ambito*/
        static void EnterScope(){
		Obj scope = new Obj();
                scope.name = ""; scope.type = null; scope.kind = head; 
		scope.locals = null; scope.nextAdr = 3;
		scope.next = topScope; topScope = scope; 
		curLevel++;
        }

        /* Para crear un tipo!! */
        /* static Struct NewStruct(){
                Struct struct = new Struct();
                return struct;
        } */

        /*Para crear un objeto variable*/
        static void NewObj1 () {
		Obj p, obj = new Obj();
                obj.name = new String(l.s); obj.type = null; obj.kind = vars;
		obj.level = curLevel;
		p = topScope.locals;
		while (p != null) { 
                        if (p.name.equals(l.s)) //Parser.SemError(1);
			p = p.next;
		}
                /*FIFO!!*/
                obj.next = topScope.locals; topScope.locals = obj;
                //if (kind == vars) {}
                obj.adr = topScope.nextAdr; topScope.nextAdr++;
                obj.view();
                while (l.sig != null){
                        l=l.sig;
                        Obj obj2 = new Obj();
                        obj2.name = new String(l.s); obj.type = null; obj.kind = vars;
                        obj2.level = curLevel;
                        p = topScope.locals;
                        while (p != null) { 
                                if (p.name.equals(l.s)) //Parser.SemError(1);
                                p = p.next;
                        }
                        obj2.adr = topScope.nextAdr; topScope.nextAdr++;
                        obj2.view();
                }
                //return obj;
        }

        static void Init(){
		topScope = null;
                l=null;
                curLevel = 0;
		undefObj = new Obj();
		undefObj.name  =  "";
                undefObj.type = null;
		undefObj.kind  = vars;
                undefObj.adr = 0;
		undefObj.level = 0;
                undefObj.next = null;
                
        }
}
