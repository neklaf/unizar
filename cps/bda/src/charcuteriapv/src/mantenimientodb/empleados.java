/*
 * Empleados.java
 *
 * Created on 14 de febrero de 2005, 21:22
 */

package MantenimientoDB;

import java.util.*;
import javax.jdo.*;
import Central.CentralEmpleados;
import Central.CentralPuntoDistribucion;
import PuntoDeVenta.PVEmpleados;
import PuntoDeVenta.PVPuntoDistribucion;
import ConexionBD.*;
/**
 *
 * @author  Ignacio Carcas
 */
public class Empleados {
    
    /** Creates a new instance of Empleados */
    public Empleados() {
    }
    
    /**
     *Se encarga de insertar un Empleado
     *
     * @author Ignacio Carcas
     */
    public int insertar(String nif,String domicilio,String cargo,boolean activo,double porcentajeComision,ArrayList tfnoPers,ArrayList tfnoEmpr,Date fechaAltaContrato,String nombre,String apellidos,double sueldoBase,String numcuenta,String nifJefe){
        //Insertar en BD Central
        
        //Insertar en BD del nodo
        
        int ok = 0;
        String mensaje = "";
        int zona = constantes.NODO_PROPIO;
        
        try{
            if (new Utilities().obtenIdObjeto(CentralEmpleados.class, "this.nif==\""+nif+"\"", constantes.NODO_CENTRAL) == null){
                ok = 1; //Nos dar� 1 si no existe = todo ok.    
            }
        
            int[] peticion = {constantes.NODO_CENTRAL, constantes.NODO_PROPIO};
            PersistenceManager conexiones[] = new Utilities().obtenerConexiones(peticion);

            if ( (ok==1) && (conexiones != null) ){
                try {
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().begin();
                    }

                    //   SEDE CENTRAL
                    //Buscar el jefe a trav�s de nifJefe
                    Object obj = new Utilities().obtenIdObjeto(CentralEmpleados.class, "this.nif==\""+nifJefe+"\"", constantes.NODO_CENTRAL);
                    CentralEmpleados empleadoJefe = null;

                    if (obj != null ){
                        empleadoJefe = (CentralEmpleados) conexiones[0].getObjectById(obj,false);
                    }
                    //Buscar el departamento
                    CentralPuntoDistribucion pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+zona, constantes.NODO_CENTRAL),false);
                    //Creamos el empleado
                    CentralEmpleados emplCentral = new CentralEmpleados(nif, domicilio, cargo, activo, porcentajeComision, tfnoPers, tfnoEmpr, fechaAltaContrato, nombre, apellidos, sueldoBase, numcuenta, empleadoJefe, pd);
                    
                    //A�adimos el empleado a la lista de empleados del punto de distribucion
                    pd.addEmpleado(emplCentral);
                   
                    //Hacemos persistente el objeto
                    conexiones[0].makePersistent(emplCentral);
 
                    //   PUNTO DE VENTA

                    //Buscar el jefe a trav�s de nifJefe
                    obj = new Utilities().obtenIdObjeto(PVEmpleados.class, "this.nif==\""+nifJefe+"\"", zona);

                    PVEmpleados empleadoJefePV = null;
                    if (obj != null ){
                        empleadoJefePV = (PVEmpleados) conexiones[1].getObjectById(obj,false);
                    }

                    //Creamos el empleado
                    PVEmpleados emplPV = new PVEmpleados(nif, domicilio, cargo, activo, porcentajeComision, tfnoPers, tfnoEmpr, fechaAltaContrato, nombre, apellidos, sueldoBase, numcuenta, empleadoJefePV, null);
                    //Hacemos persistente el objeto
                    conexiones[1].makePersistent(emplPV);
                    
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().commit();
                    }
                    ok = 1;
                }catch (RuntimeException x) {
                    System.out.println("Error en insertar empleado: " + x.getMessage());
                    ok = 0;
                    mensaje = "Error al insertar el empleado";
                }

                // Close the PersistenceManager instance:
                finally {
                    //Cerramos las BDs si est�n abiertas
                    new Utilities().ejecutaFinally(conexiones);
                }
            }else{
                ok = 0;
                mensaje = "No se puede insertar el empleado al no existir conexion o ser una clave repetida";
                System.err.println("No se puede insertar el empleado al no existir conexion o ser una clave repetida");
            }
        }catch(Exception e){
            ok = 0;
            System.err.println(e.getMessage());;
        }
        return (ok);
    }
    
    
    /**
     *Se encarga de eliminar un Empleado dado. Realmente no lo elimina. Lo marca como inactivo. activo=false; Se hace
     *as� para que las relaciones de los pedidos vendidos no se estropeen.
     *
     * @author Ignacio Carcas
     */
    public int eliminar(String nif){
        //Eliminar de BD Central
        //Eliminar de BD del nodo
        
        int ok = 0;
        String mensaje = "";
        
        PersistenceManager conexion1 = new Utilities().obtenerConexiones(constantes.NODO_CENTRAL);
        PersistenceManager conexion2 = new Utilities().obtenerConexiones(constantes.NODO_PROPIO);
        
        if (conexion1 != null){
            try {
                conexion1.currentTransaction().begin();
                
                //ELIMINAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                CentralEmpleados empleado = (CentralEmpleados) conexion1.getObjectById(new Utilities().obtenIdObjeto(CentralEmpleados.class,"this.nif==\""+nif+"\" && this.activo==true", constantes.NODO_CENTRAL),false);
                //Obtenemos el objeto del punto de venta
                PVEmpleados empleadoPV = (PVEmpleados) conexion2.getObjectById(new Utilities().obtenIdObjeto(PVEmpleados.class,"this.nif==\""+nif+"\"", constantes.NODO_PROPIO),false);
                
                if (empleadoPV == null){
                    mensaje = "El empleado no existe, no puede ser eliminado o ya est� desactivado";
                    throw new Exception("El empleado no existe o ya est� desactivado");
                }
                
                
                //INICIO de Hacemos que sus empleados subordinados apunten a su empleado jefe
                CentralEmpleados empleadoSubordinado;
                
                //Debemos coger una colecci�n de empleados subordinados y cambiar uno a uno apuntanto como jefe a al jefe de este empleado
                Collection colecSubordinados = null;
                Query query = null;
                try{
                    // Creamos la sentencia de consulta
                    query = conexion1.newQuery(CentralEmpleados.class, "this.jefe.nif==nif");
                    query.declareParameters("String nif");
                    // Ejecutamos la sentencia pas�ndole los par�metros
                    colecSubordinados = (Collection) query.execute(nif);

                    Iterator i = colecSubordinados.iterator();
                    while (i.hasNext()){
                        empleadoSubordinado = (CentralEmpleados) i.next();
                        empleadoSubordinado.setJefe(empleado.getJefe());
                    }

                }catch(Exception e){ 
                    System.err.println("Error cambiando orden en jerarquia de empleados: " + e.getMessage());
                }finally{
                     query.close(colecSubordinados);
                }
                //FIN de Hacemos que sus empleados subordinados apunten a su empleado jefe
               
                
                empleado.setActivo(false);
                
                // PUNTO DE VENTA: S�lo eliminamos de nuevo el empleado si no pertenece a la SEDE CENTRAL
                conexion2.currentTransaction().begin();

                //INICIO de Hacemos que sus empleados subordinados apunten a su empleado jefe
                PVEmpleados empleadoSubordinadoPV;

                //Debemos coger una colecci�n de empleados subordinados y cambiar uno a uno apuntanto como jefe a al jefe de este empleado
                Collection colecSubordinadosPV = null;
                query = null;
                try{
                    // Creamos la sentencia de consulta
                    query = conexion2.newQuery(PVEmpleados.class, "this.jefe.nif==nif");
                    query.declareParameters("String nif");
                    // Ejecutamos la sentencia pas�ndole los par�metros
                    colecSubordinadosPV = (Collection) query.execute(nif);

                    Iterator i = colecSubordinadosPV.iterator();
                    while (i.hasNext()){
                        empleadoSubordinadoPV = (PVEmpleados) i.next();
                        empleadoSubordinadoPV.setJefe(empleadoPV.getJefe());
                    }
                    //empleadoSubordinado = (CentralEmpleados) conexion1.getObjectById(new Utilities().obtenIdObjeto(CentralEmpleados.class,"this.jefe.nif==\""+nif+"\"", constantes.NODO_CENTRAL),false);

                }catch(Exception e){ 
                    System.err.println("Error cambiando orden en jerarquia de empleados: " + e.getMessage());
                }finally{
                     query.close(colecSubordinadosPV);
                }
                //FIN de Hacemos que sus empleados subordinados apunten a su empleado jefe

                empleadoPV.setActivo(false);

                //Cerramos la transacci�n
                conexion2.currentTransaction().commit();
                conexion1.currentTransaction().commit();
                
                ok = 1;
            }catch (RuntimeException x) {
                System.out.println("Error en eliminar empleado 1: " + x.getMessage());
                ok = 0;
            }catch (Exception e){
                System.out.println("Error en eliminar empleado 2: " + e.getMessage());
                ok = 0;
            }

            // Close the PersistenceManager instance:
            finally {
                //Cerramos las BDs si est�n abiertas
                PersistenceManager []conexiones = {conexion1, conexion2};
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede eliminar en empleado al no existir conexion");
        }
        
        return (ok);
        
    }
    
    public void cambiaNifEnTodasDB(String antiguoNif, String nuevoNif, PersistenceManager[] conexiones) throws Exception{
        //comprobar primero que nadie tiene el nuevo nif, lo comprobamos en la sede central ya que tiene todos los empleados
        Object objEmpleado = new Utilities().obtenIdObjeto(CentralEmpleados.class,"this.nif==\""+nuevoNif+"\"", constantes.NODO_CENTRAL);
        //Si ning�n empleado tiene ese nif
        if (objEmpleado==null){
            //cambiar todos los nif antiguos por los nuevos
            //primero cambiamos el de la sede central
            CentralEmpleados empleado = (CentralEmpleados) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralEmpleados.class,"this.nif==\""+antiguoNif+"\"", constantes.NODO_CENTRAL),false);
            empleado.setNif(nuevoNif);

            //ahora cambiamos el del resto de sedes
            for (int i=1; i<conexiones.length; i++){
                //buscar si tiene un empleado con ese nif y sustituirlo
                objEmpleado = new Utilities().obtenIdObjeto(PVEmpleados.class,"this.nif==\""+antiguoNif+"\"", i+1);
                PVEmpleados empleadoPV = null;
                if (objEmpleado != null){
                    empleadoPV = (PVEmpleados) conexiones[i].getObjectById(objEmpleado,false);
                    empleadoPV.setNif(nuevoNif);
                }
            }

        }else{
            throw new Exception("El nif nuevo ya lo posee otro usuario");
        }
    }
    
    /**
     *Se encarga de modificar un Empleado dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(String antiguoNif, String nuevoNif, String domicilio,String cargo, double porcentajeComision, Date fechaAltaContrato,String nombre,String apellidos, double sueldoBase, String numcuenta, String nifJefe, boolean activo){
        //Modificar en la BD de la sede central
        //Modificar en la BD del nodo
        
        int ok = 0;
        String mensaje = "";
        int zona = constantes.NODO_PROPIO;
        
        PersistenceManager [] conexiones = null;
        conexiones = new Utilities().obtenerConexiones(null);
            
        if (conexiones[0] != null){
            try {
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                //MODIFICAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                CentralEmpleados empleado = (CentralEmpleados) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralEmpleados.class,"this.nif==\""+antiguoNif+"\"", constantes.NODO_CENTRAL),false);
                //Obtenemos el objeto
                PVEmpleados empleadoPV = (PVEmpleados) conexiones[zona-1].getObjectById(new Utilities().obtenIdObjeto(PVEmpleados.class,"this.nif==\""+antiguoNif+"\"", zona),false);

                if (empleadoPV == null){
                    mensaje = "El empleado no existe, no puede ser modificado";
                    throw new Exception("El empleado no existe, no se puede modificar");
                }

                //Modificamos sus propiedades
                //empleado.setNif(nuevoNif); // Ya est� modificado desde el m�todo cambiaNifEnTodasDB()
                empleado.setDomicilio(domicilio);
                empleado.setCargo(cargo);
                empleado.setPorcentajeComision(porcentajeComision);
                empleado.setFechaAltaContrato(fechaAltaContrato);
                empleado.setNombre(nombre);
                empleado.setApellidos(apellidos);
                empleado.setSueldoBase(sueldoBase);
                empleado.setNumcuenta(numcuenta);
                empleado.setActivo(activo);
                
                //Si tiene un nuevo jefe
                CentralEmpleados nuevoJefe = null;
                    //Si el nuevo jefe no es null y el jefe anterior no era null y no es el mismo jefe cuando no era null...
                //if (( (!nifJefe.equals("")) &&(empleado.getJefe()!=null) && (! empleado.getJefe().getNif().equals(nifJefe) ) ) || ((!nifJefe.equals("")) && (empleado.getJefe()==null))){
                if (( (empleado.getJefe()!=null) && (! empleado.getJefe().getNif().equals(nifJefe) ) ) || ((!nifJefe.equals("")) && (empleado.getJefe()==null))){
                    //Buscamos el nuevo jefe
                    Object obj1 = new Utilities().obtenIdObjeto(CentralEmpleados.class,"this.nif==\""+nifJefe+"\"", constantes.NODO_CENTRAL);
                    if (obj1 != null){
                        nuevoJefe = (CentralEmpleados) conexiones[0].getObjectById(obj1,false);
                    }
                    empleado.setJefe(nuevoJefe);
                }

                // PUNTO DE VENTA: S�lo modificamos de nuevo el empleado si no pertenece a la SEDE CENTRAL
                if (empleadoPV == null){
                    mensaje = "El empleado no existe, no puede ser modificado";
                    throw new Exception("El empleado no existe, no se puede modificar");
                }

                //Modificamos sus propiedades
                //empleadoPV.setNif(nuevoNif); // Ya est� modificado desde el m�todo cambiaNifEnTodasDB()
                empleadoPV.setDomicilio(domicilio);
                empleadoPV.setCargo(cargo);
                empleadoPV.setPorcentajeComision(porcentajeComision);
                empleadoPV.setFechaAltaContrato(fechaAltaContrato);
                empleadoPV.setNombre(nombre);
                empleadoPV.setApellidos(apellidos);
                empleadoPV.setSueldoBase(sueldoBase);
                empleadoPV.setNumcuenta(numcuenta);
                empleadoPV.setActivo(activo);

                //Si tiene un nuevo jefe
                if ( ( (empleadoPV.getJefe()!=null) && (! empleadoPV.getJefe().getNif().equals(nifJefe)) ) || ( (empleadoPV.getJefe()==null) && (!nifJefe.equals("")) )){
                    //Buscamos el nuevo jefe
                    PVEmpleados nuevoJefePV = null;
                    Object obj1 = new Utilities().obtenIdObjeto(PVEmpleados.class,"this.nif==\""+nifJefe+"\"", zona);
                    if (obj1 != null){
                        nuevoJefePV = (PVEmpleados) conexiones[zona-1].getObjectById(obj1,false);
                    }
                    //Lo asignamos
                    empleadoPV.setJefe(nuevoJefePV);
                }

                if (!antiguoNif.equals(nuevoNif)){
                    cambiaNifEnTodasDB(antiguoNif, nuevoNif, conexiones);
                }
                
                //Cerramos la transacci�n
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
                ok = 1;
            }catch (RuntimeException x) {
                System.out.println("Error en modificar empleado 1: " + x.getMessage());
                ok = 0;
            }catch (Exception e){
                System.out.println("Error en modificar empleado 2: " + e.getMessage());
                ok = 0;
            }

            // Close the PersistenceManager instance:
            finally {
                //Cerramos las BDs si est�n abiertas
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede eliminar en empleado al no existir conexion");
        }

        return (ok);
        
    }
    
    /**
     *Se encarga de buscar los Empleados con unos par�metros
     *
     *@param nif nif del empleado a buscar. Si es "-1" busca todos los empleados.
     *@param estado: s�lo lo tiene en cuenta si nif="-1", (0) busca inactivos, (1) busca activos, (2) busca ambos 
     *@param zona: indica el punto de distribuci�n del cual se quieren obtener los empleados. Si zona=-1 obtienen los de todos los puntos de distribuci�n
     *
     * @author Ignacio Carcas
     */
    public Vector buscar(String nif, int estado){
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
                
                query = conexion.newQuery(PVEmpleados.class);
                
                if ( estado!=2){
                    //Creamos la consulta
                    boolean activo = true;
                    if(estado == 1){
                        consulta += "this.activo==activo";
                        activo = true;
                    }else if(estado == 0){
                        consulta += "this.activo==activo";
                        activo = false;
                    }
                    //Declaramos el nuevo par�metro
                    parameters += "boolean activo";
                    args.put("activo", new Boolean(activo));
                }
                
                if (!nif.equals("-1")){
                    if (!consulta.equals("")){
                        consulta += " && ";
                    }
                    //Creamos la consulta
                    consulta += "this.nif==nif";
                    //Declaramos el nuevo par�metro
                    if (!parameters.equals("")){
                        parameters += ", ";
                    }
                    parameters += "String nif";
                    args.put("nif", nif);
                }
                
                //Declaramos los par�metros
                if (!parameters.equals("")){
                    query.declareParameters(parameters);
                }
                
                //Establecemos el filtro de consulta
                if (!consulta.equals("")){
                    query.setFilter(consulta);
                    //System.out.println(consulta);
                }
                
                // Ejecutamos la sentencia pas�ndole los par�metros
                if (args.size()>0){
                    //System.out.println("Tamanio:"+args.size());
                    result = (Collection) query.executeWithMap(args);
                }else{
                    result = (Collection) query.execute();
                }

                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error consultando empleados: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
                /*if (!conexion.isClosed()){
                    conexion.close();
                }*/
            }
        }else{
            v = null; // Fallo en la conexi�n. 
            System.err.println("�Exiten de empleados? Fallo en la conexi�n a la BD");
        }
        return (v);
    }
    
    
    /**
     *M�todo para retocar los n�meros de tfno personales.
     *@param nif: nif de la persona a la que pertenecen esos tfnos
     *@param nuevos_tfnos: lista de tfnos de la persona
     *@param tipoTfno: 1 -> tfnos personales, 2 -> tfnos empresa
     *
     * @author Ignacio Carcas
     */
    public int modificarTfno(String nif, ArrayList nuevos_tfnos, int tipoTfno){
        int ok = 1;
        int zonaEmpleado = constantes.NODO_PROPIO;
        PersistenceManager conexion1 = null;
        PersistenceManager conexion2 = null;
        try{
            conexion1 = new Utilities().obtenerConexiones(constantes.NODO_CENTRAL);
            conexion2 = new Utilities().obtenerConexiones(zonaEmpleado);
            
            // Comprobamos si exist�a alg�n producto con esa referencia
            Object objEmpleado = new Utilities().obtenIdObjeto(CentralEmpleados.class,"this.nif==\""+nif+"\"", constantes.NODO_CENTRAL);
            //Si existe empleado con ese nif -> todo ok
            if (objEmpleado!=null){
                
                // SEDE CENTRAL
                CentralEmpleados empleadoCentral = (CentralEmpleados) conexion1.getObjectById(objEmpleado,false);
                
                conexion1.currentTransaction().begin();
                //Actualizamos los tfnos
                if (tipoTfno==1){
                    empleadoCentral.setTfnoPers(nuevos_tfnos);
                }else if (tipoTfno==2){
                    empleadoCentral.setTfnoEmpr(nuevos_tfnos);
                }
                

                // PUNTO DE VENTA
                // obtenemos el empleado
                objEmpleado = new Utilities().obtenIdObjeto(PVEmpleados.class,"this.nif==\""+nif+"\"", zonaEmpleado);
                PVEmpleados empleadoPV = (PVEmpleados) conexion2.getObjectById(objEmpleado,false);

                conexion2.currentTransaction().begin();
                //Actualizamos los tfnos
                if (tipoTfno==1){
                    empleadoPV.setTfnoPers(nuevos_tfnos);
                }else if (tipoTfno==2){
                    empleadoPV.setTfnoEmpr(nuevos_tfnos);
                }

                conexion2.currentTransaction().commit();
                conexion1.currentTransaction().commit();
              
            
            }else{//Si no existe empleado con ese nif -> ok = 0; fallo
                throw new Exception("Empleado no encontrado");
            }
        }catch(Exception e){
            System.out.println("Error en modificar tfno empleado: " + e.getMessage());
            ok = 0;
        }finally{
            PersistenceManager conexiones[] = {conexion1, conexion2};
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return ok;
    }
    
}