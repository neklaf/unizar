/*
 * PuntoDistribucion.java
 *
 * Created on 14 de febrero de 2005, 21:10
 */

package MantenimientoDB;

import java.util.*;
import MantenimientoDB.*;
import ConexionBD.*;
import Central.*;
import PuntoDeVenta.*;
import javax.jdo.*;

/**
 *
 * @author  Ignacio Carcas
 */
public class PuntoDistribucion {
    
    /** Creates a new instance of PuntoDistribucion */
    public PuntoDistribucion() {
    }
    
    
    /**
     *Se encarga de modificar un Punto de Distribuci�n dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(String localizacion, String tfno){
        //Modificar en la BD de la sede central
        
        //Modificar en la BD del nodo
        
        int ok = 0;
        String mensaje = "";
                
        PersistenceManager conexiones[] = new Utilities().obtenerConexiones(null);
                
        if (conexiones[0] != null){
            try {
                 
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                //MODIFICAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                Object obj1 = new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+constantes.NODO_PROPIO, constantes.NODO_CENTRAL);
                CentralPuntoDistribucion pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(obj1,false);
                
                if (pd==null){
                    mensaje = "El punto de distribucion no existe, no puede ser modificado";
                    throw new Exception("El punto de distribucion no existe");
                }
                
                //Modificar sus propiedades
                pd.setLocalizacion(localizacion);
                pd.setTfno(tfno);
                //asi sucesivamente
                
                
                //MODIFICAMOS DE LOS PUNTOS DE VENTA
                for (int i=1;i<conexiones.length;i++){
                    //Obtenemos el objeto
                    Object obj = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class, "this.zona=="+constantes.NODO_PROPIO, i+1);
                    PVPuntoDistribucion pdventa = (PVPuntoDistribucion) conexiones[i].getObjectById(obj,false);

                    if (pd==null){
                        mensaje = "El punto de distribucion no existe, no puede ser modificado";
                        throw new Exception("El punto de distribucion no existe");
                    }
                   
                    //Modificar sus propiedades
                    pdventa.setLocalizacion(localizacion);
                    pdventa.setTfno(tfno);
                }
                
                //Cerramos la transacci�n
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
                ok = 1;
            }catch (RuntimeException x) {
                System.out.println("Error en modificar punto de distribucion: " + x.getMessage());
                ok = 0;
            }catch (Exception e){
                System.out.println("Error en modificar punto de distribucion: " + e.getMessage());
                ok = 0;
            }

            // Close the PersistenceManager instance:
            finally {
                //Cerramos las BDs si est�n abiertas
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede modificar en punto de distribucion al no existir conexion");
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de buscar los Puntos de Distribuci�n con unos par�metros. Si se pasa como 
     * par�metro zona=-1 devuelve todos los puntos de distribuci�n existentes.
     *
     * @author Ignacio Carcas
     */
    public Vector buscar(int zona){
        //Buscamos en la base de datos de la sede central
        Vector v = new Vector();
        
        int peticion = constantes.NODO_PROPIO;
        PersistenceManager conexion = new Utilities().obtenerConexiones(peticion);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            try{
                if (zona==-1){ //Buscamos todos los puntos de distribucion
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(PVPuntoDistribucion.class);
                    // Ejecutamos la sentencia pas�ndole los par�metros
                    result = (Collection) query.execute();
                }else{
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(PVPuntoDistribucion.class, "this.zona==zona");
                
                    // Declaramos los par�metros que va a tener la consulta
                    query.declareParameters("int zona");
                    
                    // Ejecutamos la sentencia pas�ndole los par�metros
                    result = (Collection) query.execute(new Integer(zona));
                }
                
                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error comprobando puntos de distribucion �nicos: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
                conexion.close();
            }
        }else{
            v = null; // Fallo en la conexi�n. 
            System.err.println("�Exiten puntos de distribucion? Fallo en la conexi�n a la BD");
        }
        return (v);
        
    }
    
    
    
}
