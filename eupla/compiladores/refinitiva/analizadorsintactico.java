/*Analizador Sint�ctico*/


/*Para gestionar los errores se puede especificar la cadena incorrecta
ya que est� en el obj con el que realizamos el interface.*/
import java.io.*;

public class analizadorsintactico{
    lex l;
    BufferedReader br;
    boolean valido;
    
    public analizadorsintactico(BufferedReader b){
        br=b;   
        l=new lex(br);//inicializar el analizador l�xico!!!
        System.out.println("Analizador sint�ctico inicialiado.");
    }
    
    public void sintaxis(){
        //tendria que hacer una llamada a un metodo del lexico 
        //que nosponga en una variable obj la cadena de texto y el tipo que 
        //tiene.
        //Deberia ser : l.verificaToken();
        
        //hacer el obj publico en la clase!!! para que no haga falta pasarlo
        //como par�metro.
        while((l.devuelveObj().cadena!=null)&&(valido)){
            //llamamos a produccion.
            produccion();
            l.verificaToken();
        }
        if(valido){
            System.out.println("Fichero v�lido.");
        }else{
            System.out.println("Fichero err�neo.");
        }
    }
    
    void produccion(){
        if(devuelveObj().tipo=="IDENTIFICADOR"){
            l.verificaToken();
            if(devuelveObj().tipo=="="){
                expresion();
                if(devuelveToken()!="."){
                    valido=false;
                    System.out.println("ERROR deber�a ser un PUNTO.");
                }
            }else{
                valido=false;
                System.out.println("ERROR deber�a ser IGUAL.");
            }
        }else{
            valido=false;
            System.out.println("ERROR deber�a ser un IDENTIFICADOR.");
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
                System.out.println("ERROR deber�a ser ")".");
            }
        }else if(devuelveObj().tipo=="["){
            expresion();
            if(devuelveObj().tipo!="]"){
                valido=false;
                System.out.println("ERROR deber�a ser "]"");
            }
        }else if(devuelveObj().tipo=="{"){
            expresion();
            if(devuelveObj().tipo!="}"){
                valido=false;
                System.out.println("ERROR deber�a ser "}"");
            }
        }else if(!((devuelveObj().tipo=="IDENTIFICADOR")||(devuelveObj().tipo=="CADENA"))){
            valido=false;
            System.out.println("ERROR deber�a ser un identificador, una cadena, o un corchete, una llave o un par�ntesis");
        }
        l.verificaToken();
    }
}