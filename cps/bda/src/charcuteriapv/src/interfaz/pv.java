/*
 * NodoCentral.java
 *
 * Created on 18 de febrero de 2005, 0:13
 */

/*TODO
 * Buscar informacion para saber como puedo cambiar el codigo que esta en azul:
 * esta tirado solamente tenemos que utilizar un editor para los ficheros .java que no
 * sea el del netbeans!!
 */


package Interfaz;
//import Interfaz.Empleados;


/**
 *
 * @author  hadden
 */
public class PV extends javax.swing.JFrame {
    
    //Declaracion de las ventanas
    public Empleados vEmpleados;
    public PuntoVenta vPuntoVenta;
    public Clientes vClientes;
    public ProductosProveedores vProductosProveedores;
    public Pedidos vPedidos;
    public Proveedores vProveedores;
    public Productos vProductos;
    public Vehiculos vVehiculos;
    public PedidosAProveedor vPedidosAProveedor;
    //fin de declaracion de las ventanas
    
    /** Creates new form PV */
    public PV() {
        vEmpleados = new Empleados();
        vPuntoVenta = new PuntoVenta();
        vClientes = new Clientes();
        vProductosProveedores = new ProductosProveedores();
        vPedidos = new Pedidos();
        vProveedores = new Proveedores();
        vProductos = new Productos();
        vVehiculos = new Vehiculos();
        vPedidosAProveedor = new PedidosAProveedor();
        
        setUpWindows();
        
        initComponents();
        
        /*Configuracion del JTabbedPane*/
        setupJTabbedPane();
        
        /*Configuracion de la ventana*/
        this.setSize(600,450);
        this.setVisible(true);
        this.setTitle("PUNTO DE VENTA");
    }
    
    private void setUpWindows(){
        /*Las del punto de venta*/
        vClientes.setPedidosCli(vPedidos);
        vEmpleados.setPedidosEmp(vPedidos);
        vProveedores.setProdProv(vProductosProveedores);
        /*TODO:
         vVehiculos.setPedidosVeh(vPedidos);
         */
        
        /*Las de la sede central*/
        /*vProveedores.setProdProv(vProductosProveedores);
        vPuntoVenta.setEmpPV(vEmpleados);
        vPuntoVenta.setVehiculosPV(vVehiculos);
        vPuntoVenta.setCliPV(vClientes);*/
    }
    
    private void setupJTabbedPane(){
        /*Tenemos que poner los titulos de los tabs*/
        /*
        jTabbedPane1.setTitleAt(0,"PtoDistribucion");
        jTabbedPane1.setTitleAt(1,"Productos");
        jTabbedPane1.setTitleAt(2,"Vehiculos");
        jTabbedPane1.setTitleAt(3,"Pedidos");
        jTabbedPane1.setTitleAt(4,"Proveedores");
        jTabbedPane1.setTitleAt(5,"Clientes");
        jTabbedPane1.setTitleAt(6,"Empleados");
        jTabbedPane1.setTitleAt(7,"Productos-Proveedores");
        jTabbedPane1.setTitleAt(8,"PedidosAProveedor");
         */
        jTabbedPane1.setTitleAt(0,"Productos");
        jTabbedPane1.setTitleAt(1,"Vehiculos");
        jTabbedPane1.setTitleAt(2,"Pedidos");
        jTabbedPane1.setTitleAt(3,"Proveedores");
        jTabbedPane1.setTitleAt(4,"Clientes");
        jTabbedPane1.setTitleAt(5,"Empleados");
        jTabbedPane1.setTitleAt(6,"Productos-Proveedores");
        jTabbedPane1.setTitleAt(7,"PedidosAProveedor");
        jTabbedPane1.setTitleAt(8,"Punto-Venta");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        Cerrar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        Productos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Vehiculos = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Pedidos = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Proveedores = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        Clientes = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        Empleados = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        productosProveedores = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        pedidosAproveedor = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        punto = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Cerrar.setText("Cerrar");
        Cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarActionPerformed(evt);
            }
        });

        getContentPane().add(Cerrar);
        Cerrar.setBounds(280, 360, 81, 29);

        jPanel2.setLayout(new java.awt.BorderLayout());

        Productos.setText("Productos");
        Productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductosActionPerformed(evt);
            }
        });

        jPanel2.add(Productos, java.awt.BorderLayout.SOUTH);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("OPERACIONES SOBRE LOS PRODUCTOS");
        jPanel2.add(jLabel2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab7", jPanel2);

        jPanel3.setLayout(new java.awt.BorderLayout());

        Vehiculos.setText("Vehiculos");
        Vehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VehiculosActionPerformed(evt);
            }
        });

        jPanel3.add(Vehiculos, java.awt.BorderLayout.SOUTH);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("OPERACIONES SOBRE LOS VEHICULOS");
        jPanel3.add(jLabel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab7", jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        Pedidos.setText("Pedidos");
        Pedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PedidosActionPerformed(evt);
            }
        });

        jPanel4.add(Pedidos, java.awt.BorderLayout.SOUTH);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("OPERACIONES SOBRE LOS PEDIDOS");
        jPanel4.add(jLabel4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab7", jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        Proveedores.setText("Proveedores");
        Proveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProveedoresActionPerformed(evt);
            }
        });

        jPanel5.add(Proveedores, java.awt.BorderLayout.SOUTH);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("OPERACIONES SOBRE LOS PROVEEDORES");
        jPanel5.add(jLabel5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab7", jPanel5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        Clientes.setText("Clientes");
        Clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientesActionPerformed(evt);
            }
        });

        jPanel6.add(Clientes, java.awt.BorderLayout.SOUTH);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("OPERACIONES SOBRE CLIENTES");
        jPanel6.add(jLabel6, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab7", jPanel6);

        jPanel7.setLayout(new java.awt.BorderLayout());

        Empleados.setText("Empleados");
        Empleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpleadosActionPerformed(evt);
            }
        });

        jPanel7.add(Empleados, java.awt.BorderLayout.SOUTH);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("OPERACIONES SOBRE LOS EMPLEADOS");
        jPanel7.add(jLabel7, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab7", jPanel7);

        jPanel8.setLayout(new java.awt.BorderLayout());

        productosProveedores.setText("Productos-Proveedores");
        productosProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosProveedoresActionPerformed(evt);
            }
        });

        jPanel8.add(productosProveedores, java.awt.BorderLayout.SOUTH);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("OPERACIONES SOBRE LOS PRODUCTOS-PROVEEDORES");
        jPanel8.add(jLabel8, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab8", jPanel8);

        jPanel9.setLayout(new java.awt.BorderLayout());

        pedidosAproveedor.setText("PedidosAProveedor");
        pedidosAproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pedidosAproveedorActionPerformed(evt);
            }
        });

        jPanel9.add(pedidosAproveedor, java.awt.BorderLayout.SOUTH);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("OPERACIONES SOBRE PEDIDOS A PROVEEDOR");
        jPanel9.add(jLabel9, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("tab9", jPanel9);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("OPERACIONES SOBRE NUESTRO PUNTO DE VENTA");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        punto.setText("Punto Venta");
        punto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puntoActionPerformed(evt);
            }
        });

        jPanel1.add(punto, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("tab9", jPanel1);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(50, 30, 500, 290);

        pack();
    }//GEN-END:initComponents

    private void puntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puntoActionPerformed
        // TODO add your handling code here:
        if(vPuntoVenta == null)
            vPuntoVenta = new PuntoVenta();
        
        vPuntoVenta.visualiza();
    }//GEN-LAST:event_puntoActionPerformed

    private void pedidosAproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pedidosAproveedorActionPerformed
        // TODO add your handling code here:
        if(vPedidosAProveedor == null)
            vPedidosAProveedor = new PedidosAProveedor();
        
        vPedidosAProveedor.visualiza();
    }//GEN-LAST:event_pedidosAproveedorActionPerformed

    private void ProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProveedoresActionPerformed
        // TODO add your handling code here:
        if(vProveedores == null)
            vProveedores = new Proveedores();
        
        vProveedores.visualiza();
    }//GEN-LAST:event_ProveedoresActionPerformed

    private void VehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VehiculosActionPerformed
        // TODO add your handling code here:
        if(vVehiculos == null)
            vVehiculos = new Vehiculos();
        
        vVehiculos.visualiza(-1);
    }//GEN-LAST:event_VehiculosActionPerformed

    private void ProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductosActionPerformed
        // TODO add your handling code here:
        if(vProductos == null)
            vProductos = new Productos();
        
        vProductos.visualiza();
    }//GEN-LAST:event_ProductosActionPerformed

    private void PedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PedidosActionPerformed
        // TODO add your handling code here:
        if(vPedidos == null)
            vPedidos = new Pedidos();
        
        vPedidos.visualiza("-1", "-1", "-1");
    }//GEN-LAST:event_PedidosActionPerformed

    private void productosProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosProveedoresActionPerformed
        // TODO add your handling code here:
        if(vProductosProveedores == null)
            vProductosProveedores = new ProductosProveedores();
        
        vProductosProveedores.visualiza("-1");
    }//GEN-LAST:event_productosProveedoresActionPerformed

    private void ClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientesActionPerformed
        // TODO add your handling code here:
        if(vClientes == null)
            vClientes = new Clientes();
        
        vClientes.visualiza(-1);
    }//GEN-LAST:event_ClientesActionPerformed

    private void CerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarActionPerformed
        // TODO add your handling code here:
        try{
            System.exit(0);
        }catch(Exception e){
            System.out.println("No se ha podido salir!!");
        }
    }//GEN-LAST:event_CerrarActionPerformed

    private void EmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpleadosActionPerformed
        // TODO add your handling code here:
        if (vEmpleados == null){
            vEmpleados = new Empleados();
        }
        
        vEmpleados.visualiza(-1);
        
    }//GEN-LAST:event_EmpleadosActionPerformed
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NodoCentral().setVisible(true);
            }
        });
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
    private javax.swing.JButton Clientes;
    private javax.swing.JButton Empleados;
    private javax.swing.JButton Pedidos;
    private javax.swing.JButton Productos;
    private javax.swing.JButton Proveedores;
    private javax.swing.JButton Vehiculos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton pedidosAproveedor;
    private javax.swing.JButton productosProveedores;
    private javax.swing.JButton punto;
    // End of variables declaration//GEN-END:variables
    
}