// ObjectDB for Java/JDO Samples - Person
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

import java.util.*;
import javax.jdo.*;

/**
 * Main of the JDO Person sample.
 */
public class Main {

    public static void main(String[] args) {
        
        // Check the arguments:
        if (args.length != 3)
        {
            System.out.println(
               "Usage: java Main <first name> <last name> <age>");
            System.exit(1);
        }

        try {
            // Obtain a database connection:
            Properties properties = new Properties();
            properties.setProperty(
                "javax.jdo.PersistenceManagerFactoryClass",
                "com.objectdb.jdo.PMF");
            properties.setProperty(
                "javax.jdo.option.ConnectionURL", "persons.odb");
            PersistenceManagerFactory pmf =
                JDOHelper.getPersistenceManagerFactory(
                    properties, JDOHelper.class.getClassLoader());
            PersistenceManager pm = pmf.getPersistenceManager();

            try {
                // Begin the transaction:
                pm.currentTransaction().begin();

                // Create and store a new Person instance:
                Person person = new Person(
                    args[0], args[1], Integer.parseInt(args[2]));
                pm.makePersistent(person);

                // Print all the Persons in the database:
                Extent extent = pm.getExtent(Person.class, false);
                Iterator itr = extent.iterator();
                while (itr.hasNext())
                    System.out.println(itr.next());
                extent.closeAll();

                // Comnmit the transaction:
                pm.currentTransaction().commit();
            }
            finally {
                // Close the database and active transaction:
                if (pm.currentTransaction().isActive())
                    pm.currentTransaction().rollback();
                if (!pm.isClosed())
                    pm.close();
            }
        }
        // Handle both JDOException and NumberFormatException:
        catch (Exception x) {
            System.err.println("Error: " + x.getMessage());
        }
    }
}
