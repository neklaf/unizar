/*
 * DetallesPP.java
 *
 * Created on 14 de marzo de 2005, 21:43
 */

package Interfaz;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 *
 * @author  hadden
 */
public class DetallesPP extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    String id;
    protected DefaultTableModel m;
    String cols[] = {"producto", "unidades", "precioCompra"};
    Vector vCol;
    
    //Numero de columnas
    private final int COLS = 3;
    //Identificadores de columnas
    private final int PRODUCTO = 0;
    private final int UNIDADES = 1;
    private final int PRECIO_COMPRA = 2;
    
    /** Creates new form DetallesPP */
    public DetallesPP() {
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        deshabilitar();
        
        /*Configuracion de la ventana*/
        setSize(400,350);
        setTitle("Visor de detalles");
        setResizable(false);
    }
    
    private void deshabilitar(){
        productos.setEnabled(false);
        proveedores.setEnabled(false);
    }
    
    public void visualiza(String i){
        Vector v;
        //System.out.println("Llegamos a la ventana detalles");
        if(i!=null){
            id = i;
            v = Pedidos.detalles2Vector(id);
            if(v != null){
                m.setDataVector(v, vCol); //Reflejamos los cambios en la tabla
                setVisible(true);
            }else
                System.out.println("No hay detalles de pedido");
        }else
            System.out.println("No tenemos id de pedido");
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
        jLabel1 = new javax.swing.JLabel();
        productos = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        proveedores = new javax.swing.JComboBox();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "producto", "unidades", "precioCompra"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(50, 30, 290, 220);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(160, 260, 76, 29);

        jLabel1.setText("Productos:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(420, 30, 80, 16);

        getContentPane().add(productos);
        productos.setBounds(400, 50, 150, 27);

        jLabel2.setText("Proveedores:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(410, 170, 90, 16);

        getContentPane().add(proveedores);
        proveedores.setBounds(400, 190, 150, 27);

        pack();
    }//GEN-END:initComponents

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO add your handling code here:
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new DetallesPP().show();
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox productos;
    private javax.swing.JComboBox proveedores;
    // End of variables declaration//GEN-END:variables
    
}
