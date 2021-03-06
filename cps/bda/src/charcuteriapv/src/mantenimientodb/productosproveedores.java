/*
 * ProductosProveedores.java
 *
 * Created on 14 de febrero de 2005, 21:22
 */

package MantenimientoDB;

import java.util.*;
import ConexionBD.*;
import javax.jdo.*;
import Central.CentralProductosProveedores;
import Central.CentralProveedores;
import Central.CentralPuntoDistribucion;
import PuntoDeVenta.PVProductosProveedores;
import PuntoDeVenta.PVProveedores;
import PuntoDeVenta.PVPuntoDistribucion;
/**
 *
 * @author  Ignacio Carcas
 */
public class ProductosProveedores {
    
    /** Creates a new instance of ProductosProveedores */
    public ProductosProveedores() {
    }
    
    /**
     *Se encarga de insertar un Producto de Proveedores
     *
     * @author Ignacio Carcas
     */
    public int insertar(String ref_prod,String nombre,String descripcion,double precio,double unidades, boolean eliminado, String cifProveedor){
        //Insertar en BD Central
        //Insertar en BD del nodo
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            // Comprobamos si exist�a alg�n producto con esa referencia
            Object objProducto = new Utilities().obtenIdObjeto(CentralProductosProveedores.class,"this.ref_prod==\""+ref_prod+"\"", constantes.NODO_CENTRAL);
            //Si no existe producto con esa referencia -> todo ok
            if (objProducto==null){
                
                // Comprobamos que el proveedor al que quieren asignar el producto existe
                Object objProveedor = new Utilities().obtenIdObjeto(CentralProveedores.class,"this.cif==\""+cifProveedor+"\"", constantes.NODO_CENTRAL);
                //Si no existe proveedor con ese cif -> error
                if (objProveedor!=null){
                
                    // Abrimos la conexi�n con todas las BD del sistema
                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().begin();
                    }

                    // Primero creamos el producto para la sede central
                      // Buscamos su proveedor
                    CentralProveedores proveedor = (CentralProveedores) conexiones[0].getObjectById(objProveedor,false);
                      // Creamos el producto
                    CentralProductosProveedores productoCentral = new CentralProductosProveedores(ref_prod, nombre, descripcion, precio, unidades, eliminado, proveedor);
                    conexiones[0].makePersistent(productoCentral);
                    proveedor.addProducto(productoCentral);

                    // En segundo lugar cambiamos el del resto de sedes
                    for (int i=1; i<conexiones.length; i++){
                        //Creamos los productos para los puntos de venta
                          // Buscamos su proveedor
                        objProveedor = new Utilities().obtenIdObjeto(PVProveedores.class,"this.cif==\""+cifProveedor+"\"", i+1);
                        PVProveedores proveedorPV = (PVProveedores) conexiones[i].getObjectById(objProveedor,false);;
                          // Creamos el producto
                        PVProductosProveedores productoPV = new PVProductosProveedores(ref_prod, nombre, descripcion, precio, unidades, eliminado, proveedorPV);
                        conexiones[i].makePersistent(productoPV);
                        
                        proveedorPV.addProducto(productoPV);
                    }

                    for (int i=0; i<conexiones.length; i++){
                        conexiones[i].currentTransaction().commit();
                    }
                }else{ // Si el proveedor seleccionado no existe en la bd
                    throw new Exception("El cif de proveedor no pertenece a ning�n proveedor");
                }
            
            }else{//Si existe producto con esa referencia -> ok = 0; fallo
                throw new Exception("La referencia nueva ya la posee otro producto de proveedor");
            }
        }catch(Exception e){
            System.out.println("Error en insertar producto proveedor: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    
    /**
     *Se encarga de eliminar un Producto de Proveedores dado
     *
     * @author Ignacio Carcas
     */
    public int eliminar(String ref_prod){
        //Eliminar de BD Central
        //Eliminar de BD del nodo
        
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            // Comprobamos si exist�a alg�n producto con esa referencia
            Object objProducto = new Utilities().obtenIdObjeto(CentralProductosProveedores.class,"this.ref_prod==\""+ref_prod+"\"", constantes.NODO_CENTRAL);
            //Si existe producto con esa referencia -> todo oK
            if (objProducto!=null){
                
                // Abrimos la conexi�n con todas las BD del sistema
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                // Buscamos el producto en la sede central
                CentralProductosProveedores productoCentral = (CentralProductosProveedores) conexiones[0].getObjectById(objProducto,false);
                productoCentral.setEliminado(true);
                // En segundo lugar cambiamos el del resto de sedes
                for (int i=1; i<conexiones.length; i++){
                    //Buscamos los productos
                    objProducto = new Utilities().obtenIdObjeto(PVProductosProveedores.class,"this.ref_prod==\""+ref_prod+"\"", i+1);
                    PVProductosProveedores productoPV = (PVProductosProveedores) conexiones[i].getObjectById(objProducto,false);
                    productoPV.setEliminado(true);                    
                }

                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
            }
            
        }catch(Exception e){
            System.out.println("Error en eliminar producto: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de modificar un Producto de Proveedores dado
     *
     * @author Ignacio Carcas
     */
    public int modificar(String antiguaRef_prod, String nuevaRef_prod, String nombre,String descripcion,double precio,double unidades, boolean eliminado, String cifProveedor){
        //Modificar en la BD de la sede central
        //Modificar en la BD del nodo
        int ok = 1;
        
        PersistenceManager [] conexiones = null;
        try{
            conexiones = new Utilities().obtenerConexiones(null);
            
            // Comprobamos si exist�a alg�n producto con esa referencia
            Object objProducto = new Utilities().obtenIdObjeto(CentralProductosProveedores.class,"this.ref_prod==\""+antiguaRef_prod+"\"", constantes.NODO_CENTRAL);

            //Si existe producto con esa referencia -> todo oK
            if (objProducto!=null){
                if (!antiguaRef_prod.equals(nuevaRef_prod)){
                    Object objProdNuevaRef = new Utilities().obtenIdObjeto(CentralProductosProveedores.class,"this.ref_prod==\""+nuevaRef_prod+"\"", constantes.NODO_CENTRAL);
                    if (objProdNuevaRef!=null){
                        throw new Exception("La nueva referencia ya la ten�a otro producto");
                    }
                }
                // Abrimos la conexi�n con todas las BD del sistema
                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().begin();
                }
                
                // Buscamos el producto en la sede central
                CentralProductosProveedores productoCentral = (CentralProductosProveedores) conexiones[0].getObjectById(objProducto,false);
                productoCentral.setRef_prod(nuevaRef_prod);
                productoCentral.setNombre(nombre);
                productoCentral.setDescripcion(descripcion);
                productoCentral.setPrecio(precio);
                productoCentral.setUnidades(unidades);
                productoCentral.setEliminado(eliminado);
                
                // si cambia el proveedor de ese producto
                if (!productoCentral.getProveedor().getCif().equals(cifProveedor)){
                    // buscamos el nuevo proveedor
                    Object objProveedor = new Utilities().obtenIdObjeto(CentralProveedores.class,"this.cif==\""+cifProveedor+"\"", constantes.NODO_CENTRAL);
                    CentralProveedores proveedor = (CentralProveedores) conexiones[0].getObjectById(objProveedor,false);
                    
                    productoCentral.getProveedor().deleteProducto(productoCentral);
                    
                    proveedor.addProducto(productoCentral);
                    
                    productoCentral.setProveedor(proveedor);
                    
                    
                }

                // En segundo lugar cambiamos el del resto de sedes
                for (int i=1; i<conexiones.length; i++){
                    //Buscamos los productos
                    objProducto = new Utilities().obtenIdObjeto(PVProductosProveedores.class,"this.ref_prod==\""+antiguaRef_prod+"\"", i+1);
                    PVProductosProveedores productoPV = (PVProductosProveedores) conexiones[i].getObjectById(objProducto,false);
                    productoPV.setRef_prod(nuevaRef_prod);
                    productoPV.setNombre(nombre);
                    productoPV.setDescripcion(descripcion);
                    productoPV.setPrecio(precio);
                    productoPV.setUnidades(unidades);
                    productoPV.setEliminado(eliminado);
                    if (!productoPV.getProveedor().getCif().equals(cifProveedor)){
                        // buscamos el nuevo proveedor
                        Object objProveedor = new Utilities().obtenIdObjeto(PVProveedores.class,"this.cif==\""+cifProveedor+"\"", i+1);
                        PVProveedores proveedor = (PVProveedores) conexiones[i].getObjectById(objProveedor,false);
                        
                         productoPV.getProveedor().deleteProducto(productoPV);
                    
                        proveedor.addProducto(productoPV);
                        
                        productoPV.setProveedor(proveedor);
                    }
                }

                for (int i=0; i<conexiones.length; i++){
                    conexiones[i].currentTransaction().commit();
                }
            }
            
        }catch(Exception e){
            System.out.println("Error en modificar productos de proveedor: " + e.getMessage());
            ok = 0;
        }finally{
            new Utilities().ejecutaFinally(conexiones);
        }
        
        return (ok);
    }
    
    /**
     *Se encarga de buscar los Productos de Proveedores con unos par�metros
     *@param ref_prod: referencia del producto a buscar, si ref_prod="-1" busca todos los los productos de proveedor.
     *@param cif: cif de la empresa de la que se quieren buscar productos. Si cif=="-1" busca encualquier empresa.
     * @author Ignacio Carcas
     */
    public Vector buscar(String ref_prod, String cif){
        //Buscar en la BD de la sede central
        
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
                // Creamos la sentencia de consulta
                query = conexion.newQuery(PVProductosProveedores.class);
                
                if (ref_prod.equals("-1")){ //Buscamos todos los productos
                    // Se buscar�n todos los productos
                }else{
                    // Creamos la sentencia de consulta
                    consulta = "this.ref_prod==ref_prod";

                    // Declaramos los par�metros que va a tener la consulta
                    parameters = "String ref_prod";
                    args.put("ref_prod", ref_prod);
                }
                
                if (!cif.equals("-1")){
                    if (!consulta.equals("")){
                        consulta += " && ";
                        parameters +=",";
                    }
                    //Creamos la consulta
                    consulta += "this.proveedor.cif==cif";
                    //Declaramos el nuevo par�metro
                    parameters += "String cif";
                    args.put("cif", cif);
                }
                
                //Declaramos los par�metros
                if (!parameters.equals("")){
                    query.declareParameters(parameters);
                }
              
                //Establecemos el filtro de consulta
                if (!consulta.equals("")){
                    query.setFilter(consulta);
                }
                // Ejecutamos la sentencia pas�ndole los par�metros
                if (args.size()>0){
                    result = (Collection) query.executeWithMap(args);
                }else{
                    result = (Collection) query.execute();
                }
                
                v.addAll(result);
                
            }catch(Exception e){ 
                System.out.println("Error consultando productos de proveedor: " + e.getMessage());
                v = null;
            }finally{
                //query.closeAll();
                query.close(result);
                /*PersistenceManager[] conexiones = {conexion};
                new Utilities().ejecutaFinally(conexiones);
                 */
            }
        }else{
            v = null; // Fallo en la conexi�n. 
            System.err.println("�Exiten de proveedor? Fallo en la conexi�n a la BD");
        }
        return (v);
    }

    
}
