/*
 * Clientes.java
 *
 * Created on 14 de febrero de 2005, 21:17
 */

package MantenimientoDB;

import java.util.*;
import MantenimientoDB.*;
import ConexionBD.*;
import Central.CentralClientes;
import Central.CentralPuntoDistribucion;
import PuntoDeVenta.PVClientes;
import javax.jdo.*;

/**
 *
 * @author  Ignacio Carcas
 */
public class Clientes {
    
    /** Creates a new instance of Clientes */
    public Clientes() {
    }
    
    /**
     *Se encarga de insertar un Cliente
     *@param cif: cif del cliente a insertar
     *@param nombre: nombre de la empresa del cliente
     *@param localizacion: domicilio del cliente
     *@param tfno: su tfno
     *@param email: su email
     *@param web: su dirección de la página web
     *@param persContacto: nombre de la persona de contacto del cliente
     *@param zona: punto de venta de nuestra empresa a la que pertenece como cliente 
     *
     * @author Ignacio Carcas
     */
    public int insertar(String cif, String nombre, String localizacion, String tfno, String email, String web, String persContacto, boolean eliminado){
        //Insertar en BD Central
        //Insertar en BD del nodo
        int ok = 0;
        String mensaje = "";
        int zona = constantes.NODO_PROPIO;
        
        try{
            if (new Utilities().obtenIdObjeto(CentralClientes.class, "this.cif==\""+cif+"\"", constantes.NODO_CENTRAL) == null){
                ok = 1; //Nos dará 1 si no existe = todo ok.    
            }
        
            int[] peticion = {constantes.NODO_CENTRAL, constantes.NODO_PROPIO};
        
            PersistenceManager conexiones[] = new Utilities().obtenerConexiones(peticion);

            if ( (ok==1) && (conexiones[0] != null) ){
                try {
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().begin();
                    }

                    //   SEDE CENTRAL
                    //Buscar el departamento
                    CentralPuntoDistribucion pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+zona, constantes.NODO_CENTRAL),false);
                    //Creamos el empleado
                    CentralClientes clienteCentral = new CentralClientes(cif, nombre, localizacion, tfno, email, web, persContacto, pd, eliminado);

                    //Añadimos el empleado a la lista de empleados del punto de distribucion
                    pd.addCliente(clienteCentral);
                   
                    //Hacemos persistente el objeto
                    conexiones[0].makePersistent(clienteCentral);

                    //   PUNTO DE VENTA
                      
                    ArrayList pedidos = null;
                    PVClientes clientePV = new PVClientes(cif, nombre, localizacion, tfno, email, web, persContacto, eliminado, pedidos);
                    //Hacemos persistente el objeto
                    conexiones[1].makePersistent(clientePV);
                    
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().commit();
                    }
                    ok = 1;
                }catch (RuntimeException x) {
                    System.out.println("Error en insertar cliente: " + x.getMessage());
                    ok = 0;
                    mensaje = "Error al insertar el cliente";
                }

                // Close the PersistenceManager instance:
                finally {
                    //Cerramos las BDs si están abiertas
                    new Utilities().ejecutaFinally(conexiones);
                }
            }else{
                ok = 0;
                mensaje = "No se puede insertar el cliente al no existir conexion o ser una clave repetida";
                System.err.println("No se puede insertar el cliente al no existir conexion o ser una clave repetida");
            }
        }catch(Exception e){
            ok = 0;
            System.err.println(e.getMessage());;
        }
        return (ok);
    }
    
    
    /**
     *Se encarga de eliminar un Cliente dado
     *
     * @author Ignacio Carcas
     */
    public int eliminar(String cif){
        //Eliminar de BD Central
        //Eliminar de BD del nodo
        
        int ok = 0;
        String mensaje = "";
        
        PersistenceManager conexion1 = new Utilities().obtenerConexiones(constantes.NODO_CENTRAL);
        PersistenceManager conexion2 = new Utilities().obtenerConexiones(constantes.NODO_PROPIO);
        
        if (conexion1 != null){
            try {
                
                
                //ELIMINAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                PVClientes cliente = (PVClientes) conexion2.getObjectById(new Utilities().obtenIdObjeto(PVClientes.class,"this.cif==\""+cif+"\" && this.eliminado==false", constantes.NODO_PROPIO),false);
                
                if (cliente == null){
                    mensaje = "El empleado no existe, no puede ser eliminado o ya está desactivado";
                    throw new Exception("El empleado no existe o ya está desactivado");
                }
                
                
                //Obtenemos el objeto
                CentralClientes clienteCentral = (CentralClientes) conexion1.getObjectById(new Utilities().obtenIdObjeto(CentralClientes.class,"this.cif==\""+cif+"\"", constantes.NODO_CENTRAL),false);
                
                conexion1.currentTransaction().begin();
                conexion2.currentTransaction().begin();
                
                clienteCentral.setEliminado(true);
                cliente.setEliminado(true);
                
                //Cerramos la transacción
                conexion2.currentTransaction().commit();
                conexion1.currentTransaction().commit();
                
                ok = 1;
            }catch (RuntimeException x) {
                System.out.println("Error en eliminar cliente 1: " + x.getMessage());
                ok = 0;
            }catch (Exception e){
                System.out.println("Error en eliminar cliente 2: " + e.getMessage());
                ok = 0;
            }

            // Close the PersistenceManager instance:
            finally {
                //Cerramos las BDs si están abiertas
                PersistenceManager []conexiones = {conexion1, conexion2};
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede eliminar en cliente al no existir conexion");
        }
        
        return (ok);
    }
    
    
    /**
     *Se encarga de modificar el cif de un Cliente dado en todas las BD
     *
     * @author Ignacio Carcas
     */
    public void cambiaCifEnTodasDB(String antiguoCif, String nuevoCif, PersistenceManager[] conexiones) throws Exception{
        //comprobar primero que nadie tiene el nuevo nif, lo comprobamos en la sede central ya que tiene todos los clientes
        Object objCliente = new Utilities().obtenIdObjeto(CentralClientes.class,"this.cif==\""+nuevoCif+"\"", constantes.NODO_CENTRAL);
        //Si ningún cliente tiene ese nif
        if (objCliente==null){
            //cambiar todos los cif antiguos por los nuevos
            //primero cambiamos el de la sede central
            CentralClientes cliente = (CentralClientes) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralClientes.class,"this.cif==\""+antiguoCif+"\"", constantes.NODO_CENTRAL),false);
            cliente.setCif(nuevoCif);
            conexiones[0].makePersistent(cliente);

            //ahora cambiamos el del resto de sedes
            for (int i=1; i<conexiones.length; i++){
                //buscar si tiene un cliente con ese cif y sustituirlo
                objCliente = new Utilities().obtenIdObjeto(PVClientes.class,"this.cif==\""+antiguoCif+"\"", i+1);
                PVClientes clientePV = null;
                if (objCliente != null){
                    clientePV = (PVClientes) conexiones[i].getObjectById(objCliente,false);
                    clientePV.setCif(nuevoCif);
                }
            }
        }else{
            throw new Exception("El cif nuevo ya lo posee otro cliente");
        }
        
    }
    
    
    /**
     *Se encarga de modificar un Cliente dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(String antiguoCif, String nuevoCif, String nombre, String localizacion, String tfno, String email, String web, String persContacto, boolean eliminado){
        //Modificar en la BD de la sede central
        
        //Modificar en la BD del nodo
        int ok = 0;
        String mensaje = "";
        int zona = constantes.NODO_PROPIO;
        
        PersistenceManager[] conexiones = null;
        conexiones = new Utilities().obtenerConexiones(null);
        
        if (conexiones[0] != null){
            try {
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                //MODIFICAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                CentralClientes cliente = (CentralClientes) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralClientes.class,"this.cif==\""+antiguoCif+"\"", constantes.NODO_CENTRAL),false);

                if (cliente == null){
                    mensaje = "El cliente no existe, no puede ser modificado";
                    throw new Exception("El cliente no existe, no se puede modificar");
                }
                
                //Modificamos sus propiedades
                //String nombre, String localizacion, String tfno, String email, String web, String persContacto, int zona
                cliente.setNombre(nombre);
                cliente.setLocalizacion(localizacion);
                cliente.setTfno(tfno);
                cliente.setEmail(email);
                cliente.setWeb(web);
                cliente.setPersContacto(persContacto);
                cliente.setEliminado(eliminado);

                
                PVClientes clientePV = null;
                // PUNTO DE VENTA: Sólo modificamos de nuevo el cliente si no pertenece a la SEDE CENTRAL
                    
                //Obtenemos el objeto
                clientePV = (PVClientes) conexiones[zona-1].getObjectById(new Utilities().obtenIdObjeto(PVClientes.class,"this.cif==\""+antiguoCif+"\"", zona),false);

                if (clientePV == null){
                    mensaje = "El cliente no existe, no puede ser modificado";
                    throw new Exception("El cliente no existe, no se puede modificar");
                }

                //Modificamos sus propiedades
                clientePV.setNombre(nombre);
                clientePV.setLocalizacion(localizacion);
                clientePV.setTfno(tfno);
                clientePV.setEmail(email);
                clientePV.setWeb(web);
                clientePV.setPersContacto(persContacto);
                clientePV.setEliminado(eliminado);
                
                if (!antiguoCif.equals(nuevoCif)){
                    cambiaCifEnTodasDB(antiguoCif, nuevoCif, conexiones);
                }

                //Cerramos la transacción
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
                ok = 1;
            }catch (RuntimeException x) {
                System.out.println("Error en modificar cliente 1: " + x.getMessage());
                ok = 0;
            }catch (Exception e){
                System.out.println("Error en modificar cliente 2: " + e.getMessage());
                ok = 0;
            }

            // Close the PersistenceManager instance:
            finally {
                //Cerramos las BDs si están abiertas
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede eliminar en cliente al no existir conexion");
        }
        
        return (ok);
    }
   
    /**
     *Se encarga de buscar los Clientes con unos parámetros en nuestra propia zona
     *@param cif: busca el cliente con ese cif. Si cif ="-1" devuelve todos.
     *
     * @author Ignacio Carcas
     */
     public Vector buscar(String cif){
        //Buscar en la BD de los nodos
        Vector v = new Vector();
        
        int peticion = constantes.NODO_PROPIO;
        PersistenceManager conexion = new Utilities().obtenerConexiones(peticion);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            String consulta = "";
            String parameters = "";
            Map args = new HashMap();
            try{
                
                query = conexion.newQuery(PVClientes.class);
                    
                 if (!cif.equals("-1")){
                    //Creamos la consulta
                    consulta += "this.cif==cif";
                    //Declaramos el nuevo parámetro
                    parameters += "String cif";
                    args.put("cif", cif);
                }
                
                //Declaramos los parámetros
                if (!parameters.equals("")){
                    query.declareParameters(parameters);
                }
                
                //Establecemos el filtro de consulta
                if (!consulta.equals("")){
                    query.setFilter(consulta);
                    //System.out.println(consulta);
                }
                
                // Ejecutamos la sentencia pasándole los parámetros
                if (args.size()>0){
                    //System.out.println("Tamanio:"+args.size());
                    result = (Collection) query.executeWithMap(args);
                }else{
                    result = (Collection) query.execute();
                }

                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error consultando clientes: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
                /*if (!conexion.isClosed()){
                    conexion.close();
                }*/
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten de clientes? Fallo en la conexión a la BD");
        }
        return (v);
    }
   
   
    /**
     *Se encarga de buscar los Clientes con unos parámetros
     *@param cif: busca el cliente con ese cif. Si cif ="-1" devuelve todos.
     *@param zona: indica el punto de distribución del cual se quieren obtener los clientes. Si zona=-1 obtienen los de todos los puntos de distribución
     *
     * @author Ignacio Carcas
     */
   public Vector buscar(String cif, int zona){
        //Buscar en la BD de los nodos
        Vector v = new Vector();
        
        int peticion = constantes.NODO_CENTRAL;
        PersistenceManager conexion = new Utilities().obtenerConexiones(peticion);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            String consulta = "";
            String parameters = "";
            Map args = new HashMap();
            try{
                
                query = conexion.newQuery(CentralClientes.class);
                    
                 if (!cif.equals("-1")){
                    //Creamos la consulta
                    consulta += "this.cif==cif";
                    //Declaramos el nuevo parámetro
                    parameters += "String cif";
                    args.put("cif", cif);
                }
                
                if (zona != -1){
                    if (!consulta.equals("")){
                        consulta += " && ";
                    }
                    //Creamos la consulta
                    consulta += "this.departamento.zona==zona";
                    //Declaramos el nuevo parámetro
                    if (!parameters.equals("")){
                        parameters += ", ";
                    }
                    parameters += "int zona";
                    args.put("zona", new Integer(zona));
                }
                
                //Declaramos los parámetros
                if (!parameters.equals("")){
                    query.declareParameters(parameters);
                }
                
                //Establecemos el filtro de consulta
                if (!consulta.equals("")){
                    query.setFilter(consulta);
                    //System.out.println(consulta);
                }
                
                // Ejecutamos la sentencia pasándole los parámetros
                if (args.size()>0){
                    //System.out.println("Tamanio:"+args.size());
                    result = (Collection) query.executeWithMap(args);
                }else{
                    result = (Collection) query.execute();
                }

                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error consultando clientes: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
                /*if (!conexion.isClosed()){
                    conexion.close();
                }*/
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten de clientes? Fallo en la conexión a la BD");
        }
        return (v);
    }
   
    
}
