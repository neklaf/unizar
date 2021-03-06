/*
 * PuntoVenta.java
 *
 * Created on 18 de febrero de 2005, 17:16
 */

/*TODO:
 * Cuando termine de hacer la implementacion normal me podr�a poner a mirar para
 * hacerlo con multiples filas (creo que no a�ade mucha complejidad aunque ya veremos!!)
 */

package Interfaz;

//import MantenimientoDB.PuntoDistribucion;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 *
 * @author  hadden
 */
public class PuntoVenta extends javax.swing.JFrame {
    
    /*Variables de la ventana*/
    DefaultTableModel m;
    String cols[] = new String [] {"zona", "localizacion", "tfno"};
    Vector vCol;
    
    /*Columnas*/
    private final int COLS = 3;
    private final int ZONA = 0;
    private final int LOCALIZACION = 1;
    private final int TFNO = 2;
    
    /*Punteros a ventanas internas*/
    private Empleados vEmpPV;
    private Vehiculos vVePV;
    private Clientes vCliPV;
    
    /** Creates new form PuntoVenta */
    public PuntoVenta() {
    
        //Creamos el vector que almacena los nombres de las columnas
        vCol = new Vector();
        //El nombre de cada columna es un elemento del vector
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
                            
        initComponents();
        
        m = (DefaultTableModel)jTable1.getModel(); //Aqui tendremos el modelo de nuestra tabla
        
        /*Configuracion de la ventana*/
        setSize(600,470);
        setTitle("Ventana de Puntos de Distribucion");
        setResizable(false);
    }
    
    public void setEmpPV(Empleados v){
        vEmpPV = v;
    }
    
    public void setVehiculosPV(Vehiculos v){
        vVePV = v;
    }
    
    public void setCliPV(Clientes v){
        vCliPV = v;
    }
    
    private void oculta(){
        empleados.setVisible(false);
        vehiculos.setVisible(false);
        clientes.setVisible(false);
    }
    
    public void visualiza(){
        Vector v;
        
        v = (Vector)new MantenimientoDB.PuntoDistribucion().buscar(-1); //Ahora tenemos un vector de movidas
        
        if(v!=null){
            db2table(v);
            this.setVisible(true);
        }else
            System.out.println("Se ha producido un error en la busqueda");
    }
    
    // Tiene que estar implementado en todas las ventanas (o en cada una de las clases)
    private void db2table(Vector v){
        if (v.size()!=0){
            String clase;
            
            /*Definimos tantas variables como tipos pueda tener la fila que vamos a recibir*/
            Central.CentralPuntoDistribucion crow;
            PuntoDeVenta.PVPuntoDistribucion pvrow;
            Vector rows = new Vector();
                       
            int i = 0;
            
            //Recorremos todas filas que nos haya proporcionado la base de datos
            while (i < v.size()){
               
                clase = v.elementAt(i).getClass().getName(); //Recibimos el nombre de la clase de la fila
                
                if(clase.endsWith("Central.CentralPuntoDistribucion")){
                    
                    crow = (Central.CentralPuntoDistribucion)v.elementAt(i);
                    Vector vfields = new Vector();
                    vfields.addElement(new Integer(crow.getZona()));
                    vfields.addElement(crow.getLocalizacion());
                    vfields.addElement(crow.getTfno());
                    
                    rows.addElement(vfields);
                    
                }else if(clase.endsWith("PuntoDeVenta.PVPuntoDistribucion")){
                    
                    pvrow = (PuntoDeVenta.PVPuntoDistribucion)v.elementAt(i);
                    
                    Vector vfields = new Vector();
                    vfields.addElement(new Integer(pvrow.getZona()));
                    vfields.addElement(pvrow.getLocalizacion());
                    vfields.addElement(pvrow.getTfno());
                    
                    rows.addElement(vfields);
                    
                }
                i++;
              
            }
            m.setDataVector(rows, vCol); //Pasamos los resultados de la base de datos a la tabla
        }else
            out.setText("No hay Puntos de Venta en la BD");
    }
    
       
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        aceptar = new javax.swing.JButton();
        Insertar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Modificar = new javax.swing.JButton();
        buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Limpiar = new javax.swing.JButton();
        nzona = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        out = new javax.swing.JTextField();
        suma = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        empleados = new javax.swing.JButton();
        vehiculos = new javax.swing.JButton();
        clientes = new javax.swing.JButton();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(421, 310, 90, 29);

        Insertar.setText("Insertar");
        Insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertarActionPerformed(evt);
            }
        });

        getContentPane().add(Insertar);
        Insertar.setBounds(40, 20, 80, 29);

        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        getContentPane().add(Eliminar);
        Eliminar.setBounds(40, 60, 80, 29);

        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        getContentPane().add(Modificar);
        Modificar.setBounds(30, 140, 100, 29);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(40, 100, 70, 29);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "zona", "localizacion", "tfno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(180, 30, 340, 260);

        Limpiar.setText("Limpiar");
        Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LimpiarActionPerformed(evt);
            }
        });

        getContentPane().add(Limpiar);
        Limpiar.setBounds(300, 310, 80, 29);

        getContentPane().add(nzona);
        nzona.setBounds(30, 200, 110, 22);

        jLabel1.setText("Nueva Zona:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 180, 90, 20);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 370, 60, 16);

        getContentPane().add(out);
        out.setBounds(100, 370, 410, 22);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(190, 310, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(230, 310, 40, 29);

        empleados.setText("Empleados");
        empleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empleadosActionPerformed(evt);
            }
        });

        getContentPane().add(empleados);
        empleados.setBounds(30, 240, 100, 29);

        vehiculos.setText("Vehiculos");
        vehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehiculosActionPerformed(evt);
            }
        });

        getContentPane().add(vehiculos);
        vehiculos.setBounds(30, 280, 100, 29);

        clientes.setText("Clientes");
        clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientesActionPerformed(evt);
            }
        });

        getContentPane().add(clientes);
        clientes.setBounds(40, 320, 79, 29);

        pack();
    }//GEN-END:initComponents

    private void clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientesActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                Integer z;
                try{
                    z = (Integer)jTable1.getValueAt(row, ZONA);
                    vCliPV.visualiza(z.intValue());
                }catch(NullPointerException nu){
                    out.setText("Error en la introduccion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_clientesActionPerformed

    private void vehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehiculosActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                Integer z;
                try{
                    z = (Integer)jTable1.getValueAt(row, ZONA);
                    vVePV.visualiza(z.intValue());
                }catch(NullPointerException nu){
                    out.setText("Error en la introduccion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_vehiculosActionPerformed

    private void empleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empleadosActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                Integer z;
                try{
                    z = (Integer)jTable1.getValueAt(row, ZONA);
                    vEmpPV.visualiza(z.intValue());
                }catch(NullPointerException nu){
                    out.setText("Error en la introduccion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_empleadosActionPerformed

    private void menosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosActionPerformed
        // TODO add your handling code here:
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            try{
                m.removeRow(jTable1.getSelectedRow());
            }catch(ArrayIndexOutOfBoundsException ai){
                System.out.println("No hay fila seleccionada");
            }
        }else
            out.setText("No hay fila seleccionada");
    }//GEN-LAST:event_menosActionPerformed

    private void sumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumaActionPerformed
        //TESTED
        Vector v = new Vector();
        v.addElement(null);
        m.addRow(v);
    }//GEN-LAST:event_sumaActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        // TODO TEST
        
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            
            if(row >= 0){
                Integer z; String l,t;
                z = (Integer)jTable1.getValueAt(row, ZONA);
                try{
                    l = ((String)jTable1.getValueAt(row, LOCALIZACION)).trim();
                    t = ((String)jTable1.getValueAt(row, TFNO)).trim();
                    try{
                        String nz;
                        nz = nzona.getText();
                        if(!nz.equals("")){
                            Integer z2 = new Integer(nz);
                            if(z!=null){
                                if((new MantenimientoDB.PuntoDistribucion().modificar(z.intValue(), z2.intValue(), l, t) == 1)){
                                    if(!z.equals(z2))
                                        m.setValueAt(nz, row, ZONA);
                                    nzona.setText("");
                                    out.setText("Modificado con exito");
                                }else
                                    out.setText("Error al modificar");
                            }else
                                out.setText("Falta introducir la zona vieja");
                        }else
                            out.setText("Nueva zona incorrecta");
                    }catch(NumberFormatException n){
                        out.setText("Error al introducir la nueva zona");
                    }
                }catch(NullPointerException n){
                    out.setText("Error en la introduccion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_ModificarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        //TESTED
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            System.out.println("Fila: " + jTable1.getSelectedRow());
            
            if(row >= 0){
                Integer z;
                try{
                    z = (Integer)jTable1.getValueAt(row, ZONA);
                    
                    if((new MantenimientoDB.PuntoDistribucion().eliminar(z.intValue())) == 1){
                        m.removeRow(row);
                        out.setText("Eliminado con exito");
                    }else
                        out.setText("Error al eliminar");
                }catch(NullPointerException n){
                    out.setText("Falta introducir la zona");
                }
            }else
                System.out.println("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_EliminarActionPerformed

    private void InsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertarActionPerformed
        //TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                Integer z; String l,t;
                /*Atributos obligatorios*/
                z = (Integer)jTable1.getValueAt(row, ZONA);
                try{ 
                    l = ((String)jTable1.getValueAt(row, LOCALIZACION)).trim();
                    t = ((String)jTable1.getValueAt(row, TFNO)).trim();
                    
                    if(z!=null){
                        if(new MantenimientoDB.PuntoDistribucion().insertar(z.intValue(), l, t) == 1)
                            out.setText("Insercion con exito");
                        else
                            out.setText("Insercion erronea");
                    }else
                        out.setText("Falta introducir la zona");
                    
                }catch(NullPointerException n){
                    out.setText("Error en la introduccion de los datos");
                }
                                        
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_InsertarActionPerformed

    private void LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpiarActionPerformed
        // TESTED
        nzona.setText("");//Limpiamos el contenido del nuevo JTextField
        Vector v = new Vector();
        v.addElement(null); //Cada elemento del vector que le pasamos como dato a setDataVector es una fila!!!
                
        m.setDataVector(v, vCol);
        out.setText("Tabla limpiada!");
    }//GEN-LAST:event_LimpiarActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        //TODO TEST
        
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            Vector v;
            int row;
            
            row = jTable1.getSelectedRow(); //Recibimos la fila que estamos seleccionando
            
            if (row >= 0){ //La fila tendra que ser mayor que -1 para que sea correcta
                try{
                    Integer zona = (Integer)jTable1.getValueAt(row, ZONA);
                
                    v = (Vector)new MantenimientoDB.PuntoDistribucion().buscar(zona.intValue());
                    
                    if(v!=null){
                        if(zona.intValue() == -1)
                            out.setText("Puntos de venta encontrados");
                        else
                            out.setText("Punto de venta encontrado");
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

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TESTED
        out.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        //TODO TEST
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        new PuntoVenta().show();
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Insertar;
    private javax.swing.JButton Limpiar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton aceptar;
    private javax.swing.JButton buscar;
    private javax.swing.JButton clientes;
    private javax.swing.JButton empleados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton menos;
    private javax.swing.JTextField nzona;
    private javax.swing.JTextField out;
    private javax.swing.JButton suma;
    private javax.swing.JButton vehiculos;
    // End of variables declaration//GEN-END:variables
    
    
    //Parte importante de la tabla que hace mas sencillo el manejo de la misma
    /*class pvModel extends AbstractTableModel{
        private int cols, rows;
        private String [] nbs = {"zona", "localizacion", "tfno"};
        private Vector vRows;
        private ResultSetMetaData rsmd;
        
        pvModel(){
            //constructor de la clase pvModel
            vRows = new Vector();
            cols = 3; rows = 0;
            
        }
        
        //Este es el metodo que tendre que llamar para modificar el modelo y la tabla!!
        //Esto es lo que he acordado que Nacho me pasar�a!!
        public void setVector(Vector v){
            vRows = v;
            fireTableChanged(null); //actualizar la tabla en la que se representan los datos!!
        }
        
        public String getColumnName(int col){
            return nbs[col];
        }
        
        public int getColumnCount() {
            return cols;
        }        
        
        public int getRowCount() {
            return vRows.size();
        }
        
        public Object getValueAt(int row, int col) {
            Vector vOneRow = (Vector)vRows.elementAt(row);
            return vOneRow.elementAt(col);
        }
        
        //La implementacion se vera reducida al tipo del que sean las columnas!!!
        public Class getColumnClass(int col){
            String className = new String();
            try{
                className = rsmd.getColumnClassName(col);
            }catch(SQLException e){
                //...
            }
            if(className.endsWith("String"))
                return String.class;
            else if(className.endsWith("Integer"))
                return Integer.class;
            else return Object.class;
        }
    }*/
    
}
