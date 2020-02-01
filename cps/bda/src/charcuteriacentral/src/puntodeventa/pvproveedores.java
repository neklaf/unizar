/*
 * Proveedores.java
 *
 * Created on 12 de febrero de 2005, 13:35
 */

/**
 *
 * @author  Ignacio
 */
package PuntoDeVenta;

import java.util.*;

public class PVProveedores {
    
    protected String cif;
    protected String localizacion;
    protected ArrayList tfno; // Lista de strings
    protected String nombre;
    protected String numcuenta;
    protected String persContacto;
    protected String web;
    protected String email;
    protected boolean eliminado;
    protected ArrayList producto; // Lista de ProductosProveedores
    /** Creates a new instance of Proveedores */
    public PVProveedores() {
    }
    
    public PVProveedores(String cif, String localizacion, ArrayList tfno, String nombre, String numcuenta, String persContacto, String web, String email, boolean eliminado, ArrayList producto) {
        Iterator i;
        
        this.cif = cif;
        this.localizacion =localizacion ;
        this.nombre = nombre;
        this.numcuenta = numcuenta;
        this.persContacto = persContacto;
        this.web = web;
        this.email = email;
        this.eliminado = eliminado;
        
        this.tfno = tfno;
        this.producto = producto;
    }
    
    public void setCif (String cif){
        this.cif = cif;
    }
    
    public String getCif (){
        return this.cif;
    }
    
    public void setLocalizacion (String localizacion){
        this.localizacion = localizacion;
    }
    
    public String getLocalizacion (){
        return this.localizacion;
    }
    
    public void setNombre ( String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre (){
        return this.nombre;
    }
    
    public void setNumcuenta (String numcuenta){
        this.numcuenta = numcuenta;
    }
    
    public String getNumCuenta (){
        return this.numcuenta;
    }
    
    public void setPersContacto (String persContacto){
        this.persContacto = persContacto;
    }
    
    public String getPersContacto (){
        return this.persContacto;
    }
    
    public void setWeb (String web){
        this.web = web;
    }
    
    public String getWeb (){
        return this.web;
    }
    
    public void setEmail (String email){
        this.email = email;
    }
    
    public String getEmail (){
        return this.email;
    }
    
    public void setEliminado (boolean eliminado){
        this.eliminado = eliminado;
    }
    
    public boolean getEliminado (){
        return this.eliminado;
    }
        
    public void addProducto(PVProductosProveedores nuevo_producto){
        if (this.producto == null){
            this.producto = new ArrayList();
        }
        this.producto.add(nuevo_producto);
    }
    
    public void deleteProducto(PVProductosProveedores nuevo_producto){
        this.producto.remove(this.producto.indexOf(nuevo_producto));
    }
    
    public void addTfno(String nuevo_tfno){
        if (this.tfno == null){
            this.tfno = new ArrayList();
        }
        this.tfno.add(nuevo_tfno);
    }
    
    public ArrayList getAllTfnos(){
        return this.tfno;
    }
    
    public void setTfno(ArrayList nuevos_tfnos){
        if (this.tfno == null){
            this.tfno = new ArrayList();
        }
        this.tfno = nuevos_tfnos;
    }
}
