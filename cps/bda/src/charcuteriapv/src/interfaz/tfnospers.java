/*
 * tfnPers.java
 *
 * Created on 8 de marzo de 2005, 17:26
 */

/*NOTA IMPORTANTE PARA QUE LOS CAMBIOS TENGAN EFECTO EN LA BASE DE DATOS TENEMOS QUE PRESIONAR ACEPTAR*/

package Interfaz;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  hadden
 */
public class tfnosPers extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    String nif; //Sera el nif del empleado al que pertenecen esos telefonos
    boolean modif, nuevo;
    
    protected DefaultTableModel m;
    String cols[] = {"Personales"};
    Vector vCol;
    
    /** Creates new form tfnPers */
    public tfnosPers() {
        modif = false;
        nuevo = false;
        vCol = new Vector();
        
        vCol.addElement(cols[0]);
        
        initComponents();
        
        m = (DefaultTableModel)jTable1.getModel();
        
        /*Configuracion de la ventana*/
        this.setSize(400,350);
        setResizable(false);
        setTitle("Telefonos Personales");
    }
    
    private void setNif(String n){
        nif = n;
        //nif = new String(n);
    }
    
    /*El modelo es un VECTOR DE VECTORES cada fila esta representada por un vector!!!!*/
    public void visualiza(String ni, boolean n){
        //TESTED
        Vector v;
        if(ni != null){
            //System.out.println("El nif en tfno: "+ni);
            nuevo = n; //Indica si el empleado es nuevo o no
            setNif(ni);
            v = Empleados.tPers2Vector(ni);
            if(v != null){
                //rows = new Vector();
                //rows.addElement(v);
                m.setDataVector(v, vCol); //Reflejamos los cambios en la tabla
                setVisible(true);
            }else
                System.err.println("No hay telefonos");
        }else
            System.err.println("No tengo nif");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        aceptar = new javax.swing.JButton();
        insertar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        ntel = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        suma = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        out = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Personales"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(200, 50, 160, 160);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(270, 240, 90, 29);

        insertar.setText("Insertar");
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });

        getContentPane().add(insertar);
        insertar.setBounds(20, 30, 80, 29);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        getContentPane().add(eliminar);
        eliminar.setBounds(20, 100, 90, 29);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        getContentPane().add(modificar);
        modificar.setBounds(20, 160, 100, 29);

        getContentPane().add(ntel);
        ntel.setBounds(20, 210, 120, 22);

        jLabel1.setText("Nuevo Telefono: ");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 190, 110, 16);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(160, 240, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(200, 240, 40, 29);

        getContentPane().add(out);
        out.setBounds(120, 290, 260, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 290, 50, 16);

        pack();
    }//GEN-END:initComponents

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO add your handling code here:
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                String tel, tel2;
                try{
                    tel = ((String)jTable1.getValueAt(row, 0)).trim();
                    tel2 = ntel.getText();
                    if(!tel2.equals("")){
                        ntel.setText("");
                        //System.out.println("tfno old: "+tel);
                        //System.out.println("tfno new: "+tel2);
                        Empleados.modificaTfnoPers(nif, tel, tel2);
                        m.setValueAt(tel2, row, 0); //Modificamos el contenido de la celda modificada
                        out.setText("Telefono modificado");
                        modif = true;
                    }else
                        out.setText("Error telefono vacio");
                        //System.err.println("Error telefono vacio");
                }catch(NullPointerException nu){
                    out.setText("Fila sin ningun telefono");
                    //System.err.println("Fila sin ningun telefono");
                }
            }else
                out.setText("Error en la fila seleccionada");
                //System.err.println("Error en la fila seleccionada");
        }else
            System.out.println("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_modificarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO add your handling code here:
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                String tel;
                try{
                    tel = ((String)jTable1.getValueAt(row, 0)).trim();
                    m.removeRow(row);
                    System.out.println("del tel: "+tel);
                    Empleados.eliminaTfnoPers(nif, tel);
                    out.setText("Telefono eliminado");
                    modif = true;
                }catch(NullPointerException nu){
                    out.setText("Fila sin ningun telefono");
                    //System.err.println("Fila sin ningun telefono");
                }
            }else
                out.setText("Error en la fila seleccionada");
                //System.err.println("Error en la fila seleccionada");
        }
    }//GEN-LAST:event_eliminarActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
        // TODO add your handling code here:
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                String tel;
                //System.out.println("NIF: "+nif);
                try{
                    tel = ((String)jTable1.getValueAt(row, 0)).trim();
                    //System.out.println("tel: "+tel);
                    Empleados.insertaTfnoPers(nif, tel);
                    out.setText("Telefono personal insertado");
                    modif = true;                    
                }catch(NullPointerException nu){
                    //System.err.println("Fila sin ningun telefono");
                    out.setText("Fila sin ningun telefono");
                }
                

            }else
                out.setText("Error en la fila seleccionada");
                //System.err.println("Error en la fila seleccionada");
        }
    }//GEN-LAST:event_insertarActionPerformed

    private void sumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumaActionPerformed
        // TODO add your handling code here:
        Vector v = new Vector();
        v.addElement(null);
        m.addRow(v);
    }//GEN-LAST:event_sumaActionPerformed
   
    private void menosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosActionPerformed
        // TODO add your handling code here:
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            try{
                m.removeRow(jTable1.getSelectedRow());
            }catch(ArrayIndexOutOfBoundsException ai){
                System.out.println("No hay fila seleccionada");
            }
        }else
            out.setText("Fila no seleccionada");
            //System.err.println("fila no seleccionada");
    }//GEN-LAST:event_menosActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        //TEST
        //ArrayList l = Empleados.getTfnoPers(nif);
        //int i=0;
        setVisible(false);
        out.setText("");
        if((modif == true)&&(nuevo == false)){ //Si el empleado no es nuevo no modificamos la bd
            /*while(i<l.size()){
                System.out.println("tele: "+l.get(i));
                i++;
            }*/
            //Actualizamos la bd
            new MantenimientoDB.Empleados().modificarTfno(nif, Empleados.getTfnoPers(nif),1);
            //System.out.println("Telefonos modificados");
            modif = false;
        }else if((modif == false)&&(nuevo == true)){
            Empleados.eliminaEmpTfnoPers(nif); //Lo eliminamos de la hash
        }else if((modif == true)&&(nuevo == true))
            modif = false;
        //Si el empleado no es nuevo y no ha modificado los telefonos NO tenemos que hacer nada!!
    }//GEN-LAST:event_aceptarActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new tfnPers().show();
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton insertar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton menos;
    private javax.swing.JButton modificar;
    private javax.swing.JTextField ntel;
    private javax.swing.JTextField out;
    private javax.swing.JButton suma;
    // End of variables declaration//GEN-END:variables
    
}
