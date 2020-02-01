// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.step3;

import java.util.*;
import javax.jdo.*;
import directory.pc.*;

/**
 * The Retrieve class shows how to use JDOQL queries to retrieve objects,
 * and how to use extents to iterate over persistent objects.
 */
public class Retrieve {
    
    /**
     * Runs the example.
     */
    public void run() {
        
        // Obtain a PersistenceManager instance:
        PersistenceManager pm =
            directory.Utilities.getPersistenceManager(false);
        
        // Use Extents and Queries:
        try {
            printAll(Category.class, pm);
            printAll(Item.class, pm);
            executeQuery1(pm);
            executeQuery2(pm);
            executeQuery3(pm);
            executeQuery4(pm);
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
     * Prints all the instances of a specified class.
     */
    private static void printAll(Class cls, PersistenceManager pm) {
        Extent extent = pm.getExtent(cls, true);
        Iterator itr = extent.iterator();
        printCollection("All " + cls.getName() + " instances:", itr);
        extent.close(itr);
    }
    
    /**
     * Prints a collection content using a specified iterator.
     */
    private static void printCollection(String title, Iterator itr) {
        System.out.println(title);
        while (itr.hasNext())
            System.out.println("  " + itr.next());
    }

    /**
     * Retrieves all the books with more than 300 pages.
     */
    private void executeQuery1(PersistenceManager pm) {
        Query query = pm.newQuery(Book.class, "pages > 300");
        Collection results = (Collection)query.execute();
        printCollection("Books with more than 300 pages:", results.iterator());
        query.closeAll();
    }

    /**
     * Retrieves all the items in small categories.
     */
    private static void executeQuery2(PersistenceManager pm) {
        Query query = pm.newQuery(Item.class, "parent.elements.size() <= size");
        query.declareParameters("int size");
        Collection results = (Collection)query.execute(new Integer(3));
        printCollection("Items in small categories:", results.iterator());
        query.closeAll();
    }

    /**
     * Retrieves all the books published in 2002.
     */
    private static void executeQuery3(PersistenceManager pm) {
        Query query = pm.newQuery(Book.class);
        query.setFilter("date >= fromDate && date <= toDate");
        query.declareImports("import java.util.Date");
        query.declareParameters("Date fromDate, Date toDate");
        query.setOrdering("date descending"); // order from new to old
        Collection results =
            (Collection)query.execute(getYearDate(2002), getYearDate(2003));
        printCollection("Books published in 2002:", results.iterator());
        query.closeAll();
    }
    
    /**
     * Returns a Date representing the first moment of a specified year.
     */
    private static Date getYearDate(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * Retrieves all the categories containing an item with a short name.
     */
    private static void executeQuery4(PersistenceManager pm) {
        Query query = pm.newQuery(Category.class);
        query.declareParameters("int length");
        query.declareVariables("Item item");
        query.setFilter(
            "elements.contains(item) && item.name.length() <= length");
        Collection results = (Collection)query.execute(new Integer(10));
        printCollection("Categories containing an item with a short name: ",
            results.iterator());
        query.closeAll();
    }
}
