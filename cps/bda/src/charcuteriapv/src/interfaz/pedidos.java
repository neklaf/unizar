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

/*Campos optativos: precioTotal*/

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
                                    "*zona", "comprador"};
                                    
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
    private static Hashtable detalles;
    
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
              
        //ocultar(); //Ocultamos lo que no es necesario en la sede central
        
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
    
    public void visualiza(String cif, String nif, String matricula){
        Vector v;
        
        if(cif!=null){
            if(nif!=null){
                //public Vector buscar(String cif, String idPedido, String nif, String matricula)
                v = (Vector)new MantenimientoDB.Pedidos().buscar(cif, "-1", nif, matricula);
    
                if(v!=null){
                    db2table(v);
                    this.setVisible(true);
                }else
                    System.out.println("Se ha producido un error en la busqueda");
            }else
                System.out.println("Error en el nif del empleado vendedor");
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
                //{"id_pedido", "estado", "**precioTotal", "fechaRealizacion", "fechaEnvio", "vehiculo", "vendedor", "comprador"};
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
    
    /*Copiamos el contenido de un ArrayList a otro*/
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
            PuntoDeVenta.PVDetallePedidos dp;
            while(i<l.size()){
                Vector v = new Vector();
                //v.addElement(l.get(i));
                dp = (PuntoDeVenta.PVDetallePedidos)l.get(i);
                v.addElement(dp.getProducto().getRef());
                v.addElement(new Integer(dp.getUnidades()));
                v.addElement(new Double(dp.getPrecioCompra()));
                
                rows.addElement(v);
                
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
    public static void modificaProdDetPed(String id, PVDetallePedidos det, PVDetallePedidos det2){
        /*NOTA: Si me planteo hacer el modificar tengo que recibir como parametros los elementos de la tabla que son Strings
         creo por lo que tendre que cambiar el tipo de los parametros y tener en cuenta que son PVDetallePedidos lo que tenemos
         almacenado en el ArrayList*/
        if((id!=null)&&(det != null)&&(det2 != null)){
            ArrayList l = (ArrayList)detalles.get(id);
            if(l!=null){
                int i=0;
                String prod = det.getProducto().getRef();
                String detProd = ((PVDetallePedidos)l.get(0)).getProducto().getRef();
                while((l.size()<i)&&(!prod.equals(detProd))){
                    detProd = ((PVDetallePedidos)l.get(i)).getProducto().getRef();
                    i++;
                }
            }else
                System.out.println("El pedido "+id+" no tiene detalles");
        }else
            System.out.println("Fallo al pasar los parametros");
    }
    
    /*Elimina producto de los detalles de un pedido*/
    public static void eliminaProdDetPed(String id, String prod){
        if((id != null)&&(prod !=null)){
            ArrayList l;
            l = (ArrayList)detalles.get(id);
            if(l!=null){
                int i = 0;
                String detProd = ((PVDetallePedidos)l.get(0)).getProducto().getRef();
                while((i<l.size())&&(!prod.equals(detProd))){
                    i++;
                    if(i<l.size())
                        detProd = ((PVDetallePedidos)l.get(i)).getProducto().getRef();
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
    
    /*Modifica el valor y la cantidad del detalle de pedido*/
    public static void modificaDetPed(String id, String ref, int cant, double pt){
        if((id != null)&&(ref !=null)){
            ArrayList l;
            l = (ArrayList)detalles.get(id);
            if(l!=null){
                int i = 0;
                String detProd = ((PVDetallePedidos)l.get(0)).getProducto().getRef();
                while((i<l.size())&&(!ref.equals(detProd))){
                    i++;
                    if(i<l.size())
                        detProd = ((PVDetallePedidos)l.get(i)).getProducto().getRef();
                }
                if(i<l.size()){
                    ((PVDetallePedidos)l.get(i)).setPrecioCompra(pt);
                    ((PVDetallePedidos)l.get(i)).setUnidades(cant);
                }else
                    System.out.println("Producto no encontrado en los detalles");
            }else
                System.out.println("El pedido "+id+" no tiene detalles");
        }else
            System.out.println("Fallo al pasar los parametros");
    }
    
    public static void insertaDetalle(String id_pedido, String prod, Integer uni, Double precio, Integer zo){
        /*NOTA: Para crear un PVDetallePedidos tenemos que tener la zona a la cual pertenece ese pedido, por lo
         que tendre que pasarselo a la ventana de detalles y despues esta lo tendra que pasar aqui!!*/
        //public PVDetallePedidos(int unidades, double precioCompra, String ref_prod, int zona)
        if((id_pedido!=null)&&(prod!=null)&&(uni!=null)&&(precio!=null)&&(zo!=null)){
            PuntoDeVenta.PVDetallePedidos det = new PuntoDeVenta.PVDetallePedidos(uni.intValue(), precio.doubleValue(), prod, zo.intValue());
            ((ArrayList)detalles.get(id_pedido)).add(det);
            
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
                    pt = pt + ((PuntoDeVenta.PVDetallePedidos)l.get(i)).getPrecioCompra();
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
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });

        getContentPane().add(insertar);
        insertar.setBounds(30, 40, 80, 25);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        getContentPane().add(eliminar);
        eliminar.setBounds(30, 100, 80, 25);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(30, 160, 69, 25);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        getContentPane().add(modificar);
        modificar.setBounds(30, 230, 90, 25);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(380, 260, 40, 25);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(420, 260, 40, 25);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(530, 260, 80, 25);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(680, 260, 80, 25);

        getContentPane().add(out);
        out.setBounds(190, 340, 450, 21);

        getContentPane().add(nid);
        nid.setBounds(30, 290, 110, 21);

        jLabel1.setText("Nuevo id:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 270, 45, 15);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(130, 340, 60, 15);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id_pedido", "fechaRealizacion ", "estado", "**precioTotal", "fechaEnvio", "vehiculo", "vendedor", "*zona", "comprador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true, true, true, false, true
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

        detalle.setText("Detalles");
        detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalleActionPerformed(evt);
            }
        });

        getContentPane().add(detalle);
        detalle.setBounds(210, 260, 80, 25);

        pack();
    }//GEN-END:initComponents

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
                        if((new MantenimientoDB.Pedidos().eliminar(id_pedido))==1){
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

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                String old_id, new_id, vehiculo, vendedor, comprador, estado;
                Date fechaRealizacion, fechaEnvio;
                Double precioTotal;
                Integer zona;
                int est;
                
                precioTotal = (Double)jTable1.getValueAt(row, PRECIO_TOTAL);
                
                try{
                    old_id = ((String)jTable1.getValueAt(row, ID_PEDIDO)).trim();
                    ArrayList det = (ArrayList)detalles.get(old_id);
                    estado = ((String)jTable1.getValueAt(row, ESTADO)).trim();
                    est = estado2DB(estado);
                    if(est==-1)
                        throw new NullPointerException();
                    vehiculo = ((String)jTable1.getValueAt(row, VEHICULO)).trim();
                    vendedor = ((String)jTable1.getValueAt(row, VENDEDOR)).trim();
                    comprador = ((String)jTable1.getValueAt(row, COMPRADOR)).trim();
                    zona = (Integer)jTable1.getValueAt(row, ZONA);
                    try{
                        fechaRealizacion = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_REALIZACION)).trim());
                        fechaEnvio = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_ENVIO)).trim());
                        new_id = nid.getText();
                        if(!new_id.equals("")){
                            //public int modificar(String antigua_id_pedido, String nueva_id_pedido,Date fechaRealizacion,int estado,Date fechaEnvio, String matricula, String nifVendedor, String cifComprador, ArrayList detallePedidos)
                            if((new MantenimientoDB.Pedidos().modificar(old_id, new_id, fechaRealizacion, est, fechaEnvio, vehiculo, vendedor, comprador, det))==1){
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
                        out.setText("Error en la recepcion de alguna de las fechas");
                    }
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_modificarActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
        // TODO TEST
        //No se tiene que poder insertar un pedido hasta que no tenga detalles!!
        //Por lo que no se puede dar el caso de que un pedido no tenga precioTotal nunca, por lo menos dentro de la bd
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //{"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "fechaEnvio", "vehiculo", "vendedor", "zona", "comprador"}
                Double precioTotal;
                precioTotal = (Double)jTable1.getValueAt(row, PRECIO_TOTAL);
                if(precioTotal == null){ //Esto se podr�a dar por un usuario zoquete
                    precioTotal = new Double(-100.0);
                    m.setValueAt(precioTotal, row, PRECIO_TOTAL);
                    out.setText("Precio Total por defecto");
                }
                
                /*campos obligatorios*/
                String id_pedido, vehiculo, vendedor, comprador, estado;
                Date fechaRealizacion, fechaEnvio;
                //Integer zona;
                int est;
                
                try{
                    id_pedido = ((String)jTable1.getValueAt(row, ID_PEDIDO)).trim();
                    ArrayList det = (ArrayList)detalles.get(id_pedido);
                    vehiculo = ((String)jTable1.getValueAt(row, VEHICULO)).trim();
                    vendedor = ((String)jTable1.getValueAt(row, VENDEDOR)).trim();
                    comprador = ((String)jTable1.getValueAt(row, COMPRADOR)).trim();
                    estado = ((String)jTable1.getValueAt(row, ESTADO)).trim();
                    est = estado2DB(estado);
                    if(est == -1)
                        throw new NullPointerException();
                    try{
                        fechaRealizacion = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_REALIZACION)).trim());
                        fechaEnvio = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA_ENVIO)).trim());
                        //zona = (Integer)jTable1.getValueAt(row, ZONA);
                        if(det != null){
                            //public int insertar(String id_pedido,Date fechaRealizacion,int estado,Date fechaEnvio, String matricula, String nifVendedor, String cifComprador, ArrayList detallePedidos)
                            if((new MantenimientoDB.Pedidos().insertar(id_pedido, fechaRealizacion, est, fechaEnvio, vehiculo, vendedor, comprador, det))==1)
                                out.setText("Insercion realizada con exito");
                            else
                                out.setText("Insercion erronea");
                        }else
                            out.setText("Los detalles son null");
                    }catch(java.text.ParseException p){
                        out.setText("Error parseando alguna fecha");
                    }
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_insertarActionPerformed

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
        //{"id_pedido", "fechaRealizacion", "estado", "**precioTotal", "fechaEnvio", "vehiculo", "vendedor", "*zona", "comprador"}
        v.addElement(null);
        v.addElement(null);
        v.addElement(null);
        v.addElement(null);
        v.addElement(null);
        v.addElement(null);
        v.addElement(null);
        v.addElement(new Integer(ConexionBD.constantes.NODO_PROPIO));
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
                    Integer zo = (Integer)jTable1.getValueAt(row, ZONA);
                    System.out.println("id de pedido: "+id);
                    System.out.println("La zona del pedido es: "+zo);
                    
                    /*Tenemos que tener en cuenta cuando estamos con un pedido nuevo y cuando no!!*/
                    if(zo!=null){
                        if(vDetalles == null)
                            vDetalles = new Detalles();
                        
                        ArrayList l = (ArrayList)detalles.get(id);
                        if(l!=null)
                            vDetalles.visualiza(id, zo, false, this);
                        else{
                            detalles.put(id, new ArrayList());
                            vDetalles.visualiza(id, zo, true, this);
                            out.setText("Detalles de un pedido nuevo");
                        }
                    }else
                        out.setText("La zona es nula");
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
                        //public Vector buscar(String cif, String idPedido, String nif, String matricula)
                        v = (Vector)new MantenimientoDB.Pedidos().buscar("-1", id, "-1", "-1");
                        if(v!=null){
                            if(id.equals("-1"))
                                out.setText("Pedidos encontrados");
                            else
                                out.setText("Pedido encontrado");
                            db2table(v);
                        }else
                            out.setText("Error en la busqueda");
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
