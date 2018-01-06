import java.io.*; 
import java.net.*; 

class clienteTCP { 

  public static void main(String args[]){ 
  
 // Leemos el primer parámetro, donde debe ir la dirección 
 // IP del servidor 
 if ( args.length != 2)
 {
	 System.out.println("Formato: java clienteTCP nb_maquina \"cadena_txt\"");
}else{	 
 InetAddress direcc = null; 
 try{ 
   direcc = InetAddress.getByName(args[0]); 
   } catch(UnknownHostException uhe){ 
   System.err.println("Host no encontrado : " + uhe); 
   System.exit(-1); 
   } 
  
 // Puerto que hemos usado para el servidor 
 int puerto = 1234; 
 String str = "";
 // Para cada uno de los argumentos... 
 for (int n=1;n<args.length;n++){ 
   Socket sckt = null;
   str = str+" "+args[n];
   DataInputStream dis = null; 
   DataOutputStream dos = null; 
   try{ 
 
   // Convertimos el texto en número 
   //int numero = Integer.parseInt(args[n]); 
 
   // Creamos el Socket 
 
   sckt = new Socket(direcc,puerto); 
 
   // Extraemos los streams de entrada y salida 
   dis = new DataInputStream(sckt.getInputStream()); 
 
   dos = new DataOutputStream(sckt.getOutputStream());   
 
 
   // Lo escribimos 
   dos.writeUTF(str); 
    
   // Leemos el resultado final 
 
   String resultado = dis.readUTF();
   int pos = resultado.indexOf('$');
   
   String cambios = resultado.substring(pos+1);
   resultado = resultado.substring(0, pos);
 
   // Indicamos en pantalla 
   System.out.println("Solicitud = " + str); 
   System.out.println("Resultado = " +resultado );
   System.out.println("Numero de caracteres cambiados = " + cambios); 
   // y cerramos los streams y el socket 
   dis.close(); 
   dos.close(); 
   } catch(Exception e){ 
   System.err.println("Se ha producido la excepción : " +e); 
   } 
    
   try{ 
   if (sckt!=null) sckt.close(); 
   } catch(IOException ioe){ 
  System.err.println("Error al cerrar el socket : " + ioe); 
  } 
 
   } 
}
 } 
  }

