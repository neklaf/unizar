// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.step2;

import java.util.*;
import javax.jdo.*;
import directory.pc.*;

/**
 * The Update class shows how to update and delete persistent objects, and
 * how to retrieve objects using queries and object IDs.
 */
public class Update {
    
    /**
     * Runs the example.
     */
    public void run() {
        
        // Obtain a PersistenceManager instance:
        PersistenceManager pm =
            directory.Utilities.getPersistenceManager(false);
        
        // Do some operations:
        try {
            Object oid = addNewBook(pm);
            updateBook(pm, oid);
            deleteBook(pm, oid);
            System.out.println("Operations have been completed...");
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
     * Adds a new book.
     *
     * @return the persistent object ID.
     */
    private static Object addNewBook(PersistenceManager pm) {

        // Retrieve the Books category:
        Query query = pm.newQuery(Category.class, "this.name == name");
        query.declareParameters("String name");
        Iterator itr = ((Collection)query.execute("Books")).iterator();
        if (!itr.hasNext())
            throw new RuntimeException("Category 'Books' cannot be found.");
        Category books = (Category)itr.next();
        query.closeAll();

        // Add a new book:
        pm.currentTransaction().begin();
        Book newBook = new Book("JDO in a Nutshell", 992,
            "http://www.oreilly.com/catalog/javanut4", "David Flanagan",
            "0596002831", "O'Reilly & Associates", 2002, 3);
        books.add(newBook);
        pm.currentTransaction().commit();
        
        // Return the new object ID:
        return JDOHelper.getObjectId(newBook);
    }

    /**
     * Updates the name field in a specified persistent object.
     */
    private static void updateBook(PersistenceManager pm, Object oid) {
        pm.currentTransaction().begin();
        Book book = (Book)pm.getObjectById(oid, true);
        book.setName("Java in a Nutshell");
        pm.currentTransaction().commit();
    }

    /**
     * Deletes a specified persistent object.
     */
    private static void deleteBook(PersistenceManager pm, Object oid) {
        pm.currentTransaction().begin();
        Book book = (Book)pm.getObjectById(oid, true);
        pm.deletePersistent(book);
        pm.currentTransaction().commit();
    }
}
