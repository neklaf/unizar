/*
 * ProductosProveedores.java
 *
 * Created on 12 de febrero de 2005, 15:05
 */

/**
 *
 * @author  Administrador
 */
package PuntoDeVenta;

public class PVProductosProveedores {

    protected String ref_prod;
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected double unidades;
    protected boolean eliminado;
    protected PVProveedores proveedor;
    
    /** Creates a new instance of ProductosProveedores */
    public PVProductosProveedores() {
    }
    
    public PVProductosProveedores(String ref_prod,String nombre,String descripcion,double precio,double unidades, boolean eliminado, PVProveedores proveedor) {
        this.ref_prod = ref_prod;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.unidades = unidades;
        this.eliminado = eliminado;
        this.proveedor = proveedor;
    }
    
    public void setRef_prod (String ref_prod){
        this.ref_prod = ref_prod;
    }
    
    public String getRef_prod (){
        return this.ref_prod;
    }
    
    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre (){
        return this.nombre;
    }
    
    public void setDescripcion (String descripcion){
        this.descripcion = descripcion;
    }
    
    public String getDescripcion (){
        return this.descripcion;
    }
    
    public void setPrecio (double precio){
        this.precio = precio;
    }
    
    public double getPrecio (){
        return this.precio;
    }
    
    public void setUnidades (double unidades){
        this.unidades = unidades;
    }
    
    public double getUnidades (){
        return this.unidades;
    }
    
    public void setEliminado (boolean eliminado){
        this.eliminado = eliminado;
    }
    
    public boolean getEliminado (){
        return this.eliminado;
    }
    
    public void setProveedor (PVProveedores proveedor){
        this.proveedor = proveedor;
    }
    
    public PVProveedores getProveedor (){
        return this.proveedor;
    }
    
    
}