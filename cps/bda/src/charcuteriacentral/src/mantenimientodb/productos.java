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
     *Se encarga de insertar un Producto de carácter GENERAL para toda la empresa
     *
     * @author Ignacio Carcas
     */
    public int insertar(String ref, String nombre, String descripcion, double precio, double unidades, boolean eliminado){
        //Insertar en BD de todos los nodos si es un producto general
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            //Comprobar que la referencia no la tiene ningún producto de ninguna base de datos, 
            //los generales se pueden comprobar en el central+1 pero los regionales únicamente en cada nodo.
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
                // Abrimos la conexión con todas las BD del sistema
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
     *Se encarga de insertar un Producto de carácter REGIONAL a ese punto de venta
     *
     * @author Ignacio Carcas
     */
    public int insertar(String ref, String nombre, String descripcion, double precio, double unidades, boolean eliminado, int zona){
        //Insertar en BD de 1 nodo si es regional
        int ok = 1;
        
        PersistenceManager conexion = null;
        try{
            int i=1;
            int numConexiones = new Utilities().obtenNumeroDB();
            //Comprobar que la referencia no la tiene ningún producto de ninguna base de datos, 
            //los generales se pueden comprobar en el central+1 pero los regionales únicamente en cada nodo.
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
                // Abrimos la conexión con la BD
                conexion.currentTransaction().begin();
                
                // En introducimos el producto 
                  // Buscamos el punto de distribucion al que pertenece
                Object objPuntoDistr = null;
                objPuntoDistr = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class,"this.zona=="+zona, zona);
                PVPuntoDistribucion pdPV = null;
                if (objPuntoDistr==null){
                    throw new Exception("Zona no encontrada, no se le puede asociar ningún producto.");
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
            
           // Abrimos la conexión con todas las BD del sistema
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
     *Se encarga de eliminar o reactivar un Producto dado dentro de una transacción.
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
    public int modificar(String antiguaRef, String nuevaRef, String nombre, String descripcion, double precio, double unidades, boolean eliminado, int zona){
        //Modificar en BD de todos los nodos si es un producto general
        //Modificar en BD de 1 nodo si es regional
        int ok = 1;
        boolean productoEliminadoAnteriormente = eliminado; //indica si el producto a modificar estaba eliminado antes de modificarlo o no.
                                                            //Se inicializa a "eliminado" para que no de problemas por si no encuentra el producto a modificar.
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
            
            // Se comprueba que la Zona elegida es correcta
            if ( (zona!=-1) && ((zona<1)||(zona>numConexiones)) ){
                throw new Exception("La punto de distribución elegido no es correcto");
            }
            
            //Si no existe producto con esa referencia -> todo ok
            if (objProducto==null){
                conexiones = new Utilities().obtenerConexiones(null);

               // Abrimos la conexión con todas las BD del sistema
                for (int i=1; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                // Buscamos el producto en todas las sedes menos en la central
                int zonaAnterior = 0;//Inicializamos la zona a un valor sin importancia hasta que obtengamos el verdadero
                for (int i=1; i<conexiones.length; i++){
                    //Buscamos los productos
                    objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+antiguaRef+"\"", i+1);
                    if (objProducto!=null){
                        productoPV = (PVProductos) conexiones[i].getObjectById(objProducto,false);
                        productoPV.setDescripcion(descripcion);
                        productoPV.setNombre(nombre);
                        productoPV.setPrecio(precio);
                        productoPV.setRef(nuevaRef);
                        productoPV.setUnidades(unidades);
                        productoEliminadoAnteriormente = productoPV.getEliminado();
                        
                        //Creamos y asignamos zonaTemp para poder compararla con el parámetro zona.
                        if (productoPV.getDepartamento()==null){
                            zonaAnterior = -1;
                        }else{
                            zonaAnterior = productoPV.getDepartamento().getZona();
                            // Para optimizar salimos del bucle
                            i=conexiones.length;
                        }
                        
                        // si el usuario simplemente ha cambiado el producto de zona, seguirá siendo un producto regional
                        if ( (zonaAnterior != zona) && (zonaAnterior!=-1) && (zona!=-1) ){
                            //Buscamso el nuevo punto de venta
                            Object objPV = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class,"this.zona=="+zona, i+1);
                            PVPuntoDistribucion pdPV = (PVPuntoDistribucion) conexiones[i].getObjectById(objPV,false);
                            
                            // Lo asignamos
                            productoPV.setDepartamento(pdPV);
                        }
                    }
                }
                        
                if (zonaAnterior != zona){
                    if (zonaAnterior==-1){ // el usuario ha marcado en el interface que es un producto regional
                        // si el producto antes era general es que hay que convertirlo en regional
                        if (convertirloEnProductoRegional(antiguaRef, zona, conexiones) == 0 ){
                            throw new Exception("Fallo al convertirlo en producto regional");
                        }
                    }else if( (zonaAnterior!=-1) && (zona==-1) ){ // el usuario ha marcado en el interface que es un producto general
                        // si antes era regional hay que convertirlo en general
                        if (convertirloEnProductoGeneral(antiguaRef, zonaAnterior, conexiones, productoPV) == 0 ){
                            throw new Exception("Fallo al convertirlo en producto general");
                        }
                    }else{ // el usuario simplemente ha cambiado el producto de zona, seguirá siendo un producto regional
                        // Se ha tratado este caso en el bucle for situado un poco más arriba
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
     *Se encarga de modificar un Producto dado para que se pueda vender en todos los puntos de venta
     *
     * @author Ignacio Carcas
     */
    public int convertirloEnProductoGeneral(String ref, int zona, PersistenceManager[] conexiones,PVProductos productoPV){
        //Ponerlo en todos
        int ok = 1;
        
        try{
            //Lo pasamos a general
            productoPV.setDepartamento(null);
            
            for (int i=1; i<conexiones.length; i++){
                //Comprobamos que antes no existía, si existía lo activamos
                Object objProducto;
                objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+ref+"\"", i+1);
                
                if (objProducto!=null){//El producto ya existía, lo activamos
                    PVProductos productoAnterior = (PVProductos) conexiones[i].getObjectById(objProducto,false);
                    productoAnterior.setEliminado(false);
                    productoAnterior.setDepartamento(null);
                    
                }else{
                    //Creamos los productos y lo vamos insertando
                    PVProductos nuevoProducto = new PVProductos(productoPV.getRef(), productoPV.getNombre(), productoPV.getDescripcion(), productoPV.getPrecio(), productoPV.getUnidades(), productoPV.getEliminado(), null);
                    conexiones[i].makePersistent(nuevoProducto);
                }
            }
            
        }catch(Exception e){
            System.out.println("Error en cambiar el producto a general: " + e.getMessage());
            ok = 0;
        }
        
        return ok;
    }
    
    
    /**
     *Se encarga de modificar un Producto dado para que sólo se pueda vender en un punto de venta
     *
     * @author Ignacio Carcas
     */
    public int convertirloEnProductoRegional (String ref, int zona, PersistenceManager[] conexiones){
        //Eliminarlo en todos los que exista y activarlo en la zona = zona
        int ok = 1;
        
        
        try{
            // Buscamos el producto en todas las sedes menos en la central
            Object objProducto;
            Object objPV;
            for (int i=1; i<conexiones.length; i++){
                //Buscamos los productos
                objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+ref+"\"", i+1);
                PVProductos productoPV = (PVProductos) conexiones[i].getObjectById(objProducto,false);
                
                objPV = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class,"this.zona=="+zona, i+1);
                PVPuntoDistribucion pdPV = (PVPuntoDistribucion) conexiones[i].getObjectById(objPV,false);
                if( i!=(zona-1) ){
                    productoPV.setEliminado(true);
                    productoPV.setDepartamento(pdPV);
                }else{
                    productoPV.setEliminado(false);
                    productoPV.setDepartamento(pdPV);
                }
            }
            
        }catch(Exception e){
            System.out.println("Error en cambiar el producto a regional: " + e.getMessage());
            ok = 0;
        }
        
        return(ok);
    }
    
    
    
    /**
     *Se encarga de buscar los Productos con unos parámetros
     *@param ref: si es "-1" se devuelven todos los productos independiente de si son regionales o generales. Si no, devuelve el producto solicitado.
     * @author Ignacio Carcas
     */
    public Vector buscar(String ref){
        //Buscar en la BD de los nodos
        Vector v = new Vector();
        
        PersistenceManager[] conexiones = new Utilities().obtenerConexiones(null);
        
        if (conexiones != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            try{
                if (ref.equals("-1")){ //Buscamos todos los productos
                    // Creamos la sentencia de consulta
                    int i = 1;
                    query = conexiones[i].newQuery(PVProductos.class);
                    // Ejecutamos la sentencia pasándole los parámetros
                    result = (Collection) query.execute();
                    v.addAll(result);
                    while (++i<conexiones.length){
                        query = conexiones[i].newQuery(PVProductos.class, "this.departamento != null && this.eliminado == false");
                        result = (Collection) query.execute();
                        v.addAll(result);
                    }
                    
                }else{
                    // Creamos la sentencia de consulta
                    int i=1;
                    while (i<conexiones.length){
                        query = conexiones[i].newQuery(PVProductos.class, "this.ref==ref");
                    
                        // Declaramos los parámetros que va a tener la consulta
                        query.declareParameters("String ref");
                    
                        // Ejecutamos la sentencia pasándole los parámetros
                        result = (Collection) query.execute(ref);
                        if (result==null){
                            i++;
                        }else{
                            i=conexiones.length;
                        }
                    }
                    v.addAll(result);
                }
                
                
            }catch(Exception e){ 
                System.out.println("Error consultando productos propios: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten productos? Fallo en la conexión a la BD");
        }
        return (v);
    }
    
}
