/*
 * fecha.java
 *
 * Created on 10 de marzo de 2005, 18:10
 */

package Interfaz;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  hadden
 */
public class fecha extends javax.swing.JFrame {
    
    //Formateador de la fecha
    DateFormat formatter;
    int fila, col;
    DefaultTableModel mod;
    
    
    /** Creates new form fecha */
    public fecha() {
        
        formatter = new SimpleDateFormat("dd/MM/yy"); //dia/mes/a�o
        
        initComponents();
        
        //date = new JFormattedTextField(formatter);
        
        /*Configuracion de la ventana*/
        setSize(300,300);
        setResizable(false);
        setTitle("Edicion de Fecha de AltaContrato");
    }
    
    public void visualiza(int f, int c, DefaultTableModel m){
        fila = f;
        col = c;
        //date.setText(od);
        mod = m;
        setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        aceptar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        dia = new javax.swing.JTextField();
        mes = new javax.swing.JTextField();
        anio = new javax.swing.JTextField();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jLabel1.setText("A\u00f1o:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 130, 40, 20);

        jLabel2.setText("Mes:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 80, 40, 16);

        jLabel3.setText("D\u00eda:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(40, 30, 45, 20);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(40, 220, 80, 29);

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        getContentPane().add(cancelar);
        cancelar.setBounds(170, 220, 90, 29);

        getContentPane().add(dia);
        dia.setBounds(110, 30, 80, 22);

        getContentPane().add(mes);
        mes.setBounds(110, 80, 80, 22);

        getContentPane().add(anio);
        anio.setBounds(110, 130, 80, 22);

        pack();
    }//GEN-END:initComponents

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
        dia.setText("");
        mes.setText("");
        anio.setText("");
        setVisible(false);
    }//GEN-LAST:event_cancelarActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        //Date d;
        /*try{
            date.commitEdit();
            d = (Date)date.getValue();
            System.out.println("nueva fecha "+d.toString());
        }catch(java.text.ParseException p){
            System.err.println("Error en el formato de la fecha");
        }*/
        String day, month, year, d;
        
        day = dia.getText();
        month = mes.getText();
        year = anio.getText();
        
        
        if(((!day.equals("")))&&(!month.equals(""))&&(!year.equals(""))){
            d=new String(day+"/"+month+"/"+year);
            mod.setValueAt(d, fila, col);
            //System.out.println("Fecha nueva: "+d);
            dia.setText("");
            mes.setText("");
            anio.setText("");
            setVisible(false);
        }else
            System.err.println("Error al introducir la fecha");
        
    }//GEN-LAST:event_aceptarActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new fecha().show();
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JTextField anio;
    private javax.swing.JButton cancelar;
    private javax.swing.JTextField dia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField mes;
    // End of variables declaration//GEN-END:variables
    
}
