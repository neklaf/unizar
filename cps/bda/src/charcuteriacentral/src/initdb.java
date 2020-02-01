/*
 * InitDB.java
 *
 * Created on 14 de febrero de 2005, 20:32
 */

/**
 *
 * @author  Administrador
 */
import javax.jdo.*;
import Central.*;
import PuntoDeVenta.*;
import java.util.*;

public class InitDB {
    
    /** Creates a new instance of InitDB */
    public InitDB() {
    }
    
    
    public void inicializa(){
        //borrarDB();
        reinicializarDB();
        //new MantenimientoDB.Empleados().pruebaVariosCommitAnidados();
        
        /*        
        //PersistenceManager pm = new ConexionBD.Utilities().getPersistenceManager("../Central/jdo.properties");
        PersistenceManager pm = new ConexionBD.Utilities().getPersistenceManager("../PuntoDeVenta/jdo.properties");
        try {
            //insertObjects(pm);
            //this.printAll(CentralPuntoDistribucion.class, pm);
            this.printAll(PVPuntoDistribucion.class, pm);
            System.out.println("Database is ready now...");
        }
        catch (RuntimeException x) {
            System.out.println("Error: " + x.getMessage());
        }

        // Close the PersistenceManager instance:
        finally {
            if (pm.currentTransaction().isActive())
                pm.currentTransaction().rollback();
            if (!pm.isClosed())
                pm.close();
        }
        */
        
        /// OPERACIONES CON PUNTOS DE DISTRIBUCION
        //new MantenimientoDB.PuntoDistribucion().insertar(1,"Via Universitas","123456789");        
        //new MantenimientoDB.PuntoDistribucion().modificar(1,2,"tralari tralara","99999999");
        //new MantenimientoDB.PuntoDistribucion().eliminar(2);
        //Vector v = new MantenimientoDB.PuntoDistribucion().buscar(-1);
        
        /// OPERACIONES CON EMPLEADOS
        ArrayList tfnoPers = new ArrayList();
        ArrayList tfnoEmpr = new ArrayList();
        tfnoPers.add("976554488");
        tfnoPers.add("911123131");
        //new MantenimientoDB.Empleados().modificarTfno("72978561K", tfnoPers, 1);
        tfnoEmpr.add("123456");
        tfnoEmpr.add("123456");
        //new MantenimientoDB.Empleados().modificarTfno("72978561K", tfnoEmpr, 2);
          //Empleado en zona 2
        //new MantenimientoDB.Empleados().insertar("72978561K","Condes de Aragon num 20", "Jefe", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Ignacio", "Carcas", 1000.0, "123123", "",2);
          //Empleado en zona 2 y subordinado del dni 72978561K
        //new MantenimientoDB.Empleados().insertar("11111111J","Downing Street num 10", "Empleadillo", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Tony", "Blair", 10.0, "123123", "72978561K",2);
          //Empleado en zona 2 y subordinado del dni 11111111J
        //new MantenimientoDB.Empleados().insertar("22222222J","Domicilio", "Empleadillo 2", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Pepe", "Aznar", 10.0, "123123", "11111111J",2);
          //Empleado en zona 1
        //new MantenimientoDB.Empleados().insertar("12345678J","Via Universitas num 56", "Jefe", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Ignacio", "Carcas", 1000.0, "123123", "",1);
                  
          //Prueba para cambiar el jefe a null
        //new MantenimientoDB.Empleados().modificar("11111111J","11111111J","Domicilio", "Empleadillo 2", 20.0, new java.util.Date(), "Pipo", "Aznor", 20.0, "222111", "", true,2);
          //Cambio de jefe y de zona 2 a 1
        //new MantenimientoDB.Empleados().modificar("11111111J","11111111J","Domicilio", "Empleadillo 2", 20.0, new java.util.Date(), "Pipo", "Aznor", 20.0, "222111", "12345678J", true,1);
          //Cambio de jefe y de zona 1 a 2
        //new MantenimientoDB.Empleados().modificar("12345678J","12345678J","Domicilio", "Empleadillo 2", 20.0, new java.util.Date(), "Pipo", "Aznor", 20.0, "222111", "22222222J", true,2);
          //Cambio de jefe y de zona 1 a 2 modificando el DNI
        //new MantenimientoDB.Empleados().modificar("12345678J","99999999J","Domicilio", "Empleadillo 2", 20.0, new java.util.Date(), "Pipo", "Aznor", 20.0, "222111", "22222222J", false,2);
          //Vuelta a la zona 2 y por tanto se prueba la reactivación
        //new MantenimientoDB.Empleados().modificar("11111111J","11111111J","Domicilio", "Empleadillo 2", 20.0, new java.util.Date(), "Pipo", "Aznor", 20.0, "222111", "72978561K", true,2);
          //Prueba de cambiar el jefe a null
        //new MantenimientoDB.Empleados().modificar("11111111J","11111111J","Domicilio", "Empleadillo 2", 20.0, new java.util.Date(), "Pipo", "Aznor", 20.0, "222111", "", false,2);
          // Eliminamos
        //new MantenimientoDB.Empleados().eliminar("11111111J");
        
          // bucamos
        //muestraBusqueda(new MantenimientoDB.Empleados().buscar("-1",2,-1));
        //muestraBusqueda(new MantenimientoDB.Empleados().buscar("-1",0,1));
        //muestraBusqueda(new MantenimientoDB.Empleados().buscar("72978561K",2,-1));
        //muestraBusqueda(new MantenimientoDB.Empleados().buscar("72978561K",0,-1));
        
        
        /// OPERACIONES CON CLIENTES
          // Insertamos cliente en zona 1
        //new MantenimientoDB.Clientes().insertar("99999999E", "Universidad de Zaragoza", "Plaza San Francisco", "976 55 55 55", "consulta@unizar.es", "www.unizar.es", "Sr Rector", false, 1);
          // Insertamos cliente en zona 2
        //new MantenimientoDB.Clientes().insertar("33333333E", "CPS", "Maria de Luna", "976 33 33 33", "consulta@cps.unizar.es", "www.cps.unizar.es", "Sr director del cps", false, 2);

          // Modificamos el dni y de la zona 2 a la 1
        //new MantenimientoDB.Clientes().modificar("33333333E", "44444444E", "CPS 2", "Maria de Luna 2", "976 44 44 44", "consulta@cps.unizar.es 2", "www.cps.unizar.es 2", "Sr director del cps 2", false, 1);
            // Comprobamos que con el modificar podemos eliminar y activar
        //new MantenimientoDB.Clientes().modificar("44444444E", "44444444E", "CPS 2", "Maria de Luna 2", "976 44 44 44", "consulta@cps.unizar.es 2", "www.cps.unizar.es 2", "Sr director del cps 2", true, 1);
        //new MantenimientoDB.Clientes().modificar("44444444E", "44444444E", "CPS 2", "Maria de Luna 2", "976 44 44 44", "consulta@cps.unizar.es 2", "www.cps.unizar.es 2", "Sr director del cps 2", false, 1);
          // Modificamos para cambiar de zona 1 a la 2
        //new MantenimientoDB.Clientes().modificar("99999999E", "99999999E", "Universidad de Zaragoza", "Plaza San Francisco", "976 55 55 55", "consulta@unizar.es", "www.unizar.es", "Sr Rector", true, 2);
        
          // Eliminamos
        //new MantenimientoDB.Clientes().eliminar("99999999E");
          // bucamos
        //muestraBusqueda(new MantenimientoDB.Clientes().buscar("-1",-1));
        //muestraBusqueda(new MantenimientoDB.Clientes().buscar("33333333E",-1));
        //muestraBusqueda(new MantenimientoDB.Clientes().buscar("33333333E",2));
        //muestraBusqueda(new MantenimientoDB.Clientes().buscar("33333333E",1));
        
        
        ///OPERACIONES CON VEHICULOS
          // Insertamos vehiculo en zona 1
        //new MantenimientoDB.Vehiculos().insertar("3254-cgb","peugeot 206", new java.util.Date(), new java.util.Date(), 12000.0, false, 1);
          // Insertamos vehiculo en zona 2
        //new MantenimientoDB.Vehiculos().insertar("1125-cgb","peugeot 806", new java.util.Date(), new java.util.Date(), 24000.0, false, 2);
          // Modificamos la matricula y cambiamos de la zona 2 a la 1
        //new MantenimientoDB.Vehiculos().modificar("1125-cgb","3333-CGB","peugeot 806", new java.util.Date(), new java.util.Date(), 24000.0, false, 1);
            // Comprobamos que se puede activar y eliminar a través del modificar
        //new MantenimientoDB.Vehiculos().modificar("3333-CGB","3333-CGB","peugeot 806", new java.util.Date(), new java.util.Date(), 24000.0, true, 1);
        //new MantenimientoDB.Vehiculos().modificar("3333-CGB","3333-CGB","peugeot 806", new java.util.Date(), new java.util.Date(), 24000.0, false, 1);
          // Modificamos cambiando de la zona 1 a la 2
        //new MantenimientoDB.Vehiculos().modificar("3254-cgb","3254-cgb","peugeot 206", new java.util.Date(), new java.util.Date(), 12000.0, false, 2);

          // Eliminamos
        //new MantenimientoDB.Vehiculos().eliminar("3254-cgb");
          // bucamos
        //muestraBusqueda(new MantenimientoDB.Vehiculos().buscar("-1", -1));
        //muestraBusqueda(new MantenimientoDB.Vehiculos().buscar("3254-cgb",1));
        //muestraBusqueda(new MantenimientoDB.Vehiculos().buscar("-1", 1));
        
        
        ///OPERACIONES CON PROVEEDORES
        //new MantenimientoDB.Proveedores().insertar("5555L", "Plaza del pilar 1", null, "Proveedor 1", "12-256-200", "Rafael Gutierrez", "www.proveedor1.com", "consultas@proveedor1.com", false);
        //new MantenimientoDB.Proveedores().insertar("6666L", "Fuenterravía 8", null, "Proveedor 2", "11-5647-9", "Pepe Sancho", "www.proveedor2.com", "consultas@proveedor2.com", false);
        //new MantenimientoDB.Proveedores().modificar("5555L", "2222L", "Plaza del pilar 2", null, "Proveedor 2", "2-12-256-200", "Rafael Gutierrez 2", "www.proveedor2.com", "consultas@proveedor2.com", false);
          // Eliminación y activación mediante la modificación -> 
        //new MantenimientoDB.Proveedores().modificar("5555L", "2222L", "Plaza del pilar 2", null, "Proveedor 2", "2-12-256-200", "Rafael Gutierrez 2", "www.proveedor2.com", "consultas@proveedor2.com", true);
        //new MantenimientoDB.Proveedores().modificar("2222L", "2222L", "Plaza del pilar 2", null, "Proveedor 2", "2-12-256-200", "Rafael Gutierrez 2", "www.proveedor2.com", "consultas@proveedor2.com", false);
          // modificamos los tfno
        //new MantenimientoDB.Proveedores().modificarTfno("5555L", tfnoPers);
          // Eliminación normal
        //new MantenimientoDB.Proveedores().eliminar("2222L");
          // bucamos
        //muestraBusqueda(new MantenimientoDB.Proveedores().buscar("-1"));
        //muestraBusqueda(new MantenimientoDB.Proveedores().buscar("5555L"));
        
        
        ///OPERACIONES CON PRODUCTOS PROVEEDORES
        //new MantenimientoDB.ProductosProveedores().insertar("prod 1","Chorizo","Sin descripcion",10.35, 1.0, false, "5555L");
        //new MantenimientoDB.ProductosProveedores().modificar("prod 18", "prod 2", "Jamón", "Pata negra",400.50, 2.0, false, "6666L");
          // Eliminación mediante la modificación -> 
        //new MantenimientoDB.ProductosProveedores().modificar("prod 1", "prod 18", "Jamón", "Pata negra",400.50, 2.0, true, "6666L");
        //new MantenimientoDB.ProductosProveedores().modificar("prod 18", "prod 18", "Jamón", "Pata negra",400.50, 2.0, false, "6666L");
          //eliminamos
        //new MantenimientoDB.ProductosProveedores().eliminar("prod 2");
          // bucamos
        //muestraBusqueda(new MantenimientoDB.ProductosProveedores().buscar("-1", "-1"));
        //muestraBusqueda(new MantenimientoDB.ProductosProveedores().buscar("prod 1", "-1"));
        //muestraBusqueda(new MantenimientoDB.ProductosProveedores().buscar("-1", "5555L"));
        
        
        ///OPERACIONES CON PRODUCTOS
          // insertamos productos generales
        //new MantenimientoDB.Productos().insertar("prod gen 1", "producto 1", "Sin descripcion", 15.20, 1.0, false);
        //new MantenimientoDB.Productos().insertar("prod gen 2", "producto 2", "Sin descripcion", 11.25, 2.0, false);
          // insertamos productos regionales
        //new MantenimientoDB.Productos().insertar("prod reg 1", "producto 3", "Sin descripcion", 5.0, 4.0, false, 2);
        //new MantenimientoDB.Productos().insertar("prod reg 2", "producto 4", "Sin descripcion", 5.0, 4.0, false, 2);
        //new MantenimientoDB.Productos().insertar("prod reg 3", "producto 5", "Sin descripcion", 2.0, 6.0, false, 3);
        
          // modificamos producto sin cambiar referencia, ni tipo (regional o general)
        //new MantenimientoDB.Productos().modificar("prod gen 1","prod gen 1", "Jamón de York", "Envasado al vacío", 3.0, 2.0, false, -1);
          // modificamos cambiando referencia
        //new MantenimientoDB.Productos().modificar("prod gen 1","prod gen 5", "Jamón de York", "Envasado al vacío", 3.0, 2.0, false, -1);
          // modificamos el anterior modificando tipo de general a regional
        //new MantenimientoDB.Productos().modificar("prod gen 1","prod gen 5", "Jamón de York", "Envasado al vacío", 3.0, 2.0, true, 2);
          // modificamos un producto regional del todo
        //new MantenimientoDB.Productos().modificar("prod reg 2","prod reg 4", "Jamón de York", "Envasado al vacío", 3.0, 2.0, false, -1);        
        
           // eliminamos y activamos desde modificar
        //new MantenimientoDB.Productos().modificar("prod gen 1","prod gen 1", "Jamón de York", "Envasado al vacío", 3.0, 2.0, true, -1);
        //new MantenimientoDB.Productos().modificar("prod gen 1","prod gen 1", "Jamón de York", "Envasado al vacío", 3.0, 2.0, false, -1);
        //new MantenimientoDB.Productos().modificar("prod gen 5","prod gen 5", "Jamón de York", "Envasado al vacío", 3.0, 2.0, true, 2);
        //new MantenimientoDB.Productos().modificar("prod gen 5","prod gen 5", "Jamón de York", "Envasado al vacío", 3.0, 2.0, false, -1);
        
          // eliminamos un producto general
        //new MantenimientoDB.Productos().modificarEliminado("prod gen 2", true);
          // eliminamos un producto regional
        //new MantenimientoDB.Productos().modificarEliminado("prod reg 1", true);
        
          // bucamos
        //muestraBusqueda(new MantenimientoDB.Productos().buscar("-1"));
        //muestraBusqueda(new MantenimientoDB.Productos().buscar("prod reg 1"));
        
        //OPERACIONES CON PEDIDOS
          // busca todos los pedidos de todas las bd
        //muestraBusqueda(new MantenimientoDB.Pedidos().buscar(-1,"-1"));
          // busca todos los pedidos del punto de venta 2
        //muestraBusqueda(new MantenimientoDB.Pedidos().buscar(2,"-1"));
          //busca todos los pedidos que pertenecen al cliente 99999999E en todas las BD
        //muestraBusqueda(new MantenimientoDB.Pedidos().buscar(-1,"99999999E"));
          //busca todos los pedidos del punto de venta 2 que pertenecen al cliente 99999999E
        //muestraBusqueda(new MantenimientoDB.Pedidos().buscar(2,"99999999E"));
        
    }
    
    public void muestraBusqueda(Vector v){
        if (v==null){
            System.out.println("No se ha encontrado nada de nada");
        }else{
            System.out.println("El número de objetos encontrados es: "+ v.size());
        }
    }
    
    public void insertObjects (PersistenceManager pm){
        CentralPuntoDistribucion pd = new CentralPuntoDistribucion(9,"Via Universitas","123456789",null, null, null);
	        
        // Store the object graph into the database:
        pm.currentTransaction().begin();
        pm.makePersistent(pd); // store the entire object graph
        pm.currentTransaction().commit();
    }
    
    
    private void printAll(Class cls, PersistenceManager pm) {
        Extent extent = pm.getExtent(cls, true);
        Iterator itr = extent.iterator();
        printCollection("All " + cls.getName() + " instances:", itr);
        extent.close(itr);
    }
   
    /*
     * rutaConexion = "../Central/jdo.properties"
     **/
    private void printAll2(Class cls, String rutaConexion) {
        PersistenceManager pm = new ConexionBD.Utilities().getPersistenceManager(rutaConexion);
        Extent extent = pm.getExtent(cls, true);
        Iterator itr = extent.iterator();
        printCollection("All " + cls.getName() + " instances:", itr);
        extent.close(itr);
        pm.close();
    }
    
    private static void printCollection(String title, Iterator itr) {
        System.out.println(title);
        while (itr.hasNext())
            System.out.println("  " + itr.next());
    }
    
    public void borrarDB(){
        PersistenceManager pm;
        Extent ext;
        Iterator i;
        //ELIMINAMOS LA BD CENTRAL
        pm = new ConexionBD.Utilities().getPersistenceManager("../Central/jdo.properties");
        
        try {
            pm.currentTransaction().begin();
            //Borramos los puntos de distribucion
            ext = pm.getExtent(CentralPuntoDistribucion.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }

            //Borramos los empleados
            ext = pm.getExtent(CentralEmpleados.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            
            //Borramos los clientes
            ext = pm.getExtent(CentralClientes.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los vehiculos
            ext = pm.getExtent(CentralVehiculos.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los proveedores
            ext = pm.getExtent(CentralProveedores.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los productos de  proveedores
            ext = pm.getExtent(CentralProductosProveedores.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            
            pm.currentTransaction().commit();
            
        }catch (RuntimeException x) {
            System.out.println("Error: " + x.getMessage());
        }

        // Close the PersistenceManager instance:
        finally {
            if (pm.currentTransaction().isActive())
                pm.currentTransaction().rollback();
            if (!pm.isClosed())
                pm.close();
        }
        
        //ELIMINAMOS LA BD DE UN PUNTO DE VENTA
        pm = new ConexionBD.Utilities().getPersistenceManager("../PuntoDeVenta/jdo.properties");
        
        try {
            pm.currentTransaction().begin();
            //Borramos los puntos de distribucion
            ext = pm.getExtent(PVPuntoDistribucion.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }

            //Borramos los empleados
            ext = pm.getExtent(PVEmpleados.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            
            //Borramos los clientes
            ext = pm.getExtent(PVClientes.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los vehiculos
            ext = pm.getExtent(PVVehiculos.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los proveedores
            ext = pm.getExtent(PVProveedores.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            
            //Borramos los productos de  proveedores
            ext = pm.getExtent(PVProductosProveedores.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los productos
            ext = pm.getExtent(PVProductos.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los pedidos
            ext = pm.getExtent(PVPedidos.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los pedidos a proveedor
            ext = pm.getExtent(PVPedidosAProveedor.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los detalle pedidos
            ext = pm.getExtent(PVDetallePedidos.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            //Borramos los detalle pedidos a proveedor
            ext = pm.getExtent(PVDetallePedidosAProveedor.class,false);
            i = ext.iterator();
            while (i.hasNext()){
                pm.deletePersistent(i.next());
            }
            
            pm.currentTransaction().commit();
        
        }catch (RuntimeException x) {
            System.out.println("Error: " + x.getMessage());
        }

        // Close the PersistenceManager instance:
        finally {
            if (pm.currentTransaction().isActive())
                pm.currentTransaction().rollback();
            if (!pm.isClosed())
                pm.close();
        }
        
    }
    
    
    /*Método que reinicializa la BD
     *
     *Author: Ignacio Carcas Alda
     */
    public void reinicializarDB(){
        //Borrar la base de datos
        borrarDB();
        // puntos de distribucion: sede central y punto de venta
        new MantenimientoDB.PuntoDistribucion().insertar(1,"Via Universitas","123456789");
        new MantenimientoDB.PuntoDistribucion().insertar(2,"tralari tralara","99999999");
        
        // empleados de cada centro con su jerarquia
        ArrayList tfnoPers = new ArrayList();
        ArrayList tfnoEmpr = new ArrayList();
        tfnoPers.add("976554488");
        tfnoPers.add("911123131");
        tfnoEmpr.add("123456");
        tfnoEmpr.add("123456");
          //Empleados en zona 2
        new MantenimientoDB.Empleados().insertar("72978561K","Condes de Aragon num 20", "Jefe", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Ignacio", "Carcas", 1000.0, "123123", "",2);
            new MantenimientoDB.Empleados().modificarTfno("72978561K", tfnoPers, 1);
            new MantenimientoDB.Empleados().modificarTfno("72978561K", tfnoEmpr, 2);
        new MantenimientoDB.Empleados().insertar("11111111J","Downing Street num 10", "Empleadillo", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Tony", "Blair", 10.0, "123123", "72978561K",2);
        new MantenimientoDB.Empleados().insertar("22222222J","Domicilio", "Empleadillo 2", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Pepe", "Aznar", 10.0, "123123", "11111111J",2);
          //Empleados en zona 1
        new MantenimientoDB.Empleados().insertar("12345678J","Via Universitas num 56", "Jefe", true, 10.0, tfnoPers, tfnoEmpr, new java.util.Date(), "Ignacio", "Carcas", 1000.0, "123123", "",1);
        
        
        // clientes
          // zona 1
        new MantenimientoDB.Clientes().insertar("99999999E", "Universidad de Zaragoza", "Plaza San Francisco", "976 55 55 55", "consulta@unizar.es", "www.unizar.es", "Sr Rector", false, 1);
          // zona 2
        new MantenimientoDB.Clientes().insertar("33333333E", "CPS", "Maria de Luna", "976 33 33 33", "consulta@cps.unizar.es", "www.cps.unizar.es", "Sr director del cps", false, 2);
        
        
        // vehiculos
          // Insertamos vehiculo en zona 1
        new MantenimientoDB.Vehiculos().insertar("3254-cgb","peugeot 206", new java.util.Date(), new java.util.Date(), 12000.0, false, 1);
          // Insertamos vehiculo en zona 2
        new MantenimientoDB.Vehiculos().insertar("1125-cgb","peugeot 806", new java.util.Date(), new java.util.Date(), 24000.0, false, 2);
        
        // proveedores
        new MantenimientoDB.Proveedores().insertar("5555L", "Plaza del pilar 1", tfnoEmpr, "Proveedor 1", "12-256-200", "Rafael Gutierrez", "www.proveedor1.com", "consultas@proveedor1.com", false);
        new MantenimientoDB.Proveedores().insertar("6666L", "Fuenterravía 8", tfnoEmpr, "Proveedor 2", "11-5647-9", "Pepe Sancho", "www.proveedor2.com", "consultas@proveedor2.com", false);
        
        
        // productos proveedores
        new MantenimientoDB.ProductosProveedores().insertar("prod 1","Chorizo","Sin descripcion",10.35, 1.0,false, "5555L");
        new MantenimientoDB.ProductosProveedores().insertar("prod 2","Jamón","Pata Negra",200.05, 1.0,false, "5555L");
        new MantenimientoDB.ProductosProveedores().insertar("prod 3","Fuet","Casa Ondiviela",10.35, 1.0,false, "6666L");
        
        
        // productos
          // productos generales
        new MantenimientoDB.Productos().insertar("prod gen 1", "producto 1", "Sin descripcion", 15.20, 1.0, false);
        new MantenimientoDB.Productos().insertar("prod gen 2", "producto 2", "Sin descripcion", 11.25, 2.0, false);
          // productos regionales
        new MantenimientoDB.Productos().insertar("prod reg 1", "producto 3", "Sin descripcion", 5.0, 4.0, false, 2);
        new MantenimientoDB.Productos().insertar("prod reg 2", "producto 4", "Sin descripcion", 5.0, 4.0, false, 2);
        //new MantenimientoDB.Productos().insertar("prod reg 3", "producto 5", "Sin descripcion", 2.0, 6.0, false, 3);
        
    }
   
}
