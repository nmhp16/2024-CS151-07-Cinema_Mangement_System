package src;

public abstract class Person {
    protected String name;
    protected String email;

    public Person() {
    }

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // double check with the UML diagram
    // Abstract method to be implemented by subclasses
    public abstract void displayInfo();
}
