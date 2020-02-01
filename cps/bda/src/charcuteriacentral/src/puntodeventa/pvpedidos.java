/*
 * Pedidos.java
 *
 * Created on 12 de febrero de 2005, 15:24
 */

/**
 *
 * @author  Administrador
 */
package PuntoDeVenta;

import java.util.*;

public class PVPedidos {
    
    protected String id_pedido;
    protected Date fechaRealizacion;
    protected int estado; //0-pedido  1-recibido
    protected double precioTotal;
    protected Date fechaEnvio;
    protected PVVehiculos vehiculo;
    protected PVEmpleados vendedor;
    protected PVClientes comprador;
    protected PVPuntoDistribucion departamentoVendedor;
    protected ArrayList detallePedidos;
        
    /** Creates a new instance of PedidosAProveedor */
    public PVPedidos() {
    }
 
    public PVPedidos(String id_pedido,Date fechaRealizacion,int estado,Date fechaEnvio, PVVehiculos vehiculo,PVEmpleados vendedor,PVClientes comprador, PVPuntoDistribucion departamentoVendedor, ArrayList detallePedidos) {
        this.id_pedido = id_pedido;
        this.fechaRealizacion = fechaRealizacion;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.vehiculo = vehiculo;
        this.vendedor = vendedor;
        this.comprador = comprador;
        this.departamentoVendedor = departamentoVendedor;
        
        if (this.detallePedidos == null){
            this.detallePedidos = new ArrayList();
        }
        this.detallePedidos = detallePedidos;
        
        this.precioTotal = calculaPrecioTotal();
    }
    
    
    /**
    * Devuelve el precio total del pedido que se corresponde con la suma del precio de cada uno de los pedidos.
    * @author  Ignacio Carcas
    */
    public double calculaPrecioTotal(){
        double precioTotal = 0.0;
        
        if (this.detallePedidos == null){
            this.detallePedidos = new ArrayList();
        }
        
        Iterator i = detallePedidos.iterator();
        while (i.hasNext()){
            PVDetallePedidos detalle = (PVDetallePedidos) i.next();
            precioTotal += detalle.getPrecioCompra();
        }
        
        return (precioTotal);
    }
    
    public void setId_pedido (String id_pedido){
        this.id_pedido = id_pedido;
    }
    
    public String getId_pedido (){
        return this.id_pedido;
    }
    
    public void setFechaRealizacion (Date fechaRealizacion){
        this.fechaRealizacion = fechaRealizacion;
    }
    
    public Date getFechaRealizacion (){
        return this.fechaRealizacion;
    }
    
    public void setEstado (int estado){
        this.estado = estado;
    }
    
    public int getEstado (){
        return this.estado;
    }
    
    public void setPrecioTotal (double precioTotal){
        this.precioTotal = precioTotal;
    }
    
    public double getPrecioTotal (){
        return this.precioTotal;
    }
    
    public void setFechaEnvio (Date fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    
    public Date getFechaEnvio (){
        return this.fechaEnvio;
    }
    
    public void setVehiculo (PVVehiculos vehiculo){
        this.vehiculo = vehiculo;
    }
    
    public PVVehiculos getVehiculo (){
        return this.vehiculo;
    }
    
    public void setVendedor (PVEmpleados vendedor){
        this.vendedor = vendedor;
    }
    
    public PVEmpleados getVendedor (){
        return this.vendedor;
    }
    
    public void setComprador (PVClientes comprador){
        this.comprador = comprador;
    }
    
    public PVClientes getComprador (){
        return this.comprador;
    }
    
    public void setDepartamentoVendedor (PVPuntoDistribucion departamentoVendedor){
        this.departamentoVendedor = departamentoVendedor;
    }
    
    public PVPuntoDistribucion getDepartamentoVendedor (){
        return this.departamentoVendedor;
    }
    
    public void addDetallePedidos(PVDetallePedidos detalle){
        if (detallePedidos == null){
            detallePedidos = new ArrayList();
        }
        
        this.detallePedidos.add(detalle);
        
        this.precioTotal = calculaPrecioTotal();
    }
    
    public ArrayList getAllPedidos (){
        return this.detallePedidos;
    }
    
    public void borrarAllPedidos (){
        this.detallePedidos.clear();
    }
}