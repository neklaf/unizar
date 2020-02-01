// ObjectDB for Java/JDO - The Guest Book - A Mimimal JDO Based Web Application
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package guestbook;

import java.io.File;
import java.util.Properties;
import javax.jdo.*;
import javax.servlet.*;

/**
 * This class handles JDO enhancement and PersistenceManager allocations.
 */
public class WebAppMgr {
    
    /**
     * Ensures on the fly JDO enhancement of persistence capable classes.
     * NOTE: NO PERSISTENCE CAPABLE CLASS IS MENTIONED IN THIS CLASS!
     * (otherwise classes might be loaded into JVM before enhancement).
     */
    public static void enhanceAll() {
        if (!enhanced) {
            com.objectdb.Enhancer.enhance("guestbook.pc.*");
            enhanced = true;
        }
    }
    private static boolean enhanced; // indicates if already been done

    /**
     * Obtains a PersistenceManager instance.
     */
    public static PersistenceManager getPersistenceManager(
        ServletContext context) {
        
        // Prepare an application scope PersistenceManagerFactory when needed:
        if (pmf == null) {
            Properties properties = new Properties();
            properties.setProperty(
                "javax.jdo.PersistenceManagerFactoryClass",
                "com.objectdb.jdo.PMF" // always the same for ObjectDB
            );
            properties.setProperty(
                "javax.jdo.option.ConnectionURL",
                context.getRealPath("/WEB-INF/db/guestbook.odb")
                    // path is relative to web application root
            );
            pmf = JDOHelper.getPersistenceManagerFactory(properties);
        }
        
        // Return a request scope PersistenceManager instance:
        return pmf.getPersistenceManager();
    }
    private static PersistenceManagerFactory pmf; // holds a global PMF
}
