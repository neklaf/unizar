/*
 * PuntoDistribucion.java
 *
 * Created on 12 de febrero de 2005, 14:07
 */

/**
 *
 * @author  Administrador
 */
package PuntoDeVenta;

import java.util.*;

public class PVPuntoDistribucion {
    
    protected int zona;
    protected String localizacion;
    protected String tfno;
    
    
    /** Creates a new instance of PuntoDistribucion */
    public PVPuntoDistribucion() {
    }
    
    public PVPuntoDistribucion(int zona, String localizacion, String tfno) {
        this.zona = zona;
        this.localizacion = localizacion;
        this.tfno = tfno;
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
    
}
