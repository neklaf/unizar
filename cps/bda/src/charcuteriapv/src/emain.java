/*
 * eMain.java
 *
 * Created on 14 de febrero de 2005, 19:08
 */

/**
 *
 * @author  Administrador
 */
public class eMain {
    
    public static void main(String[] args) {
        
        // Enhance the persistence capable classes when necessary:
        com.objectdb.Enhancer.enhance("Central.*, PuntoDeVenta.*");

        // Now run the example:
        Main.main(args);
    }
}
