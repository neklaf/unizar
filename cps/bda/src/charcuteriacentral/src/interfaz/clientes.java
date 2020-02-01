/*
 * Clientes.java
 *
 * Created on 11 de marzo de 2005, 11:28
 */

/*NOTA: Como es el ifaz del nodo central no tenemos que poner los pedidos pero en el ifaz del punto de venta SI!!*/

/*NOTA: tenemos que llamar desde un boton a la ventana de los pedidos (en el PV!)*/

package Interfaz;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Iterator;

/*Campos optativos: localizacion, email, web, p.contacto*/

/*NOTA: Tenemos que implementar un boton para los pedidos*/

/**
 *
 * @author  hadden
 */
public class Clientes extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    DefaultTableModel m; //Modelo de la tabla
    String cols[] = new String [] {"cif", "nombre", "*localizacion", "tfno", "*email", "*web", "*P.Contacto", "departamento", "eliminado"};
    Vector vCol; //Vector en el que almaceno las columnas de la tabla
    
    /*Columnas*/
    private final int COLS = 9;
    
    private final int CIF = 0;
    private final int NOMBRE = 1;
    private final int LOCALIZACION = 2;
    private final int TFNO = 3;
    private final int EMAIL = 4;
    private final int WEB = 5;
    private final int CONTACTO = 6;
    private final int DEPARTAMENTO = 7;
    private final int ELIMINADO = 8;
    
    
    /** Creates new form Clientes */
    public Clientes() {
               
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        ocultar();
        
        m = (DefaultTableModel)jTable1.getModel(); //Aqui tendremos el modelo de nuestra tabla
        
        /*Configuracion de la ventana*/
        this.setSize(950,400);
        //this.setVisible(true);
        this.setTitle("Ventana de Clientes");
        this.setResizable(false);
    }
    
    private void ocultar(){
        pedidos.setVisible(false);
    }
    
    /*Abre la ventana representando los clientes que hay en la bd central*/
    public void visualiza(int zona){
        //TODO TEST
        Vector v;
        
        //public Vector buscar(String cif, int zona)
        v = (Vector)new MantenimientoDB.Clientes().buscar("-1", zona);
        
        if(v!=null){
            db2table(v);
            this.setVisible(true);
        }else
            System.out.println("Se ha producido un error en la busqueda");
    }
    
    private void db2table(Vector v){
        //TODO TEST
        
        if(v.size()!=0){
            String clase;
            
            //System.out.println("El size del vector es: "+ v.size());
            
            /*Definimos tantas variables como tipos pueda tener la fila que vamos a recibir*/
            Central.CentralClientes crow;
            PuntoDeVenta.PVClientes pvrow;
            Vector rows = new Vector();
                       
            int i = 0;
            String cif; //Sera necesario para indexar las ventas
            
            //Recorremos todas filas que nos haya proporcionado la base de datos
            while (i < v.size()){
                /*ADVERTENCIA: Tenemos que poner un try-catch para poder soportar la situacion en la que uno de los campos
                 que este en la BD sea null igual que en el caso de los jefes en la ventana Empleados*/
                
                clase = v.elementAt(i).getClass().getName(); //Recibimos el nombre de la clase de la fila
                
                if(clase.endsWith("Central.CentralClientes")){
                    
                    crow = (Central.CentralClientes)v.elementAt(i);
                    Vector vfields = new Vector();
                    
                    /*La secuencia de recepcion de los campos tendra que coincidir con el orden en el 
                     que se representen en la tabla*/
                    /*{"cif", "nombre", "localizacion", "tfno", "email", "web", "P.Contacto", "departamento", "eliminado"}*/
                    cif = crow.getCif();
                    vfields.addElement(cif);
                    vfields.addElement(crow.getNombre());
                    vfields.addElement(crow.getLocalizacion());
                    vfields.addElement(crow.getTfno());
                    vfields.addElement(crow.getEmail());
                    vfields.addElement(crow.getWeb());
                    vfields.addElement(crow.getPersContacto());
                    vfields.addElement(new Integer(((Central.CentralPuntoDistribucion)crow.getDepartamento()).getZona()));
                    vfields.addElement(new Boolean(crow.getEliminado()));
                    //No tiene pedidos!!!
                    
                    rows.addElement(vfields);
               }else if(clase.endsWith("PuntoDeVenta.PVClientes")){
                   /*La clase PuntoDeVenta.PVClientes no tiene departamento porque como el cliente esta asignado a su punto de venta*/
                   /*La clase PuntoDeVenta.PVClientes tiene pedidos que NO tiene la clase Central.CentralClientes*/
                   
                   pvrow = (PuntoDeVenta.PVClientes)v.elementAt(i);
                    Vector vfields = new Vector();
                   
                    cif = pvrow.getCif();
                    vfields.addElement(cif);
                    vfields.addElement(pvrow.getNombre());
                    vfields.addElement(pvrow.getLocalizacion());
                    vfields.addElement(pvrow.getTfno());
                    vfields.addElement(pvrow.getEmail());
                    vfields.addElement(pvrow.getWeb());
                    vfields.addElement(pvrow.getPersContacto());
                    
                    vfields.addElement(new Boolean(pvrow.getEliminado()));
                    
                    rows.addElement(vfields); //Introducimos la fila en el vector de filas
               }
               i++;
            }
            m.setDataVector(rows, vCol);
        }else
            out.setText("No hay clientes en la BD");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ncif = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        out = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        aceptar = new javax.swing.JButton();
        pedidos = new javax.swing.JButton();

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
        insertar.setBounds(40, 40, 80, 29);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        getContentPane().add(eliminar);
        eliminar.setBounds(40, 100, 80, 29);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(40, 160, 75, 29);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        getContentPane().add(modificar);
        modificar.setBounds(40, 220, 90, 29);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(240, 290, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(280, 290, 40, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(450, 290, 80, 29);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "cif", "nombre", "*localizacion", "tfno", "*email", "*web", "*P. Contacto", "departamento", "eliminado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(180, 20, 760, 250);

        getContentPane().add(ncif);
        ncif.setBounds(30, 280, 100, 22);

        jLabel1.setText("Nuevo cif:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 260, 80, 16);

        getContentPane().add(out);
        out.setBounds(220, 340, 540, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(140, 340, 50, 16);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(710, 290, 76, 29);

        pedidos.setText("Pedidos");
        getContentPane().add(pedidos);
        pedidos.setBounds(40, 320, 77, 29);

        pack();
    }//GEN-END:initComponents

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
        ncif.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

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
                        v = new MantenimientoDB.Clientes().buscar(cif, -1);
                        if(v!=null){
                            if(cif.equals("-1"))
                                out.setText("Clientes encontrados con exito");
                            else
                                out.setText("Cliente encontrado con exito");
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

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public int eliminar(String cif)
                try{
                    String cif;
                    cif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    Boolean eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    if(!cif.equals("")){
                        if(eliminado.booleanValue()==false){
                            if((new MantenimientoDB.Clientes().eliminar(cif))==1){
                                m.setValueAt(new Boolean(true), row, ELIMINADO);
                                out.setText("Eliminacion realizada con exito");
                            }else
                                out.setText("Eliminacion erronea");
                        }else
                            out.setText("No se puede eliminar un cliente eliminado");
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

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public int insertar(String cif, String nombre, String localizacion, String tfno, String email, String web, String persContacto, int zona)
                
                /*Campos optativos: email, web, Pers.Contacto, localizacion*/
                /*Campos optativos: localizacion, email, web, p.contacto*/
                String email, web, contacto, localizacion; //Tenemos que declarar los strings aqui porque sino se enfadan los catch's
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
                    contacto = ((String)jTable1.getValueAt(row, CONTACTO)).trim();
                }catch(NullPointerException nu){
                    contacto = "";
                    m.setValueAt(contacto, row, CONTACTO);
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
                    String cif, nombre, tfno;
                    Boolean eliminado;
                    Integer zona;
                    cif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    tfno = ((String)jTable1.getValueAt(row, TFNO)).trim();
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    if(eliminado == null){
                        eliminado = new Boolean(false);
                        m.setValueAt(eliminado, row, ELIMINADO);
                    }
                    zona = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                    if(zona != null){
                        if((new MantenimientoDB.Clientes().insertar(cif, nombre, localizacion, tfno, email, web, contacto, eliminado.booleanValue(), zona.intValue()))==1){
                            out.setText("Insercion realizada con exito");
                        }else{
                            out.setText("Insercion erronea");
                        //Dejamos la linea por si ha habido algun error de escritura
                        }
                    }else
                        out.setText("No se ha insertado el departamento");
                }catch(NullPointerException nu){
                    out.setText("Error en la introduccion de datos");
                }
                    
            }else
                out.setText("Error fila no valida");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_insertarActionPerformed

    /*Cuando implementemos la ventana para PuntoDeVenta de clientes tendremos que tener en cuenta los pedidos del Cliente*/
    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public int modificar(String antiguoCif, String nuevoCif, String nombre, String localizacion, String tfno, String email, String web, String persContacto, int zona)
                String oldCif, newCif, nombre, localizacion, tfno, email, web, contacto;
                Boolean eliminado;
                Integer zona;
                try{
                    oldCif = ((String)jTable1.getValueAt(row, CIF)).trim();
                    //System.out.println("oldCif: '"+oldCif+"'");
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    //System.out.println("nombre: '"+nombre+"'");
                    localizacion = ((String)jTable1.getValueAt(row, LOCALIZACION)).trim();
                    //System.out.println("localizacion: '"+localizacion+"'");
                    tfno = ((String)jTable1.getValueAt(row, TFNO)).trim();
                    //System.out.println("Tfno: '"+tfno+"'");
                    email = ((String)jTable1.getValueAt(row, EMAIL)).trim();
                    //System.out.println("Email: '"+email+"'");
                    web = ((String)jTable1.getValueAt(row, WEB)).trim();
                    //System.out.println("Web: '"+web+"'");
                    contacto = ((String)jTable1.getValueAt(row, CONTACTO)).trim();
                    if(contacto == null){
                        contacto = "Fiesta1";
                        System.out.println("Contacto es null");
                    }
                    
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    /*if(eliminado == null)
                        System.out.println("Eliminado es null");*/
                    
                    zona = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                    /*if(zona == null)
                        System.out.println("Zona es null");*/
                    
                    newCif = ncif.getText();
                    if(!newCif.equals("")){
                        //public int modificar(String antiguoCif, String nuevoCif, String nombre, String localizacion, String tfno, String email, String web, String persContacto, boolean eliminado, int zona)
                        if(zona != null){
                            if((new MantenimientoDB.Clientes().modificar(oldCif, newCif, nombre, localizacion, tfno, email, web, contacto, eliminado.booleanValue(), zona.intValue()))==1){
                                if(oldCif != newCif)
                                    m.setValueAt(newCif, row, CIF);
                                out.setText("Modificacion realizada con exito");
                                ncif.setText("");
                            }else
                                out.setText("Error en la modificacion");
                        }else
                            out.setText("No tenemos una zona valida");
                    }else
                        out.setText("Nuevo cif incorrecto");
                    
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                    contacto = "fiesta2";
                    
                    System.out.println("Contacto: '"+contacto+"'");
                }
                
            }else
                out.setText("Error fila no valida");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_modificarActionPerformed

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
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new Clientes().show();
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
    private javax.swing.JButton pedidos;
    private javax.swing.JButton suma;
    // End of variables declaration//GEN-END:variables
    
}
