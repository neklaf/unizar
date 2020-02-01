/*
 * Utilities.java
 *
 * Created on 14 de febrero de 2005, 19:51
 */

/**
 *
 * @author  Administrador
 */
package ConexionBD;

import java.io.*;
import java.util.Properties;
import java.util.Collection;
import java.util.Iterator;
import javax.jdo.*;


/**
 * General JDO Utilities
 */
public class Utilities {
    
    /**
     * Obtains a PersistenceManager representing a database connection.
     *
     * @param file indica a qué base de datos hay que conectar
     */
    public static PersistenceManager getPersistenceManager(String file) {
        
        try {
            // Prepare the jdo.properties file for reading:
            InputStream in =
                Utilities.class.getResourceAsStream(file);

            try {
                // Load the properties from the file:
                Properties properties = new Properties();
                properties.load(in);
                              
                // Obtain a PersistenceManagerFactory and a PersistenceManager:
                PersistenceManagerFactory pmf =
                    JDOHelper.getPersistenceManagerFactory(
                        properties, JDOHelper.class.getClassLoader());
                return pmf.getPersistenceManager();
            }finally {
                in.close();
            }
        }
        
        // Handle errors:
        catch (IOException x) {
            throw new RuntimeException("Error reading "+file);
        }
    }
    
    /**
     * Método que devuelve las conexiones de las bases de datos indicadas en 
     * un vector del tipo PersistenceManager. 
     * @param conexiones vector de longitud indefinida al cual se le indica el número de la conexión a obtener.
     * Si conexiones == null devolverá todas las conexiones que existan según el orden del archivo conexiones.properties.
     * @return devuelve un vector con las conexiones solicitadas en el mismo orden que se pidieron. Devuelve null si fallo.
     * @author Ignacio Carcas
     */
    public PersistenceManager[] obtenerConexiones(int conexiones[]){
        int i = 0;
        PersistenceManager[] pm = null;
        try{
            // Preparamos el archivo conexiones.properties para ser leido
            InputStream in = Utilities.class.getResourceAsStream("../conexiones.properties");

            try{
                // Cargamos el archivo de propiedades
                Properties properties = new Properties();
                properties.load(in);

                //obtenemos todas las conexiones solicitadas
                if (conexiones != null){

                    pm =  new PersistenceManager[conexiones.length];

                    for (i=0;i<conexiones.length;i++){
                        pm[i] = getPersistenceManager("../"+properties.get("conexion"+conexiones[i]).toString()+"/jdo.properties");
                    }
                 }else{// como conexiones es null recogemos todas
                    int num_conexiones = properties.size();
                    pm =  new PersistenceManager[num_conexiones];

                    for (i=0;i<num_conexiones;i++){
                        pm[i] = getPersistenceManager("../"+properties.get("conexion"+(i+1)).toString()+"/jdo.properties");
                    }
                 }
                 
            }catch(Exception e){
                pm = null;
                System.err.println("Fallo al obtener las conexiones: "+ e.getMessage());
            }finally{
                in.close();
            }
            
        }catch (IOException x) {
            throw new RuntimeException("Error leyendo conexiones.properties");
        }finally{
            return (pm);
        }
       
    }
    
    
    public int obtenNumeroDB(){
        int numConexiones = 0;
        PersistenceManager[] pm = null;
        try{
            // Preparamos el archivo conexiones.properties para ser leido
            InputStream in = Utilities.class.getResourceAsStream("../conexiones.properties");
            
            // Cargamos el archivo de propiedades
            Properties properties = new Properties();
            properties.load(in);
            
            // Obtenemos el tamaño
            numConexiones = properties.size();
            
        }catch (IOException x) {
            System.err.println("Error leyendo conexiones.properties: "+x.getMessage());
        }
        
        return numConexiones;
    }
    
    /**
     * Método que devuelve la conexion de las bases de datos indicadas en el parámetro. 
     * @param conexion Indica el número de conexión que se desea obtener.
     * @return devuelve la conexión solicitada. Devuelve null si fallo.
     * @author Ignacio Carcas
     */
    public PersistenceManager obtenerConexiones(int conexion){
        int i = 0;
        PersistenceManager pm = null;
        try{
            // Preparamos el archivo conexiones.properties para ser leido
            InputStream in = Utilities.class.getResourceAsStream("../conexiones.properties");

            try{
                // Cargamos el archivo de propiedades
                Properties properties = new Properties();
                properties.load(in);

                //obtenemos la conexion solicitada
                pm = getPersistenceManager("../"+properties.get("conexion"+conexion).toString()+"/jdo.properties");
                 
            }catch(Exception e){
                pm = null;
                System.err.println("Fallo al obtener la conexion: "+ e.getMessage());
            }finally{
                in.close();
            }
            
        }catch (IOException x) {
            throw new RuntimeException("Error leyendo conexiones.properties");
        }finally{
            return (pm);
        }
       
    }
    
    
    /**
     * Se encarga de buscar y devolver en la clase persistente indicada el objeto indicado en la 
     * consulta y en la BD indicada mediante el entero peticion.
     * @param cls clase en la que se debe de buscar
     * @param consulta cadena que marca el criterio de búsqueda
     * @peticion entero que indica en que conexion buscar
     * @return Objeto = existe objeto para esa consulta; null = no existe objeto para esa consulta o fallo;
     * @author Ignacio Carcas
     */
    public Object obtenIdObjeto(java.lang.Class cls, String consulta /*"this.cif==cif"*/, int peticion) throws Exception{
        Object obj = null;
        // Sólo preguntamos a nuestro propio nodo, en este caso el central para no tener que
        // solicitar más recursos de los necesarios.
        PersistenceManager conexion = this.obtenerConexiones(peticion);
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            try{
                // Creamos la sentencia de consulta
                query = conexion.newQuery(cls, consulta);
            
                // Ejecutamos la sentencia pasándole los parámetros
                result = (Collection) query.execute();

                if (result.size()>0){
                    Iterator i = result.iterator();
                    obj = conexion.getObjectId(i.next()); // El objeto indicado existe
                }else{
                    obj = null; // no existe ningún objeto con ese id
                }

            }catch(Exception e){ 
                obj = null;
                System.err.println("Error comprobando claves únicos: " + e.getMessage());
                throw new Exception("Fallo en la consulta");
            }finally{
                 query.close(result);
            }

        }else{
            obj = null; // Fallo en la conexión. 
            System.err.println("¿Hay algo en esa clase? Fallo en la conexión a la BD");
            throw new Exception("Fallo en la conexion");
        }

        return obj;
    }
    
    
    /**
     * Cierra todas las conexiones que se hayan podido quedar abiertas y son pasadas en el vector de conexiones
     * @param conexiones vector con las conexiones abiertas del tipo PersistenceManager
     * @author Ignacio Carcas
     */
    public void ejecutaFinally(PersistenceManager conexiones[]){
        // Cerramos las BDs si están abiertas
        if (conexiones!=null){
            for (int i=0;i<conexiones.length;i++){
                if (conexiones[i]!=null){
                    if (conexiones[i].currentTransaction().isActive()){
                        conexiones[i].currentTransaction().rollback();
                    }
                    if (!conexiones[i].isClosed()){
                        conexiones[i].close();
                    }
                }
            }
        }
    }
    
}

