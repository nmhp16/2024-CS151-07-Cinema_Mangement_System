public class Customer extends Person {
    private String phone;

    public Customer () {}
    // Constructor with phone
    public Customer(String name, String email, String phone) {
        super(name, email);
        this.phone = phone;
    }

    // Constructor without phone
    public Customer(String name, String email) {
        this(name, email, null); // Default phone to null if not provided
    }

    // Implementing abstract method from Person
    @Override
    public void displayInfo() {
        System.out.println("Customer Info - Name: " + name + ", Email: " + email + (phone != null ? ", Phone: " + phone : ""));
    }

    // Validate the phone number
    public boolean validatePhone() {
        return phone.matches("\\d{10}");
    }

    // Getter and Setter Methods
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
     
    public String getEmail() { return email; }
    public void setEmail(String email) {this.email = email;} 
}
