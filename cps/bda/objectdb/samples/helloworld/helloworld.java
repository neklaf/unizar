// ObjectDB for Java/JDO Samples - Hello World
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

import java.util.*;
import javax.jdo.*;
import com.objectdb.Utilities;

/**
 * A simple program that manages a list of strings in a database.
 * Detailed explanations on this program can be found in section 2.1 of
 * ObjectDB for Java/JDO developer's guide.
 *
 * Note: This program uses ObjectDB extensions, so it is not JDO portable.
 * Look at JDO Person for a simple JDO portable program.
 */
public class HelloWorld {

    public static void main(String[] args) {
        
        // Create or open a database and begin a transaction:
        PersistenceManager pm = Utilities.getPersistenceManager("hello.odb");
        pm.currentTransaction().begin();
        
        // Obtain a persistent list:
        ArrayList list;
        try {
            // Retrieve the list from the database by its name:
            list = (ArrayList)pm.getObjectById("Hello World", true);
        }
        catch (JDOException x) {
            // If not found - construct and store a new list:
            list = new ArrayList();
            Utilities.bind(pm, list, "Hello World");
        }
        
        // Add a new string to the persistent list:
        list.add("Hello World " + list.size());
        
        // Display the content of the persistent list:
        Iterator itr = list.iterator();
        while (itr.hasNext())
            System.out.println(itr.next());

        // Close the transaction and the database:
        pm.currentTransaction().commit();
        pm.close();
    }
}
