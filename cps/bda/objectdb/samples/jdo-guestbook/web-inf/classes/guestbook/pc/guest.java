// ObjectDB for Java/JDO - The Guest Book - A Mimimal JDO Based Web Application
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package guestbook.pc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Guest class represents a single guest in the guest book.
 */
public class Guest {
    
    // Static Date Formatter:
    private static SimpleDateFormat formatter =
        new SimpleDateFormat("MMM dd, yyyy");
    
    // Persistent Fields:
    private String firstName;
    private String lastName;
    private Date date;
    
    // Constructors:
    public Guest() {}
    public Guest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = new Date();
    }
    
    // String Representation:
    public String toString() {
        return firstName + " " + lastName + " (" + formatter.format(date) + ")";
    }
}
