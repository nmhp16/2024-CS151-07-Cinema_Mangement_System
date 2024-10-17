package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

public class CustomerTest {

    private Customer customer;
    private Transaction transaction1;
    private Transaction transaction2;
    private Ticket ticket;
    private Movie movie;
    private Showtime showtime;

    @BeforeEach
    public void setUp() {
        // Setup objects
        customer = new Customer("John Doe", "johndoe@example.com", "1234567890");
        ticket = new Ticket("T001", 15.50, true); // Mocked Ticket
        movie = new Movie("Avengers: Endgame"); // Mocked Movie
        showtime = new Showtime("S001", "12:00 PM"); // Mocked Showtime
        
        transaction1 = new Transaction(movie, showtime, ticket);
        transaction2 = new Transaction(movie, showtime, ticket);
    }

    @Test
    public void testConstructor_WithNameEmailPhone() {
        Customer c = new Customer("Alice", "alice@example.com", "0987654321");
        assertEquals("Alice", c.getName());
        assertEquals("alice@example.com", c.getEmail());
        assertEquals("0987654321", c.getPhone());
    }

    @Test
    public void testConstructor_WithNameEmailOnly() {
        Customer c = new Customer("Bob", "bob@example.com");
        assertEquals("Bob", c.getName());
        assertEquals("bob@example.com", c.getEmail());
        assertNull(c.getPhone());
    }

    @Test
    public void testValidatePhone_ValidPhone() {
        assertTrue(customer.validatePhone("1234567890"));
    }

    @Test
    public void testValidatePhone_InvalidPhone() {
        assertFalse(customer.validatePhone("12345")); // Invalid phone number
    }

    @Test
    public void testUpdateProfile() {
        customer.updateProfile("Jane Doe", "janedoe@example.com", "0987654321");
        assertEquals("Jane Doe", customer.getName());
        assertEquals("janedoe@example.com", customer.getEmail());
        assertEquals("0987654321", customer.getPhone());
    }

    @Test
    public void testAddTransaction() {
        customer.addTransaction(transaction1);
        customer.addTransaction(transaction2);

        List<Transaction> transactions = customer.getTransactionHistory();

        assertEquals(2, transactions.size());
        assertEquals(transaction1, transactions.get(0));
        assertEquals(transaction2, transactions.get(1));
    }

    @Test
    public void testDisplayTransactionHistory_NoHistory() {
        customer.displayTransactionHistory(); // This will print "No transaction history available."
    }

    @Test
    public void testDisplayTransactionHistory_WithHistory() {
        customer.addTransaction(transaction1);
        customer.addTransaction(transaction2);

        // Capture the output to verify the transaction history is displayed correctly
        customer.displayTransactionHistory();
    }

    @Test
    public void testDisplayReservedTickets() {
        customer.addTransaction(transaction1); // This transaction contains a reserved ticket.
        customer.displayReservedTickets();
        // This will output the summary for reserved tickets
    }

    @Test
    public void testAddCustomer() {
        String input = "Charlie\ncharlie@example.com\n1234567890\n";
        Scanner scanner = new Scanner(input);

        Customer newCustomer = new Customer();
        newCustomer.addCustomer(scanner);

        assertEquals("Charlie", newCustomer.getName());
        assertEquals("charlie@example.com", newCustomer.getEmail());
        assertEquals("1234567890", newCustomer.getPhone());
    }
}
