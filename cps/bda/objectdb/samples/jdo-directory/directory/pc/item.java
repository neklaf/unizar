// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.pc;

/**
 * The Item class represents a simple link item in the directory.
 */
public class Item extends Element {

    // Persistent Fields:
    private String link;

    // Constructors:
    public Item() {}
    public Item(String name, String link) {
        super(name);
        this.link = link;
    }
    
    // Accessor Methods:
    public String getLink() {
        return link;
    }
}
