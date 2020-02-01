/*
 * Vehiculos.java
 *
 * Created on 14 de febrero de 2005, 21:18
 */

package MantenimientoDB;

import java.util.*;
import ConexionBD.*;
import Central.CentralVehiculos;
import Central.CentralPuntoDistribucion;
import PuntoDeVenta.PVVehiculos;
import javax.jdo.*;
/**
 *
 * @author  Ignacio Carcas
 */
public class Vehiculos {
    
    /** Creates a new instance of Vehiculos */
    public Vehiculos() {
    }
    
    /**
     *Se encarga de insertar un Vehículo
     *
     * @author Ignacio Carcas
     */
    public int insertar(String matricula, String modelo, Date fechaProxITV, Date fechaCompra, double precioCompra, boolean eliminado, int zona){
        //Insertar en BD Central
        
        //Insertar en BD del nodo
        
        int ok = 0;
        String mensaje = "";
        
        try{
            if (new Utilities().obtenIdObjeto(CentralVehiculos.class, "this.matricula==\""+matricula+"\"", constantes.NODO_CENTRAL) == null){
                ok = 1; //Nos dará 1 si no existe = todo ok.    
            }
        
            int[] peticion;
            if (zona == constantes.NODO_CENTRAL){
                peticion = new int[1];
                peticion[0] = constantes.NODO_CENTRAL;
            }else{
                peticion = new int[2];
                peticion[0] = constantes.NODO_CENTRAL;
                peticion[1] = zona;
            }
        
            PersistenceManager conexiones[] = new Utilities().obtenerConexiones(peticion);

            if ( (ok==1) && (conexiones != null) ){
                try {
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().begin();
                    }

                    //   SEDE CENTRAL
                    //Buscar el departamento
                    CentralPuntoDistribucion pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+zona, constantes.NODO_CENTRAL),false);
                    //Creamos el vehiculo
                    CentralVehiculos vehiculoCentral = new CentralVehiculos(matricula, modelo, fechaProxITV, fechaCompra, precioCompra, false, pd);

                    //Añadimos el empleado a la lista de vehiculos del punto de distribucion
                    pd.addVehiculo(vehiculoCentral);
                   
                    //Hacemos persistente el objeto
                    conexiones[0].makePersistent(vehiculoCentral);

                    //   PUNTO DE VENTA
                    if (zona!=constantes.NODO_CENTRAL){ // Lo insertamos sólamente si el punto de venta no es la sede central
                        
                        //Creamos el vehiculo
                        ArrayList pedidos = null;
                        PVVehiculos vehiculoPV = new PVVehiculos(matricula, modelo, fechaProxITV, fechaCompra, precioCompra, false, pedidos);
                        //Hacemos persistente el objeto
                        conexiones[1].makePersistent(vehiculoPV);
                    }
               
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().commit();
                    }
                    ok = 1;
                }catch (RuntimeException x) {
                    System.out.println("Error en insertar vehiculo: " + x.getMessage());
                    ok = 0;
                    mensaje = "Error al insertar el vehiculo";
                }

                // Close the PersistenceManager instance:
                finally {
                    //Cerramos las BDs si están abiertas
                    new Utilities().ejecutaFinally(conexiones);
                }
            }else{
                ok = 0;
                mensaje = "No se puede insertar el vehiculo al no existir conexion o ser una clave repetida";
                System.err.println("No se puede insertar el vehiculo al no existir conexion o ser una clave repetida");
            }
        }catch(Exception e){
            ok = 0;
            System.err.println(e.getMessage());;
        }
        return (ok);
    }
    
    
    /**
     *Se encarga de eliminar un Vehiculo dado
     *
     * @author Ignacio Carcas
     */
    public int eliminar(String matricula){
        //Eliminar de BD Central
        //Eliminar de BD del nodo
        
        int ok = 0;
        String mensaje = "";
        
        PersistenceManager conexion1 = new Utilities().obtenerConexiones(constantes.NODO_CENTRAL);
        PersistenceManager conexion2 = null;
        
        if (conexion1 != null){
            try {
                conexion1.currentTransaction().begin();
                
                //ELIMINAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                CentralVehiculos vehiculo = (CentralVehiculos) conexion1.getObjectById(new Utilities().obtenIdObjeto(CentralVehiculos.class,"this.matricula==\""+matricula+"\" && this.eliminado==false", constantes.NODO_CENTRAL),false);
                
                if (vehiculo == null){
                    mensaje = "El vehiculo no existe, no puede ser eliminado o ya está desactivado";
                    throw new Exception("El vehiculo no existe o ya está desactivado");
                }
                
                //Obtenemos la segunda conexion
                int zona = vehiculo.getDepartamento().getZona();
                
                vehiculo.setEliminado(true);
                
                // PUNTO DE VENTA: Sólo eliminamos de nuevo el vehiculo si no pertenece a la SEDE CENTRAL
                if (zona != constantes.NODO_CENTRAL){
                    conexion2 = new Utilities().obtenerConexiones(zona);
                    conexion2.currentTransaction().begin();
                    
                    //Obtenemos el objeto
                    PVVehiculos vehiculoPV = (PVVehiculos) conexion2.getObjectById(new Utilities().obtenIdObjeto(PVVehiculos.class,"this.matricula==\""+matricula+"\"", zona),false);
                    
                    // Deshacemos su persistencia --> // conexion2.deletePersistent(empleadoPV);
                    vehiculoPV.setEliminado(true);
                    
                    conexion2.currentTransaction().commit();

                }
                
                //Cerramos la transacción
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
     *Se encarga de modificar la matricula de un Vehiculo dado en todas las BD
     *
     * @author Ignacio Carcas
     */
    public void cambiaMatriculaEnTodasDB(String antiguaMatricula, String nuevaMatricula, PersistenceManager[] conexiones) throws Exception{
        //comprobar primero que nadie tiene la nueva matricula, lo comprobamos en la sede central ya que tiene todos los vehiculos
        Object objVehiculo = new Utilities().obtenIdObjeto(CentralVehiculos.class,"this.matricula==\""+nuevaMatricula+"\"", constantes.NODO_CENTRAL);
        //Si ningún vehiculo tiene esa matricula
        if (objVehiculo==null){
            //cambiar todos las matriculas antiguas por las nuevas
            //primero cambiamos el de la sede central
            CentralVehiculos vehiculo = (CentralVehiculos) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralVehiculos.class,"this.matricula==\""+antiguaMatricula+"\"", constantes.NODO_CENTRAL),false);
            vehiculo.setMatricula(nuevaMatricula);

            //ahora cambiamos el del resto de sedes
            for (int i=1; i<conexiones.length; i++){
                //buscar si tiene un vehiculo con esa matricula y sustituirla
                objVehiculo = new Utilities().obtenIdObjeto(PVVehiculos.class,"this.matricula==\""+antiguaMatricula+"\"", i+1);
                PVVehiculos vehiculoPV = null;
                if (objVehiculo != null){
                    vehiculoPV = (PVVehiculos) conexiones[i].getObjectById(objVehiculo,false);
                    vehiculoPV.setMatricula(nuevaMatricula);
                }
            }
        }else{
            throw new Exception("El cif nuevo ya lo posee otro vehiculo");
        }
    }
    
    /**
     *Se encarga de modificar un Vehiculo dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(String antiguaMatricula, String nuevaMatricula, String modelo, Date fechaProxITV, Date fechaCompra, double precioCompra, boolean eliminado, int zona){
        //Modificar en la BD de la sede central
        
        //Modificar en la BD del nodo
        
        int ok = 0;
        String mensaje = "";

        PersistenceManager [] conexiones = null;
        conexiones = new Utilities().obtenerConexiones(null);
         
        if (conexiones[0] != null){
            try {
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                //MODIFICAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                CentralVehiculos vehiculo = (CentralVehiculos) conexiones[0].getObjectById(new Utilities().obtenIdObjeto(CentralVehiculos.class,"this.matricula==\""+antiguaMatricula+"\"", constantes.NODO_CENTRAL),false);
                if (vehiculo == null){
                    mensaje = "El vehiculo no existe, no puede ser modificado";
                    throw new Exception("El vehiculo no existe, no se puede modificar");
                }

                int zonaAnterior = vehiculo.getDepartamento().getZona();
                
                //Modificamos sus propiedades
                vehiculo.setModelo(modelo);
                vehiculo.setFechaProxITV(fechaProxITV);
                vehiculo.setFechaCompra(fechaCompra);
                vehiculo.setPrecioCompra(precioCompra);
                vehiculo.setEliminado(eliminado);
                
                //Si se le cambia de la zona actual a otra se establece el nuevo dept en la BD de la Sede central
                if (zonaAnterior != zona){
                    CentralPuntoDistribucion pd;
                    // Borrar del punto de distribución anterior ese cliente 
                    Object obj1 = new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+zonaAnterior, constantes.NODO_CENTRAL);
                    pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(obj1,false);
                   
                    pd.deleteVehiculo(vehiculo);

                    // Cambiar su puntero departamento a la nueva sede a la que pertenece
                    obj1 = new Utilities().obtenIdObjeto(CentralPuntoDistribucion.class, "this.zona=="+zona, constantes.NODO_CENTRAL);
                    pd = (CentralPuntoDistribucion) conexiones[0].getObjectById(obj1,false);
                    vehiculo.setDepartamento(pd);

                    // Añadir el cliente al nuevo punto de distribucion
                    pd.addVehiculo(vehiculo);
                }
                
                PVVehiculos vehiculoPV = null;
                // PUNTO DE VENTA: Sólo modificamos de nuevo el cliente si no pertenece a la SEDE CENTRAL
                if (zonaAnterior != constantes.NODO_CENTRAL){
                    //Obtenemos el objeto
                    vehiculoPV = (PVVehiculos) conexiones[zonaAnterior-1].getObjectById(new Utilities().obtenIdObjeto(PVVehiculos.class,"this.matricula==\""+antiguaMatricula+"\"", zonaAnterior),false);
                    
                    if (vehiculoPV == null){
                        mensaje = "El cliente no existe, no puede ser modificado";
                        throw new Exception("El cliente no existe, no se puede modificar");
                    }

                    //Modificamos sus propiedades
                    vehiculoPV.setModelo(modelo);
                    vehiculoPV.setFechaProxITV(fechaProxITV);
                    vehiculoPV.setFechaCompra(fechaCompra);
                    vehiculoPV.setPrecioCompra(precioCompra);
                    vehiculoPV.setEliminado(eliminado);
                    
                    //Si se le cambia de la zona actual a otra
                    if (zonaAnterior != zona){
                        //eliminar el cliente de ese punto de venta
                        vehiculoPV.setEliminado(true);
                    }
                }
                
                if (zonaAnterior!=zona){
                    if (zona!=constantes.NODO_CENTRAL){
                        // Se le cambia a cualquier otro punto de venta que NO sea la sede central
                        //Insertarlo en el nuevo punto de venta

                        //Nos aseguramos que antes no existia
                        PVVehiculos vehiculoPVNuevo = null;
                        Object objVehiculo = new Utilities().obtenIdObjeto(PVVehiculos.class,"this.matricula==\""+antiguaMatricula+"\"", zona);

                        if (objVehiculo != null){
                            vehiculoPVNuevo = (PVVehiculos) conexiones[zona-1].getObjectById(objVehiculo,false);
                        }
                      
                        if (vehiculoPVNuevo == null){ // no hay ningun cliente con esos datos en ese punto de venta, se puede insertar
                            //Creamos el cliente
                            ArrayList pedidos = null;
                            if (vehiculoPV != null){
                                pedidos = vehiculoPV.getAllPedidos();
                            }
                            vehiculoPVNuevo = new PVVehiculos(nuevaMatricula, modelo, fechaProxITV, fechaCompra, precioCompra, eliminado, pedidos);

                            //Lo insertamos
                            //Hacemos persistente el objeto
                            conexiones[zona-1].makePersistent(vehiculoPVNuevo);
                        }else{ // existia un cliente con esos datos en ese punto de venta, hay que activarlo
                            vehiculoPVNuevo.setEliminado(eliminado);
                            vehiculoPVNuevo.setModelo(modelo);
                            vehiculoPVNuevo.setFechaProxITV(fechaProxITV);
                            vehiculoPVNuevo.setFechaCompra(fechaCompra);
                            vehiculoPVNuevo.setPrecioCompra(precioCompra);
                        }
                    }
                }
               
                if (!antiguaMatricula.equals(nuevaMatricula)){
                    cambiaMatriculaEnTodasDB(antiguaMatricula, nuevaMatricula, conexiones);
                }
                
                //Cerramos la transacción
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
                ok = 1;
            }catch (RuntimeException x) {
                System.out.println("Error en modificar vehiculo 1: " + x.getMessage());
                ok = 0;
            }catch (Exception e){
                System.out.println("Error en modificar vehiculo 2: " + e.getMessage());
                ok = 0;
            }

            // Close the PersistenceManager instance:
            finally {
                //Cerramos las BDs si están abiertas
                new Utilities().ejecutaFinally(conexiones);
            }
        }else{
            ok = 0;
            System.err.println("No se puede eliminar en vehiculo al no existir conexion");
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de buscar los Vehiculos con unos parámetros
     *@param matricula: busca el vehiculo con esa matricula. Si matricula ="-1" devuelve todos.
     *@param zona: indica el punto de distribución del cual se quieren obtener los vehículos. Si zona=-1 obtienen los de todos los puntos de distribución
     * @author Ignacio Carcas
     */
     public Vector buscar(String matricula, int zona){
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
                
                query = conexion.newQuery(CentralVehiculos.class);
                    
                 if (!matricula.equals("-1")){
                    //Creamos la consulta
                    consulta += "this.matricula==matricula";
                    //Declaramos el nuevo parámetro
                    parameters += "String matricula";
                    args.put("matricula", matricula);
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
                System.out.println("Error consultando vehiculos: " + e.getMessage());
                v = null;
            }finally{
                //query.closeAll();
                query.close(result);
                /*if (!conexion.isClosed()){
                    conexion.close();
                }*/
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten de vehiculos? Fallo en la conexión a la BD");
        }
        return (v);
    }
}
