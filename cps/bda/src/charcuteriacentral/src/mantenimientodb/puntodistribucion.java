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
     *Se encarga de insertar un Punto de Distribución
     *
     * @author Ignacio Carcas
     */
    public int insertar(int zona, String localizacion, String tfno){
        int ok = 0;
        String mensaje = "";
        //Insertar en BD Central
        //Insertar en BD del nodo
        try{
            if (new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+zona, constantes.NODO_CENTRAL) == null){
                ok = 1; //Nos dará 1 si no existe, todo ok.    
            }else{
                throw new Exception("Clave repetida al insertar un Punto de Distribución");
            }
        
            CentralPuntoDistribucion pdcentral = new CentralPuntoDistribucion(zona, localizacion, tfno, null, null, null);
        
            PVPuntoDistribucion pdPV = new PVPuntoDistribucion(zona, localizacion, tfno);
        
            int[] peticion = null;
        
            PersistenceManager conexiones[] = new Utilities().obtenerConexiones(peticion);

            if ( (ok==1) && (conexiones != null) ){
                try {
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().begin();
                    }
                    
                    conexiones[0].makePersistent(pdcentral);

                    for (int i=1; i<conexiones.length; i++){
                        conexiones[i].makePersistent(pdPV);
                    }
            
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().commit();
                    }
                    ok = 1;
                }catch (RuntimeException x) {
                    System.out.println("Error en insertar punto de distribucion: " + x.getMessage());
                    ok = 0;
                    mensaje = "Error al insertar el punto de distribucion";
                }

                // Close the PersistenceManager instance:
                finally {
                    //Cerramos las BDs si están abiertas
                    new Utilities().ejecutaFinally(conexiones);
                }
            }else{
                ok = 0;
                mensaje = "No se puede insertar el punto de distribucion al no existir conexion o ser una clave repetida";
                System.err.println("No se puede insertar el punto de distribucion al no existir conexion o ser una clave repetida");
            }
        }catch(Exception e){
            ok = 0;
            System.err.println(e.getMessage());;
        }
        return (ok);
        
    }
    
    
    /**
     *Se encarga de eliminar un Punto de Distribución dado
     *
     * @author Ignacio Carcas
     */
    public int eliminar(int zona){
        //Eliminar de BD Central
        
        //Eliminar de BD del nodo
        int ok = 0;
        String mensaje = "";
        
        int []peticion = null;
        PersistenceManager conexiones[] = new Utilities().obtenerConexiones(peticion);
                
        if (conexiones[0] != null){
            try {
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }    

                //ELIMINAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                Object obj1 = new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+zona, constantes.NODO_CENTRAL);
                CentralPuntoDistribucion pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(obj1,false);

                if (pd == null){
                    mensaje = "El punto de distribucion no existe, no puede ser eliminado";
                    throw new Exception("El punto de distribucion no existe");
                }
                
                /*********NO ME TERMINA DE MOLAR HACER ESTO, SERÍA MEJOR NO ELIMINAR SI NO ESTÁN TODOS ELIMINADOS
                //Eliminamos sus clientes en el nodo central
                Iterator iter = pd.getClientes();
                CentralClientes cliente;
                while (iter.hasNext()){
                    cliente = (CentralClientes) iter.next();
                    cliente.setDepartamento(null);
                    cliente.setEliminado(true);
                }
                
                //Eliminamos sus vehiculos en el nodo central
                iter = pd.getVehiculos();
                CentralVehiculos vehiculo;
                while (iter.hasNext()){
                    vehiculo = (CentralVehiculos) iter.next();
                    vehiculo.setDepartamento(null);
                    vehiculo.setEliminado(true);
                }
                
                //Eliminamos sus vehiculos en el nodo central
                iter = pd.getEmpleados();
                CentralEmpleados empleado;
                while (iter.hasNext()){
                    empleado = (CentralEmpleados) iter.next();
                    empleado.setDepartamento(null);
                    empleado.setEliminado(true);
                }
                */
                
                //Lo eliminamos
                conexiones[0].deletePersistent(pd);

                //ELIMINAMOS DE LOS PUNTOS DE VENTA
                for (int i=1;i<conexiones.length;i++){
                    //Obtenemos el objeto
                    Object obj = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class, "this.zona=="+zona, i+1);
                    PVPuntoDistribucion pdventa = (PVPuntoDistribucion) conexiones[i].getObjectById(obj,false);

                    if (pd==null){
                        mensaje = "El punto de distribucion no existe, no puede ser eliminado";
                        throw new Exception("El punto de distribucion no existe");
                    }
                    //Lo eliminamos de cada punto de venta de los nodos
                    conexiones[i].deletePersistent(pdventa);
                }
                
                //Cerramos la transacción
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
                ok = 1;
            }catch (RuntimeException x) {
                System.out.println("Error en eliminar punto de distribucion 1: " + x.getMessage());
                ok = 0;
            }catch (Exception e){
                System.out.println("Error en eliminar punto de distribucion 2: " + e.getMessage());
                ok = 0;
            }

            // Close the PersistenceManager instance:
            finally {
                //Cerramos las BDs si están abiertas
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede eliminar en punto de distribucion al no existir conexion");
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de modificar un Punto de Distribución dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(int antigua_zona, int nueva_zona, String localizacion, String tfno){
        //Modificar en la BD de la sede central
        
        //Modificar en la BD del nodo
        
        int ok = 0;
        String mensaje = "";
                
        PersistenceManager conexiones[] = new Utilities().obtenerConexiones(null);
                
        if (conexiones[0] != null){
            try {
                 if (new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+nueva_zona, constantes.NODO_CENTRAL) != null){
                    throw new Exception("Clave repetida al modificar un Punto de Distribución");
                }
                
                
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                //MODIFICAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                Object obj1 = new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+antigua_zona, constantes.NODO_CENTRAL);
                CentralPuntoDistribucion pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(obj1,false);
                
                if (pd==null){
                    mensaje = "El punto de distribucion no existe, no puede ser modificado";
                    throw new Exception("El punto de distribucion no existe");
                }
                
                //Modificar sus propiedades
                pd.setZona(nueva_zona);
                pd.setLocalizacion(localizacion);
                pd.setTfno(tfno);
                //asi sucesivamente
                
                /*************** CREO QUE NO HACE FALTA QUE LO ACTUALICE, SIGUE REFERENCIANDO AL MISMO OBJECT ID
                //Modificar la lista de empleados en PuntoDistribucion y en Empleados
                
                //Modificar la lista de vehiculos en PuntoDistribucion y en Vehiculos
                
                //Modificar la lista de clientes en PuntoDistribucion y en Clientes
                *******************/
                
                //MODIFICAMOS DE LOS PUNTOS DE VENTA
                for (int i=1;i<conexiones.length;i++){
                    //Obtenemos el objeto
                    Object obj = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class, "this.zona=="+antigua_zona, i+1);
                    PVPuntoDistribucion pdventa = (PVPuntoDistribucion) conexiones[i].getObjectById(obj,false);

                    if (pd==null){
                        mensaje = "El punto de distribucion no existe, no puede ser modificado";
                        throw new Exception("El punto de distribucion no existe");
                    }
                   
                    //Modificar sus propiedades
                    pdventa.setZona(nueva_zona);
                    pdventa.setLocalizacion(localizacion);
                    pdventa.setTfno(tfno);
                }
                
                //Cerramos la transacción
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
                //Cerramos las BDs si están abiertas
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede modificar en punto de distribucion al no existir conexion");
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de buscar los Puntos de Distribución con unos parámetros. Si se pasa como 
     * parámetro zona=-1 devuelve todos los puntos de distribución existentes.
     *
     * @author Ignacio Carcas
     */
    public Vector buscar(int zona){
        //Buscamos en la base de datos de la sede central
        Vector v = new Vector();
        
        int peticion = constantes.NODO_CENTRAL;
        PersistenceManager conexion = new Utilities().obtenerConexiones(peticion);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            try{
                if (zona==-1){ //Buscamos todos los puntos de distribucion
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(CentralPuntoDistribucion.class);
                    // Ejecutamos la sentencia pasándole los parámetros
                    result = (Collection) query.execute();
                }else{
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(CentralPuntoDistribucion.class, "this.zona==zona");
                
                    // Declaramos los parámetros que va a tener la consulta
                    query.declareParameters("int zona");
                    
                    // Ejecutamos la sentencia pasándole los parámetros
                    result = (Collection) query.execute(new Integer(zona));
                }
                
                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error comprobando puntos de distribucion únicos: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten puntos de distribucion? Fallo en la conexión a la BD");
        }
        return (v);
        
    }
    
    
    
}
