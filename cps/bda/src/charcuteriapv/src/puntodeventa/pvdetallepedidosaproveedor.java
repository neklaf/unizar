/*
 * PVDetallePedidosAProveedor.java
 *
 * Created on 17 de marzo de 2005, 20:45
 */

package PuntoDeVenta;

import ConexionBD.*;
import javax.jdo.*;

/**
 *
 * @author Ignacio Carcas
 */
public class PVDetallePedidosAProveedor{
        protected int unidades;
        protected double precioCompra;
        protected PVProductosProveedores producto;
        
        /* CONSTRUCTOR 1 */
        public PVDetallePedidosAProveedor(){
            
        }
        
        /* CONSTRUCTOR 2 */
        public PVDetallePedidosAProveedor(int unidades, double precioCompra, PVProductosProveedores producto){
            this.unidades = unidades;
            this.precioCompra = precioCompra;
            this.producto = producto;
        }
        
        /* CONSTRUCTOR 3 */
        public PVDetallePedidosAProveedor(int unidades, double precioCompra, String ref_prod, int zona){
            this.unidades = unidades;
            this.precioCompra = precioCompra;
            
            PersistenceManager conexion = null;
            try{
                conexion= new Utilities().obtenerConexiones(zona);
                Object objProducto = new Utilities().obtenIdObjeto(PVProductosProveedores.class,"this.ref_prod==\""+ref_prod+"\"", zona);
                PVProductosProveedores producto = (PVProductosProveedores) conexion.getObjectById(objProducto,false);

                this.producto = producto;
            }catch(Exception e){
                System.out.println("Error al obtener el producto del proveedor para ser asignado al objeto.");
            }finally{
                if (!conexion.isClosed()){
                    conexion.close();
                }
            }
        }
        
        public void setUnidades (int unidades){
            this.unidades = unidades;
        }
    
        public int getUnidades (){
            return this.unidades;
        }
        
        public void setPrecioCompra (double precioCompra){
            this.precioCompra = precioCompra;
        }
    
        public double getPrecioCompra (){
            return this.precioCompra;
        }
        
        public void setProducto (PVProductosProveedores precioCompra){
            this.producto = producto;
        }
    
        public PVProductosProveedores getProducto (){
            return this.producto;
        }
        
    }
    