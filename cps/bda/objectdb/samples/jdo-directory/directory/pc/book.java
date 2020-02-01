// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.pc;

import java.util.*;

/**
 * The Book class represents a book item in the directory.
 */
public class Book extends Item {

    // Persistent Fields:
    private String isbn;
    private String authors;
    private String publisher;
    private Date date;
    private int pages;

    // Constructors:
    public Book() {}
    public Book(String name, int pages, String link, String authors,
                String isbn, String publisher, int year, int month) {
        super(name, link);
        this.pages = pages;
        this.isbn = isbn;
        this.authors = authors;
        this.publisher = publisher;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, 1);
        this.date = calendar.getTime();
    }
    
    // Accessor Methods:
    public int getPages() {
        return pages;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getAuthors() {
        return authors;
    }
    public String getPublisher() {
        return publisher;
    }
    public Date getDate() {
        return date;
    }
    public int getYear() {
        if (date == null)
            return 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    // String Representation:
    public String toString() {
        if (getName() == null)
            return super.toString();
        else
            return getAuthors() + ", " + getName() + ", " +
                getPublisher() + " " + getYear();
    }
}
