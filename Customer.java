import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends Person {
    private String phone;
    private List<Transaction> transactionHistory;

    // Constructor
    public Customer() {
        this.transactionHistory = new ArrayList<>();
    }

    public Customer(String name, String email, String phone) {
        super(name, email);
        this.phone = phone;
        this.transactionHistory = new ArrayList<>();
    }

    public Customer(String name, String email) {
        this(name, email, null); // Default phone to null if not provided
    }

    // Methods
    // Implementing abstract method from Person
    @Override
    public void displayInfo() {
        System.out.println(
                "Customer Info - Name: " + name + ", Email: " + email + (phone != null ? ", Phone: " + phone : ""));
    }

    // Validate the phone number
    public boolean validatePhone() {
        return phone != null && phone.matches("\\d{10}");
    }

    public void updateProfile(String newName, String newEmail, String newPhone) {
        this.name = newName;
        this.email = newEmail;
        this.phone = newPhone;
    }

    public List<Transaction> viewTransactionHistory() {
        return transactionHistory;
    }

    public void purchaseTicket(Movie movie, Showtime showtime, Ticket ticket) {
        // Implement purchase logic
        Transaction transaction = new Transaction(movie, showtime, ticket);
        transactionHistory.add(transaction);
        ticket.reserve(); // Reserve the ticket
        System.out.println("Ticket purchased successfully!");
    }

    public void cancelReservation(Ticket ticket) {
        if (ticket.isReserved()) {
            ticket.cancelReservation(); // Cancel the reservation
            System.out.println("Reservation canceled successfully!");
        } else {
            System.out.println("No reservation found for this ticket.");
        }
    }

    // Scanner still in use, no close() statement
    @SuppressWarnings("resource")
    public void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.println("Enter customer email: ");
        String email = scanner.nextLine();

        System.out.println("Enter customer phone number: ");

        while (this.validatePhone() == false) {
            String phone = scanner.nextLine();
            this.phone = phone;

            if (this.validatePhone() == false) {
                System.out.println("Please enter phone number again with 10 digits:");
            }
        }

        this.name = name;
        this.email = email;

        System.out.println("Customer added: " + this.name + ", " + this.email + ", " + this.phone);

    }

    // Getter and Setter Methods
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
