// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory;

import java.io.*;
import java.util.Properties;
import javax.jdo.*;

/**
 * General JDO Utilities
 */
public class Utilities {
    
    /**
     * Obtains a PersistenceManager representing a database connection.
     *
     * @param clean indicates if a clean new database is preferred
     */
    public static PersistenceManager getPersistenceManager(boolean clean) {
        
        try {
            // Prepare the jdo.properties file for reading:
            InputStream in =
                Utilities.class.getResourceAsStream("jdo.properties");

            try {
                // Load the properties from the file:
                Properties properties = new Properties();
                properties.load(in);
                
                // If requested - try to delete an old local odb file:
                if (clean) {
                    String path = (String)properties.get(
                        "javax.jdo.option.ConnectionURL");
                    if (!path.startsWith("objectdb://")) // only local
                        new File(path).delete();
                }
                
                // Obtain a PersistenceManagerFactory and a PersistenceManager:
                PersistenceManagerFactory pmf =
                    JDOHelper.getPersistenceManagerFactory(
                        properties, JDOHelper.class.getClassLoader());
                return pmf.getPersistenceManager();
            }
            finally {
                in.close();   
            }
        }
        
        // Handle errors:
        catch (IOException x) {
            throw new RuntimeException("Error reading jdo.properties");
        }
    }
}
