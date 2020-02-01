/*
 * Vehiculos.java
 *
 * Created on 14 de marzo de 2005, 21:51
 */

package Interfaz;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.text.SimpleDateFormat;
import java.util.Date;

/*NOTA: CAMBIOS PARA LA VENTANA DE PV*/

/*Campos opcionales: pedidos(PVVehiculos), fechaProxITV, precioCompra*/

/**
 *
 * @author  hadden
 */
public class Vehiculos extends javax.swing.JFrame {
   
    /*Variables de la ventana*/
    SimpleDateFormat formatter;
    DefaultTableModel m; //Modelo de la tabla
    String cols[] = new String [] {"matricula", "modelo", "*fechaProxITV", "fechaCompra", "*precioCompra", "departamento", "eliminado"};
    Vector vCol; //Vector en el que almaceno las columnas de la tabla
    
    /*Columnas*/
    private final int COLS = 7;
    
    private final int MATRICULA = 0;
    private final int MODELO = 1;
    private final int FECHA_PROX_ITV = 2;
    private final int FECHA_COMPRA = 3;
    private final int PRECIO_COMPRA = 4;
    private final int DEPARTAMENTO = 5;
    private final int ELIMINADO = 6;
    
       
    /** Creates new form Vehiculos */
    public Vehiculos() {
        formatter = new SimpleDateFormat("dd/MM/yy");
        
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        oculta();
        
        m = (DefaultTableModel)jTable1.getModel(); //Aqui tendremos el modelo de nuestra tabla
        
        /*Configuracion de la ventana*/
        this.setSize(825,425);
        this.setTitle("Ventana de Vehiculos");
        this.setResizable(false);
    }
    
    private void oculta(){
        pedidos.setVisible(false);
    }
    
    public void visualiza(int zona){
        Vector v;
        //public Vector buscar(String matricula, int zona)
        v = (Vector)new MantenimientoDB.Vehiculos().buscar("-1", zona);
        
        if(v!=null){
            db2table(v);
            this.setVisible(true);
        }else
            System.out.println("Se ha producido un error en la busqueda");
    }
    
    private void db2table(Vector v){
        if(v.size()!=0){
            String clase;
            
            /*Definimos tantas variables como tipos pueda tener la fila que vamos a recibir*/
            Central.CentralVehiculos crow;
            PuntoDeVenta.PVVehiculos pvrow;
            Vector rows = new Vector();
            
            int i = 0;
            while (i < v.size()){
                clase = v.elementAt(i).getClass().getName();
                
                if(clase.endsWith("Central.CentralVehiculos")){
                    crow = (Central.CentralVehiculos)v.elementAt(i);
                    Vector vfields = new Vector();
                    //{"matricula", "modelo", "*fechaProxITV", "fechaCompra", "*precioCompra", "departamento", "eliminado"}
                    
                    vfields.addElement(crow.getMatricula());
                    vfields.addElement(crow.getModelo());
                    /*Probablemente habr’a que almacenar algo en la bd para saber si la fecha es la correcta o no*/
                    vfields.addElement(formatter.format(crow.getFechaProxITV()));
                    vfields.addElement(formatter.format(crow.getFechaCompra()));
                    vfields.addElement(new Double(crow.getPrecioCompra()));
                    
                    Central.CentralPuntoDistribucion pd = crow.getDepartamento();
                    vfields.addElement(new Integer(pd.getZona()));
                    
                    vfields.addElement(new Boolean(crow.getEliminado()));
                    
                    rows.addElement(vfields);
                }else if(clase.endsWith("PuntoDeVenta.PVVehiculos")){
                    pvrow = (PuntoDeVenta.PVVehiculos)v.elementAt(i);
                    Vector vfields = new Vector();
                    //{"matricula", "modelo", "*fechaProxITV", "*fechaCompra", "precioCompra", "eliminado"}
                    //NOTA: el campo pedidos debera proporcionarse a traves de un boton dentro de la ventana
                    //MODIFICAR LA JTABLE QUE TENEMOS EN LA VENTANA
                    
                    vfields.addElement(pvrow.getMatricula());
                    vfields.addElement(pvrow.getModelo());
                    vfields.addElement(formatter.format(pvrow.getFechaProxITV()));
                    vfields.addElement(formatter.format(pvrow.getFechaCompra()));
                    vfields.addElement(new Double(pvrow.getPrecioCompra()));
                    vfields.addElement(new Boolean(pvrow.getEliminado()));
                    
                    rows.addElement(vfields);
                }
                i++;
            }
            m.setDataVector(rows, vCol);
        }else
            out.setText("No hay Vehiculos en la bd");
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
        jLabel1 = new javax.swing.JLabel();
        nmatri = new javax.swing.JTextField();
        out = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        suma = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        limpiar = new javax.swing.JButton();
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
        insertar.setBounds(30, 20, 80, 29);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        getContentPane().add(eliminar);
        eliminar.setBounds(30, 80, 90, 29);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(30, 140, 75, 29);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        getContentPane().add(modificar);
        modificar.setBounds(30, 200, 87, 29);

        jLabel1.setText("Nueva matricula:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 240, 106, 16);

        getContentPane().add(nmatri);
        nmatri.setBounds(30, 260, 120, 22);

        getContentPane().add(out);
        out.setBounds(180, 360, 500, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(110, 360, 50, 16);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "matricula", "modelo", "*fechaProxITV", "*fechaCompra", "precioCompra", "departamento", "eliminado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(180, 30, 630, 260);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(270, 310, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(310, 310, 40, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(490, 310, 80, 29);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(650, 310, 80, 29);

        pedidos.setText("Pedidos");
        getContentPane().add(pedidos);
        pedidos.setBounds(40, 310, 77, 29);

        pack();
    }//GEN-END:initComponents

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
        nmatri.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        // TODO TEST
        nmatri.setText("");//Limpiamos el contenido del nuevo cif
        
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
                try{
                    String matricula;
                    matricula = ((String)jTable1.getValueAt(row, MATRICULA)).trim();
                    if(!matricula.equals("")){
                        //public int eliminar(String matricula)
                        if((new MantenimientoDB.Vehiculos().eliminar(matricula))==1){
                            m.setValueAt(new Boolean(true), row, ELIMINADO);
                            out.setText("Eliminacion realizada con exito");
                        }else
                            out.setText("Eliminacion erronea");
                    }else
                        out.setText("No es posible eliminar una matricula vacia");
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
                    //public Vector buscar(String matricula)
                    String matricula;
                    matricula = ((String)jTable1.getValueAt(row, MATRICULA)).trim();
                    if(!matricula.equals("")){
                        Vector v;
                        v = new MantenimientoDB.Vehiculos().buscar(matricula, -1);
                        if(v!=null){
                            if(matricula.equals("-1"))
                                out.setText("Vehiculos encontrados con exito");
                            else
                                out.setText("Vehiculo encontrado con exito");
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
                
                /*Campos opcionales: pedidos(PVVehiculos), fechaProxITV, precioCompra*/
                Date fechaProxITV;
                Double precioCompra;
                try{
                    try{
                        fechaProxITV = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_PROX_ITV)).trim());
                    }catch(java.text.ParseException p){
                        out.setText("Error parseando la fecha ITV");
                        fechaProxITV= new Date(); //Nueva fecha asignada
                        m.setValueAt(formatter.format(fechaProxITV), row, FECHA_PROX_ITV);
                    }
                }catch(NullPointerException nu){
                    fechaProxITV= new Date();
                    m.setValueAt(formatter.format(fechaProxITV), row, FECHA_PROX_ITV);
                    out.setText("Fecha de ITV por defecto");
                }
                
                precioCompra = (Double)jTable1.getValueAt(row, PRECIO_COMPRA);
                if(precioCompra == null){
                    precioCompra = new Double(-100.0);
                    m.setValueAt(precioCompra, row, PRECIO_COMPRA);
                    out.setText("Precion de compra por defecto");
                }
                
                try{
                    String matricula, modelo;
                    Boolean eliminado;
                    Date fechaCompra;
                    Integer departamento;
                
                    matricula = ((String)jTable1.getValueAt(row, MATRICULA)).trim();
                    modelo = ((String)jTable1.getValueAt(row, MODELO)).trim();
                    departamento = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    if(eliminado==null){ //Si no se toca eliminado suponemos que es false por logica
                        eliminado = new Boolean(false);
                        m.setValueAt(eliminado, row, ELIMINADO);
                    }
                    try{
                        fechaCompra = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_COMPRA)).trim());
                        if(departamento!=null){
                            //public int insertar(String matricula, String modelo, Date fechaProxITV, Date fechaCompra, double precioCompra, boolean eliminado, int zona)
                            if((new MantenimientoDB.Vehiculos().insertar(matricula, modelo, fechaProxITV, fechaCompra, precioCompra.doubleValue(), eliminado.booleanValue(), departamento.intValue()))==1)
                                out.setText("Insercion realizada con exito");
                            else
                                out.setText("Insercion erronea");
                        }else
                            out.setText("Departamento incorrecto");
                    }catch(java.text.ParseException p){
                        out.setText("Error parseando la fecha de compra");
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
                    String oldMatricula, newMatricula, modelo;
                    Double precioCompra;
                    Boolean eliminado;
                    Date fechaProxITV, fechaCompra;
                    Integer departamento;
                
                    oldMatricula = ((String)jTable1.getValueAt(row, MATRICULA)).trim();
                    modelo = ((String)jTable1.getValueAt(row, MODELO)).trim();
                    precioCompra = (Double)jTable1.getValueAt(row, PRECIO_COMPRA);
                    eliminado = (Boolean)jTable1.getValueAt(row, ELIMINADO);
                    departamento = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                    try{
                        fechaProxITV = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_PROX_ITV)).trim());
                        fechaCompra = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_COMPRA)).trim());
                        newMatricula = nmatri.getText();
                        if(!newMatricula.equals("")){
                            //public int modificar(String antiguaMatricula, String nuevaMatricula, String modelo, Date fechaProxITV, Date fechaCompra, double precioCompra, boolean eliminado, int zona)
                            if((new MantenimientoDB.Vehiculos().modificar(oldMatricula, newMatricula, modelo, fechaProxITV, fechaCompra, precioCompra.doubleValue(), eliminado.booleanValue(), departamento.intValue()))==1){
                                if(!oldMatricula.equals(newMatricula))
                                    m.setValueAt(newMatricula, row, MATRICULA);
                                out.setText("Modificacion realizada con exito");
                                nmatri.setText("");                                
                            }else
                                out.setText("Error en la modificacion");
                        }else
                            out.setText("La nueva matricula no puede ser vacia");
                    }catch(java.text.ParseException p){
                        out.setText("Error parseando la fecha");
                    }
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
        new Vehiculos().show();
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
    private javax.swing.JTextField nmatri;
    private javax.swing.JTextField out;
    private javax.swing.JButton pedidos;
    private javax.swing.JButton suma;
    // End of variables declaration//GEN-END:variables
    
}
