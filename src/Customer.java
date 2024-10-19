package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// The Customer class extends the Person class and handles customer-specific details
public class Customer extends Person {
    private String phone;
    private List<Transaction> transactionHistory = new ArrayList<>();

    private static int instanceCount = 0; // Class-level variable to track instances
    private static final int MAX_INSTANCES = 100; // Maximum allowed instances

    // Constructor
    public Customer() {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Customer instances (" + MAX_INSTANCES + ") reached.");
        }
        instanceCount++; // Increment the instance count on successful creation
    }

    public Customer(String name, String email, String phone) {
        super(name, email);
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Customer instances (" + MAX_INSTANCES + ") reached.");
        }
        instanceCount++; // Increment the instance count on successful creation
        this.phone = phone;
        this.transactionHistory = new ArrayList<>();
    }

    public Customer(String name, String email) {
        this(name, email, null); // Default phone to null if not provided
    }

    // Method to reset the Customer count (for testing purposes)
    public static void resetCustomerCount() {
        instanceCount = 0;
    }

    // Methods
    // Implementing abstract method from Person class, overridden to display
    // customer details
    @Override
    public void displayInfo() {
        System.out.println(
                "Customer Info - Name: " + name + ", Email: " + email + (phone != null ? ", Phone: " + phone : ""));
    }

    // Validate the phone number
    public boolean validatePhone(String phone) {
        return phone != null && phone.matches("\\d{10}"); // Check if phone number not null and contain 10 digits
    }

    // Method to update customer profile details
    public void updateProfile(String newName, String newEmail, String newPhone) {
        this.name = newName;
        this.email = newEmail;
        this.phone = newPhone;
        System.out.println("Profile updated successfully.");
    }

    // Search customer transaction history
    public void searchTransactionHistory() {
        displayInfo(); // Display customer information

        System.out.println();
        displayReservedTickets(); // Display reserved tickets

        displayTransactionHistory(); // Display transaction history
    }

    // Display customer transaction history
    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) { // Check if there are any transaction
            System.out.println("No transaction history available.");
            return;
        }
        // Display the number of transactions and the transaction receipts
        System.out.println(transactionHistory.size() + " Transaction History: ");
        System.out.println("\nCustomer receipt: ");
        for (Transaction transaction : transactionHistory) {
            transaction.printReceipt(); // Print transaction receipt
        }
    }

    // Method to check reserved tickets
    public void displayReservedTickets() {
        for (Transaction transaction : transactionHistory) {
            Ticket ticket = transaction.getTicket(); // Get ticket associated with transaction

            if (ticket.isReserved()) { // If reserved display detail
                System.out.println("Reserved Ticket: ");
                ticket.getSummary(); // Get ticket summary
            }
        }
    }

    // Add transaction to customer transaction history
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    // Get customer information and add it using scanner
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

    public List<Transaction> geTransactionsHistory() {
        return transactionHistory;
    }
}
