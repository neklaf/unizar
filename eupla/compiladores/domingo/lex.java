/*Analizador léxico*/
/*Falta crear interfaz de entrada*/

import java.io.*;
import java.util.StringTokenizer;

/*Será la clase que recibirá el sintáctico*/
class obj{
    public String cadena,tipo;
    //Constructor
    public obj(){
        cadena="";
        tipo="";
    }
}

public class lex{
   public String cad,file;
   public StringTokenizer linea;
   public int ln;   
   public StringTokenizer l;
   BufferedReader lector;
   //Estaes la variable que deberá llevar el 
   obj o=new obj();
       
    //Constructor
    public lex(BufferedReader d){
        lector=d;
        file="";
        ln=1;
        System.out.println("Inicializado.");
    }
         
    void verificaToken(String t,StringTokenizer st){
        o=new obj();//
        String aux="";
        //aqui estarán los tokens libres de espacios en blanco!!!
        //seran trozos de caracteres puros
        StringTokenizer sinesp=new StringTokenizer(t);
        //tokens=sinesp;
        while(sinesp.hasMoreTokens()!=false){
                //Convertimos el string a un array
            aux=sinesp.nextToken();//esta es la cadena a observar
                //System.out.println("Cadena sin espacios:"+aux);
                char array[]=aux.toCharArray();
                cad="";//inicialiamos la cadena que le entregaremos al Sintactico!!
                //reconocemos identificador!!!
                if((((int)array[0]>='A')&&((int)array[0]<='Z'))||(((int)array[0]>='a')&&((int)array[0]<='z'))){
                    o.cadena=aux;
                    o.tipo="IDENTIFICADOR";
                }else if(((int)array[0]>='0')&&((int)array[0]<='9')){
                    //cad="ERROR!!";
                    o.cadena=aux;
                    o.tipo="OTRO";
                    //System.out.println(cad);
                }else if(array[0]=='"'){
                //Le asignaremos a cad lo que le queda hasta llegar a las siguientes comillas dobles!! o hasta de fichero
                //El fallo está aquí!!!!!!
                //!!!Aqui habria que poner un try para recoger NoSuchElementException
                    try{
                        aux=st.nextToken("\"");
                        o.cadena=aux;
                        o.tipo="CADENA";
                        st.nextToken();//Para saltar el token que tiene las " finales!!!
                        //linea=new StringTokenizer(st.nextToken(),"=.|()[]{}\"",true);
                    }catch(Exception e){
                        o.cadena="";
                        o.cadena="ERROR";
                        System.out.println("ERROR!");
                        /*StringTokenizer sp2=new StringTokenizer(aux,"=.|()[]{}\"",true);
                        verificaToken(sp2.nextToken(),sp2);*/
                    }               
                }
                else{
                    switch(array[0]){
                        case '(':
                            cad="(";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("parentesis izq "+cad);
                            break;
                        case ')':
                            cad=")";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("parentesis der "+cad);
                            break;
                        case '{':
                            cad="{";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("llave izq "+cad);
                            break;
                        case '}':
                            cad="}";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("llave der "+cad);
                            break;                    
                        case '[':
                            cad="[";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("corchete izq "+cad);
                            break;                    
                        case ']':
                            cad="]";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("corchete der "+cad);
                            break;                    
                        case '|':
                            cad="|";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("barra "+cad);
                            break;                    
                        case '=':
                            cad="=";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println("igual "+cad);
                            break;                    
                        case '.':
                            cad=".";
                            o.cadena=cad;
                            o.tipo=cad;
                            break;
                        default:
                            cad="DESCONOCIDO";
                            o.cadena=cad;
                            o.tipo=cad;
                            //System.out.println(cad);
                    }
               }  
          }           
    }
    void verificaLinea(){
              
        try{
            //verificaToken(linea.nextToken(),linea);
            System.out.println("Token:"+linea.nextToken());
        }catch(Exception e){
            System.out.println("final");
            o.cadena="";
            o.tipo=null;
        }
    }
    
    public obj devuelveObj(){
        return o;
    }
    
    void QuitaEspacios(){
        String aux="",file2="";
        StringTokenizer st1=new StringTokenizer(file,"\"",true);
        try{
            while(st1.hasMoreTokens()){
                aux=st1.nextToken();
                char array2[]=aux.toCharArray();
                if(array2[0]=='"'){
                    file2=file2+aux;
                    try{
                        String aux2=st1.nextToken("\"");
                        String aux3=st1.nextToken();
                        file2=file2+aux2+aux3;
                }catch(Exception e){
                    System.out.println("FALLO DE ST1");
                }
                }else{
                    StringTokenizer st2=new StringTokenizer(aux);
                    try{
                        while(st2.hasMoreTokens()){
                            file2=file2+st2.nextToken();
                        }
                    }catch(Exception e){
                        //System.out.println("FALLO DE ST2");
                    }
                }
            }
        }catch(Exception e){
           System.out.println("EXCEPCION GENERAL.");
        }
        this.file=file2;
        System.out.println("espacios quitados");
    }
    
    public void pasaContenido(){
        String li="";
        try{
            while((li=this.lector.readLine())!=null){
                this.file=this.file+li;
            }
        }catch(IOException e){
            System.out.println("ERROR EN LA ENTRADA/SALIDA.");
        }
        QuitaEspacios();
        
        linea=new StringTokenizer(file,"=.|()[]{}\"",true);
        //System.out.println("Archivo pasado.");
    }
        
    /*public static void main(String args[]){             
       //char c;
       //boolean control=true;
        try{
            //AQUI UNICAMENTE PASO EL CONTENIDO DEL FICHERO A UN STRING.
            File f=new File("c:/juegos/compiladores/texto3.dat");
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            lex lexi=new lex(br); 
            while((lexi.linea=lexi.lector.readLine())!=null){
                lexi.file=lexi.file+lexi.linea;
            }
            lexi.verificaLinea(lexi.file);
        } 
        catch(IOException e){
               System.out.println("ERROR AL CREAR EL FLUJO.");
        }
    }*/
}
