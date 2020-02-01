// ObjectDB for Java/JDO Samples - Person
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

/**
 * The Person persistence capable class
 */
public class Person {

    // Persistent Fields:
    private String firstName;
    private String lastName;
    private int age;
    
    // Constructors:
    public Person() {}
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    
    // String Representation:
    public String toString() {
        return firstName + " " + lastName + " (" + age + ")";
    }
}
