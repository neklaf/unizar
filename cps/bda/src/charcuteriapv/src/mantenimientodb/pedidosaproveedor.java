/*
 * PedidosAProveedor.java
 *
 * Created on 14 de febrero de 2005, 21:22
 */

package MantenimientoDB;

import java.util.*;
import ConexionBD.*;
import PuntoDeVenta.PVPedidosAProveedor;
import PuntoDeVenta.PVDetallePedidosAProveedor;
import PuntoDeVenta.PVProductosProveedores;
import PuntoDeVenta.PVPuntoDistribucion;
import javax.jdo.*;
/**
 *
 * @author  Ignacio Carcas
 */
public class PedidosAProveedor {
    
    /** Creates a new instance of PedidosAProveedor */
    public PedidosAProveedor() {
    }
    
    
    public int insertar(String id_pedido,Date fechaRealizacion,int estado, ArrayList detallePedidos){
        int ok = 1;
        
        PersistenceManager[] conexiones = null;
        PersistenceManager conexion = null;
        
        try{
            Object objPedido = null;
            conexiones = new Utilities().obtenerConexiones(null);
            
            //Comprobar que el id_pedido no lo tiene ningún pedido de ninguna base de datos, 
            int i=1;
            int numConexiones = new Utilities().obtenNumeroDB();
            while (i<numConexiones){
                objPedido = new Utilities().obtenIdObjeto(PVPedidosAProveedor.class,"this.id_pedido==\""+id_pedido+"\"", i+1);
                if (objPedido==null){
                    i++;
                }else{
                    i=numConexiones;
                }
            }
            // Finalizamos las conexiones
            new Utilities().ejecutaFinally(conexiones);
            
            // Procedemos a insertar el pedido
            if (objPedido == null){ // Si no existe ningún pedido con ese identificador de pedido
                conexion = new Utilities().obtenerConexiones(constantes.NODO_PROPIO);
                conexion.currentTransaction().begin();
                
                // obtenemos el punto de vetna
                Object obj = new Utilities().obtenIdObjeto(PVPuntoDistribucion.class,"this.zona=="+constantes.NODO_PROPIO,constantes.NODO_PROPIO);
                PVPuntoDistribucion departamento = (PVPuntoDistribucion) conexion.getObjectById(obj,false);
                
                PVPedidosAProveedor pedido = new PVPedidosAProveedor(id_pedido,fechaRealizacion,estado, departamento, null);
                
                conexion.makePersistent(pedido);
                
                Iterator it = detallePedidos.iterator();
                PVDetallePedidosAProveedor detalle;
                while (it.hasNext()){
                    detalle = (PVDetallePedidosAProveedor) it.next();
                    obj = this.creaDetPedToBD(detalle.getUnidades(), detalle.getPrecioCompra(), detalle.getProducto().getRef_prod(),constantes.NODO_PROPIO);
                    pedido.addDetallePedidos( (PVDetallePedidosAProveedor)conexion.getObjectById(obj, false) );
                }
                
                conexion.currentTransaction().commit();
            }else{
                throw new Exception("Ese identificador de pedido ya existía");
            }
        }catch(Exception e){
            ok = 0;
            System.out.println("Fallo al insertar pedidos a proveedor: "+e.getMessage());
            conexion.currentTransaction().rollback();
        }finally{
            new Utilities().ejecutaFinally(conexiones);
            if (!conexion.isClosed()){
                conexion.close();
            }
        }
        
        return ok;
    }
    
    
    /**
     *Método para eliminar un pedido
     *
     *
     *Author: Ignacio Carcas
     */
    public int eliminar(String id_pedido){
        int ok = 1;
        
        PersistenceManager conexion = null;
        try{
            conexion = new Utilities().obtenerConexiones(constantes.NODO_PROPIO);

            // obtenemos el pedido
            Object obj = new Utilities().obtenIdObjeto(PVPedidosAProveedor.class,"this.id_pedido==\""+id_pedido+"\"",constantes.NODO_PROPIO);
            PVPedidosAProveedor pedido = (PVPedidosAProveedor) conexion.getObjectById(obj, false);
            
            conexion.currentTransaction().begin();
            
            
            // Eliminamos el pedido
            ArrayList misdetalles = pedido.getAllPedidos();
            Iterator i = misdetalles.iterator();
            PVDetallePedidosAProveedor detalle = null;
            while (i.hasNext()){
                detalle = (PVDetallePedidosAProveedor) i.next();
                conexion.deletePersistent(detalle);
            }
            conexion.deletePersistent(pedido);
            
            conexion.currentTransaction().commit();
            
        }catch(Exception e){
            ok = 0;
            System.out.println("Fallo al eliminar pedidos a proveedor");
            conexion.currentTransaction().rollback();
        }finally{
            if (!conexion.isClosed()){
                conexion.close();
            }
        }
        
        return ok;
    }
    
    
    /*Modifica el pedido
     *
     *
     */
    public int modificar(String antigua_id_pedido, String nueva_id_pedido,Date fechaRealizacion,int estado, ArrayList detallePedidos){
        int ok = 1;
        
        PersistenceManager[] conexiones = null;
        PersistenceManager conexion = null;
        try{
            Object objPedido = null;
            conexiones = new Utilities().obtenerConexiones(null);
            
            //Comprobar que el id_pedido no lo tiene ningún pèdido de ninguna base de datos
            if (!antigua_id_pedido.equals(nueva_id_pedido)){
                int i=1;
                int numConexiones = new Utilities().obtenNumeroDB();
                while (i<numConexiones){
                    objPedido = new Utilities().obtenIdObjeto(PVPedidosAProveedor.class,"this.id_pedido==\""+nueva_id_pedido+"\"", i+1);
                    if (objPedido==null){
                        i++;
                    }else{
                        i=numConexiones;
                    }
                }
            }
            // Finalizamos las conexiones
            new Utilities().ejecutaFinally(conexiones);
            
            // Procedemos a modificar el pedido antiguo
            if (objPedido == null){ // Si no existe ningún pedido con ese identificador de pedido
                
                conexion = new Utilities().obtenerConexiones(constantes.NODO_PROPIO);
                
                conexion.currentTransaction().begin();
                
                // obtenemos el pedido
                Object obj = new Utilities().obtenIdObjeto(PVPedidosAProveedor.class,"this.id_pedido==\""+antigua_id_pedido+"\"",constantes.NODO_PROPIO);
                PVPedidosAProveedor pedido = (PVPedidosAProveedor) conexion.getObjectById(obj, false);
                
                pedido.setId_pedido(nueva_id_pedido);
                pedido.setEstado(estado);
                pedido.setFechaRealizacion(fechaRealizacion);
                
                // eliminamos los detalles de pedido anteriores
                Iterator it = pedido.getAllPedidos().iterator();
                while (it.hasNext()){
                    conexion.deletePersistent( (PVDetallePedidosAProveedor) it.next() );
                }
                
                pedido.borraAllPedidos();
                
                // introducimos los nuevos detalle de pedido
                it = detallePedidos.iterator();
                PVDetallePedidosAProveedor detalle;
                while (it.hasNext()){
                    detalle = (PVDetallePedidosAProveedor) it.next();
                    obj = this.creaDetPedToBD(detalle.getUnidades(), detalle.getPrecioCompra(), detalle.getProducto().getRef_prod(),constantes.NODO_PROPIO);
                    pedido.addDetallePedidos( (PVDetallePedidosAProveedor)conexion.getObjectById(obj, false) );
                }
                
                conexion.currentTransaction().commit();

            }else{
                throw new Exception("Ese identificador de pedido ya existía");
            }
        }catch(Exception e){
            ok = 0;
            System.out.println("Fallo al modificar pedidos a proveedor");
            conexion.currentTransaction().rollback();
        }finally{
            new Utilities().ejecutaFinally(conexiones);
            if (!conexion.isClosed()){
                conexion.close();
            }
        }
        
        return ok;
    }
    
    
    
    /* Crea un detalle de pedido a proveedor
     *
    */
    public Object creaDetPedToBD(int unidades, double precioCompra, String ref_prod, int zona){
        PuntoDeVenta.PVProductosProveedores prod = null;
        PersistenceManager conexion = null;
        PVDetallePedidosAProveedor detalle = null;
        Object obj = null;
        try{
            conexion= new ConexionBD.Utilities().obtenerConexiones(zona);
            conexion.currentTransaction().begin();

            Object objProducto = new ConexionBD.Utilities().obtenIdObjeto(PuntoDeVenta.PVProductosProveedores.class,"this.ref_prod==\""+ref_prod+"\"", zona);
            prod = (PuntoDeVenta.PVProductosProveedores) conexion.getObjectById(objProducto,false);

            detalle = new PVDetallePedidosAProveedor(unidades, precioCompra, prod);

            conexion.makePersistent(detalle);

            obj = conexion.getObjectId(detalle);

            conexion.currentTransaction().commit();
        }catch(Exception e){
            System.out.println("Error al obtener el producto para ser asignado al objeto.");
            conexion.currentTransaction().rollback();
        }finally{
            if (!conexion.isClosed()){
                conexion.close();
            }
        }
        return(obj); 
    }
    
    
    /*Modifica el detalle del pedido
     *
     *
     */
    public int modificarDetallePedido(String id_pedido, ArrayList detallePedidos){
        int ok = 1;
        
        PersistenceManager conexion = null;
        try{
            conexion = new Utilities().obtenerConexiones(constantes.NODO_PROPIO);

            conexion.currentTransaction().begin();

            // obtenemos el pedido
            Object obj = new Utilities().obtenIdObjeto(PVPedidosAProveedor.class,"this.id_pedido==\""+id_pedido+"\"",constantes.NODO_PROPIO);
            PVPedidosAProveedor pedido = (PVPedidosAProveedor) conexion.getObjectById(obj, false);

            Iterator it = pedido.getAllPedidos().iterator();
            while (it.hasNext()){
                conexion.deletePersistent( (PVDetallePedidosAProveedor) it.next() );
            }
            
            pedido.borraAllPedidos();
            
            it = detallePedidos.iterator();
            PVDetallePedidosAProveedor detalle;
            while (it.hasNext()){
                detalle = (PVDetallePedidosAProveedor) it.next();
                obj = this.creaDetPedToBD(detalle.getUnidades(), detalle.getPrecioCompra(), detalle.getProducto().getRef_prod(),constantes.NODO_PROPIO);
                pedido.addDetallePedidos( (PVDetallePedidosAProveedor)conexion.getObjectById(obj, false) );
            }

            conexion.currentTransaction().commit();

        }catch(Exception e){
            ok = 0;
            System.out.println("Fallo al modificar pedidos a proveedor");
            conexion.currentTransaction().rollback();
        }finally{
            if (!conexion.isClosed()){
                conexion.close();
            }
        }
        
        return ok;
    }
    
    
    
    /**
     *Se encarga de buscar los Pedidos a Proveedor con unos parámetros
     *@param zona: zona en la que buscar los pedidos a mostrar
     * @author Ignacio Carcas
     */
    public Vector buscar(String idPedido){
        //Buscar en la BD de los nodos
        Vector v = new Vector();
        
        int peticion = constantes.NODO_PROPIO;
        PersistenceManager conexion = new Utilities().obtenerConexiones(peticion);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            String consulta = "";
            Map args = new HashMap();
            try{
                // Creamos la sentencia de consulta
                query = conexion.newQuery(PVPedidosAProveedor.class);
                
                if (!idPedido.equals("-1")){
                    //Creamos la consulta
                    consulta += "this.id_pedido==idPedido";
                    //Declaramos el nuevo parámetro
                    query.declareParameters("String idPedido");
                    args.put("idPedido", idPedido);
                }
                
               //Establecemos el filtro de consulta
                if (!consulta.equals("")){
                    query.setFilter(consulta);
                }

                // Ejecutamos la sentencia pasándole los parámetros
                if (args.size()>0){
                    result = (Collection) query.executeWithMap(args);
                }else{
                    result = (Collection) query.execute();
                }

                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error consultando peidos a proveedor: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
                /*if (!conexion.isClosed()){
                    conexion.close();
                }*/
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten pedidos a proveedor? Fallo en la conexión a la BD");
        }
        return (v);
    }
    
}
