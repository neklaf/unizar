/*
 * Productos.java
 *
 * Created on 14 de febrero de 2005, 21:23
 */

package MantenimientoDB;

import java.util.*;
import ConexionBD.*;
import javax.jdo.*;
import PuntoDeVenta.PVProductos;
import PuntoDeVenta.PVPuntoDistribucion;

/**
 *
 * @author  Ignacio Carcas
 */
public class Productos {
    
    /** Creates a new instance of Productos */
    public Productos() {
    }
    
    /**
     *Se encarga de insertar un Producto de car�cter GENERAL para toda la empresa
     *
     * @author Ignacio Carcas
     */
    public int insertar(String ref, String nombre, String descripcion, double precio, double unidades, boolean eliminado){
        //Insertar en BD de todos los nodos si es un producto general
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            //Comprobar que la referencia no la tiene ning�n producto de ninguna base de datos, 
            //los generales se pueden comprobar en el central+1 pero los regionales �nicamente en cada nodo.
            int i=1;
            int numConexiones = new Utilities().obtenNumeroDB();
            Object objProducto = null;
            while (i<numConexiones){
                objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+ref+"\"", i+1);
                if (objProducto==null){
                    i++;
                }else{
                    i=numConexiones;
                }
            }
            
            //Si no existe producto con esa referencia -> todo ok
            if (objProducto==null){
                // Abrimos la conexi�n con todas las BD del sistema
                for (i=1; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }

                // En introducimos el producto en todas las sedes
                for (i=1; i<conexiones.length; i++){
                    //Creamos los productos para los puntos de venta
                      // Creamos el producto
                    PVProductos productoPV = new PVProductos(ref, nombre, descripcion, precio, unidades, eliminado, null);
                    conexiones[i].makePersistent(productoPV);                    
                }

                for (i=1; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
            
            }else{//Si existe producto con esa referencia -> ok = 0; fallo
                throw new Exception("La referencia nueva ya la posee otro producto");
            }
        }catch(Exception e){
            System.out.println("Error en insertar producto: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        return (ok);
    }
    
    /**
     *Se encarga de insertar un Producto de car�cter REGIONAL a ese punto de venta
     *
     * @author Ignacio Carcas
     */
    public int insertar(String ref, String nombre, String descripcion, double precio, double unidades, boolean eliminado, int zona){
        //Insertar en BD de 1 nodo si es regional
        int ok = 1;
        zona = constantes.NODO_PROPIO; //La altero porque siempre tiene que ser para este punto de venta.
        
        PersistenceManager conexion = null;
        try{
            int i=1;
            int numConexiones = new Utilities().obtenNumeroDB();
            //Comprobar que la referencia no la tiene ning�n producto de ninguna base de datos, 
            //los generales se pueden comprobar en el central+1 pero los regionales �nicamente en cada nodo.
            Object objProducto = null;
            while (i<numConexiones){
                objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+ref+"\"", i+1);
                if (objProducto==null){
                    i++;
                }else{
                    i=numConexiones;
                }
            }
            conexion = new Utilities().obtenerConexiones(zona);
            //Si no existe producto con esa referencia -> todo ok
            if (objProducto==null){
                // Abrimos la conexi�n con la BD
                conexion.currentTransaction().begin();
                
                // En introducimos el producto 
                  // Buscamos el punto de distribucion al que pertenece
                Object objPuntoDistr = null;
                objPuntoDistr = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class,"this.zona=="+zona, zona);
                PVPuntoDistribucion pdPV = null;
                if (objPuntoDistr==null){
                    throw new Exception("Zona no encontrada, no se le puede asociar ning�n producto.");
                }else{
                    pdPV = (PVPuntoDistribucion) conexion.getObjectById(objPuntoDistr,false);
                }
                  // Creamos el producto
                PVProductos productoPV = new PVProductos(ref, nombre, descripcion, precio, unidades, eliminado, pdPV);
                conexion.makePersistent(productoPV);                    
                
                //Cerrramos la conexion
                conexion.currentTransaction().commit();
                
            
            }else{//Si existe producto con esa referencia -> ok = 0; fallo
                throw new Exception("La referencia nueva ya la posee otro producto");
            }
        }catch(Exception e){
            System.out.println("Error en insertar producto regional: " + e.getMessage());
            ok = 0;
        }finally{
            PersistenceManager[] conexiones = {conexion};
            new Utilities().ejecutaFinally(conexiones);
        }
        return (ok);
    }
    
    /**
     *Se encarga de eliminar o reactivar un Producto dado.
     *@param ref: refencia del producto a elimina o activar
     *@param eliminado: true si se desea eliminar, false si se desea activar el producto
     *
     * @author Ignacio Carcas
     */
    public int modificarEliminado(String ref, boolean eliminado){
        //Eliminar o Activar en BD de todos los nodos si es un producto general
        //Eliminar o Activar en BD de 1 nodo si es regional
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
           // Abrimos la conexi�n con todas las BD del sistema
            for (int i=1; i<conexiones.length; i++){
                conexiones[i].currentTransaction().begin();
            }
            
            // Buscamos el producto en todas las sedes menos en la central
            Object objProducto;
            for (int i=1; i<conexiones.length; i++){
                //Buscamos los productos
                objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+ref+"\"", i+1);
                PVProductos productoPV = (PVProductos) conexiones[i].getObjectById(objProducto,false);
                productoPV.setEliminado(eliminado); 
                if (productoPV.getDepartamento()!=null){
                    i=conexiones.length;
                }
            }

            for (int i=1; i<conexiones.length; i++){
                conexiones[i].currentTransaction().commit();
            }
            
        }catch(Exception e){
            System.out.println("Error en eliminar o activar producto: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    
    /**
     *Se encarga de eliminar o reactivar un Producto dado dentro de una transacci�n.
     *@param ref: refencia del producto a elimina o activar
     *@param eliminado: true si se desea eliminar, false si se desea activar el producto
     *
     * @author Ignacio Carcas
     */
    public void modificarEliminado(String ref, boolean eliminado, PersistenceManager[] conexiones) throws Exception{
        //Eliminar o Activar en BD de todos los nodos si es un producto general
        //Eliminar o Activar en BD de 1 nodo si es regional

        // Buscamos el producto en todas las sedes menos en la central
        Object objProducto;
        for (int i=1; i<conexiones.length; i++){
            //Buscamos los productos
            objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+ref+"\"", i+1);
            PVProductos productoPV = (PVProductos) conexiones[i].getObjectById(objProducto,false);
            productoPV.setEliminado(eliminado); 
            if (productoPV.getDepartamento()!=null){
                i=conexiones.length;
            }
        }
    }
    
    
    
    
    /**
     *Se encarga de modificar un Producto dado
     *
     *@param zona: indica la zona a la que pertenece el producto. Si es un producto general, zona=-1.
     *
     * @author Ignacio Carcas
     */
    public int modificar(String antiguaRef, String nuevaRef, String nombre, String descripcion, double precio, double unidades, boolean eliminado){
        //Modificar en BD de todos los nodos si es un producto general
        //Modificar en BD de 1 nodo si es regional
        int ok = 1;
        boolean productoEliminadoAnteriormente = eliminado; //indica si el producto a modificar estaba eliminado antes de modificarlo o no.
                                                            //Se inicializa a "eliminado" para que no de problemas por si no encuentra el producto a modificar.
        int zona = constantes.NODO_PROPIO;
        
        PersistenceManager [] conexiones = null;
        try{
            int numConexiones = new Utilities().obtenNumeroDB();
                    
            // Se comprueba que la nueva referencia no la tiene nadie
            Object objProducto = null;
            PVProductos productoPV = null;
            if (!antiguaRef.equals(nuevaRef)){
                int i = 1;
                while (i<numConexiones){
                    objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+nuevaRef+"\"", i+1);
                    if (objProducto==null){
                        i++;
                    }else{
                        i=numConexiones;
                    }
                }
            }
            
            
            //Si no existe producto con esa referencia -> todo ok
            if (objProducto==null){
                conexiones = new Utilities().obtenerConexiones(null);

               // Abrimos la conexi�n con todas las BD del sistema
                for (int i=1; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                // Buscamos el producto en todas las sedes menos en la central
                for (int i=1; i<conexiones.length; i++){
                    objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+antiguaRef+"\"", i+1);
                    if (objProducto!=null){
                        productoPV = (PVProductos) conexiones[i].getObjectById(objProducto,false);
                        productoPV.setDescripcion(descripcion);
                        productoPV.setNombre(nombre);
                        productoPV.setPrecio(precio);
                        productoPV.setRef(nuevaRef);
                        productoPV.setUnidades(unidades);
                        productoEliminadoAnteriormente = productoPV.getEliminado();
                    }
                }
                        
                
                //Ahora lo eliminamos si cambia el atrib eliminado
                if (eliminado != productoEliminadoAnteriormente){
                    modificarEliminado(antiguaRef, eliminado, conexiones);
                }
                
                for (int i=1; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
                
            }else{//Si existe producto con esa referencia -> ok = 0; fallo
                throw new Exception("La referencia nueva ya la posee otro producto");
            }
            
        }catch(Exception e){
            System.out.println("Error en modificar producto: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de buscar los Productos con unos par�metros
     *@param ref: si es "-1" se devuelven todos los productos independiente de si son regionales o generales. Si no, devuelve el producto solicitado.
     * @author Ignacio Carcas
     */
    public Vector buscar(String ref){
        //Buscar en la BD del nodo
        Vector v = new Vector();
        
        PersistenceManager conexion = new Utilities().obtenerConexiones(constantes.NODO_PROPIO);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            try{
                if (ref.equals("-1")){ //Buscamos todos los productos
                    // Creamos la sentencia de consulta
                    int i = 1;
                    query = conexion.newQuery(PVProductos.class);
                    // Ejecutamos la sentencia pas�ndole los par�metros
                    result = (Collection) query.execute();
                    v.addAll(result);
                    
                    
                }else{
                    // Creamos la sentencia de consulta
                    int i=1;
                   
                    query = conexion.newQuery(PVProductos.class, "this.ref==ref");

                    // Declaramos los par�metros que va a tener la consulta
                    query.declareParameters("String ref");

                    // Ejecutamos la sentencia pas�ndole los par�metros
                    result = (Collection) query.execute(ref);

                    v.addAll(result);
                }
                
                
            }catch(Exception e){ 
                System.out.println("Error consultando productos propios: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
                /*if (!conexion.isClosed()){
                    conexion.close();
                }*/
            }
        }else{
            v = null; // Fallo en la conexi�n. 
            System.err.println("�Exiten productos? Fallo en la conexi�n a la BD");
        }
        return (v);
    }
    
}