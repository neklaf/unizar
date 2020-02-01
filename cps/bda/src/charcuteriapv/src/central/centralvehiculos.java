/*
 * Vehiculos.java
 *
 * Created on 12 de febrero de 2005, 14:52
 */

/**
 *
 * @author  Administrador
 */
package Central;

import java.util.*;

public class CentralVehiculos {
    
    protected String matricula;
    protected String modelo;
    protected Date fechaProxITV;
    protected Date fechaCompra;
    protected double precioCompra;
    protected CentralPuntoDistribucion departamento;
    protected boolean eliminado;
    //protected List pedidos;  // Lista de la clase Pedidos
    
    /** Creates a new instance of Vehiculos */
    public CentralVehiculos() {
    }

    public CentralVehiculos(String matricula, String modelo, Date fechaProxITV, Date fechaCompra, double precioCompra, boolean eliminado, CentralPuntoDistribucion departamento/*, List pedidos*/) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.fechaProxITV = fechaProxITV;
        this.fechaCompra = fechaCompra;
        this.precioCompra = precioCompra;
        this.departamento = departamento;
        this.eliminado = eliminado;
        //this.pedidos = pedidos;
    }
    
    public void setMatricula (String matricula){
        this.matricula = matricula;
    }
    
    public String getMatricula (){
        return this.matricula;
    }
    
    public void setModelo (String modelo){
        this.modelo = modelo;
    }
    
    public String getModelo (){
        return this.modelo;
    }
    
    public void setFechaProxITV (Date fechaProxITV){
        this.fechaProxITV = fechaProxITV;
    }
    
    public Date getFechaProxITV (){
        return this.fechaProxITV;
    }
    
    public void setFechaCompra (Date fechaCompra){
        this.fechaCompra = fechaCompra;
    }
    
    public Date getFechaCompra (){
        return this.fechaCompra;
    }
    
    public void setPrecioCompra (double precioCompra){
        this.precioCompra = precioCompra;
    }
    
    public double getPrecioCompra (){
        return this.precioCompra;
    }
    
    public void setEliminado (boolean eliminado){
        this.eliminado = eliminado;
    }
    
    public boolean getEliminado (){
        return this.eliminado;
    }
    
    public void setDepartamento (CentralPuntoDistribucion departamento){
        this.departamento = departamento;
    }
    
    public CentralPuntoDistribucion getDepartamento (){
        return this.departamento;
    }
    
}
