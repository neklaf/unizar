

import java.util.*;


public class prueba2{
    
    public static void main(String args[]){
        String cadena="aitor =      \"      kiko                \" {        }  [  ].",aux="",file2="";
        StringTokenizer st1=new StringTokenizer(cadena,"\"",true);
        //StringTokenizer st2=new StringTokenizer(cadena);
        
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
        System.out.println(file2);
    }
    
}