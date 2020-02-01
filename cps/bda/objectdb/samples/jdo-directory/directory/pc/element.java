// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.pc;

import javax.jdo.*;

/**
 * The Element abstract class is a super class of both Item and Category.
 */
public abstract class Element implements InstanceCallbacks {

    // Persistent Fields:
    private String name;
    private Category parent;

    // Constructors:
    protected Element() {
    }
    protected Element(String name) {
        this.name = name;
    }
    
    // JDO Callbacks:
    public void jdoPreDelete() {
        if (parent != null)
            parent.getElements().remove(this);
    }
    public void jdoPostLoad() {}
    public void jdoPreStore() {}
    public void jdoPreClear() {}

    // Accessor Methods:
    public String getName() {
        return name;
    }
    public Category getParent() {
        return parent;
    }
    
    // Mutator Methods:
    public void setName(String name) {
        this.name = name;
    }
    public void setParent(Category parent) {
        this.parent = parent;
        parent.getElements().add(this);
    }

    // String Representation:
    public String toString() {
        return (name != null) ? name : "<new " + getClass().getName() + ">";
    }
}
