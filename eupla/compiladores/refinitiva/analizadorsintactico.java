/*Analizador Sintáctico*/


/*Para gestionar los errores se puede especificar la cadena incorrecta
ya que está en el obj con el que realizamos el interface.*/
import java.io.*;

public class analizadorsintactico{
    lex l;
    BufferedReader br;
    boolean valido;
    
    public analizadorsintactico(BufferedReader b){
        br=b;   
        l=new lex(br);//inicializar el analizador léxico!!!
        System.out.println("Analizador sintáctico inicialiado.");
    }
    
    public void sintaxis(){
        //tendria que hacer una llamada a un metodo del lexico 
        //que nosponga en una variable obj la cadena de texto y el tipo que 
        //tiene.
        //Deberia ser : l.verificaToken();
        
        //hacer el obj publico en la clase!!! para que no haga falta pasarlo
        //como parámetro.
        while((l.devuelveObj().cadena!=null)&&(valido)){
            //llamamos a produccion.
            produccion();
            l.verificaToken();
        }
        if(valido){
            System.out.println("Fichero válido.");
        }else{
            System.out.println("Fichero erróneo.");
        }
    }
    
    void produccion(){
        if(devuelveObj().tipo=="IDENTIFICADOR"){
            l.verificaToken();
            if(devuelveObj().tipo=="="){
                expresion();
                if(devuelveToken()!="."){
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
    }
    
    void expresion(){
        termino();
        while(l.devuelveObj().tipo=="BARRA"){
            termino();
        }
    }
    
    void termino(){
        l.verificaToken();
        do{
            factor();
        }while((devuelveObj().tipo=="IDENTIFICADOR")||(devuelveObj().tipo=="CADENA")||(devuelveObj().tipo=="{")||(devuelveObj().tipo=="}")||(devuelveObj().tipo=="[")||(devuelveObj().tipo=="]")||(devuelveObj().tipo=="(")||(devuelveObj().tipo==")"));
    }
    
    void factor(){
        if(devuelveObj().tipo=="("){
            expresion();
            if(devuelveObj().tipo!=")"){
                valido=false;
                System.out.println("ERROR debería ser ")".");
            }
        }else if(devuelveObj().tipo=="["){
            expresion();
            if(devuelveObj().tipo!="]"){
                valido=false;
                System.out.println("ERROR debería ser "]"");
            }
        }else if(devuelveObj().tipo=="{"){
            expresion();
            if(devuelveObj().tipo!="}"){
                valido=false;
                System.out.println("ERROR debería ser "}"");
            }
        }else if(!((devuelveObj().tipo=="IDENTIFICADOR")||(devuelveObj().tipo=="CADENA"))){
            valido=false;
            System.out.println("ERROR debería ser un identificador, una cadena, o un corchete, una llave o un paréntesis");
        }
        l.verificaToken();
    }
}