package src;

/**
 * Abstract class representing a person with a name and email.
 * This class provides a structure for other classes to inherit.
 */
public abstract class Person {
    protected String name;
    protected String email;

    /**
     * Default constructor.
     */
    public Person() {
    }

    /**
     * Constructor with name and email parameters.
     * 
     * @param name  The person's name.
     * @param email The person's email.
     */
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Abstract method to display the person's information.
     * Subclasses must implement this method.
     */
    public abstract void displayInfo();
}
