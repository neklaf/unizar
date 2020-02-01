/*
 * Pedidos.java
 *
 * Created on 13 de marzo de 2005, 13:57
 */

package Interfaz;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import PuntoDeVenta.PVDetallePedidos;

/**
 *
 * @author  hadden
 */
public class Pedidos extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    DateFormat formatter;
    DefaultTableModel m;
    /*Cuando eliminamos alguna columna tambien tiene que ser eliminada de aqui*/
    String cols[] = new String [] {"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "fechaEnvio", "vehiculo", "vendedor", 
                                    "zona", "comprador"};
                                    
    private final int COLS = 9;
    /*Columnas*/
    private final int ID_PEDIDO = 0;
    private final int FECHA_REALIZACION = 1;
    private final int ESTADO = 2;
    private final int PRECIO_TOTAL = 3;
    private final int FECHA_ENVIO = 4;
    private final int VEHICULO = 5;
    private final int VENDEDOR = 6;
    private final int ZONA = 7;
    private final int COMPRADOR= 8;
    
    Vector vCol;
          
    /*Almacena los detalles de cada uno de los pedidos*/
    static Hashtable detalles;
    
    //Ventana interna
    Detalles vDetalles;
    
    /** Creates new form Pedidos */
    public Pedidos() {
        /*Para escribir la fecha adecuadamente*/
        formatter = new SimpleDateFormat("dd/MM/yy");
        
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        m = (DefaultTableModel)jTable1.getModel();
        
        detalles = new Hashtable();
              
        ocultar(); //Ocultamos lo que no es necesario en la sede central
        
        /*Configuracion de la ventana*/
        this.setSize(900,450);
        //this.setVisible(true);
        this.setTitle("Ventana de Pedidos");
        this.setResizable(false);
        
    }
    
    private void ocultar(){
        insertar.setVisible(false);
        eliminar.setVisible(false);
        modificar.setVisible(false);
        /*suma.setEnabled(false);
        menos.setEnabled(false);*/
        nid.setVisible(false);
        jLabel1.setVisible(false);
    }
    
    private void deshabilitar(){
        /*Hemos desactivado todos los botones que no nos eran necesarios*/
        insertar.setEnabled(false);
        eliminar.setEnabled(false);
        modificar.setEnabled(false);
        /*suma.setEnabled(false);
        menos.setEnabled(false);*/
        nid.setEnabled(false);
        jLabel1.setEnabled(false);
    }
    
    public void visualiza(String cif){
        Vector v;
        
        if(cif!=null){
            v = (Vector)new MantenimientoDB.Pedidos().buscar(-1, cif);
    
            if(v!=null){
                db2table(v);
                this.setVisible(true);
            }else
                System.out.println("Se ha producido un error en la busqueda");
        }else
            System.out.println("Error en el cif del cliente");
    }
    
    private void db2table(Vector v){
        
        if (v.size()!=0){
            PuntoDeVenta.PVPedidos pvrow;
            Vector rows = new Vector();
            
            int i = 0;
            String id_pedido;
            int estado;
            
            while (i < v.size()){
                pvrow = (PuntoDeVenta.PVPedidos)v.elementAt(i);
                Vector vfields = new Vector();
                //{"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "fechaEnvio", "vehiculo", "vendedor", "zona", "comprador"};
                id_pedido = pvrow.getId_pedido();
                vfields.addElement(id_pedido);
                
                vfields.addElement(formatter.format((Date)pvrow.getFechaRealizacion())); //clase Date
                
                estado = pvrow.getEstado();
                vfields.addElement(estado2String(estado)); //Toma: ((estado==0) ? "pedido" : "recibido")
                
                vfields.addElement(new Double(pvrow.getPrecioTotal()));
                
                vfields.addElement(formatter.format((Date)pvrow.getFechaEnvio())); //clase Date
                vfields.addElement(pvrow.getVehiculo().getMatricula()); //TODO TEST
                vfields.addElement(pvrow.getVendedor().getNif()); //TODO TEST
                vfields.addElement(new Integer(pvrow.getDepartamentoVendedor().getZona())); //TODO TEST 
                vfields.addElement(pvrow.getComprador().getCif()); //TODO TEST
                /*Hemos terminado de recibir toda la informacion necesaria para la tabla principal*/
                ArrayList n = copiaArrayList(pvrow.getAllPedidos());
                detalles.put(id_pedido, n);
                
                rows.addElement(vfields);
                i++;
            }
            m.setDataVector(rows, vCol); //Pasamos los resultados de la bd a la tabla
        }else
            out.setText("No hay Pedidos en la BD");
    }
    
    private ArrayList copiaArrayList(ArrayList a){
        ArrayList n = new ArrayList();
        Iterator it = a.iterator();
        while(it.hasNext()){
            n.add(it.next());
        }
        return n;
    }
    
    private String estado2String(int e){
        if(e == 0)
            return "pedido";
        else if(e == 1)
            return "recibido";
        else
            return "";
    }
    
    //Para transformar la representacion del estado en la tabla a la bd
    private int estado2DB(String et){
        int e;
        if(et.equals("pedido"))
            return 0;
        else if(et.equals("recibido"))
            return 1;
        else
            return -1;
    }
    
    public static Vector detalles2Vector(String id){
        return hash2Vector(detalles, id);
    }
    
    private static Vector hash2Vector(Hashtable h, String nif){
        Vector rows;
        if((nif != null) && (h != null)){
            rows = new Vector(); 
            int i = 0;
            ArrayList l = (ArrayList)h.get(nif);
            while(i<l.size()){
                Vector v = new Vector();
                v.addElement(l.get(i));
                rows.addElement(v);
                //System.out.println("tel: "+l.get(i));
                i++;
            }
            return rows;
        }else{
            System.err.println("Error en la entrada de los parametros");
            return null;
        }
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
        out = new javax.swing.JTextField();
        nid = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        detalle = new javax.swing.JButton();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        insertar.setText("Insertar");
        getContentPane().add(insertar);
        insertar.setBounds(30, 40, 80, 29);

        eliminar.setText("Eliminar");
        getContentPane().add(eliminar);
        eliminar.setBounds(30, 100, 80, 29);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(30, 160, 75, 29);

        modificar.setText("Modificar");
        getContentPane().add(modificar);
        modificar.setBounds(30, 230, 90, 29);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(380, 260, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(420, 260, 40, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(530, 260, 80, 29);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(680, 260, 80, 29);

        getContentPane().add(out);
        out.setBounds(190, 340, 450, 22);

        getContentPane().add(nid);
        nid.setBounds(30, 290, 110, 22);

        jLabel1.setText("Nuevo id:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 270, 60, 16);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(130, 340, 60, 16);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id_pedido", "fechaRealizacion ", "estado", "**precioTotal", "fechaEnvio", "vehiculo", "vendedor", "zona", "comprador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true, true, true, true, true
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
        jScrollPane1.setBounds(150, 40, 710, 200);

        detalle.setText("Detalle");
        detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalleActionPerformed(evt);
            }
        });

        getContentPane().add(detalle);
        detalle.setBounds(210, 260, 75, 29);

        pack();
    }//GEN-END:initComponents

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        // TODO TEST
        //nid.setText("");//Limpiamos el contenido de la nueva referencia
        
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

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

    private void sumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumaActionPerformed
        // TODO TEST
        Vector v = new Vector();
        v.addElement(null);
        m.addRow(v);
    }//GEN-LAST:event_sumaActionPerformed

    private void detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalleActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    String id = (String)jTable1.getValueAt(row, ID_PEDIDO);
                    System.out.println("id de pedido: "+id);
                    if(vDetalles == null)
                        vDetalles = new Detalles();
                    
                    vDetalles.visualiza(id);
                }catch(NullPointerException nu){
                    out.setText("Error en la introduccion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_detalleActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                
                Vector v;
                try{
                    Integer zona = (Integer)jTable1.getValueAt(row, ZONA);
                    v = (Vector)new MantenimientoDB.Pedidos().buscar(zona.intValue(), "-1");
                    
                    if(v!=null){
                        if(zona.intValue() == -1)
                            out.setText("Pedidos encontrados");
                        else
                            out.setText("Pedido encontrado");
                        db2table(v);
                    }else
                        out.setText("Error en la busqueda");
                    
                }catch(NullPointerException n){
                    out.setText("Error en la introduccion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_buscarActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new Pedidos().show();
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton buscar;
    private javax.swing.JButton detalle;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton insertar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton limpiar;
    private javax.swing.JButton menos;
    private javax.swing.JButton modificar;
    private javax.swing.JTextField nid;
    private javax.swing.JTextField out;
    private javax.swing.JButton suma;
    // End of variables declaration//GEN-END:variables
    
}

