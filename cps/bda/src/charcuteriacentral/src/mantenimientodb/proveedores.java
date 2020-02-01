/*
 * Proveedores.java
 *
 * Created on 14 de febrero de 2005, 21:21
 */

package MantenimientoDB;

import java.util.*;
import ConexionBD.*;
import javax.jdo.*;
import Central.CentralProveedores;
import Central.CentralPuntoDistribucion;
import PuntoDeVenta.PVProveedores;
import PuntoDeVenta.PVPuntoDistribucion;

/**
 *
 * @author  Ignacio Carcas
 */
public class Proveedores {
    
    /** Creates a new instance of Proveedores */
    public Proveedores() {
    }
    
    /**
     *Se encarga de insertar un Proveedor
     *
     * @author Ignacio Carcas
     */
    public int insertar(String cif, String localizacion, ArrayList tfno, String nombre, String numcuenta, String persContacto, String web, String email, boolean eliminado){
        //Insertar en BD Central
        //Insertar en BD de todos los nodos
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            // Comprobamos si existía algún proveedor con ese cif
            Object objProveedor = new Utilities().obtenIdObjeto(CentralProveedores.class,"this.cif==\""+cif+"\"", constantes.NODO_PROPIO);
            //Si no existe proveedor con ese cif -> todo ok
            if (objProveedor==null){
                
                // Abrimos la conexión con todas las BD del sistema
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }

                
                // Primero creamos el proveedor para la sede central
                ArrayList productos = null;
                CentralProveedores proveedorCentral = new CentralProveedores(cif, localizacion, tfno, nombre, numcuenta, persContacto, web, email, eliminado, productos);
                conexiones[0].makePersistent(proveedorCentral);

                // En segundo lugar cambiamos el del resto de sedes
                for (int i=1; i<conexiones.length; i++){
                    //Creamos los proveedores para los puntos de venta
                    PVProveedores proveedorPV = new PVProveedores(cif, localizacion, tfno, nombre, numcuenta, persContacto, web, email, eliminado, productos);
                    conexiones[i].makePersistent(proveedorPV);                    
                }

                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
            
            }else{//Si existe proveedor con ese cif -> ok = 0; fallo
                throw new Exception("El cif nuevo ya lo posee otro proveedor");
            }
        }catch(Exception e){
            System.out.println("Error en insertar proveedor: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    
    /**
     *Se encarga de eliminar un Proveedor dado
     *
     * @author Ignacio Carcas
     */
    public int eliminar(String cif){
        //Eliminar de BD Central
        //Eliminar de BD del nodo

        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            // Comprobamos si existía algún proveedor con ese cif //ATENCION CAMBIAR LA CLASE CENTRALPROVEEDORES POR PVPROVEEDORES CUANDO LO PORTE AL PUNTO DE VENTA
            Object objProveedor = new Utilities().obtenIdObjeto(CentralProveedores.class,"this.cif==\""+cif+"\"", constantes.NODO_PROPIO);
            //Si existe proveedor con ese cif -> todo oK
            if (objProveedor!=null){
                
                // Abrimos la conexión con todas las BD del sistema
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                // Buscamos el proveedor para la sede central
                CentralProveedores proveedorCentral = (CentralProveedores) conexiones[0].getObjectById(objProveedor,false);
                proveedorCentral.setEliminado(true);
                // En segundo lugar cambiamos el del resto de sedes
                for (int i=1; i<conexiones.length; i++){
                    //Buscamos los proveedores para los puntos de venta
                    objProveedor = new Utilities().obtenIdObjeto(PVProveedores.class,"this.cif==\""+cif+"\"", i+1);
                    PVProveedores proveedorPV = (PVProveedores) conexiones[i].getObjectById(objProveedor,false);
                    proveedorPV.setEliminado(true);                    
                }

                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
            }
            
        }catch(Exception e){
            System.out.println("Error en eliminar proveedor: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de modificar un Proveedor dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(String antiguoCif, String nuevoCif, String localizacion, ArrayList tfno, String nombre, String numcuenta, String persContacto, String web, String email, boolean eliminado){
        //Modificar en la BD de la sede central
        //Modificar en la BD del nodo
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            // Comprobamos si existía algún proveedor con ese cif //ATENCION CAMBIAR LA CLASE CENTRALPROVEEDORES POR PVPROVEEDORES CUANDO LO PORTE AL PUNTO DE VENTA
            Object objProveedor = new Utilities().obtenIdObjeto(CentralProveedores.class,"this.cif==\""+antiguoCif+"\"", constantes.NODO_PROPIO);
            //Si existe proveedor con ese cif modificamos los datos del proveedor
            if (objProveedor!=null){
                //Comprobamos que el nuevo cif no lo tiene ningún otro proveedor
                if (!antiguoCif.equals(nuevoCif)){
                    Object objProveedorNuevoCif = new Utilities().obtenIdObjeto(CentralProveedores.class,"this.cif==\""+nuevoCif+"\"", constantes.NODO_PROPIO);
                    if (objProveedorNuevoCif!=null){
                        throw new Exception("Ya existía un proveedor con ese cif, no puede ser modificado");
                    }
                }
                
                // Abrimos la conexión con todas las BD del sistema
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }

                // Buscamos el proveedor para la sede central
                CentralProveedores proveedorCentral = (CentralProveedores) conexiones[0].getObjectById(objProveedor,false);
                proveedorCentral.setCif(nuevoCif);
                proveedorCentral.setLocalizacion(localizacion);
                proveedorCentral.setTfno(tfno);
                proveedorCentral.setNombre(nombre);
                proveedorCentral.setNumcuenta(numcuenta);
                proveedorCentral.setPersContacto(persContacto);
                proveedorCentral.setWeb(web);
                proveedorCentral.setEmail(email);
                proveedorCentral.setEliminado(eliminado);
                // En segundo lugar cambiamos el del resto de sedes
                for (int i=1; i<conexiones.length; i++){
                    //Creamos los proveedores para los puntos de venta
                    objProveedor = new Utilities().obtenIdObjeto(PVProveedores.class,"this.cif==\""+antiguoCif+"\"", i+1);
                    PVProveedores proveedorPV = (PVProveedores) conexiones[i].getObjectById(objProveedor,false);
                    proveedorPV.setCif(nuevoCif);
                    proveedorPV.setLocalizacion(localizacion);
                    proveedorPV.setTfno(tfno);
                    proveedorPV.setNombre(nombre);
                    proveedorPV.setNumcuenta(numcuenta);
                    proveedorPV.setPersContacto(persContacto);
                    proveedorPV.setWeb(web);
                    proveedorPV.setEmail(email);
                    proveedorPV.setEliminado(eliminado);
                }

                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
            }
            
        }catch(Exception e){
            System.out.println("Error en modificar proveedor: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    
    /**
     *Método para retocar los números de tfno del proveedor.
     *@param cif: cif de la empresa a la que pertenecen esos tfnos
     *@param nuevos_tfnos: lista de tfnos de la empresa
     *
     * @author Ignacio Carcas
     */
    public int modificarTfno(String cif, ArrayList nuevos_tfnos){
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            // Comprobamos si existía algún proveedor con ese cif //ATENCION CAMBIAR LA CLASE CENTRALPROVEEDORES POR PVPROVEEDORES CUANDO LO PORTE AL PUNTO DE VENTA
            Object objProveedor = new Utilities().obtenIdObjeto(CentralProveedores.class,"this.cif==\""+cif+"\"", constantes.NODO_PROPIO);
            //Si existe proveedor con ese cif modificamos los datos del proveedor
            if (objProveedor!=null){
                // Abrimos la conexión con todas las BD del sistema
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }

                // Buscamos el proveedor para la sede central
                CentralProveedores proveedorCentral = (CentralProveedores) conexiones[0].getObjectById(objProveedor,false);
                proveedorCentral.setTfno(nuevos_tfnos);
                // En segundo lugar cambiamos el del resto de sedes
                for (int i=1; i<conexiones.length; i++){
                    //Creamos los proveedores para los puntos de venta
                    objProveedor = new Utilities().obtenIdObjeto(PVProveedores.class,"this.cif==\""+cif+"\"", i+1);
                    PVProveedores proveedorPV = (PVProveedores) conexiones[i].getObjectById(objProveedor,false);
                    proveedorPV.setTfno(nuevos_tfnos);
                }

                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
            }
            
        }catch(Exception e){
            System.out.println("Error en modificar proveedor: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    
    /**
     *Se encarga de buscar los Proveedores con unos parámetros
     *@param cif: busca el proveedor con ese cif. Si cif ="-1" devuelve todos.
     *
     * @author Ignacio Carcas
     */
    public Vector buscar(String cif){
        //Buscar en la BD de la sede central
        
        Vector v = new Vector();
        
        int peticion = constantes.NODO_CENTRAL;
        PersistenceManager conexion = new Utilities().obtenerConexiones(peticion);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            try{
                if (cif.equals("-1")){ //Buscamos todos los proveedores
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(CentralProveedores.class);
                    // Ejecutamos la sentencia pasándole los parámetros
                    result = (Collection) query.execute();
                }else{
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(CentralProveedores.class, "this.cif==cif");
                
                    // Declaramos los parámetros que va a tener la consulta
                    query.declareParameters("String cif");
                    
                    // Ejecutamos la sentencia pasándole los parámetros
                    result = (Collection) query.execute(cif);
                }
                
                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error consultando proveedores: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten proveedores? Fallo en la conexión a la BD");
        }
        return (v);
    }
    
}
