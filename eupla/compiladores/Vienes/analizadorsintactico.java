/*Analizador Sintáctico*/


/*Para gestionar los errores se puede especificar la cadena incorrecta
ya que está en el obj con el que realizamos el interface.*/
import java.io.*;

public class analizadorsintactico{
    //Analizador léxico!!!
    lex l;
    BufferedReader br2;
    boolean valido=true;
    String contenido="";
    public static final String fichero="c:/users/9291/texto3.dat";
    //Constructor!!
    public analizadorsintactico(BufferedReader b){
        br2=b;   
        l=new lex(br2);//inicializar el analizador léxico!!!
        System.out.println("Analizador sintáctico inicializado.");
    }
    
    public void sintaxis(){
        //tendria que hacer una llamada a un metodo del lexico 
        //que nos ponga en una variable obj la cadena de texto y el tipo que 
        //tiene.
        //Deberia ser : 
        l.verificaLinea();
        //System.out.println(l.o.cadena);
        //System.out.println(l.o.tipo);
        contenido=contenido+l.o.cadena;
        //hacer el obj publico en la clase!!! para que no haga falta pasarlo
        //como parámetro.
        while((l.devuelveObj().cadena!=null)&&(valido)){
            //llamamos a produccion.
            produccion();
            l.verificaLinea();
        }
        if(valido){
            System.out.println("Fichero válido.");
        }else{
            System.out.println("Fichero erróneo.");
        }
    }
    
    void produccion(){
        
        //contenido=contenido+l.o.cadena;
        if(l.devuelveObj().tipo=="IDENTIFICADOR"){
            l.verificaLinea();
            contenido=contenido+l.o.cadena;
            if(l.devuelveObj().tipo=="="){
                expresion();
                if(l.devuelveObj().tipo!="."){
                    valido=false;
                    System.out.println("ERROR debería ser un PUNTO.");
                }
            }else{
                valido=false;
                System.out.println("ERROR debería ser IGUAL.");
            }
        }else{
            valido=false;
            System.out.println("ERROR debería ser un IDENTIFICADOR.");
        }
        System.out.println(contenido);
    }
    
    void expresion(){
        termino();
        while(l.devuelveObj().tipo=="BARRA"){
            termino();
        }
    }
    
    void termino(){
        l.verificaLinea();
        contenido=contenido+l.o.cadena;
        do{
            factor();
        }while((l.devuelveObj().tipo=="IDENTIFICADOR")||(l.devuelveObj().tipo=="CADENA")||(l.devuelveObj().tipo=="{")||(l.devuelveObj().tipo=="}")||(l.devuelveObj().tipo=="[")||(l.devuelveObj().tipo=="]")||(l.devuelveObj().tipo=="(")||(l.devuelveObj().tipo==")"));
    }
    
    void factor(){
        if(l.devuelveObj().tipo=="("){
            expresion();
            if(l.devuelveObj().tipo!=")"){
                valido=false;
                System.out.println("ERROR debería ser ')'.");
            }
        }else if(l.devuelveObj().tipo=="["){
            expresion();
            if(l.devuelveObj().tipo!="]"){
                valido=false;
                System.out.println("ERROR debería ser ']'");
            }
        }else if(l.devuelveObj().tipo=="{"){
            expresion();
            if(l.devuelveObj().tipo!="}"){
                valido=false;
                System.out.println("ERROR debería ser '}'");
            }
        }else if(!((l.devuelveObj().tipo=="IDENTIFICADOR")||(l.devuelveObj().tipo=="CADENA"))){
            System.out.println("cadena:"+l.o.cadena);
            System.out.println("tipo:"+l.o.tipo);
            valido=false;
            System.out.println("ERROR debería ser un identificador, una cadena, o un corchete, una llave o un paréntesis");
        }
        l.verificaLinea();
        contenido=contenido+l.o.cadena;
    }
    
    public static void main(String args[]){
        try{
            File f=new File(fichero);
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            analizadorsintactico as=new analizadorsintactico(br);
            //Ahora está el contenido del fichero entero en la variable file del lexico!!
            as.l.pasaContenido();
            as.sintaxis();
            
        }catch(IOException e){
            System.out.println("ERROR AL CREAR EL FLUJO.");
        }
    }
}