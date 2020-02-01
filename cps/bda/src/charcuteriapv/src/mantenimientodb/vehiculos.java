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
    public int insertar(String matricula, String modelo, Date fechaProxITV, Date fechaCompra, double precioCompra, boolean eliminado){
        //Insertar en BD Central
        
        //Insertar en BD del nodo
        
        int ok = 0;
        String mensaje = "";
        int zona = constantes.NODO_PROPIO;
        
        try{
            if (new Utilities().obtenIdObjeto(CentralVehiculos.class, "this.matricula==\""+matricula+"\"", constantes.NODO_CENTRAL) == null){
                ok = 1; //Nos dará 1 si no existe = todo ok.    
            }
        
            int[] peticion = {constantes.NODO_CENTRAL, constantes.NODO_PROPIO};
            
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
                    
                    //Creamos el vehiculo
                    ArrayList pedidos = null;
                    PVVehiculos vehiculoPV = new PVVehiculos(matricula, modelo, fechaProxITV, fechaCompra, precioCompra, false, pedidos);
                    //Hacemos persistente el objeto
                    conexiones[1].makePersistent(vehiculoPV);
                    
               
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
        int zona = constantes.NODO_PROPIO;
        
        PersistenceManager conexion1 = new Utilities().obtenerConexiones(constantes.NODO_CENTRAL);
        PersistenceManager conexion2 = new Utilities().obtenerConexiones(zona);
        
        if (conexion1 != null){
            try {
                conexion1.currentTransaction().begin();
                conexion2.currentTransaction().begin();
                
                //ELIMINAMOS DE LA SEDE CENTRAL
                //Obtenemos el objeto
                CentralVehiculos vehiculo = (CentralVehiculos) conexion1.getObjectById(new Utilities().obtenIdObjeto(CentralVehiculos.class,"this.matricula==\""+matricula+"\" && this.eliminado==false", constantes.NODO_CENTRAL),false);
                
                if (vehiculo == null){
                    mensaje = "El vehiculo no existe, no puede ser eliminado o ya está desactivado";
                    throw new Exception("El vehiculo no existe o ya está desactivado");
                }
                
                vehiculo.setEliminado(true);
                
                
                // PUNTO DE VENTA
                    
                //Obtenemos el objeto
                PVVehiculos vehiculoPV = (PVVehiculos) conexion2.getObjectById(new Utilities().obtenIdObjeto(PVVehiculos.class,"this.matricula==\""+matricula+"\"", zona),false);

                // Deshacemos su persistencia --> // conexion2.deletePersistent(empleadoPV);
                vehiculoPV.setEliminado(true);

                
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

            //ahora cambiamos nuestro nodo
            objVehiculo = new Utilities().obtenIdObjeto(PVVehiculos.class,"this.matricula==\""+antiguaMatricula+"\"", constantes.NODO_PROPIO);
            PVVehiculos vehiculoPV = (PVVehiculos) conexiones[1].getObjectById(objVehiculo,false);
            vehiculoPV.setMatricula(nuevaMatricula);
            
        }else{
            throw new Exception("El cif nuevo ya lo posee otro vehiculo");
        }
    }
    
    /**
     *Se encarga de modificar un Vehiculo dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(String antiguaMatricula, String nuevaMatricula, String modelo, Date fechaProxITV, Date fechaCompra, double precioCompra, boolean eliminado){
        //Modificar en la BD de la sede central
        
        //Modificar en la BD del nodo
        
        int ok = 0;
        String mensaje = "";
        int zona = constantes.NODO_PROPIO;
        
        int[] peticion = {constantes.NODO_CENTRAL,zona};
        PersistenceManager [] conexiones = new Utilities().obtenerConexiones(peticion);
         
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

                
                //Modificamos sus propiedades
                vehiculo.setModelo(modelo);
                vehiculo.setFechaProxITV(fechaProxITV);
                vehiculo.setFechaCompra(fechaCompra);
                vehiculo.setPrecioCompra(precioCompra);
                vehiculo.setEliminado(eliminado);
                
                // PUNTO DE VENTA: Sólo modificamos de nuevo el vehiculo si no pertenece a la SEDE CENTRAL
                //Obtenemos el objeto
                PVVehiculos vehiculoPV  = (PVVehiculos) conexiones[1].getObjectById(new Utilities().obtenIdObjeto(PVVehiculos.class,"this.matricula==\""+antiguaMatricula+"\"", constantes.NODO_PROPIO),false);

                if (vehiculoPV == null){
                    mensaje = "El vehiculo no existe, no puede ser modificado";
                    throw new Exception("El vehiculo no existe, no se puede modificar");
                }

                //Modificamos sus propiedades
                vehiculoPV.setModelo(modelo);
                vehiculoPV.setFechaProxITV(fechaProxITV);
                vehiculoPV.setFechaCompra(fechaCompra);
                vehiculoPV.setPrecioCompra(precioCompra);
                vehiculoPV.setEliminado(eliminado);

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
     *
     * @author Ignacio Carcas
     */
    public Vector buscar(String matricula){
        //Buscar en la BD de la sede central
        Vector v = new Vector();
        
        int peticion = constantes.NODO_PROPIO;
        PersistenceManager conexion = new Utilities().obtenerConexiones(peticion);
        
        if (conexion != null){
            // Creamos un try-catch para comprobar que todo va bien en la consulta
            Query query = null;
            Collection result = null;
            try{
                if (matricula.equals("-1")){ //Buscamos todos los vehiculos
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(PVVehiculos.class);
                    // Ejecutamos la sentencia pasándole los parámetros
                    result = (Collection) query.execute();
                }else{
                    // Creamos la sentencia de consulta
                    query = conexion.newQuery(PVVehiculos.class, "this.matricula==matricula");
                
                    // Declaramos los parámetros que va a tener la consulta
                    query.declareParameters("String matricula");
                    
                    // Ejecutamos la sentencia pasándole los parámetros
                    result = (Collection) query.execute(matricula);
                }
                
                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error consultando vehiculos: " + e.getMessage());
                v = null;
            }finally{
                query.close(result);
            }
        }else{
            v = null; // Fallo en la conexión. 
            System.err.println("¿Exiten vehiculos? Fallo en la conexión a la BD");
        }
        return (v);
    }
}
