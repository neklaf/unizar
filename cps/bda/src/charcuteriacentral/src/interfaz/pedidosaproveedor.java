/*
 * PedidosAProveedor.java
 *
 * Created on 14 de marzo de 2005, 21:17
 */

package Interfaz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Iterator;

/*NOTA: La dificultad de la implementacion de esta ventana estriba en el Punto de venta donde tendremos que gestionar los detalles de 
 pedido al proveedor, al igual que en la clase pedido*/
/*HAY QUE TENER MUY EN CUENTA QUE A LA HORA DE INSERTAR TANTO UN PEDIDO A PROVEEDOR COMO UN PEDIDO TENEMOS QUE SELECCIONAR TANTO UN 
 PROVEEDOR COMO UNOS PRODUCTOS QUE EXISTAN!!*/

/*La ventana en la que representaremos los detalles de ambos tipos de pedidos tendra que tener una manera de seleccionar un producto
 que exita de forma segura*/

/*campos optativos: precioTotal*/

/**
 *
 * @author  hadden
 */
public class PedidosAProveedor extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    SimpleDateFormat formatter;
    DefaultTableModel m; //Modelo de la tabla
    String cols[] = new String [] {"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "zonaComprador"};
    Vector vCol; //Vector en el que almaceno las columnas de la tabla
    
    /*Columnas*/
    private final int COLS = 5;
    
    private final int ID_PEDIDO = 0;
    private final int FECHA_REALIZACION = 1;
    private final int ESTADO = 2;
    private final int PRECIO_TOTAL = 3;
    private final int ZONA_COMPRADOR = 4;
   
    /*Almacena los detalles de cada uno de los pedidos*/
    static Hashtable detalles;
    
    //Ventana interna
    DetallesPP vDetalles;
    
    /** Creates new form PedidosAProveedor */
    public PedidosAProveedor() {
        formatter = new SimpleDateFormat("dd/MM/yy");
        vCol = new Vector();
        
        detalles = new Hashtable();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        ocultar();
        
        m = (DefaultTableModel)jTable1.getModel();
        
        /*Configuracion de la ventana*/
        this.setSize(800,500);
        //this.setVisible(true);
        this.setTitle("Ventana de Pedidos a Proveedores");
        this.setResizable(false);
    }
    
    public void visualiza(){
        Vector v;
        
        //public Vector buscar(int zona)
        v = (Vector)new MantenimientoDB.PedidosAProveedor().buscar(-1);
    
        if(v!=null){
            db2table(v);
            this.setVisible(true);
        }else
            System.err.println("Se ha producido un error en la busqueda");
    }
    
    private void db2table(Vector v){
        if (v.size()!=0){
            String clase;
            
            PuntoDeVenta.PVPedidosAProveedor pvrow;
            Vector rows = new Vector();
                       
            int i = 0;
            String id_pedido;
                        
            //Recorremos todas filas que nos haya proporcionado la base de datos
            while (i < v.size()){
                pvrow = (PuntoDeVenta.PVPedidosAProveedor)v.elementAt(i);
                Vector vfields = new Vector();
                
                //{"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "zonaComprador"}
                id_pedido = pvrow.getId_pedido();
                vfields.addElement(id_pedido);
                vfields.addElement(formatter.format(pvrow.getFechaRealizacion())); //TODO TEST
                vfields.addElement(estado2String(pvrow.getEstado()));
                vfields.addElement(new Double(pvrow.getPrecioTotal()));
                vfields.addElement(new Integer(pvrow.getDepartamentoComprador().getZona()));
                
                ArrayList n = copiaArrayList(pvrow.getAllPedidos());
                detalles.put(id_pedido, n);
                rows.addElement(vfields);
                i++;
            }
            m.setDataVector(rows, vCol);
        }else
            out.setText("No hay Pedidos a Proveedores en la BD");
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
    
    private void ocultar(){
        insertar.setVisible(false);
        eliminar.setVisible(false);
        modificar.setVisible(false);
        nid.setVisible(false);
        jLabel1.setVisible(false);
    }
    
    private void deshabilitar(){
        /*Hemos desactivado todos los botones que no nos eran necesarios*/
        insertar.setEnabled(false);
        eliminar.setEnabled(false);
        modificar.setEnabled(false);
        nid.setEnabled(false);
        jLabel1.setEnabled(false);
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
        jLabel1 = new javax.swing.JLabel();
        nid = new javax.swing.JTextField();
        out = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        suma = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        limpiar = new javax.swing.JButton();
        aceptar = new javax.swing.JButton();
        detalle = new javax.swing.JButton();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        insertar.setText("Insertar");
        getContentPane().add(insertar);
        insertar.setBounds(40, 60, 80, 29);

        eliminar.setText("Eliminar");
        getContentPane().add(eliminar);
        eliminar.setBounds(40, 120, 80, 29);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(40, 180, 75, 29);

        modificar.setText("Modificar");
        getContentPane().add(modificar);
        modificar.setBounds(40, 230, 87, 29);

        jLabel1.setText("Nuevo id:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 280, 70, 16);

        getContentPane().add(nid);
        nid.setBounds(28, 300, 100, 22);

        getContentPane().add(out);
        out.setBounds(210, 380, 500, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(120, 380, 60, 16);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "id_pedido", "fechaRealizacion", "estado", "**precioTotal", "zonaComprador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(180, 50, 530, 270);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(330, 330, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(370, 330, 40, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(440, 330, 80, 29);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(610, 330, 80, 29);

        detalle.setText("Detalle");
        detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalleActionPerformed(evt);
            }
        });

        getContentPane().add(detalle);
        detalle.setBounds(210, 330, 75, 29);

        pack();
    }//GEN-END:initComponents

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
        nid.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

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
                        vDetalles = new DetallesPP();
                    
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
                    Integer zona = (Integer)jTable1.getValueAt(row, ZONA_COMPRADOR);
                    v = (Vector)new MantenimientoDB.Pedidos().buscar(zona.intValue(), "-1");
                    
                    if(v!=null){
                        if(zona.intValue() == -1)
                            out.setText("Pedidos a Proveedores encontrados");
                        else
                            out.setText("Pedido a Proveedor encontrado");
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
        new PedidosAProveedor().show();
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
