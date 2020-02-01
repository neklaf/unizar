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
import PuntoDeVenta.PVDetallePedidosAProveedor;


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
    String cols[] = new String [] {"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "*zonaComprador"};
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
        
        //ocultar();
        
        m = (DefaultTableModel)jTable1.getModel();
        
        /*Configuracion de la ventana*/
        this.setSize(800,500);
        //this.setVisible(true);
        this.setTitle("Ventana de Pedidos a Proveedores");
        this.setResizable(false);
    }
    
    public void visualiza(){
        Vector v;
        
        //public Vector buscar(String id_pedido)
        v = (Vector)new MantenimientoDB.PedidosAProveedor().buscar("-1");
    
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
            PuntoDeVenta.PVDetallePedidosAProveedor dpp;
            if(l.size()==0)
                System.out.println("El tama–o de la lista de detalles es 0");
            else
                System.out.println("El tama–o de la lista de detalles es "+l.size());
            while(i<l.size()){
                Vector v = new Vector();
                //v.addElement(l.get(i));
                dpp = (PuntoDeVenta.PVDetallePedidosAProveedor)l.get(i);
                v.addElement(dpp.getProducto().getRef_prod());
                v.addElement(new Integer(dpp.getUnidades()));
                v.addElement(new Double(dpp.getPrecioCompra()));
                
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
    
    /*GESTION DEL HASHTABLE*/
    /*Elimina el pedido del hashtable*/
    public static void eliminaPedido(String id){
        detalles.remove(id);
    }
    
    /*Recibe los detalles del pedido*/
    public static ArrayList getDetallesPed(String id){
        return (ArrayList)detalles.get(id);
    }
    
    /*Modifica los productos de los detalles de un pedido*/
    public static void modificaProdDetPed(String id, PVDetallePedidosAProveedor det, PVDetallePedidosAProveedor det2){
        /*NOTA: Si me planteo hacer el modificar tengo que recibir como parametros los elementos de la tabla que son Strings
         creo por lo que tendre que cambiar el tipo de los parametros y tener en cuenta que son PVDetallePedidos lo que tenemos
         almacenado en el ArrayList*/
        if((id!=null)&&(det != null)&&(det2 != null)){
            ArrayList l = (ArrayList)detalles.get(id);
            if(l!=null){
                int i=0;
                String prod = det.getProducto().getRef_prod();
                String detProd = ((PVDetallePedidosAProveedor)l.get(0)).getProducto().getRef_prod();
                while((l.size()<i)&&(!prod.equals(detProd))){
                    detProd = ((PVDetallePedidosAProveedor)l.get(i)).getProducto().getRef_prod();
                    i++;
                }
            }else
                System.out.println("El pedido "+id+" no tiene detalles");
        }else
            System.out.println("Fallo al pasar los parametros");
    }
    
    /*Elimina producto de los detalles de un pedido*/
    public static void eliminaProdDetPed(String id, String prod){
        /*NOTA IMPORTANTE: tenemos que tener en cuenta que en la lista vamos a trabajar con String's y en la hashtable
         trabajaremos con objetos de tipo PVDetallePedidosAProveedor*/
        if((id != null)&&(prod !=null)){
            ArrayList l;
            l = (ArrayList)detalles.get(id);
            if(l!=null){
                int i = 0;
                String detProd = ((PVDetallePedidosAProveedor)l.get(0)).getProducto().getRef_prod();
                while((i<l.size())&&(!prod.equals(detProd))){
                    i++;
                    if(i<l.size())
                        detProd = ((PVDetallePedidosAProveedor)l.get(i)).getProducto().getRef_prod();
                }
                if(i<l.size())
                    l.remove(i);
                else
                    System.out.println("Producto no encontrado en los detalles");
            }else
                System.out.println("El pedido "+id+" no tiene detalles");
        }else
            System.out.println("Fallo al pasar los parametros");
    }
    
    public static void insertaDetalle(String id, String prod, Integer uni, Double precio, Integer zo){
        /*NOTA: Para crear un PVDetallePedidosAProveedor tenemos que tener la zona a la cual pertenece ese pedido, por lo
         que tendre que pasarselo a la ventana de detalles y despues esta lo tendra que pasar aqui!!*/
        //public PVDetallePedidosAProveedor(int unidades, double precioCompra, String ref_prod, int zona)
        if((id!=null)&&(prod!=null)&&(uni!=null)&&(precio!=null)&&(zo!=null)){
            PuntoDeVenta.PVDetallePedidosAProveedor det = new PuntoDeVenta.PVDetallePedidosAProveedor(uni.intValue(), precio.doubleValue(), prod, zo.intValue());
            ((ArrayList)detalles.get(id)).add(det);
        }else
            System.out.println("Error al insertar detalle, en los parametros pasados");
    }
    
    /*Calcula el campo denominado precioTotal*/
    public void calculaPrecioTotal(String id){
        if(id!=null){
            int row = jTable1.getSelectedRow();
            ArrayList l = (ArrayList)detalles.get(id);
            if(l!=null){
                int i=0;
                double pt=0.0;
                while(i<l.size()){
                    pt = pt + ((PuntoDeVenta.PVDetallePedidosAProveedor)l.get(i)).getPrecioCompra();
                    i++;
                }
                m.setValueAt(new Double(pt), row, PRECIO_TOTAL);
            }else
                m.setValueAt(new Double(0), row, PRECIO_TOTAL);
        }else
            out.setText("Error calculando el precio total");
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
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });

        getContentPane().add(insertar);
        insertar.setBounds(40, 60, 80, 29);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

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
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

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
                "id_pedido", "fechaRealizacion", "estado", "**precioTotal", "*zonaComprador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false
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

        detalle.setText("Detalles");
        detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalleActionPerformed(evt);
            }
        });

        getContentPane().add(detalle);
        detalle.setBounds(210, 330, 90, 29);

        pack();
    }//GEN-END:initComponents

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //{"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "zonaComprador"}
                String old_id, new_id, estado;
                Date fechaRealizacion;
                Integer zonaComprador;
                Double precioTotal;
                int est;
                
                precioTotal = (Double)jTable1.getValueAt(row, PRECIO_TOTAL);
                
                try{
                  old_id = ((String)jTable1.getValueAt(row, ID_PEDIDO)).trim();
                  ArrayList det = (ArrayList)detalles.get(old_id);
                  estado = ((String)jTable1.getValueAt(row, ESTADO)).trim();
                  est = estado2DB(estado);
                  zonaComprador = (Integer)jTable1.getValueAt(row, ZONA_COMPRADOR);
                  try{
                        fechaRealizacion = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_REALIZACION)).trim());
                        new_id = nid.getText();
                        if(!new_id.equals("")){
                            //public int modificar(String antigua_id_pedido, String nueva_id_pedido,Date fechaRealizacion,int estado, ArrayList detallePedidos)
                            if((new MantenimientoDB.PedidosAProveedor().modificar(old_id, new_id, fechaRealizacion, est, det))==1){
                                if(new_id != old_id){
                                    m.setValueAt(new_id, row, ID_PEDIDO);
                                    detalles.put(new_id, detalles.get(old_id));
                                    detalles.remove(old_id);
                                }    
                                out.setText("Pedido modificado con exito");
                                nid.setText("");
                            }else
                                out.setText("Error en la modificacion");
                        }else
                            out.setText("Nuevo id incorrecto");
                  }catch(java.text.ParseException p){
                      out.setText("Error en la recepcion de la fecha de realizacion");
                  }
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_modificarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                String id_pedido;
                try{
                    id_pedido = ((String)jTable1.getValueAt(row, ID_PEDIDO)).trim();
                    if(!id_pedido.equals("")){
                        //public int eliminar(String id_pedido)
                        if((new MantenimientoDB.PedidosAProveedor().eliminar(id_pedido))==1){
                            m.removeRow(row);
                            out.setText("Eliminacion realizada con exito");
                        }else
                            out.setText("Eliminacion erronea");
                    }else
                        out.setText("No se puede eliminar un id vacio");
                }catch(NullPointerException nu){
                    
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
                /*campos optativos: precioTotal*/
                /*Aunque en este caso se supone que tendria que tener tambien valor, pero no podemos contar con que lo tenga*/
                //{"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "zonaComprador"}
                Double precioTotal;
                precioTotal = (Double)jTable1.getValueAt(row, PRECIO_TOTAL);
                if(precioTotal == null){
                    precioTotal = new Double(-100.0);
                    m.setValueAt(precioTotal, row, PRECIO_TOTAL);
                    out.setText("Precio Total por defecto");
                }
                
                String id_pedido, estado;
                Date fechaRealizacion;
                //Integer zonaComprador;
                int est;
                
                try{
                    id_pedido = ((String)jTable1.getValueAt(row, ID_PEDIDO)).trim();
                    ArrayList det = (ArrayList)detalles.get(id_pedido);
                    estado = ((String)jTable1.getValueAt(row, ESTADO)).trim();
                    est = estado2DB(estado);
                    try{
                        fechaRealizacion = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_REALIZACION)).trim());
                        //zonaComprador = (Integer)jTable1.getValueAt(row, ZONA_COMPRADOR);
                        if(det!=null){
                            //public int insertar(String id_pedido,Date fechaRealizacion,int estado, ArrayList detallePedidos)
                            if((new MantenimientoDB.PedidosAProveedor().insertar(id_pedido, fechaRealizacion, est, det))==1)
                                out.setText("Insercion realizada con exito");
                            else
                                out.setText("Insercion erronea");
                        }else
                            out.setText("Los detalles son null");
                    }catch(java.text.ParseException p){
                       //fechaRealizacion = new Date();
                       //m.setValueAt(fechaRealizacion, row, FECHA_REALIZACION);
                       out.setText("Error parseando fechaRealizacion"); 
                    }
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_insertarActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        out.setText("");
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
                    String id = ((String)jTable1.getValueAt(row, ID_PEDIDO)).trim();
                    Integer zo = (Integer)jTable1.getValueAt(row, ZONA_COMPRADOR);
                    System.out.println("id de pedido: "+id);
                    System.out.println("La zona del pedido es: "+zo);
                    
                    /*Tenemos que tener en cuenta cuando estamos con un pedido nuevo y cuando no!!*/
                    if(zo!=null){  //Es lo unico recogido de la ventana que no esta verificado
                        if(vDetalles == null)
                            vDetalles = new DetallesPP();
                            
                        ArrayList l = (ArrayList)detalles.get(id);
                        if(l!=null)
                            vDetalles.visualiza(id, zo, false, this);
                        else{
                            detalles.put(id, new ArrayList());
                            vDetalles.visualiza(id, zo, true, this);
                            out.setText("Detalles de un pedido nuevo");
                        }
                    }else
                        out.setText("La zona comprador es nula");
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
                try{
                    String id = ((String)jTable1.getValueAt(row, ID_PEDIDO)).trim();
                    if(!id.equals("")){
                        Vector v;
                        v = (Vector)new MantenimientoDB.PedidosAProveedor().buscar(id);
                        if(id.equals("-1"))
                            out.setText("Pedidos a Proveedores encontrados");
                        else
                            out.setText("Pedido a Proveedor encontrado");
                        db2table(v);
                    }else
                        out.setText("No es posible buscar un identificador vacio");
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
