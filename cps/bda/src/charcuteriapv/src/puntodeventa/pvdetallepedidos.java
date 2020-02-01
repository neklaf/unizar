/*
 * PVDetallePedidos.java
 *
 * Created on 17 de marzo de 2005, 20:35
 */

package PuntoDeVenta;

import ConexionBD.*;
import javax.jdo.*;

/**
 *
 * @author Ignacio Carcas
 */
public class PVDetallePedidos{
    protected int unidades;
    protected double precioCompra;
    protected PVProductos producto;

    /* CONSTRUCTOR 1 */
    public PVDetallePedidos(){

    }

    /* CONSTRUCTOR 2 */
    public PVDetallePedidos(int unidades, double precioCompra, PVProductos producto){
        this.unidades = unidades;
        this.precioCompra = precioCompra;
        this.producto = producto;
    }

    /* CONSTRUCTOR 3 */
    public PVDetallePedidos(int unidades, double precioCompra, String ref_prod, int zona){
        this.unidades = unidades;
        this.precioCompra = precioCompra;

        PersistenceManager conexion = null;
        try{
            conexion= new Utilities().obtenerConexiones(zona);
            Object objProducto = new Utilities().obtenIdObjeto(PVProductos.class,"this.ref==\""+ref_prod+"\"", zona);
            PVProductos prod = (PVProductos) conexion.getObjectById(objProducto,false);
            this.producto = prod;                
        }catch(Exception e){
            System.out.println("Error al obtener el producto para ser asignado al objeto.");
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

    public void setProducto (PVProductos producto){
        this.producto = producto;
    }

    public PVProductos getProducto (){
        return this.producto;
    }

}
