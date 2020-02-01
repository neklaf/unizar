/*
 * ProductosProveedores.java
 *
 * Created on 12 de marzo de 2005, 1:09
 */

package Interfaz;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/*Campo optativo: descripcion*/

/**
 *
 * @author  hadden
 */
public class ProductosProveedores extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    DefaultTableModel m; //Modelo de la tabla
    String cols[] = new String [] {"ref_prod", "nombre", "*descripcion", "precio", "unidades", "eliminado", "proveedor"};
    Vector vCol; //Vector en el que almaceno las columnas de la tabla
    
    /*Columnas*/
    private final int COLS = 7;
    
    private final int REF_PROD = 0;
    private final int NOMBRE = 1;
    private final int DESCRIPCION = 2;
    private final int PRECIO = 3;
    private final int UNIDADES = 4;
    private final int ELIMINADO = 5;
    private final int PROVEEDOR = 6;
    
    
        
    /** Creates new form ProductosProveedores */
    public ProductosProveedores() {
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        m = (DefaultTableModel)jTable1.getModel(); //Aqui tendremos el modelo de nuestra tabla
        
        /*Configuracion de la ventana*/
        this.setSize(825,425);
        //this.setVisible(true);
        this.setTitle("Ventana de Productos-Proveedores");
        this.setResizable(false);
    }
    
    /*Abre la ventana representando los clientes que hay en la bd central*/
    public void visualiza(String cif){
        Vector v;
        
        if(cif!=null){
            v = (Vector)new MantenimientoDB.ProductosProveedores().buscar("-1", cif);
        
            if(v!=null){
                db2table(v);
                this.setVisible(true);
            }else
                System.out.println("Se ha producido un error en la busqueda");
        }else
            System.out.println("No se ha recibido un cif de proveedor correcto");
    }
    
    private void db2table(Vector v){
        //TODO TEST IMPORTANTE PORQUE HE HECHO ALGUNOS CAMBIOS CON RESPECTO A ANTERIORES VERSIONES!!!
        if(v.size()!=0){
            String clase;
            
            /*Definimos tantas variables como tipos pueda tener la fila que vamos a recibir*/
            Central.CentralProductosProveedores crow;
            PuntoDeVenta.PVProductosProveedores pvrow;
            Vector rows = new Vector();
            
            int i = 0;
            while (i < v.size()){
                clase = v.elementAt(i).getClass().getName(); //Recibimos el nombre de la clase de la fila
                /*La secuencia de recepcion de los campos tendra que coincidir con el orden en el 
                 que se representen en la tabla*/
                if(clase.endsWith("Central.CentralProductosProveedores")){
                    crow = (Central.CentralProductosProveedores)v.elementAt(i);
                    Vector vfields = new Vector();
                    
                    //{"ref_prod", "nombre", "*descripcion", "precio", "unidades", "eliminado", "proveedor"}
                    vfields.addElement(crow.getRef_prod());
                    vfields.addElement(crow.getNombre());
                    vfields.addElement(crow.getDescripcion()); //Es campo optativo pero tiene "" como minimo
                    //Creo que es un desperdicio de memoria aunque puede que se rallara el JDO
                    vfields.addElement(new Double(crow.getPrecio()));
                    vfields.addElement(new Double(crow.getUnidades()));
                    vfields.addElement(new Boolean(crow.getEliminado()));
                    vfields.addElement(crow.getProveedor().getCif()); //TODO TEST
                    
                    rows.addElement(vfields);
                }else if(clase.endsWith("PuntoDeVenta.PVProductosProveedores")){
                    pvrow = (PuntoDeVenta.PVProductosProveedores)v.elementAt(i);
                    Vector vfields = new Vector();
                    
                    vfields.addElement(pvrow.getRef_prod());
                    vfields.addElement(pvrow.getNombre());
                    vfields.addElement(pvrow.getDescripcion()); //Es campo optativo pero tiene "" como minimo
                    //Creo que es un desperdicio de memoria aunque puede que se rallara el JDO
                    vfields.addElement(new Double(pvrow.getPrecio()));
                    vfields.addElement(new Double(pvrow.getUnidades()));
                    vfields.addElement(new Boolean(pvrow.getEliminado()));
                    vfields.addElement(pvrow.getProveedor().getCif()); //Puede que esto pete!!
                    
                    rows.addElement(vfields);
                }
                i++;
            }
            m.setDataVector(rows, vCol);
        }else
            out.setText("No hay Productos-Proveedores en la bd");
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
        aceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        nref = new javax.swing.JTextField();
        out = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

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
        insertar.setBounds(40, 50, 80, 29);

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
        modificar.setBounds(40, 210, 87, 29);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(230, 290, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(270, 290, 40, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(460, 290, 80, 29);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(640, 290, 80, 29);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ref_prod", "nombre", "*descripcion ", "precio", "unidades", "eliminado", "proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(170, 50, 600, 220);

        jLabel1.setText("Nueva referencia:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 260, 120, 16);

        getContentPane().add(nref);
        nref.setBounds(20, 280, 120, 22);

        getContentPane().add(out);
        out.setBounds(170, 340, 480, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(90, 340, 60, 16);

        pack();
    }//GEN-END:initComponents

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
        nref.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

    //public Vector buscar(String ref_prod)
    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    //public Vector buscar(String ref_prod)
                    String ref;
                    ref = ((String)jTable1.getValueAt(row, REF_PROD)).trim();
                    if(!ref.equals("")){
                        Vector v;
                        v = new MantenimientoDB.ProductosProveedores().buscar(ref, "-1");
                        if(v!=null){
                            if(ref.equals("-1"))
                                out.setText("Productos Proveedores encontrados con exito");
                            else
                                out.setText("Producto Proveedor encontrado con exito");
                            db2table(v);
                        }else
                            out.setText("Busqueda sin resultados");
                    }else
                        out.setText("No es posible buscar la referencia vacia");
                        
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_buscarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    //public int eliminar(String ref_prod)
                    String ref;
                    ref = ((String)jTable1.getValueAt(row, REF_PROD)).trim();
                    Boolean eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    if(!ref.equals("")){
                        if(eliminado.booleanValue()==false){
                            if((new MantenimientoDB.ProductosProveedores().eliminar(ref))==1){
                                m.setValueAt(new Boolean(true), row, ELIMINADO);
                                out.setText("Eliminacion realizada con exito");
                            }else
                                out.setText("Eliminacion erronea");
                        }else
                            out.setText("No se puede eliminar un producto de proveedor ya eliminado");
                    }else
                        out.setText("No es posible eliminar una referencia vacia");
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_eliminarActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
           int row = jTable1.getSelectedRow();
            if(row >= 0){
                
                /*campo optativo: descripcion*/
                String descripcion;
                try{ //Este try se tiene que hacer debido a como a–ado una fila en la tabla
                    descripcion = ((String)jTable1.getValueAt(row, DESCRIPCION)).trim();
                }catch(NullPointerException nu){
                    descripcion = "";
                    m.setValueAt(descripcion, row, DESCRIPCION);
                }
                try{
                    String ref, nombre, proveedor;
                    Double precio, unidades;
                    Boolean eliminado;
                    
                    ref = ((String)jTable1.getValueAt(row, REF_PROD)).trim();
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    descripcion = ((String)jTable1.getValueAt(row, DESCRIPCION)).trim();
                    proveedor = ((String)jTable1.getValueAt(row, PROVEEDOR)).trim();
                    precio = (Double)jTable1.getValueAt(row, PRECIO);
                    unidades = (Double)jTable1.getValueAt(row, UNIDADES);
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    if(eliminado == null){
                        eliminado = new Boolean(false);
                        m.setValueAt(eliminado, row, ELIMINADO);
                    }
                    //public int insertar(String ref_prod,String nombre,String descripcion,double precio,double unidades, boolean eliminado, String cifProveedor)
                    if((new MantenimientoDB.ProductosProveedores().insertar(ref, nombre, descripcion, precio.doubleValue(), unidades.doubleValue(), eliminado.booleanValue(), proveedor))==1)
                        out.setText("Insercion realizada con exito");
                    else
                        out.setText("Insercion erronea");
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_insertarActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public int modificar(String antiguaRef_prod, String nuevaRef_prod, String nombre,String descripcion,double precio,double unidades, boolean eliminado, String cifProveedor)
                try{
                    String oldRef, newRef, nombre, descripcion, proveedor;
                    Double precio, unidades;
                    Boolean eliminado;
                
                    oldRef = ((String)jTable1.getValueAt(row, REF_PROD)).trim();
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    descripcion = ((String)jTable1.getValueAt(row, DESCRIPCION)).trim();
                    proveedor = ((String)jTable1.getValueAt(row, PROVEEDOR)).trim();
                    precio = (Double)jTable1.getValueAt(row, PRECIO);
                    unidades = (Double)jTable1.getValueAt(row, UNIDADES);
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    newRef = nref.getText();
                    if(!newRef.equals("")){
                        if((new MantenimientoDB.ProductosProveedores().modificar(oldRef, newRef, nombre, descripcion, precio.doubleValue(), unidades.doubleValue(), eliminado.booleanValue(), proveedor))==1){
                            if(!oldRef.equals(newRef))
                                m.setValueAt(newRef, row, REF_PROD);
                            out.setText("Modificacion realizada con exito");
                            nref.setText("");                                
                        }else
                            out.setText("Error en la modificacion");
                    }else
                        out.setText("La nueva referencia no puede ser vacia");
                    
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_modificarActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        // TODO TEST
        nref.setText("");//Limpiamos el contenido de la nueva referencia
        
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
        new ProductosProveedores().show();
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
    private javax.swing.JTextField nref;
    private javax.swing.JTextField out;
    private javax.swing.JButton suma;
    // End of variables declaration//GEN-END:variables
    
}
