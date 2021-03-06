/*
 * Productos.java
 *
 * Created on 15 de marzo de 2005, 4:17
 */

package Interfaz;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/*campos optativos: descripcion*/

/**
 *
 * @author  hadden
 */
public class Productos extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    DefaultTableModel m; //Modelo de la tabla
    String cols[] = new String [] {"ref", "nombre", "*descripcion", "precio", "unidades", "eliminado", "departamento"};
    Vector vCol; //Vector en el que almaceno las columnas de la tabla
    
    /*Columnas*/
    private final int COLS = 7;
    
    private final int REF = 0;
    private final int NOMBRE = 1;
    private final int DESCRIPCION = 2;
    private final int PRECIO = 3;
    private final int UNIDADES = 4;
    private final int ELIMINADO = 5;
    private final int DEPARTAMENTO = 6;
    
    /** Creates new form Productos */
    public Productos() {
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        m = (DefaultTableModel)jTable1.getModel(); //Aqui tendremos el modelo de nuestra tabla
        
        /*Configuracion de la ventana*/
        this.setSize(850,425);
        //this.setVisible(true);
        this.setTitle("Ventana de Productos");
        this.setResizable(false);
    }
    
    public void visualiza(){
        Vector v;
        
        //public Vector buscar(String ref)
        v = (Vector)new MantenimientoDB.Productos().buscar("-1");
        
        if(v!=null){
            db2table(v);
            this.setVisible(true);
        }else
            System.err.println("Se ha producido un error en la busqueda");
    }
    
    private void db2table(Vector v){
        if(v.size()!=0){
            
            PuntoDeVenta.PVProductos pvrow;
            Vector rows = new Vector();
            
            int i = 0;
            while (i < v.size()){
                pvrow = (PuntoDeVenta.PVProductos)v.elementAt(i);
                Vector vfields = new Vector();
                
                //{"ref", "nombre", "*descripcion", "precio", "unidades", "eliminado", "esRegional"}
                vfields.addElement(pvrow.getRef());
                vfields.addElement(pvrow.getNombre());
                vfields.addElement(pvrow.getDescripcion()); //Es campo optativo pero tiene "" como minimo
                //Creo que es un desperdicio de memoria aunque puede que se rallara el JDO
                vfields.addElement(new Double(pvrow.getPrecio()));
                vfields.addElement(new Double(pvrow.getUnidades()));
                vfields.addElement(new Boolean(pvrow.getEliminado()));
                PuntoDeVenta.PVPuntoDistribucion pd = pvrow.getDepartamento();
                
                if(pd!=null){
                    //vfields.addElement(new Boolean(true));
                    vfields.addElement(new Integer(pd.getZona()));
                }else{
                    //vfields.addElement(new Boolean(false)); //Producto general
                    vfields.addElement(new Integer(-1));
                }
                //vfields.addElement(new Boolean(pvrow.getEsRegional()));
            
                rows.addElement(vfields);
                i++;
            }
            m.setDataVector(rows, vCol);
        }else
            out.setText("No hay Productos en la bd");
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
        jLabel1 = new javax.swing.JLabel();
        nref = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        out = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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
        buscar.setBounds(40, 170, 75, 29);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        getContentPane().add(modificar);
        modificar.setBounds(40, 240, 87, 29);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(290, 300, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(330, 300, 40, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(440, 300, 80, 29);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(560, 300, 90, 29);

        jLabel1.setText("Nueva ref:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 280, 70, 16);

        getContentPane().add(nref);
        nref.setBounds(30, 300, 120, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(150, 360, 60, 16);

        getContentPane().add(out);
        out.setBounds(220, 360, 410, 22);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ref", "nombre", "*descripcion", "precio", "unidades", "eliminado", "departamento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(180, 40, 590, 220);

        pack();
    }//GEN-END:initComponents

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
        nref.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        // TODO TEST
        nref.setText("");
        
        Vector v = new Vector();
        v.addElement(null);
                
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
                try{
                    String ref;
                    Boolean eliminado;
                    ref = ((String)jTable1.getValueAt(row, REF)).trim();
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    if(!ref.equals("")){
                        if(eliminado.booleanValue() == false){ //Creo que los if's en Java son cortocircuitados
                            //public int modificarEliminado(String ref, boolean eliminado)
                            if((new MantenimientoDB.Productos().modificarEliminado(ref, true))==1){
                                m.setValueAt(new Boolean(true), row, ELIMINADO); //Actualizamos la tabla
                                out.setText("Eliminacion realizada con exito");
                            }else
                                out.setText("Eliminacion erronea");
                        }else
                            out.setText("No se puede borrar un producto eliminado");
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

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    //public Vector buscar(String ref)
                    String ref;
                    ref = ((String)jTable1.getValueAt(row, REF)).trim();
                    if(!ref.equals("")){
                        Vector v;
                        v = new MantenimientoDB.Productos().buscar(ref);
                        if(v!=null){
                            if(ref.equals("-1"))
                                out.setText("Productos encontrados con exito");
                            else
                                out.setText("Producto encontrado con exito");
                            db2table(v);
                        }else
                            out.setText("Busqueda sin resultados");
                    }else
                        out.setText("No es posible eliminar la referencia vacia");
                        
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_buscarActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
           int row = jTable1.getSelectedRow();
            if(row >= 0){
                /*campo optativo: descripcion*/
                String descripcion;
                try{
                    /*Cuando no introducimos un valor en la tabla entonces lo que ocurre es que getValueAt devuelve un objeto
                     null y cuando intentamos acceder a un metodo de un objeto null salta la excepcion*/
                    descripcion = ((String)jTable1.getValueAt(row, DESCRIPCION)).trim();
                }catch(NullPointerException nu){
                    descripcion = "";
                    m.setValueAt("", row, DESCRIPCION); //Necesario para el modificar!!
                    out.setText("Descripcion por defecto");
                }
                
                /*campos obligatorios*/
                String ref, nombre;
                Double precio, unidades;
                Integer departamento;
                Boolean eliminado;    
                //{"ref", "nombre", "*descripcion", "precio", "unidades", "eliminado", "departamento"}
                    
                precio = (Double)jTable1.getValueAt(row, PRECIO);
                unidades = (Double)jTable1.getValueAt(row, UNIDADES);
                departamento = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                /*Tenemos que tratar de una manera especial a los booleanos porque cuando se representan
                 en la tabla parece que tienen asignado el valor false, aunque en realidad esto no es cierto,
                 son null, tendremos pues que darle un valor en caso de que sea valor false*/
                eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO); //CASO ESPECIAL
                if(eliminado == null){
                    //System.out.println("Eliminado tiene que ser false");
                    eliminado = new Boolean(false);
                    m.setValueAt(eliminado, row, ELIMINADO);//TEST
                }
                try{
                    /*Todas las variables String deben ser introducidas dentro del try-catch, las demas no*/
                    ref = ((String)jTable1.getValueAt(row, REF)).trim();
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    
                    if(departamento == null){
                        //Estamos tratando una insercion de producto general
                        m.setValueAt(new Integer(-1), row, DEPARTAMENTO);
                        System.out.println("El departamento es null");
                        //public int insertar(String ref, String nombre, String descripcion, double precio, double unidades, boolean eliminado)
                        if((new MantenimientoDB.Productos().insertar(ref, nombre, descripcion, precio.doubleValue(), unidades.doubleValue(), eliminado.booleanValue()))==1)
                            out.setText("Insercion de producto general realizada con exito");
                        else
                            out.setText("Insercion de producto general erronea");
                    }else{
                        //Estamos tratando una insercion de producto regional
                        //public int insertar(String ref, String nombre, String descripcion, double precio, double unidades, boolean eliminado, int zona)
                        if((new MantenimientoDB.Productos().insertar(ref, nombre, descripcion, precio.doubleValue(), unidades.doubleValue(), eliminado.booleanValue(), departamento.intValue()))==1)
                            out.setText("Insercion de producto regional realizada con exito");
                        else
                            out.setText("Insercion de producto regional erronea");
                    }
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
                
                try{
                    String oldRef, newRef, nombre, descripcion;
                    Double precio, unidades;
                    Boolean eliminado;
                    Integer zona;
                
                    oldRef = ((String)jTable1.getValueAt(row, REF)).trim();
                    nombre = ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    descripcion = ((String)jTable1.getValueAt(row, DESCRIPCION)).trim();
                    precio = (Double)jTable1.getValueAt(row, PRECIO);
                    unidades = (Double)jTable1.getValueAt(row, UNIDADES);
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    //esRegional = (Boolean)jTable1.getValueAt(row, ES_REGIONAL);
                    
                    //zona = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                    
                    newRef = nref.getText();
                    if(!newRef.equals("")){
                        //public int modificar(String antiguaRef, String nuevaRef, String nombre, String descripcion, double precio, double unidades, boolean eliminado)
                        if((new MantenimientoDB.Productos().modificar(oldRef, newRef, nombre, descripcion, precio.doubleValue(), unidades.doubleValue(), eliminado.booleanValue()))==1){
                            if(!oldRef.equals(newRef))
                                m.setValueAt(newRef, row, REF);
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
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new Productos().show();
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
