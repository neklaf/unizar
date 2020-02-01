/*
 * Empleados.java
 *
 * Created on 12 de febrero de 2005, 14:57
 */

/**
 *
 * @author  Administrador
 */
package PuntoDeVenta;

import java.util.*;

public class PVEmpleados {
    
    protected String nif;
    protected String domicilio;
    protected String cargo;
    protected boolean activo;
    protected double porcentajeComision;
    protected ArrayList tfnoPers;
    protected ArrayList tfnoEmpr;
    protected Date fechaAltaContrato;
    protected String nombre;
    protected String apellidos;
    protected double sueldoBase;
    protected String numcuenta;
    protected PVEmpleados jefe;
    //protected PVPuntoDistribucion departamento;
    protected ArrayList ventas;
    
    /** Creates a new instance of Empleados */
    public PVEmpleados() {
    }
    
    public PVEmpleados(String nif,String domicilio,String cargo,boolean activo,double porcentajeComision,ArrayList tfnoPers,ArrayList tfnoEmpr,Date fechaAltaContrato,String nombre,String apellidos,double sueldoBase,String numcuenta,PVEmpleados jefe,/*PVPuntoDistribucion departamento,*/ArrayList ventas) {
        this.nif = nif;
        this.domicilio = domicilio;
        this.cargo = cargo;
        this.activo = activo;
        this.porcentajeComision = porcentajeComision;
        this.tfnoPers = tfnoPers;
        this.tfnoEmpr = tfnoEmpr;
        this.fechaAltaContrato = fechaAltaContrato;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sueldoBase = sueldoBase;
        this.numcuenta = numcuenta;
        this.jefe = jefe;
        //this.departamento = departamento;
        this.ventas = ventas;
    }
    
    public void setNif (String nif){
        this.nif = nif;
    }
    
    public String getNif(){
        return this.nif;
    }
        
    public void setDomicilio (String domicilio){
        this.domicilio = domicilio;
    }
    
    public String getDomicilio (){
        return this.domicilio;
    }
    
    public void setCargo (String cargo){
        this.cargo = cargo;
    }
    
    public String getCargo (){
        return this.cargo;
    }
    
    public void setActivo (boolean activo){
        this.activo = activo;
    }
    
    public boolean getActivo (){
        return this.activo;
    }
    
    public void setPorcentajeComision (double porcentajeComision){
        this.porcentajeComision = porcentajeComision;
    }
    
    public double getPorcentajeComision (){
        return this.porcentajeComision;
    }
    
    public void setFechaAltaContrato (Date fechaAltaContrato){
        this.fechaAltaContrato = fechaAltaContrato;
    }
    
    public Date getFechaAltaContrato (){
        return this.fechaAltaContrato;
    }
    
    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre (){
        return this.nombre;
    }
    
    public void setApellidos (String apellidos){
        this.apellidos = apellidos;
    }
    
    public String getApellidos (){
        return this.apellidos;
    }
    
    public void setSueldoBase (double sueldoBase){
        this.sueldoBase = sueldoBase;
    }
    
    public double getSueldoBase (){
        return this.sueldoBase;
    }
    
    public void setNumcuenta (String numcuenta){
        this.numcuenta = numcuenta;
    }
    
    public String getNumcuenta (){
        return this.numcuenta;
    }
    
    public void setJefe (PVEmpleados jefe){
        this.jefe = jefe;
    }
    
    public PVEmpleados getJefe (){
        return this.jefe;
    }
    
    /*
    public void setDepartamento (PVPuntoDistribucion departamento){
        this.departamento = departamento;
    }
    
    public PVPuntoDistribucion getDepartamento (){
        return this.departamento;
    }
    */
    
    public void addTfnoPers(String nuevo_tfno){
        if (this.tfnoPers == null){
            this.tfnoPers = new ArrayList();
        }
        this.tfnoPers.add(nuevo_tfno);
    }
    
    public ArrayList getAllTfnoPers (){
        return this.tfnoPers;
    }
    
    public void setAllTfnoPers (ArrayList tfnos){
        if (this.tfnoPers == null){
            this.tfnoPers = new ArrayList();
        }
        this.tfnoPers.addAll(tfnos);
    }
    
    public void setTfnoPers(ArrayList nuevos_tfnos){
        if (this.tfnoPers == null){
            this.tfnoPers = new ArrayList();
        }
        this.tfnoPers = nuevos_tfnos;
    }
    
    public void addTfnoEmpr(String nuevo_tfno){
        if (this.tfnoEmpr == null){
            this.tfnoEmpr = new ArrayList();
        }
        this.tfnoEmpr.add(nuevo_tfno);
    }
    
    public ArrayList getAllTfnoEmpr (){
        return this.tfnoEmpr;
    }
    
    public void setAllTfnoEmpr (ArrayList tfnos){
        if (this.tfnoEmpr == null){
            this.tfnoEmpr = new ArrayList();
        }
        this.tfnoEmpr.addAll(tfnos);
    }
    
    public void setTfnoEmpr(ArrayList nuevos_tfnos){
        if (this.tfnoEmpr == null){
            this.tfnoEmpr = new ArrayList();
        }
        this.tfnoEmpr = nuevos_tfnos;
    }
    
    public void addPedido(PVPedidos nuevo_pedido){
        if (this.ventas == null){
            this.ventas = new ArrayList();
        }
        this.ventas.add(nuevo_pedido);
    }
    
}
