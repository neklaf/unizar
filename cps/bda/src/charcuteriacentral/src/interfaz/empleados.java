/*
 * Empleados.java
 *
 * Created on 13 de febrero de 2005, 23:41
 */

/*TODO:
 * Tengo que indagar un poco en las propiedades que nos ofrecen las JTable's para poder trabajar con tablas grandes
 */

/*IDEA:
 * Hacer que los ArrayList aparezcan en otra ventana con una tabla, en la que se pueda editar y eliminar los telefonos
 * Tenemos que tener una œnica ventana en la que se representaran bien los telefonos personales o bien los telefonos de la empresa
 *  Implicaciones:  podemos tener una ventana abierta y querer abrir la otra??? Tiene alguna funcionalidad posible
 *                  tenemos que cambiar el nombre de la columna cada vez que se vuelva a visualizar
 *                  Patron singleton +/- como en las anteriores ventanas
 */

/*NOTA IMPORTANTE:
 * Para Empleados hemos seleccionado la fragmentacion por punto de distribucion y asignaremos cada fragmento a su punto de distribucion
 * y en la sede central todos los fragmentos
 */

/*TODO: visualizacion de las fechas FALTA NO ENTIENDO PORQUE COJONES NO FUNCIONA*/

package Interfaz;

//import MantenimientoDB.Empleados;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.text.SimpleDateFormat;

/**
 *
 * @author  hadden
 */
public class Empleados extends javax.swing.JFrame {

    /*Variables necesarias para la tabla*/
    SimpleDateFormat formatter;
    DefaultTableModel m;
    /*Cuando eliminamos alguna columna tambien tiene que ser eliminada de aqui*/
    String cols[] = new String [] {"nif", "apellidos", "nombre", "%Comision", "activo", "cargo", "*numCuenta", 
                                    "departamento", "*jefe", "sueldoBase", "fechaAltaContrato", "domicilio"};
    Vector vCol;                                
    
    private final int COLS = 12;
    /*Columnas*/
    private final int NIF = 0;
    private final int APELLIDOS = 1;
    private final int NOMBRE = 2;
    private final int COMISION = 3;
    private final int ACTIVO = 4;
    private final int CARGO = 5;
    private final int CUENTA = 6;
    private final int DEPARTAMENTO = 7;
    private final int JEFE = 8;
    private final int SUELDO = 9;
    private final int FECHA = 10;
    private final int DOMICILIO = 11;
    
        
    /*Almacenan los telefonos de cada uno de los empleados que tenemos en la tabla, tenemos que mantener coherente la hashtable!!!*/
    private static Hashtable tPers, tEmpr;
    
    //Ventanas internas
    tfnosPers vTfnosPers;
    tfnosEmpr vTfnosEmpr;
    fecha vFecha; //Ventana de la fecha
    
    
    /** Creates new form Empleados */
    public Empleados() {
        
        /*Para escribir la fecha adecuadamente*/
        formatter = new SimpleDateFormat("dd/MM/yy");
        
        vCol = new Vector();
        
        for(int i=0; i<COLS; i++)
            vCol.addElement(cols[i]);
                
        initComponents();
        
        m = (DefaultTableModel)jTable1.getModel(); //Aqui tendremos el modelo de nuestra tabla
        
        oculta();
        
        tPers = new Hashtable();
        tEmpr = new Hashtable();
              
        /*Configuracion de la ventana*/
        this.setSize(1000,500);
        //this.setVisible(true);
        this.setTitle("Ventana de Empleados");
        this.setResizable(false);
    }
    
    private void oculta(){
        fecha.setVisible(false);
    }
    
    /*Abre la ventana representando los empleados que hay en la bd central, en este caso*/
    public void visualiza(int zona){
        Vector v;
        
        //Con esto conseguimos la informacion de la bd
        v = (Vector)new MantenimientoDB.Empleados().buscar("-1", 2, zona);
    
        if(v!=null){
            db2table(v);
            this.setVisible(true);
        }else
            System.err.println("Se ha producido un error en la busqueda");
    }
    
    /*Esta funcion realiza un mapeo de la informacion contenida en la bd de la clase CentralEmpleados en la tabla que representa sus datos*/
    /*La raz—n por la que se ha implementado considerando dos posibles clases diferentes siendo que en la BD Central que es para la que 
     estamos implementando esta ventana tiene muchas similitudes con la ventana que existira en los puntos de distribucion*/
    /*TODO Almacenar los telefonos en alguna estructura*/
    private void db2table(Vector v){
        /*Tenemos que tener en cuenta que columnas son las que vamos a representar en la tabla*/
        /*En este caso NO vamos a representar las columnas que son los ArrayList (los telefonos) sino que se representaran en otra ventana
         Pero tenemos que tener en cuenta que hay dos columnas que no se encuentran en las dos clases por lo que no tendre que tenerlas
         en cuenta dependiendo de la clase que este manejando, esto lo hago asi porque lo tendre que hacer para la ventana de los Empleados
         en el punto de distribucion*/
        if (v.size()!=0){
            /*La solucion que podemos adoptar es no representar toda la informacion de los empleados, por lo menos lo que no 
             tienen ambas clases*/
            String clase;
            
            /*Definimos tantas variables como tipos pueda tener la fila que vamos a recibir*/
            Central.CentralEmpleados crow;
            PuntoDeVenta.PVEmpleados pvrow;
            Vector rows = new Vector();
                       
            int i = 0;
            String nif; //Esta variable aqui es necesaria
            
            //Recorremos todas filas que nos haya proporcionado la base de datos
            while (i < v.size()){
               
                clase = v.elementAt(i).getClass().getName(); //Recibimos el nombre de la clase de la fila
                
                if(clase.endsWith("Central.CentralEmpleados")){
                    
                    crow = (Central.CentralEmpleados)v.elementAt(i);
                    Vector vfields = new Vector();
                    /*Tenemos que recuperar todos los campos del vector que representa la fila de la bd*/
                    /*La clase de CentralEmpleados NO tiene el campo ventas pero tiene el campo departamento*/
                    
                    /*La secuencia de recepcion de los campos tendra que coincidir con el orden en el que se representen en la tabla*/
                    /*{"nif", "apellidos", "nombre", "%Comision", "activo", "cargo", "numCuenta", 
                                    "departamento", "jefe", "sueldoBase", "fechaAltaContrato", "domicilio"};*/
                    nif = crow.getNif();
                    vfields.addElement(nif);
                    vfields.addElement(crow.getApellidos());
                    vfields.addElement(crow.getNombre());
                    vfields.addElement(new Double(crow.getPorcentajeComision()));
                    
                    ArrayList n = copiaArrayList((ArrayList)crow.getAllTfnoPers());
                    //Siempre que leemos un empleado de la bd tendremos que crear una entrada en la hash para el para diferenciarlos
                    //de los nuevos empleados que vayamos a crear
                    if(n!=null)
                        tPers.put(nif, n);
                    else
                        tPers.put(nif, new ArrayList());
                    
                    vfields.addElement(new Boolean(crow.getActivo()));
                    vfields.addElement(crow.getCargo());
                    
                    ArrayList n2 = copiaArrayList((ArrayList)crow.getAllTfnoEmpr());
                    if(n2!=null)
                        tEmpr.put(nif, n2);
                    else
                        tEmpr.put(nif, new ArrayList());
                    
                    vfields.addElement(crow.getNumcuenta()); //No puede ser nunca null aunque sea optativo
                                       
                    vfields.addElement(new Integer(((Central.CentralPuntoDistribucion)crow.getDepartamento()).getZona())); //Clase: CentralPuntoDistribucion
                    
                    try{ //Se tiene que poner un try porque es posible que sea null
                        vfields.addElement(((Central.CentralEmpleados)crow.getJefe()).getNif()); //Clase: CentralEmpleados
                    }catch(NullPointerException nu){
                        vfields.addElement("");
                    }
                    vfields.addElement(new Double(crow.getSueldoBase()));
                    
                    vfields.addElement(formatter.format((Date)crow.getFechaAltaContrato())); //Clase: Date
                    vfields.addElement(crow.getDomicilio());
                    
                    rows.addElement(vfields);
                    
                }else if(clase.endsWith("PuntoDeVenta.PVEmpleados")){
                    /*La clase PVEmpleados no tiene el campo departamento pero tiene el campo ventas*/
                    /*Recordar que el campo ventas se tendra que solucionar como lo de los telefonos*/
                    /*Entonces tendremos que tener un boton ventas que aparezca en la ventana!!!*/
                    /*VENTAS ES OPTATIVO A LA HORA DE INTRODUCIR UN EMPLEADO*/
                    
                    pvrow = (PuntoDeVenta.PVEmpleados)v.elementAt(i);
                    
                    Vector vfields = new Vector();
                    
                    nif = pvrow.getNif();
                    vfields.addElement(nif);
                    vfields.addElement(pvrow.getApellidos());
                    vfields.addElement(pvrow.getNombre());
                    vfields.addElement(new Double(pvrow.getPorcentajeComision()));
                    
                    ArrayList n = copiaArrayList(pvrow.getAllTfnoPers());
                    tPers.put(nif, n);
                    
                    vfields.addElement(new Boolean(pvrow.getActivo()));
                    vfields.addElement(pvrow.getCargo());
                    
                    ArrayList n2 = copiaArrayList(pvrow.getAllTfnoEmpr());
                    if(n2 == null)
                        tEmpr.put(nif, new ArrayList());
                    else
                        tEmpr.put(nif, n2);
                    
                    vfields.addElement(pvrow.getNumcuenta());
                    //vfields.addElement(new Integer(((Central.CentralPuntoDistribucion)crow.getDepartamento()).getZona())); //Clase: CentralPuntoDistribucion
                    //vfields.addElement(null); //No lo tendria ni que considerar
                    try{
                        vfields.addElement(((PuntoDeVenta.PVEmpleados)pvrow.getJefe()).getNif()); //Clase: CentralEmpleados
                    }catch(NullPointerException nu){
                        vfields.addElement("");
                    }
                    vfields.addElement(new Double(pvrow.getSueldoBase()));
                    vfields.addElement(formatter.format((Date)pvrow.getFechaAltaContrato())); //Clase: Date
                    vfields.addElement(pvrow.getDomicilio());
                    
                    rows.addElement(vfields);
                    
                }
                i++;
              
            }
            m.setDataVector(rows, vCol); //Pasamos los resultados de la base de datos a la tabla
        }else
            out.setText("No hay Empleados en la BD");
    }
    
    private ArrayList copiaArrayList(ArrayList a){
        ArrayList n = new ArrayList();
        try{
            Iterator it = a.iterator();
            while(it.hasNext()){
                n.add((String)it.next());
            }
            return n;
        }catch(NullPointerException nu){
            System.out.println("Lista de tfnos vacia");
            return null;
        }
    }
    
    /***MOVIDAS CON LOS HASHTABLES***/
    
    public static void eliminaEmpTfnoPers(String nif){
        eliminaEmpTfno(tPers, nif);
    }
    
    public static void eliminaEmpTfnoEmpr(String nif){
        eliminaEmpTfno(tEmpr, nif);
    }
    
    /*Elimina la persona del hashtable*/
    private static void eliminaEmpTfno(Hashtable h, String nif){
        h.remove(nif);
    }
    
    public static ArrayList getTfnoPers(String nif){
        return (getTfno(tPers, nif));
    }
    
    public static ArrayList getTfnoEmpr(String nif){
        return (getTfno(tEmpr, nif));
    }
    
    /*Recibe los telefonos de la persona que le pasamos como parametro*/
    private static ArrayList getTfno(Hashtable h, String nif){
        return (ArrayList)h.get(nif);
    }
    
    public static void modificaTfnoPers(String nif, String tel, String tel2){
        modificaTfno(tPers, nif, tel, tel2);
    }
    
    public static void modificaTfnoEmpr(String nif, String tel, String tel2){
        modificaTfno(tEmpr, nif, tel, tel2);
    }
    
    /*Modificamos el telefono de la persona que le pasamos como parametro*/
    private static void modificaTfno(Hashtable h, String nif, String tel, String tel2){
        if((h!=null)&&(nif!=null)&&(tel!=null)&&(tel2!=null)){
            ArrayList l; 
            l = (ArrayList)h.get(nif);
            if(l!=null){
                int i=0;
                while((i<l.size())&&(!tel.equals(l.get(i)))){
                    i++;
                }
                if(i < l.size())
                    l.set(i, tel2);
                else
                    System.out.println("Telenfono no encontrado");
            }else
                System.err.println("El nif "+nif+" no tiene telefonos");
        }else
            System.err.println("Fallo al pasar los parametros");
    }
    
    public static void eliminaTfnoPers(String nif, String tel){
        eliminaTfno(tPers, nif, tel);
    }
    
    public static void eliminaTfnoEmpr(String nif, String tel){
        eliminaTfno(tEmpr, nif, tel);
    }
    
    /*Elimina el telefono de la persona que le pasamos como parametro*/
    private static void eliminaTfno(Hashtable h, String nif, String tel){
        if((h!=null)&&(nif!=null)&&(tel!=null)){
            ArrayList l; 
            l = (ArrayList)h.get(nif);
            if(l!=null){
                int i=0;
                while((i<l.size())&&(!tel.equals(l.get(i)))){
                    i++;
                }
                if(i < l.size())
                    l.remove(i);
                else
                    System.out.println("Telenfono no encontrado");
            }else
                System.err.println("El nif "+nif+" no tiene telefonos");
        }else
            System.err.println("Fallo al pasar los parametros");
    }
    
    
    public static void insertaTfnoPers(String nif, String t){
        insertaTfno(tPers, nif, t);
    }
    
    public static void insertaTfnoEmpr(String nif, String t){
        insertaTfno(tEmpr, nif, t);
    }
    
    /*Inserta el telefono a la persona que le pasamos como parametro*/
    private static void insertaTfno(Hashtable h, String nif, String t){
        //TEngo que insertar el telefono dentro del ArrayList de telefonos del pavo
        ((ArrayList)h.get(nif)).add(t);
    }
    
    /*Interface para recibir los datos de las hashtables*/
    public static Vector tPers2Vector(String nif){
        return(hash2Vector(tPers, nif));
    }
    
    public static Vector tEmpr2Vector(String nif){
        return(hash2Vector(tEmpr, nif));
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
        
    /*Imprime por pantalla los telefonos de la pe–a*/
    private void listaTfnos(ArrayList tfnos){
        int i=0;
        if(tfnos != null){
            while(i<tfnos.size()){
                System.out.println("telefono: " + (String)tfnos.get(i));
                i++;
            }
        }else
            System.out.println("No tiene telefonos");
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
        aceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        modificar = new javax.swing.JButton();
        limpiar = new javax.swing.JButton();
        nnif = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        out = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tpers = new javax.swing.JButton();
        tempr = new javax.swing.JButton();
        suma = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        fecha = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        eliminar.setBounds(20, 80, 80, 29);

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        getContentPane().add(buscar);
        buscar.setBounds(20, 130, 75, 29);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        getContentPane().add(aceptar);
        aceptar.setBounds(790, 360, 90, 29);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "nif", "apellidos", "nombre", "%Comision", "activo", "cargo", "*numCuenta", "departamento", "*jefe", "sueldoBase", "fechaAltaContrato", "domicilio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(130, 50, 860, 290);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        getContentPane().add(modificar);
        modificar.setBounds(10, 190, 90, 29);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        getContentPane().add(limpiar);
        limpiar.setBounds(560, 360, 81, 29);

        getContentPane().add(nnif);
        nnif.setBounds(10, 240, 110, 22);

        jLabel1.setText("Nuevo nif:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 220, 80, 16);

        getContentPane().add(out);
        out.setBounds(140, 410, 600, 22);

        jLabel2.setText("Estado:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 410, 70, 16);

        tpers.setText("Tfno. Pers");
        tpers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tpersActionPerformed(evt);
            }
        });

        getContentPane().add(tpers);
        tpers.setBounds(10, 300, 100, 29);

        tempr.setText("Tfno. Empr");
        tempr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temprActionPerformed(evt);
            }
        });

        getContentPane().add(tempr);
        tempr.setBounds(10, 340, 110, 29);

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        getContentPane().add(suma);
        suma.setBounds(360, 360, 40, 29);

        menos.setText("-");
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        getContentPane().add(menos);
        menos.setBounds(400, 360, 40, 29);

        fecha.setText("Fecha");
        fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaActionPerformed(evt);
            }
        });

        getContentPane().add(fecha);
        fecha.setBounds(220, 360, 81, 29);

        pack();
    }//GEN-END:initComponents

    private void temprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temprActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            if(vTfnosEmpr == null)
                vTfnosEmpr = new tfnosEmpr();
            
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    String ni;
                    
                    ni = ((String)jTable1.getValueAt(row, NIF)).trim();
                    
                    //Tenemos que contemplar la posibilidad de que queramos insertar los telefonos de un empleado nuevo
                    ArrayList l = (ArrayList)tEmpr.get(ni);
                    if(l!=null)
                        vTfnosEmpr.visualiza(ni, false);
                    else{
                        //Si no existe el empleado lo creamos en la hashtable
                        tEmpr.put((String)ni, new ArrayList()); //Creamos una nueva entrada en la hashtable
                        vTfnosEmpr.visualiza(ni, true);
                        out.setText("Telefonos de Empresa de un empleado nuevo");
                    }
                }catch(NullPointerException nu){
                    out.setText("Error en la recepcion el nif");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_temprActionPerformed

    private void fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaActionPerformed
        // TODO add your handling code here:
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                if(vFecha == null)
                    vFecha = new fecha();
                
                    vFecha.visualiza(row, FECHA, m);
            }else
                out.setText("Error en la fila seleccionada");
        }
    }//GEN-LAST:event_fechaActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO TEST
     
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public int modificar(String antiguoNif, String nuevoNif, String domicilio,String cargo, double porcentajeComision, Date fechaAltaContrato,String nombre,String apellidos, double sueldoBase, String numcuenta, String nifJefe, int zona)
                try{
                    String oldNif, newNif, domicilio, cargo, nombre, apellidos, cuenta, jefe;
                    Double comision, sueldo;
                    Date fecha;
                    Boolean activo;
                    Integer zona;
                    //La modificacion de los telefonos se hace a parte
                    //Comprobamos los datos de la tabla y del campo de texto
                    oldNif = ((String)jTable1.getValueAt(row, NIF)).trim();
                    domicilio = ((String)jTable1.getValueAt(row, DOMICILIO)).trim();
                    cargo = ((String)jTable1.getValueAt(row, CARGO)).trim();
                    nombre= ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    apellidos = ((String)jTable1.getValueAt(row, APELLIDOS)).trim();
                    activo = (Boolean)jTable1.getValueAt(row, ACTIVO);
                    comision = (Double)jTable1.getValueAt(row, COMISION);
                    sueldo = (Double)jTable1.getValueAt(row, SUELDO);
                    zona = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                    cuenta = ((String)jTable1.getValueAt(row, CUENTA)).trim();
                    jefe = ((String)jTable1.getValueAt(row, JEFE)).trim();
                    try{ //Este try es debido al parseo de la fecha
                        fecha = (Date)formatter.parse(((String)jTable1.getValueAt(row, FECHA)).trim());
                        newNif = nnif.getText();
                        if(!newNif.equals("")){
                            //public int modificar(String antiguoNif, String nuevoNif, String domicilio,String cargo, double porcentajeComision, Date fechaAltaContrato,String nombre,String apellidos, double sueldoBase, String numcuenta, String nifJefe, boolean activo, int zona)
                            if((new MantenimientoDB.Empleados().modificar(oldNif, newNif, domicilio, cargo, comision.doubleValue(), fecha, nombre, apellidos, sueldo.doubleValue(), cuenta, jefe, activo.booleanValue(), zona.intValue()))==1){
                                if(oldNif != newNif)
                                    m.setValueAt(newNif, row, NIF); //Cambiamos el valor del nif en la tabla
                                
                                out.setText("Empleado modificado con exito");
                                nnif.setText("");
                            }else
                                out.setText("Error en la modificacion");
                        }else
                            out.setText("Nuevo nif incorrecto");
                    }catch(java.text.ParseException p){
                        out.setText("Error parseando la fecha");
                    }
                 }catch(NullPointerException nu){
                    out.setText("Error en la recepcion de los datos");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_modificarActionPerformed

    private void menosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosActionPerformed
        // TODO add your handling code here:
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
        // TODO add your handling code here:
        Vector v = new Vector();
        v.addElement(null);
        m.addRow(v);
    }//GEN-LAST:event_sumaActionPerformed

    private void tpersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tpersActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            if(vTfnosPers == null)
                vTfnosPers = new tfnosPers();
            
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                try{
                    String ni;
                    ni = ((String)jTable1.getValueAt(row, NIF)).trim();
                    //Tenemos que contemplar la posibilidad de que queramos insertar los telefonos de un empleado nuevo
                    ArrayList l = (ArrayList)tPers.get(ni); //hashtable.get(nif) devuelve un Object de ahi el cast
                    if(l!=null)
                        vTfnosPers.visualiza(ni, false);
                    else{
                        tPers.put((String)ni, new ArrayList()); //Creamos una nueva entrada en la hashtable
                        vTfnosPers.visualiza(ni, true);
                        out.setText("Telefonos personales de un empleado nuevo");
                    }
                
                }catch(NullPointerException nu){
                    out.setText("Error recibiendo el nif");
                }
                    
            }else
                out.setText("Error en la fila seleccionada");
            
        }else
            out.setText("Problema en la configuracion de la ventana");
        
    }//GEN-LAST:event_tpersActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
        // TODO TEST
        //NOTA: Tenemos que hacer que los telefonos sean obligatorios antes de que se inserte un empleado
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                String cuenta, jefe;
                                
                /*Los campos opcionales en Empleados son: cuenta, jefe*/
                try{
                    cuenta = ((String)jTable1.getValueAt(row, CUENTA)).trim();
                }catch(NullPointerException nu){
                    cuenta = new String("");
                    m.setValueAt(cuenta, row, CUENTA);
                    out.setText("Numero de cuenta por defecto");
                }
                try{
                    jefe = ((String)jTable1.getValueAt(row, JEFE)).trim();
                }catch(NullPointerException nu){
                    jefe = new String("");
                    m.setValueAt(jefe, row, JEFE);
                    out.setText("Jefe por defecto");
                }
                /*Campos obligatorios*/
                try{
                    String nif, domicilio, cargo, nombre, apellidos;
                    Boolean activo;
                    Double comision, sueldo;
                    ArrayList tp, te;
                    Date fecha;
                    Integer zona;
                    nif = ((String)jTable1.getValueAt(row, NIF)).trim();
                    domicilio = ((String)jTable1.getValueAt(row, DOMICILIO)).trim();
                    cargo = ((String)jTable1.getValueAt(row, CARGO)).trim();
                    nombre= ((String)jTable1.getValueAt(row, NOMBRE)).trim();
                    apellidos = ((String)jTable1.getValueAt(row, APELLIDOS)).trim();
                    activo = (Boolean)jTable1.getValueAt(row, ACTIVO); //El valor por defecto vendra dado por la representacion
                    if(activo == null){
                        activo = new Boolean(false);
                        m.setValueAt(activo, row, ACTIVO);
                    }
                    comision = (Double)jTable1.getValueAt(row, COMISION);
                    sueldo = (Double)jTable1.getValueAt(row, SUELDO);
                    //Cogemos los telefonos de las variables locales en las que estan mantenidos
                    tp = (ArrayList)(tPers.get(nif));
                    te = (ArrayList)(tEmpr.get(nif));
                    zona = (Integer)jTable1.getValueAt(row, DEPARTAMENTO);
                    try{
                        fecha = (Date)formatter.parse((String)jTable1.getValueAt(row, FECHA));
                        if(tp!=null){
                            if((new MantenimientoDB.Empleados().insertar(nif, domicilio, cargo, activo.booleanValue(), comision.doubleValue(), tp, te, fecha, nombre, apellidos, sueldo.doubleValue(), cuenta, jefe, zona.intValue())) == 1)
                                out.setText("Empleado insertado con exito");
                            else
                                out.setText("Inserion erronea");
                        //Se podria borrar la linea si la insercion no es correcta pero puede que queramos editar los cambios porque ha sido 
                        //un error tipografico
                        }else
                            out.setText("Falta introducir telefonos al empleado");
                    }catch(java.text.ParseException p){
                        out.setText("Error al parsear la fecha");
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
        nnif.setText("");//Limpiamos el contenido del nuevo nif
        
        Vector v = new Vector();
        v.addElement(null); //Cada elemento del vector que le pasamos como dato a setDataVector es una fila!!!
                
        m.setDataVector(v, vCol);
        out.setText("Tabla limpiada!");
    }//GEN-LAST:event_limpiarActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                String nif;
                try{
                    Vector v;
                    nif = ((String)jTable1.getValueAt(row, NIF)).trim();
                    if(!nif.equals("")){ //Tenemos que evitar que se seleccione una cadena vacia como nif
                        //public Vector buscar(String nif, int estado)
                        v = new MantenimientoDB.Empleados().buscar(nif, 2, -1);
                        if(v != null){
                            if(nif.equals("-1"))
                                out.setText("Empleados encontrados con exito");
                            else
                                out.setText("Empleado encontrado con exito");
                            db2table(v);
                        }else
                            out.setText("Busqueda sin exito");
                    }
                }catch(NullPointerException nu){
                    out.setText("Error en el nif introducido");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");
    }//GEN-LAST:event_buscarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO TEST
        if(!jTable1.getColumnSelectionAllowed() && jTable1.getRowSelectionAllowed()){
            int row = jTable1.getSelectedRow();
            if(row >= 0){
                //public int eliminar(String nif)
                String nif;
                try{
                    nif = ((String)jTable1.getValueAt(row, NIF)).trim();
                    if(!nif.equals("")){ //Tenemos que evitar que se seleccione una cadena vacia como nif
                        if((new MantenimientoDB.Empleados().eliminar(nif)) == 1){
                            m.setValueAt(new Boolean(false), row, ACTIVO);
                            out.setText("Eliminado con exito");
                        }else
                            out.setText("Empleado no ha podido eliminarse de la bd");
                    }else
                        out.setText("El nif no puede ser la cadena vacia");
                }catch(NullPointerException nu){
                    out.setText("Error en el nif introducido");
                }
            }else
                out.setText("Error en la fila seleccionada");
        }else
            out.setText("Problema en la configuracion de la ventana");    
    }//GEN-LAST:event_eliminarActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TESTED
        out.setText("");
        setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Empleados().setVisible(true);
            }
        });
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton buscar;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton fecha;
    private javax.swing.JButton insertar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton limpiar;
    private javax.swing.JButton menos;
    private javax.swing.JButton modificar;
    private javax.swing.JTextField nnif;
    private javax.swing.JTextField out;
    private javax.swing.JButton suma;
    private javax.swing.JButton tempr;
    private javax.swing.JButton tpers;
    // End of variables declaration//GEN-END:variables
    
}

/*Clase que tenemos que implementar para visualizar las fechas dentro de la tabla*/
/*La implementacion de esta nueva clase se hace minima porque extendemos la clase DefaultTableCellRenderer*/
/*class miDateRenderer extends DefaultTableCellRenderer{
    DateFormat f;
    public miDateRenderer(){ super(); }
    
    public void setValue(Object value){
        if (f == null){
            //f = DateFormat.getDateInstance(DateFormat.SHORT);
            f = DateFormat.getDateInstance(DateFormat.SHORT);
        }
        if((value != null) && (value instanceof Date)){
            //setText((value == null) ? "" : (f.format((Date)value)));
            super.setText(f.format((Date)value));
            }
            //Date d = (Date) value;            
            //f.format(d);
        //}else
            //System.out.println("No es una fecha o es null");
    }
}*/
