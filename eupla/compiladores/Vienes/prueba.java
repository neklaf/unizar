import java.io.*;


public class prueba{
    public static void main(String args[]){
        String linea="";
        //el linea tiene todos los tokens separadoras por ",{,[,(,.,=
        try{
            File f=new File("c:/users/9291/texto3.dat");
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            lex lex=new lex(br);
            lex.pasaContenido();
            
            /*System.out.println("Archivo completo:" +lex.file);
            while(lex.o.cadena!=null){
                lex.verificaLinea();            
            }*/
        }
        catch(IOException e){
            System.out.println("ERROR AL CREAR EL FLUJO.");
        }
        
    }
}