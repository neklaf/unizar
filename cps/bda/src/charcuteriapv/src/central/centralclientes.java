/*
 * Clientes.java
 *
 * Created on 12 de febrero de 2005, 14:14
 */

/**
 *
 * @author  Administrador
 */
package Central;

import java.util.*;

public class CentralClientes {

    protected String cif;
    protected String nombre;
    protected String localizacion;
    protected String tfno;
    protected String email;
    protected String web;
    protected String persContacto;
    protected CentralPuntoDistribucion departamento;
    protected boolean eliminado;
    //protected List pedidos; // lista de la clase Pedidos
    
    /** Creates a new instance of Clientes */
    public CentralClientes() {    
    }

    public CentralClientes( String cif, String nombre, String localizacion, String tfno, String email, String web, String persContacto, CentralPuntoDistribucion departamento/*, List pedidos*/,boolean eliminado) {    
        this.cif = cif;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.tfno = tfno;
        this.email = email;
        this.web = web;
        this.persContacto = persContacto;
        this.departamento = departamento;
        this.eliminado = eliminado;
        //this.pedidos = pedidos;
    }

    public void setCif (String cif){
        this.cif = cif;
    }
    
    public String getCif (){
        return this.cif;
    }
    
    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre (){
        return this.nombre;
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
    
    public void setEmail (String email){
        this.email = email;
    }
    
    public String getEmail (){
        return this.email;
    }
    
    public void setWeb (String web){
        this.web = web;
    }
    
    public String getWeb (){
        return this.web;
    }
    
    public void setPersContacto (String persContacto){
        this.persContacto = persContacto;
    }
    
    public String getPersContacto (){
        return this.persContacto;
    }
    
    public void setDepartamento (CentralPuntoDistribucion departamento){
        this.departamento = departamento;
    }
    
    public CentralPuntoDistribucion getDepartamento (){
        return this.departamento;
    }
    
    public void setEliminado (boolean eliminado){
        this.eliminado = eliminado;
    }
    
    public boolean getEliminado (){
        return this.eliminado;
    }
    
    /*
    public void addPedido(CentralPedidos nuevo_pedido){
        this.pedidos.add(nuevo_pedido);
    }
    */
}
