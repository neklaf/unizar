import java.io.*;
import java.net.*;

class clienteUDP {  

public static void main(String args[]){  

// Leemos el primer parámetro, donde debe ir la dirección  
// IP del servidor
if(args.length != 2)
{
	System.out.println("Formato: java clienteUDP nb_maquina \"cadena_texto\" ");
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
 
 	// Creamos el Socket  
	DatagramSocket ds = null;  
	try{  
	   ds = new DatagramSocket();  
	} catch(SocketException se){  
		System.err.println("Error al abrir el socket : " + se);  
		System.exit(-1);  
	}  
	int cont = 0;
	// Para cada uno de los argumentos...  
	for (int n=1;n<args.length;n++){ 
	   // Número máximo de caracteres de una palabra será < 254
	   String palabra = args[n];
	   System.out.println("Cadena enviada por cliente: "+palabra);
	   try{  
	   	// Creamos un buffer para escribir  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	   	DataOutputStream dos = new DataOutputStream(baos);  
	    	// Lo escribimos  
	   	dos.writeBytes(args[n]+'$');
	   	// y cerramos el buffer  
	   	dos.close();  
	   	// Creamos paquete  
	   	DatagramPacket dp = new DatagramPacket(baos.toByteArray(),dos.size(),direcc,puerto);
	  	// y lo mandamos
	   	ds.send(dp);  
	   	// Preparamos buffer para recibir palabras  
	   	byte bufferEntrada[] = new byte[dos.size()+4];
	   	// Creamos el contenedor del paquete  
	   	dp = new DatagramPacket(bufferEntrada,dos.size()+4);  
	   	// y lo recibimos  
	   	ds.receive(dp);
	   	
	   	byte p[] = dp.getData();
	   	String resultado = new String(p);
	   	int pos = 0;
	   	pos = resultado.indexOf("$");
	   	// Indicamos en pantalla  
	   	System.out.println("Solicitud = " + args[n]);
	   	System.out.println("Resultado = " +resultado.replace('$',' '));  
	   	System.out.println("Número de caracteres cambiados : "+ resultado.charAt(pos+1)+" de la palabra número "+n);
	   	System.out.println();
     	    } catch (Exception e){  
	     	System.err.println("Se ha producido un error : " + e);  
     	    }//fin catch  
  	}//fin for1
}//fin del else	
}// fin main  
}//fin cliente
