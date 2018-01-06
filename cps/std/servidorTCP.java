import java.io.*; 
import java.net.*; 

class servidorTCP{ 

  public static void main(String args[]){ 
  
 // Primero indicamos la dirección IP local 
 try{ 
   System.out.println("LocalHost = " + InetAddress.getLocalHost().toString()); 
   } catch (UnknownHostException uhe){ 
   System.err.println("No puedo saber la dirección IP local : " + uhe); 
   } 
  
  
 // Abrimos un "Socket de Servidor" TCP en el puerto 1234. 
 ServerSocket ss = null; 
 try{ 
   ss = new ServerSocket(1234); 
   } catch (IOException ioe){ 
   System.err.println("Error al abrir el socket de servidor : " + ioe); 
   System.exit(-1); 
   } 

  
 //int entrada; 
 //long salida; 
 String str;
 String salida;
 // Bucle infinito 
 while(true){ 
   try{ 
   // Esperamos a que alguien se conecte a nuestro Socket 
 
   Socket sckt = ss.accept(); 
 
   // Extraemos los Streams de entrada y de salida 
   DataInputStream dis = new DataInputStream(sckt.getInputStream()); 
 
   DataOutputStream dos = new DataOutputStream(sckt.getOutputStream()); 
 
   // Podemos extraer información del socket 
   // Nº de puerto remoto 
   int puerto = sckt.getPort(); 
 
   // Dirección de Internet remota 
   InetAddress direcc = sckt.getInetAddress(); 
   
   // Leemos datos de la peticion 
 
   str = dis.readUTF();
   String aux = new String(str);
   aux = aux.toUpperCase(); 
   int cambios = 0;
   //Calculamos resultado 
  for (int i = 0; i < str.length(); i++)
  {
	  if (aux.charAt(i) != str.charAt(i))
	  {
		cambios++;
	  }
  }
  
   salida = aux +'$'+cambios; 

   // Escribimos el resultado 
 
   dos.writeUTF(salida); 
   // Cerramos los streams 
   dis.close(); 
   dos.close(); 
 
   sckt.close(); 
 
 
   // Registramos en salida estandard  
   System.out.println(  "Cliente = " + direcc + ":" + puerto + 
  "\tEntrada = " + str + 
  "\tSalida = " + aux ); 
   } catch(Exception e){ 
   System.err.println("Se ha producido la excepción : " +e); 
   } 
   } 
 
 } 
  
 } 

