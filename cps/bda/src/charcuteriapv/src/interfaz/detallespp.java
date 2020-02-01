/*
 * DetallesPP.java
 *
 * Created on 14 de marzo de 2005, 21:43
 */

package Interfaz;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  hadden
 */
public class DetallesPP extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    String id; //Indentificador del pedido al proveedor
    boolean nuevo, modif; //Indica si el pedido al proveedor es nuevo
    Interfaz.PedidosAProveedor parent; //La ventana que llama a esta
    Integer zona; //Almacena la zona que ha realizado el pedido
    
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
        parent = null;
        modif = false;
        nuevo = false;
        
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
        
        initComponents();
        
        configurarCombos();
        
        //deshabilitar();
        
        m = (DefaultTableModel)jTable1.getModel();
        
        /*Configuracion de la ventana*/
        setSize(565,350);
        setTitle("Visor de detalles");
        setResizable(false);
    }
    
    private void configurarCombos(){
        productos.setEditable(false);
        proveedores.setEditable(false);
    }
    
    private void deshabilitar(){
        productos.setEnabled(false);
        proveedores.setEnabled(false);
    }
    
    public void visualiza(String i, Integer zo, boolean n, Interfaz.PedidosAProveedor p){
        Vector v, vprov, vprod;
        
        nuevo = n;
        parent = p;
        
        /*Parte de los JCombos
         NOTA: Es m‡s complicado que en la ventana Detalle*/
        vprov = new MantenimientoDB.Proveedores().buscar("-1");
        
        if(vprov!=null)
            proveedores2Combo(vprov);
        
        //System.out.println("Llegamos a la ventana detalles");
        if(i!=null){
            if(zo!=null){
                id = i;
                zona = zo;
                v = PedidosAProveedor.detalles2Vector(id);
                
                if(v != null){
                    m.setDataVector(v, vCol); //Reflejamos los cambios en la tabla
                    setVisible(true);
                }else
                    System.out.println("No hay detalles de pedido");
            }else
                System.out.println("No tenemos la zona del pedido");
        }else
            System.out.println("No tenemos id de pedido");
    }
    
    private void proveedores2Combo(Vector v){
        if(v.size()!=0){
            proveedores.removeAllItems(); //Borramos todos los posibles proveedores que hay en el combo
            int i=0;
            PuntoDeVenta.PVProveedores prov;
            while(i<v.size()){
                prov = (PuntoDeVenta.PVProveedores)v.elementAt(i);
                proveedores.addItem(prov.getCif());
                i++;
            }
        }else
            System.out.println("El numero de proveedores en el PV es 0");
    }
    
    private void productos2Combo(Vector v){
        //Si estamos dentro de este metodo se supone que la ventana ha sido creada
        //y tiene un JCombo inicializado ya!!
        if(v.size()!=0){
            productos.removeAllItems(); //Borramos los posibles productos que hay en el JCombo
            int i=0;
            PuntoDeVenta.PVProductosProveedores prod;
            while(i<v.size()){
                prod = (PuntoDeVenta.PVProductosProveedores)v.elementAt(i);
                productos.addItem(prod.getRef_prod());
                i++;
            }
        }else
            System.out.println("El numero de productos en el PV es 0");
        
    }
    
    /*Renueva el contenido del jcombo de productos*/
    private void updateProductos(String prov){
        //TODO TEST
        Vector v;
        if(prov!=null){
            //public Vector buscar(String ref_prod, String cif)
            v = new MantenimientoDB.ProductosProveedores().buscar("-1", prov);
            //Creo que el metodo buscar lo que devuelve es un vector vacio NO NULL!!!
            if(v.size()!=0)
                productos2Combo(v);
            else{
                System.out.println("El proveedor no tiene ningun producto");
                productos.removeAllItems();
            }
        }else
            System.out.println("El proveedor es nulo(updateProductos)");
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
        suma = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        suma_unidades = new javax.swing.JButton();
        menos_unidades = new javax.swing.JButton();

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
        aceptar.setBounds(450, 260, 80, 29);

        jLabel1.setText("Productos:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(420, 30, 80, 16);

        getContentPane().add(productos);
        productos.setBounds(400, 50, 150, 27);

        jLabel2.setText("Proveedores:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(410, 170, 90, 16);

        proveedores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proveedoresItemStateChanged(evt);
            }
        });

        getContentPane().add(proveedores);
        proveedores.setBounds(400, 190, 150, 27);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(60, 260, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(100, 260, 40, 29);

        suma_unidades.setText("+ unidades");
        suma_unidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suma_unidadesActionPerformed(evt);
            }
        });

        getContentPane().add(suma_unidades);
        suma_unidades.setBounds(190, 260, 100, 29);

        menos_unidades.setText("- unidades");
        menos_unidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos_unidadesActionPerformed(evt);
            }
        });

        getContentPane().add(menos_unidades);
        menos_unidades.setBounds(300, 260, 97, 29);

        pack();
    }//GEN-END:initComponents

    private void menosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosActionPerformed
        // TODO TEST
        /*Hemos hecho codigo redundante*/
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            try{
                int row = jTable1.getSelectedRow();
                if(row != -1){
                    String prod = ((String)jTable1.getValueAt(row, PRODUCTO)).trim();
                    ArrayList l = PedidosAProveedor.getDetallesPed(id);
                    if(l.size()>1){
                        PedidosAProveedor.eliminaProdDetPed(id, prod);
                        m.removeRow(row);
                        System.out.println("Producto de proveedore del detalle eliminado");
                        modif = true;
                    }else
                        System.out.println("Producto no se puede eliminar porque solamente hay uno!");
                    
                }else
                    System.out.println("No hay ninguna fila seleccionada");
            }catch(ArrayIndexOutOfBoundsException ai){
                System.out.println("Fila seleccionada no valida");
            }
        }else
            System.out.println("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_menosActionPerformed

    private void sumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumaActionPerformed
        // TODO TEST
        Vector v = new Vector();
        Vector row = new Vector();
        Vector v2;
        //Si funciona igual que para la tabla si no hay nada seleccionado devolver‡ un null
        String ref_prod = (String)productos.getSelectedItem();
        String prov = (String)proveedores.getSelectedItem();
        if(ref_prod != null){
            v2 = new MantenimientoDB.ProductosProveedores().buscar(ref_prod, prov);
            if(v2 != null){
                PuntoDeVenta.PVProductosProveedores p;
                p = (PuntoDeVenta.PVProductosProveedores)v2.get(0);
                
                Integer uni = new Integer(1);
                Double precio = new Double(p.getPrecio());
                
                row.addElement(ref_prod);
                //Los valores para las otras dos columnas
                row.addElement(uni);
                row.addElement(precio);
                
                //v.addElement(row);
                m.addRow(row);
                PedidosAProveedor.insertaDetalle(id, ref_prod, uni, precio, zona);
                modif = true;
            }
        }else
            System.out.println("No tenemos nada seleccionado en el JCombo");
    }//GEN-LAST:event_sumaActionPerformed

    private void suma_unidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suma_unidadesActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                /*Suponemos que no puede haber ningun problema por como son introducidos*/
                Integer u = (Integer)jTable1.getValueAt(row, UNIDADES);
                Double p = (Double)jTable1.getValueAt(row, PRECIO_COMPRA);
                
                double pu = p.doubleValue()/u.intValue();
                pu = pu + p.doubleValue();
                
                /*Cambiamos la tabla*/
                m.setValueAt(new Double(pu), row, PRECIO_COMPRA);
                m.setValueAt(new Integer(u.intValue()+1), row, UNIDADES);
                modif = true;
            }else
                System.out.println("Error en la fila seleccionada");
        }else
            System.out.println("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_suma_unidadesActionPerformed

    private void menos_unidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos_unidadesActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                Integer u = (Integer)jTable1.getValueAt(row, UNIDADES);
                if(u.intValue()==1){
                    System.out.println("No se puede decrementar mas las unidades!!");
                }else{
                    Double p = (Double)jTable1.getValueAt(row, PRECIO_COMPRA);
                    
                    double pu = p.doubleValue()/u.intValue();
                    pu = p.doubleValue() - pu;
                    
                    /*Cambiamos la tabla*/
                    m.setValueAt(new Double(pu), row, PRECIO_COMPRA);
                    m.setValueAt(new Integer(u.intValue()-1), row, UNIDADES);
                    modif = true;
                }
            }else
                System.out.println("Error en la fila seleccionada");
        }else
            System.out.println("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_menos_unidadesActionPerformed

    private void proveedoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proveedoresItemStateChanged
        // TODO TEST
        /*Este es el metodo que hara se ejecuta cuando seleccionamos un componente del jcombo*/
        /*Tenemos que recibir el valor del item seleccionado y llamar a updateProductos*/
        
        /*Es posible que salta una execepcion en tiempo de ejecucion porque el metodo getItem devuelve un Object*/
        
        /*NOTA: Puede que el if sobre, pero es para estar mas seguro*/
        if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED){
            updateProductos((String)evt.getItem());
        }
    }//GEN-LAST:event_proveedoresItemStateChanged

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO TEST
        //tendremos que calcular el precio total del pedido con los productos que hemos seleccionado
        setVisible(false);
        if((modif == true)&&(nuevo == false)){
            new MantenimientoDB.PedidosAProveedor().modificarDetallePedido(id, PedidosAProveedor.getDetallesPed(id));
            parent.calculaPrecioTotal(id);
            modif = false;
        }else if((modif == false)&&(nuevo == true))
            PedidosAProveedor.eliminaPedido(id);
        else if((modif == true)&&(nuevo == true)){
            parent.calculaPrecioTotal(id);
            modif = false;
        }
        //Si el pedido no es nuevo y no ha modificado nada no tenemos que hacer nada!
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
    private javax.swing.JButton menos;
    private javax.swing.JButton menos_unidades;
    private javax.swing.JComboBox productos;
    private javax.swing.JComboBox proveedores;
    private javax.swing.JButton suma;
    private javax.swing.JButton suma_unidades;
    // End of variables declaration//GEN-END:variables
    
}
