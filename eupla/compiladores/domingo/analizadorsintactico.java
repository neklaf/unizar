/*Analizador Sint�ctico*/


/*Para gestionar los errores se puede especificar la cadena incorrecta
ya que est� en el obj con el que realizamos el interface.*/
import java.io.*;

public class analizadorsintactico{
    //Analizador l�xico!!!
    lex l;
    BufferedReader br2;
    boolean valido=true;
    String contenido="";
    public static final String fichero="c:/juegos/compiladores/texto3.dat";
    
    public analizadorsintactico(BufferedReader b){
        br2=b;   
        l=new lex(br2);//inicializar el analizador l�xico!!!
        System.out.println("Analizador sint�ctico inicializado.");
    }
    
    public void sintaxis(){
        l.verificaLinea();
        contenido=contenido+l.o.cadena;
        while((l.devuelveObj().tipo!=null)&&(valido)){
            produccion();
            l.verificaLinea();
            contenido=contenido+l.o.cadena;
        }
        if(valido){
            System.out.println("Fichero v�lido.");
        }else{
            System.out.println("Fichero err�neo.");
        }
        
        System.out.println("hemos analizado:"+contenido);
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
        l.verificaLinea();
        contenido=contenido+l.o.cadena;
        do{
            factor();
        }while((l.devuelveObj().tipo=="IDENTIFICADOR")||(l.devuelveObj().tipo=="CADENA")||(l.devuelveObj().tipo=="{")||(l.devuelveObj().tipo=="[")||(l.devuelveObj().tipo=="("));
    }
    
    void factor(){
        if(l.devuelveObj().tipo=="("){
            expresion();
            if(l.devuelveObj().tipo!=")"){
                valido=false;
                System.out.println("ERROR deber�a ser ')'.");
            }
        }else if(l.devuelveObj().tipo=="["){
            expresion();
            if(l.devuelveObj().tipo!="]"){
                valido=false;
                System.out.println("ERROR deber�a ser ']'");
            }
        }else if(l.devuelveObj().tipo=="{"){
            expresion();
            if(l.devuelveObj().tipo!="}"){
                valido=false;
                System.out.println("ERROR deber�a ser '}'");
            }
        }else if(!((l.devuelveObj().tipo=="IDENTIFICADOR")||(l.devuelveObj().tipo=="CADENA"))){
            System.out.println("cadena:"+l.o.cadena);
            System.out.println("tipo:"+l.o.tipo);
            valido=false;
            System.out.println("ERROR deber�a ser un identificador, una cadena, o un corchete, una llave o un par�ntesis");
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
            //Ahora est� el contenido del fichero entero en la variable file del lexico!!
            as.l.pasaContenido();
            as.sintaxis();
            
        }catch(IOException e){
            System.out.println("ERROR AL CREAR EL FLUJO.");
        }
    }
}