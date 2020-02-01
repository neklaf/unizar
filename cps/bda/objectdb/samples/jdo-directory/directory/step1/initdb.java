// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.step1;

import javax.jdo.*;
import directory.pc.*;

/**
 * The InitDB class shows how to create a new database and how to store
 * an object graph into the database.
 */
public class InitDB {
    
    /**
     * Runs the example.
     */
    public void run() {
        
        // Obtain a PersistenceManager instance:
        PersistenceManager pm =
            directory.Utilities.getPersistenceManager(true);
        
        // Store an object graph into the database:
        try {
            insertObjects(pm);
            System.out.println("Database is ready now...");
        }
        catch (RuntimeException x) {
            System.out.println("Error: " + x.getMessage());
        }

        // Close the PersistenceManager instance:
        finally {
            if (pm.currentTransaction().isActive())
                pm.currentTransaction().rollback();
            if (!pm.isClosed())
                pm.close();
        }
    }
    
    /**
     * Stores an object graph into the database.
     */
    public static void insertObjects(PersistenceManager pm) {
        
        // Construct the "Consulting & Training" category and items:
        Category consulting = new Category("Consulting & Training");
        consulting.add(new Item("Barry and Associates",
            "http://www.service-architecture.com"));
        consulting.add(new Item("C2B2 Consulting",
            "http://www.c2b2-consulting.co.uk/jdo.php"));
        consulting.add(new Item("Object Identity",
            "http://www.objectidentity.com"));
        consulting.add(new Item("Ogilvie Partners",
            "http://www.ogilviepartners.com/JdoEvents.html"));
        consulting.add(new Item("Smart Soft", "http://www.smart-soft.com"));
            
        // Construct the "Books" category and items:
        Category books = new Category("Books");
        books.add(new Book("Java Data Objects", 264,
            "http://www.ogilviepartners.com/JdoBook.html",
            "Robin Roos", "0321123808", "Addison-Wesley", 2002, 8));
        books.add(new Book("Java Data Objects", 384,
            "http://www.oreilly.com/catalog/jvadtaobj",
            "David Jordan and Craig Russell",
            "0596002769", "O'Reilly & Associates, Inc.", 2003, 4));
        books.add(new Book("Using and Understanding Java Data Objects", 312,
            "http://www.apress.com/book/bookDisplay.html?bID=106",
            "David Ezzio", "1590590430", "Apress", 2003, 4));
        books.add(new Book("Core JDO Book", 256,
            "http://www.theserverside.com/resources/coreJDOreview.jsp?news49",
            "K. McCammon, H. Bobzin, S. Tyagi and M. Vorburger",
            "0131407317", "Sun Press and Prentice Hall", 2003, 5));

        // Construct the root category:
        Category root = new Category("JDO Directory");
        root.add(consulting);
        root.add(books);

        // Store the object graph into the database:
        pm.currentTransaction().begin();
        pm.makePersistent(root); // store the entire object graph
        pm.currentTransaction().commit();
    }
}
