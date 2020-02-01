// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.pc;

import java.util.ArrayList;
import javax.jdo.*;

/**
 * The Category class represents a category in the directory.
 */
public class Category extends Element {

    // Persistent Fields:
    private ArrayList elements = new ArrayList();

    // Constructors:
    public Category() {}
    public Category(String name) {
        super(name);
    }
    
    // JDO Callbacks:
    public void jdoPreDelete() {
        JDOHelper.getPersistenceManager(this).deletePersistentAll(elements);
        super.jdoPreDelete();
    }
    public void jdoPostLoad() {}
    public void jdoPreStore() {}
    public void jdoPreClear() {}

    // Accessor Methods:
    public ArrayList getElements() {
        return elements;
    }
    
    // Mutator Methods:
    public void add(Element element) {
        element.setParent(this);
    }
}
