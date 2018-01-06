import java.net.*;  
import java.io.*;  

class servidorUDP{  

public static void main(String args[]){  
int cont = 0;          
// Primero indicamos la dirección IP local  
   try{  
	  System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());  
   } catch (UnknownHostException uhe){  
 	  System.err.println("No puedo saber la dirección IP local : " + uhe);  
   }  
 
 
 // Abrimos un Socket UDP en el puerto 1234.  
 // A través de este Socket enviaremos datagramas del tipo DatagramPacket  

   DatagramSocket ds = null;  
   try{  
  	ds = new DatagramSocket(1234);  
   }catch(SocketException se){  
 	System.err.println("Se ha producido un error al abrir el socket : " + se);  
        System.exit(-1);  
   }  
 

// Bucle infinito  
  while(true){
 	try{  
	  // Preparados para recibir cadena de texto     
	  //byte bufferEntrada[] = new byte[4];
	  byte bufferEntrada[] = new byte[256];
    
	  // Creamos un "contenedor" de datagrama, cuyo buffer  
	  // será el array creado antes  
	  DatagramPacket dp = new DatagramPacket(bufferEntrada,256);
	 
	  // Esperamos a recibir un paquete  
  	  ds.receive(dp);
	  byte p[] = dp.getData();
	  int i = 0;
	  int cambios = 0;
	  String entrada = new String(p);
	  String salida = new String(p);
	  salida = salida.toUpperCase();
	  while ( salida.charAt(i) != '$')
	  {
		if ( p[i] != salida.charAt(i) )
		{
			cambios++;
		}
		i++;
	 }
	  salida = salida.trim()+cambios;
	  System.out.println("Preparado para enviar : "+ salida);
	  // Podemos extraer información del paquete  
	  // Nº de puerto desde donde se envió  
	  int puerto = dp.getPort();  
	  // Dirección de IP desde donde se envió  
	  InetAddress direcc = dp.getAddress();  
    
 	  // Creamos un ByteArrayOutputStream sobre el que podamos escribir  
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	  // Lo empaquetamos en un DataOutputStream  
	  DataOutputStream dos = new DataOutputStream(baos);  
	  // Escribimos el resultado
	  dos.writeBytes(salida);
	  // Cerramos el buffer de escritura  
	  dos.close();  
    
	  // Generamos el paquete de vuelta, usando los datos  
	  // del remitente del paquete original  
	  dp = new DatagramPacket(baos.toByteArray(),salida.length(),direcc,puerto);  
    
	  // Enviamos  
	  ds.send(dp);  
     
	  // Registramos en salida estandard   
	  System.out.println(" Cliente = " + direcc + ":" + puerto);  
          System.out.println(" Entrada = " + entrada.trim().replace('$',' '));  
          System.out.println(" Salida = " + salida.replace('$',' ') );
          System.out.println(); 
  } catch(Exception e){  
     System.err.println("Se ha producido el error " + e);  
  }//fin try  
}// fin while(true)
    
}//fin main  
}//fin clase
