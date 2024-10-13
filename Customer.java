import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends Person {
    private String phone;
    private List<Transaction> transactionHistory = new ArrayList<>();

    // Constructor
    public Customer() {
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
    public boolean validatePhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public void updateProfile(String newName, String newEmail, String newPhone) {
        this.name = newName;
        this.email = newEmail;
        this.phone = newPhone;
        System.out.println("Profile updated successfully.");
    }

    public void searchTransactionHistory() {
        displayInfo();

        System.out.println();
        displayReservedTickets();

        displayTransactionHistory();
    }

    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history available.");
            return;
        }

        System.out.println(transactionHistory.size() + " Transaction History: ");
        System.out.println("\nCustomer receipt: ");
        for (Transaction transaction : transactionHistory) {
            transaction.printReceipt();
        }
    }

    // Method to check reserved tickets
    public void displayReservedTickets() {
        for (Transaction transaction : transactionHistory) {
            Ticket ticket = transaction.getTicket();

            if (ticket.isReserved()) {
                System.out.println("Reserved Ticket: ");
                ticket.getSummary();
            }
        }
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void addCustomer(Scanner scanner) {
        scanner.nextLine(); // Clear Input Buffer

        System.out.println("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.println("Enter customer email: ");
        String email = scanner.nextLine();

        System.out.println("Enter customer phone number: ");

        boolean isValid = false;

        while (!isValid) {
            phone = scanner.nextLine();
            if (validatePhone(phone)) {
                isValid = true;
            } else {
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
