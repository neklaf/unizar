// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.step3;

/**
 * A main class for Step 3 of the Directory Demo.
 *
 * This class ensures that all the persistence capable classes will be enhanced
 * at runtime just before they are being loaded into the JVM.
 * To make it work - NO PERSISTENCE CAPABLE CLASS IS MENTIONED IN THIS CLASS!
 * (otherwise non enhanced versions of the classes might be loaded into JVM).
 */
public class eMain {
    
    public static void main(String[] args) {
        
        // Enhance the persistence capable classes when necessary:
        com.objectdb.Enhancer.enhance("directory.pc.*");

        // Now run the example:
        new Retrieve().run();
    }
}
