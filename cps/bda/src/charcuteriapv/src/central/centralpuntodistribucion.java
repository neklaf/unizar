/*
 * PuntoDistribucion.java
 *
 * Created on 12 de febrero de 2005, 14:07
 */

/**
 *
 * @author  Administrador
 */
package Central;

import java.util.*;

public class CentralPuntoDistribucion {
    
    protected int zona;
    protected String localizacion;
    protected String tfno;
    protected ArrayList empleados = new ArrayList();  //referencia a la clase Empleados
    protected ArrayList vehiculos = new ArrayList();
    //protected ArrayList pedidosAProv;
    //protected ArrayList ofertas;
    protected ArrayList clientes = new ArrayList();
    //protected Vector miVector;
    
    /** Creates a new instance of PuntoDistribucion */
    public CentralPuntoDistribucion() {
        //this.miVector = new Vector();
    }
    
    public CentralPuntoDistribucion(int zona, String localizacion, String tfno, ArrayList empleados, ArrayList vehiculos,/* List pedidosAProv, List ofertas,*/ ArrayList clientes) {
        this.zona = zona;
        this.localizacion = localizacion;
        this.tfno = tfno;
        this.empleados = empleados;
        this.vehiculos = vehiculos;
        //this.pedidosAProv = pedidosAProv;
        //this.ofertas = ofertas;
        this.clientes = clientes;
        //this.miVector = new Vector();
    }
    
    public void setZona (int zona){
        this.zona = zona;
    }
    
    public int getZona (){
        return this.zona;
    }
    
    public void setLocalizacion (String localizacion){
        this.localizacion = localizacion;
    }
    
    public String getLocalizacion (){
        return this.localizacion;
    }
    
    public void setTfno (String tfno){
        this.tfno = tfno;
    }
    
    public String getTfno (){
        return this.tfno;
    }
    
    
    public ArrayList getEmpleado(){
        return this.empleados;
    }
    
    public void addEmpleado(CentralEmpleados nuevo_empleado){
        if (this.empleados == null){
            this.empleados = new ArrayList();
        }
        
        this.empleados.add(nuevo_empleado);
        
    }
    
    public void deleteEmpleado(CentralEmpleados empleado){
        this.empleados.remove(this.empleados.indexOf(empleado));
    }
    
    
    public ArrayList getAllVehiculos(){
        return this.vehiculos;
    }
    
    
    public void addVehiculo(CentralVehiculos nuevo_vehiculo){
        if (this.vehiculos == null){
            this.vehiculos = new ArrayList();
        }
        this.vehiculos.add(nuevo_vehiculo);
    }
    
    
    public void deleteVehiculo(CentralVehiculos vehiculo){
        this.vehiculos.remove(this.vehiculos.indexOf(vehiculo));
    }
    
    
    
    public Iterator getClientes(){
        return this.clientes.iterator();
    }
    
    
     public void addCliente(CentralClientes nuevo_cliente){
        if (this.clientes == null){
            this.clientes = new ArrayList();
        }
        this.clientes.add(nuevo_cliente);
    }
    
    
    public void deleteCliente(CentralClientes cliente){
        this.clientes.remove(this.clientes.indexOf(cliente));
    }
    
}
