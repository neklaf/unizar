/*
 * Empleados.java
 *
 * Created on 12 de febrero de 2005, 14:57
 */

/**
 *
 * @author  Administrador
 */
package Central;

import java.util.*;

public class CentralEmpleados {
    
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
    protected CentralEmpleados jefe;
    protected CentralPuntoDistribucion departamento;
    //protected List ventas;
    
    /** Creates a new instance of Empleados */
    public CentralEmpleados() {
    }
    
    public CentralEmpleados(String nif,String domicilio,String cargo,boolean activo,double porcentajeComision,ArrayList tfnoPers,ArrayList tfnoEmpr,Date fechaAltaContrato,String nombre,String apellidos,double sueldoBase,String numcuenta,CentralEmpleados jefe,CentralPuntoDistribucion departamento/*,List ventas*/) {
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
        this.departamento = departamento;
        //this.ventas = ventas;
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
    
    public void setJefe (CentralEmpleados jefe){
        this.jefe = jefe;
    }
    
    public CentralEmpleados getJefe (){
        return this.jefe;
    }
    
    public void setDepartamento (CentralPuntoDistribucion departamento){
        this.departamento = departamento;
    }
    
    public CentralPuntoDistribucion getDepartamento (){
        return this.departamento;
    }
    
    public void addTfnoPers(String nuevo_tfno){
        if (this.tfnoPers == null){
            this.tfnoPers = new ArrayList();
        }
        this.tfnoPers.add(nuevo_tfno);
    }
    
    public void setTfnoPers(ArrayList nuevos_tfnos){
        if (this.tfnoPers == null){
            this.tfnoPers = new ArrayList();
        }
        this.tfnoPers = nuevos_tfnos;
    }
    
    public ArrayList getAllTfnoPers (){
        return this.tfnoPers;
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
    
    public void setTfnoEmpr(ArrayList nuevos_tfnos){
        if (this.tfnoEmpr == null){
            this.tfnoEmpr = new ArrayList();
        }
        this.tfnoEmpr = nuevos_tfnos;
    }
    
    

}