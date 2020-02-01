/*
 * Proveedores.java
 *
 * Created on 14 de marzo de 2005, 21:59
 */

package Interfaz;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ArrayList;

/*Campos opcionales: localizacion, numCuenta, persContacto, web, email*/

/**
 *
 * @author  hadden
 */
public class Proveedores extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    DefaultTableModel m; //Modelo de la tabla
    String cols[] = new String [] {"cif", "*localizacion", "nombre", "*numCuenta", "*persContacto", "*web", "*email", "eliminado"};
    Vector vCol; //Vector en el que almaceno las columnas de la tabla
    
    /*Columnas*/
    private final int COLS = 8;
    
    private final int CIF = 0;
    private final int LOCALIZACION = 1;
    private final int NOMBRE = 2;
    private final int NUM_CUENTA = 3;
    private final int PERS_CONTACTO = 4;
    private final int WEB = 5;
    private final int EMAIL = 6;
    private final int ELIMINADO = 7;
    
    //ArrayList de telefonos
    private static Hashtable tfnos;
    
    tfnosProv vTfnosProv;
    ProductosProveedores vProdProv;
    
    /** Creates new form Proveedores */
    public Proveedores() {
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        m = (DefaultTableModel)jTable1.getModel();
        
        tfnos = new Hashtable();
        
        /*Configuracion de la ventana*/
        this.setSize(1050,450);
        this.setTitle("Ventana de Proveedores");
        this.setResizable(false);
    }
    
    public void setProdProv(ProductosProveedores v){
        vProdProv = v;
    }
    
    public void visualiza(){
        Vector v;
        
        //public Vector buscar(String cif)
        v = (Vector)new MantenimientoDB.Proveedores().buscar("-1");
    
        if(v!=null){
            db2table(v);
            this.setVisible(true);
        }else
            System.err.println("Se ha producido un error en la busqueda");
    }
    
    private void db2table(Vector v){
        if (v.size()!=0){
            String clase;
            
            /*Definimos tantas variables como tipos pueda tener la fila que vamos a recibir*/
            Central.CentralProveedores crow;
            PuntoDeVenta.PVProveedores pvrow;
            Vector rows = new Vector();
                       
            int i = 0;
            String cif; //Esta variable aqui es necesaria
            
            //Recorremos todas filas que nos haya proporcionado la base de datos
            while (i < v.size()){
                clase = v.elementAt(i).getClass().getName();
                
                if(clase.endsWith("Central.CentralProveedores")){
                    crow = (Central.CentralProveedores)v.elementAt(i);
                    Vector vfields = new Vector();
                    
                    //{"cif", "*localizacion", "nombre", "*numCuenta", "*persContacto", "*web", "*email", "eliminado"}
                    cif = crow.getCif();
                    vfields.addElement(cif);
                    vfields.addElement(crow.getLocalizacion());
                    vfields.addElement(crow.getNombre());
                    vfields.addElement(crow.getNumCuenta());
                    vfields.addElement(crow.getPersContacto());
                    vfields.addElement(crow.getWeb());
                    vfields.addElement(crow.getEmail());
                    vfields.addElement(new Boolean(crow.getEliminado()));
                    
                    ArrayList n = copiaArrayList(crow.getAllTfnos());
                    tfnos.put(cif, n);
                    
                    //Creo que la mejor solucion seria llamando a la ventana de ProductosProveedores y visualizando los productos
                    //del proveedor en cuestion => Necesitamos un buscar ProductosProveedores por Proveedor!!! (es lo m‡s simple)
                                       
                    
                    rows.addElement(vfields);
                }else if(clase.endsWith("PuntoDeVenta.PVProveedores")){
                    pvrow = (PuntoDeVenta.PVProveedores)v.elementAt(i);
                    Vector vfields = new Vector();
                    
                    //{"cif", "*localizacion", "nombre", "*numCuenta", "*persContacto", "*web", "*email", "eliminado"}
                    cif = pvrow.getCif();
                    vfields.addElement(cif);
                    vfields.addElement(pvrow.getLocalizacion());
                    vfields.addElement(pvrow.getNombre());
                    vfields.addElement(pvrow.getNumCuenta());
                    vfields.addElement(pvrow.getPersContacto());
                    vfields.addElement(pvrow.getWeb());
                    vfields.addElement(pvrow.getEmail());
                    vfields.addElement(new Boolean(pvrow.getEliminado()));
                                        
                    ArrayList n = copiaArrayList(pvrow.getAllTfnos());
                    if(n==null)
                        n = new ArrayList();
                    tfnos.put(cif, n);
                    
                    rows.addElement(vfields);
                }
                i++;
            }
            m.setDataVector(rows, vCol);
        }else
            out.setText("No hay Proveedores en la BD");
    }
    
    private ArrayList copiaArrayList(ArrayList a){
        ArrayList n = new ArrayList();
        /*NOTA: Puede que no tenga ningun telefono el proveedor*/
        /*Pero los mecanismos de correccion frente a meter los telefonos del proveedor desde el programa no funcionan
         hay que verificar como se han introducido los telefonos del proveedor*/
        if(a!=null){
            Iterator it = a.iterator();
            while(it.hasNext()){
                n.add((String)it.next());
            }
            return n;
        }else{
            System.out.println("El proveedor no tiene telefonos");
            return null;
        }
    }
    
    /*Funciones para la gestion del hashtable*/
    
    /*Elimina un proveedor del hashtable*/
    public static void eliminaProvTfnos(String cif){
        tfnos.remove(cif);
    }
    
    /*Recibimos la lista de telefonos de un */
    public static ArrayList getTfnosProv(String cif){
        return (ArrayList)tfnos.get(cif);
    }
    
    /*Modificamos el telefono del proveedor que le pasamos como parametro*/
    public static void modificaTfnoProv(String cif, String tel, String tel2){
        if((tfnos!=null)&&(cif!=null)&&(tel!=null)&&(tel2!=null)){
            ArrayList l; 
            l = (ArrayList)tfnos.get(cif);
            if(l!=null){
                int i=0;
                while((i<l.size())&&(!tel.equals(l.get(i)))){
                    i++;
                }
                if(i < l.size())
                    l.set(i, tel2);
                else
                    System.out.println("Telenfono no encontrado");
            }else
                System.out.println("El cif "+cif+" no tiene telefonos");
        }else
            System.out.println("Fallo al pasar los parametros");
    }
    
    /*Elimina el telefono del proveedor que le pasamos como parametro*/
    public static void eliminaTfnoProv(String cif, String tel){
        if((tfnos!=null)&&(cif!=null)&&(tel!=null)){
            ArrayList l; 
            l = (ArrayList)tfnos.get(cif);
            if(l!=null){
                int i=0;
                while((i<l.size())&&(!tel.equals(l.get(i)))){
                    i++;
                }
                if(i < l.size())
                    l.remove(i);
                else
                    System.out.println("Telenfono no encontrado");
            }else
                System.out.println("El cif "+cif+" no tiene telefonos");
        }else
            System.err.println("Fallo al pasar los parametros");
    }
    
    /*Inserta el telefono al proveedor que le pasamos como parametro*/
    public static void insertaTfnoProv(String cif, String t){
        ((ArrayList)tfnos.get(cif)).add(t);
    }
    
    /*Interface para recibir los datos de las hashtables*/
    public static Vector tfnos2Vector(String cif){
        Vector rows;
        if((cif != null) && (tfnos != null)){
            rows = new Vector(); 
            int i = 0;
            ArrayList l = (ArrayList)tfnos.get(cif);
            while(i<l.size()){
                Vector v = new Vector();
                v.addElement(l.get(i));
                rows.addElement(v);
                //System.out.println("tel: "+l.get(i));
                i++;
            }
            return rows;
        }else{
            System.out.println("Error en la entrada de los parametros");
            return null;
        }
    }
    
    /*Imprime por pantalla los telefonos de los proveedores*/
    private void listaTfnos(ArrayList tf){
        int i=0;
        if(tf != null){
            while(i<tf.size()){
                System.out.println("telefono: " + (String)tf.get(i));
                i++;
            }
        }else
            System.out.println("No tiene telefonos");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        insertar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        buscar = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        suma = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        limpiar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ncif = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        out = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        aceptar = new javax.swing.JButton();
        tfno = new javax.swing.JButton();
        productos = new javax.swing.JButton();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        insertar.setText("Insertar");
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });

        getContentPane().add(insertar);
        insertar.setBounds(20, 30, 76, 29);

        eliminar.setText("Eiminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        getContentPane().add(eliminar);
        eliminar.setBounds(20, 80, 75, 29);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(20, 130, 75, 29);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        getContentPane().add(modificar);
        modificar.setBounds(20, 180, 87, 29);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(350, 310, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(390, 310, 40, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(490, 310, 80, 29);

        jLabel1.setText("Nuevo cif:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 230, 70, 16);

        getContentPane().add(ncif);
        ncif.setBounds(10, 250, 110, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(160, 370, 60, 16);

        getContentPane().add(out);
        out.setBounds(230, 370, 510, 20);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "cif", "*localizacion", "nombre", "*numCuenta", "*persContacto", "*web", "*email", "eliminado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(150, 30, 860, 250);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(630, 310, 80, 29);

        tfno.setText("Telefonos");
        tfno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnoActionPerformed(evt);
            }
        });

        getContentPane().add(tfno);
        tfno.setBounds(10, 320, 110, 29);

        productos.setText("Productos");
        productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosActionPerformed(evt);
            }
        });

        getContentPane().add(productos);
        productos.setBounds(180, 310, 100, 29);

        pack();
    }//GEN-END:initComponents

    private void productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    String cif;
                    cif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    vProdProv.visualiza(cif);
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_productosActionPerformed

    private void tfnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnoActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            if(vTfnosProv == null)
                vTfnosProv = new tfnosProv();
            
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    String ci;
                    ci = ((String)jTable1.getValueAt(row, CIF)).trim();
                    //Tenemos que contemplar la posibilidad de que queramos insertar los telefonos de un empleado nuevo
                    try{
                        ArrayList l;
                        l = (ArrayList)tfnos.get(ci); //hashtable.get(nif) devuelve un Object de ahi el cast
                        vTfnosProv.visualiza(ci, false);
                    }catch(NullPointerException nu){
                        tfnos.put((String)ci, new ArrayList()); //Creamos una nueva entrada en la hashtable
                        vTfnosProv.visualiza(ci, true);
                        out.setText("Telefonos de un proveedor nuevo");
                    }
                
                }catch(NullPointerException nu){
                    out.setText("Error recibiendo el nif");
                }
                    
            }else
                out.setText("Error en la fila seleccionada");
            
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_tfnoActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        // TODO TEST
        ncif.setText("");//Limpiamos el contenido del nuevo cif
        
        Vector v = new Vector();
        v.addElement(null); //Cada elemento del vector que le pasamos como dato a setDataVector es una fila!!!
                
        m.setDataVector(v, vCol);
        out.setText("Tabla limpiada!");
    }//GEN-LAST:event_limpiarActionPerformed

    private void menosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            try{
                m.removeRow(jTable1.getSelectedRow());
            }catch(ArrayIndexOutOfBoundsException ai){
                System.out.println("No hay fila seleccionada");
            }
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_menosActionPerformed

    private void sumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumaActionPerformed
        // TODO TEST
        Vector v = new Vector();
        v.addElement(null);
        m.addRow(v);
    }//GEN-LAST:event_sumaActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
         // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public int eliminar(String cif)
                try{
                    String cif;
                    cif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    if(!cif.equals("")){
                        Boolean eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                        if((eliminado!=null)&&(eliminado.booleanValue()==false)){
                            if((new MantenimientoDB.Proveedores().eliminar(cif))==1){
                                m.setValueAt(new Boolean(true), row, ELIMINADO);
                                out.setText("Eliminacion realizada con exito");
                                //No borramos los telefonos del arraylist por si activamos despues al proveedor
                                //tfnos.remove(cif); //Borramos los telefonos
                            }else
                                out.setText("Eliminacion erronea");
                        }else
                            out.setText("El proveedor ya estaba eliminado");
                    }else
                        out.setText("No es posible eliminar el cif vacio");
                }catch(NullPointerException nu){
                    out.setText("Error en el cif introducido");
                }
            }else
                out.setText("Fila seleccionada no valida");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_eliminarActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public Vector buscar(String cif){
                try{
                    String cif;
                    Vector v;
                    cif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    if(!cif.equals("")){
                        v = new MantenimientoDB.Proveedores().buscar(cif);
                        if(v!=null){
                            if(cif.equals("-1"))
                                out.setText("Proveedores encontrados con exito");
                            else
                                out.setText("Proveedor encontrado con exito");
                            db2table(v);
                        }else
                            out.setText("Busqueda sin resultados");
                    }else
                        out.setText("No se puede buscar el cif vacio");
                }catch(NullPointerException nu){
                    out.setText("Error al seleccionar el cif");
                }
            }else
                out.setText("Error fila incorrecta");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_buscarActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                                
                /*Campos opcionales: localizacion, numCuenta, persContacto, web, email*/
                String email, web, contacto, localizacion, numCuenta; //Tenemos que declarar los strings aqui porque sino se enfadan los catch's
                try{
                    email = ((String)jTable1.getValueAt(row, EMAIL)).trim();
                }catch(NullPointerException nu){
                    email = ""; //Se crea implicitamente un objeto String
                    m.setValueAt(email, row, EMAIL);
                    out.setText("Email por defecto");
                }
                try{
                    web = ((String)jTable1.getValueAt(row, WEB)).trim();
                }catch(NullPointerException nu){
                    web = "";
                    m.setValueAt(web, row, WEB);
                    out.setText("Web por defecto");
                }
                try{
                    contacto = ((String)jTable1.getValueAt(row, PERS_CONTACTO)).trim();
                }catch(NullPointerException nu){
                    contacto = "";
                    m.setValueAt(contacto, row, PERS_CONTACTO);
                    out.setText("Contacto por defecto");
                }
                try{
                    localizacion = ((String)jTable1.getValueAt(row, LOCALIZACION)).trim();
                }catch(NullPointerException nu){
                    localizacion = "";
                    m.setValueAt(localizacion, row, LOCALIZACION);
                    out.setText("Localizacion por defecto");
                }
                try{
                    numCuenta = ((String)jTable1.getValueAt(row, NUM_CUENTA)).trim();
                }catch(NullPointerException nu){
                    numCuenta = "";
                    m.setValueAt(numCuenta, row, NUM_CUENTA);
                    out.setText("Numero de cuenta por defecto");
                }
                
                /*Campos obligatorios*/
                try{
                    String cif, nombre;
                    Boolean eliminado;
                    Integer zona;
                    cif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    if(eliminado==null){
                        eliminado = new Boolean(false);
                        m.setValueAt(eliminado, row, ELIMINADO);
                    }
                    ArrayList l = (ArrayList)tfnos.get(cif);
                    if(l!=null){ //Necesitamos un ArrayList porque es multivaluado
                        //public int insertar(String cif, String localizacion, ArrayList tfno, String nombre, String numcuenta, String persContacto, String web, String email, boolean eliminado)                    
                        if((new MantenimientoDB.Proveedores().insertar(cif, localizacion, l, nombre, numCuenta,  contacto, web, email, eliminado.booleanValue()))==1){
                            out.setText("Insercion realizada con exito");
                        }else{
                            out.setText("Insercion erronea");
                        //Dejamos la linea por si ha habido algun error de escritura
                        }
                    }else
                        out.setText("No existen telefonos para este proveedor");
                }catch(NullPointerException nu){
                    out.setText("Error en la introduccion de datos");
                }
                    
            }else
                out.setText("Error fila no valida");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_insertarActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                
                try{
                    //{"cif", "*localizacion", "nombre", "*numCuenta", "*persContacto", "*web", "*email", "eliminado"}
                    String oldCif, newCif, nombre, localizacion, email, web, contacto, numCuenta;
                    Boolean eliminado;
                                        
                    oldCif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    localizacion = ((String)jTable1.getValueAt(row, LOCALIZACION)).trim();
                    email = ((String)jTable1.getValueAt(row, EMAIL)).trim();
                    web = ((String)jTable1.getValueAt(row, WEB)).trim();
                    contacto = ((String)jTable1.getValueAt(row, PERS_CONTACTO)).trim();
                    numCuenta= ((String)jTable1.getValueAt(row, NUM_CUENTA)).trim();
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    ArrayList l= (ArrayList)tfnos.get(oldCif);
                    
                    newCif = ncif.getText();
                    if(!newCif.equals("")){
                        //public int modificar(String antiguoCif, String nuevoCif, String localizacion, ArrayList tfno, String nombre, String numcuenta, String persContacto, String web, String email, boolean eliminado)
                        if((new MantenimientoDB.Proveedores().modificar(oldCif, newCif, localizacion, l, nombre, numCuenta, contacto, web, email, eliminado.booleanValue()))==1){
                            if(oldCif != newCif){
                                m.setValueAt(newCif, row, CIF);
                                tfnos.put(newCif, l); //TODO TEST -> tfnos.put(newCif, l.clone());
                                tfnos.remove(oldCif); //borramos la referencia vieja
                            }
                            out.setText("Modificacion realizada con exito");
                            ncif.setText("");
                        }else
                            out.setText("Error en la modificacion");
                    }else
                        out.setText("Nuevo cif incorrecto");
                    
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
                
            }else
                out.setText("Error fila no valida");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_modificarActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new Proveedores().show();
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton buscar;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton insertar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton limpiar;
    private javax.swing.JButton menos;
    private javax.swing.JButton modificar;
    private javax.swing.JTextField ncif;
    private javax.swing.JTextField out;
    private javax.swing.JButton productos;
    private javax.swing.JButton suma;
    private javax.swing.JButton tfno;
    // End of variables declaration//GEN-END:variables
    
}
