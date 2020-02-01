/*
 * Pedidos.java
 *
 * Created on 14 de febrero de 2005, 21:23
 */

package MantenimientoDB;

import java.util.*;
import ConexionBD.*;
import PuntoDeVenta.PVPedidos;
import javax.jdo.*;

/**
 *
 * @author  Ignacio Carcas
 */
public class Pedidos {
    
    /** Creates a new instance of Pedidos */
    public Pedidos() {
    }
    
    /**
     *Se encarga de buscar los Pedidos con unos parámetros
     *@param zona: zona en la que se va a buscar, si zona=-1 busca en todas las BD.
     *@param cif: cif de la empresa cliente de la que se quieren buscar pedidos. Si cif=="-1" busca todos los pedidos.
     * @author Ignacio Carcas
     */
    public Vector buscar(int zona, String cif){
        //Buscar en la BD de los nodos
        Vector v = new Vector();
        
        int[] peticion = {zona};
        if ((zona < 2)||(zona > new Utilities().obtenNumeroDB()) ){
            peticion = null;
        }
        
        PersistenceManager[] conexiones = new Utilities().obtenerConexiones(peticion);
        
        System.out.println("Estoy dentro de buscar: zona:"+zona+",cif:"+cif );
        
        if (conexiones[0] != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            String consulta = "";
            Map args = new HashMap();
            try{
                int i,j;
                if (zona == -1){
                    // Si se quieren todos los pedidos habrá que buscar en todas las BD menos en la central
                    i = 1;
                }else{
                    // Buscaremos en la única que ha decidido buscar
                    i = 0;
                }
                for (j=i;j<conexiones.length;j++){
                    // Creamos la sentencia de consulta
                    query = conexiones[j].newQuery(PVPedidos.class);
                    
                     if (!cif.equals("-1")){
                        //Creamos la consulta
                        consulta += "this.comprador.cif==cif";
                        //Declaramos el nuevo parámetro
                        query.declareParameters("String cif");
                        args.put("cif", cif);
                    }

                    //Establecemos el filtro de consulta
                    if (!consulta.equals("")){
                        query.setFilter(consulta);
                    }
                    // Ejecutamos la sentencia pasándole los parámetros
                    if (args.size()>0){
                        result = (Collection) query.executeWithMap(args);
                    }else{
                        result = (Collection) query.execute();
                    }
               
                    v.addAll(result);
                    System.out.println("tamaño"+v.size());
                }
                
            }catch(Exception e){ 
                System.out.println("Error consultando pedidos: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
                //new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten de proveedor? Fallo en la conexión a la BD");
        }
        return (v);
    }
}
