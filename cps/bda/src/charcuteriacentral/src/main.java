/*
 * Main.java
 *
 * Created on 14 de febrero de 2005, 19:09
 */

import Interfaz.NodoCentral;

/**
 *
 * @author  Administrador
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //new InitDB().inicializa();
        new NodoCentral().setVisible(true);
        
    }
    
}
