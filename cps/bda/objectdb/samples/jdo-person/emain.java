// ObjectDB for Java/JDO Samples - Person
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

/**
 * An enhancer main class for the JDO Person Sample
 *
 * This main class ensures that all the persistence capable classes will be
 * enhanced at runtime just before they are being loaded into the JVM.
 * To make it work - NO PERSISTENCE CAPABLE CLASS IS MENTIONED IN THIS CLASS!
 * (otherwise non enhanced versions of the classes might be loaded into JVM).
 */
public class eMain {

    public static void main(String[] args) {
        
        // Enhance the Person class if necessary:
        com.objectdb.Enhancer.enhance("Person");

        // Now run the real main:
        Main.main(args);
    }
}
