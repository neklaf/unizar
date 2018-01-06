/*Analizador léxico*/
/*REVISAR!!! SE SALE!!!*/

import java.io.*;
import java.util.StringTokenizer;

public class lex{
   private String linea,cad,file;
   private int ln;   
   private StringTokenizer l;
   BufferedReader lector;
   //Constantes
   //public static final int ID=1,CADENA=2,PAR_I=3,PAR_D=4,LLA_I=5,LLA_D=6,COR_I=7,COR_D=8,BARRA_V=9,IGUAL=10,PUNTO=11,DESCONOCIDO=12,EOF=-1;
    
    //Constructor
    public lex(BufferedReader d){
        lector=d;
        file="";
        ln=1;
        System.out.println("Inicializado.");
    }
         
    void verificaToken(String t,StringTokenizer st){
        String aux="";
        //aqui estarán los tokens libres de espacios en blanco!!!
        //seran trozos de caracteres puros
        StringTokenizer sinesp=new StringTokenizer(t);
        while(sinesp.hasMoreTokens()!=false){
            //Convertimos el string a un array
            aux=sinesp.nextToken();//esta es la cadena a observar
            char array[]=aux.toCharArray();
            cad="";//inicialiamos la cadena que le entregaremos al Sintactico!!
            //reconocemos identificador!!!
            if((((int)array[0]>='A')&&((int)array[0]<='Z'))||(((int)array[0]>='a')&&((int)array[0]<='z'))){
                cad="IDENTIFICADOR "+aux;
                System.out.println(cad);
                //Si empieza por un numero el identificador no es correcto.
            }else if(((int)array[0]>='0')&&((int)array[0]<='9')){
                cad="ERROR!!";
                System.out.println(cad);
            }else if(array[0]=='"'){
            //Le asignaremos a cad lo que le queda hasta llegar a las siguientes comillas dobles!! o hasta final de fichero
            //El fallo está aquí!!!!!!
            //!!!Aqui habria que poner un try para recoger NoSuchElementException
                try{
                    aux=st.nextToken("\"");
                    cad="CADENA "+aux;
                    st.nextToken();//Para saltar el token que tiene las " finales!!!
                    System.out.println(cad);
                    verificaLinea(st.nextToken());
                }catch(Exception e){
                    System.out.println("ERROR!");
                    /*StringTokenizer sp2=new StringTokenizer(aux,"=.|()[]{}\"",true);
                    verificaToken(sp2.nextToken(),sp2);*/
                }               
            }
            else{
                switch(array[0]){
                    case '(':
                        cad="(";
                        System.out.println("parentesis izq "+cad);
                        break;
                    case ')':
                        cad=")";
                        System.out.println("parentesis der "+cad);
                        break;
                    case '{':
                        cad="{";
                        System.out.println("llave izq "+cad);
                        break;
                    case '}':
                        cad="}";
                        System.out.println("llave der "+cad);
                        break;                    
                    case '[':
                        cad="[";
                        System.out.println("corchete izq "+cad);
                        break;                    
                    case ']':
                        cad="]";
                        System.out.println("corchete der "+cad);
                        break;                    
                    case '|':
                        cad="|";
                        System.out.println("barra "+cad);
                        break;                    
                    case '=':
                        cad="=";
                        System.out.println("igual "+cad);
                        break;                    
                    case '.':
                        cad=".";
                        System.out.println("punto "+cad);
                        break;                    
                    default:
                        cad="DESCONOCIDO";
                        System.out.println(cad);
                }
            }
        }
    }
    void verificaLinea(String lin){
        //Vamos a pasar la linea a un StringTokenizer!!
        //Para eliminar los espacios en blanco!!
        int i=1;
        String aux="";
        //anterior StringTokenizer s=new StringTokenizer(lin,"\t\r\n=.|()[]{}\"",true);
        StringTokenizer s=new StringTokenizer(lin,"=.|()[]{}\"",true);
        while(s.hasMoreTokens()!=false){
            aux=s.nextToken();
                verificaToken(aux,s);
                /*System.out.println("Token"+i+":"+aux);
                i++;*/
        } 
    }
    
    public static void main(String args[]){             
       char c;
       boolean control=true;
        try{
            File f=new File("c:/juegos/compiladores/texto3.dat");
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            lex lexi=new lex(br); 
            while((lexi.linea=lexi.lector.readLine())!=null){
                lexi.file=lexi.file+lexi.linea;
            }
            //Para marcar el final de fichero.
            //lexi.file+="$";
            lexi.verificaLinea(lexi.file);
        } 
        catch(IOException e){
               System.out.println("ERROR AL CREAR EL FLUJO.");
        }
    }
}
