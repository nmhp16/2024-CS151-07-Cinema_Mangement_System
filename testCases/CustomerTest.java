// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.CustomerTest
package testCases;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import src.*;

public class CustomerTest {

    private Customer customer;
    private Transaction transaction1;
    private Transaction transaction2;
    private Ticket ticket;
    private Movie movie;
    private Showtime showtime;

    @Before
    public void setUp() {
        Customer.resetCustomerCount();

        // Setup objects
        customer = new Customer("John Doe", "johndoe@example.com", "1234567890");
        ticket = new Ticket("VIP", "Adult", 1); // Mocked Ticket
        movie = new Movie(1, "Movie A", "Action"); // Mocked Movie
        showtime = new Showtime(1, "12:00 PM"); // Mocked Showtime

        // Initialize selectedItems for food and drinks
        List<FoodAndDrink> selectedItems = new ArrayList<>();
        selectedItems.add(new FoodAndDrink("Popcorn", 5.00));
        selectedItems.add(new FoodAndDrink("Drink", 3.00));

        // Initialize transactions and set customer
        transaction1 = new Transaction(movie, showtime, ticket, customer, selectedItems);
        transaction2 = new Transaction(movie, showtime, ticket, customer, selectedItems);
    }

    @Test
    public void testConstructor_WithNameEmailPhone() {
        Customer c = new Customer("Alice", "alice@example.com", "0987654321");
        Assert.assertEquals("Alice", c.getName());
        Assert.assertEquals("alice@example.com", c.getEmail());
        Assert.assertEquals("0987654321", c.getPhone());
    }

    @Test
    public void testConstructor_WithNameEmailOnly() {
        Customer c = new Customer("Bob", "bob@example.com");
        Assert.assertEquals("Bob", c.getName());
        Assert.assertEquals("bob@example.com", c.getEmail());
        Assert.assertNull(c.getPhone());
    }

    @Test
    public void testValidatePhone_ValidPhone() {
        Assert.assertTrue(customer.validatePhone("1234567890"));
    }

    @Test
    public void testValidatePhone_InvalidPhone() {
        Assert.assertFalse(customer.validatePhone("12345")); // Invalid phone number
    }

    @Test
    public void testUpdateProfile() {
        customer.updateProfile("Jane Doe", "janedoe@example.com", "0987654321");
        Assert.assertEquals("Jane Doe", customer.getName());
        Assert.assertEquals("janedoe@example.com", customer.getEmail());
        Assert.assertEquals("0987654321", customer.getPhone());
    }

    @Test
    public void testAddTransaction() {
        customer.addTransaction(transaction1);
        customer.addTransaction(transaction2);

        List<Transaction> transactions = customer.geTransactionsHistory();

        Assert.assertEquals(2, transactions.size());
        Assert.assertEquals(transaction1, transactions.get(0));
        Assert.assertEquals(transaction2, transactions.get(1));
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
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        customer.displayTransactionHistory();

        // Verify that transaction history and movie details are printed
        String output = outContent.toString();
        Assert.assertTrue(output.contains("Customer receipt:"));
        Assert.assertTrue(output.contains("Movie A")); // Assuming "Movie A" appears in the receipt

        // Reset the output stream
        System.setOut(System.out);
    }

    @Test
    public void testDisplayReservedTickets() {
        customer.addTransaction(transaction1); // This transaction contains a reserved ticket.
        customer.displayReservedTickets();
        // This will output the summary for reserved tickets
    }

    @Test
    public void testAddCustomer() {
        // Adding a blank line to the input string to simulate the buffer clearing
        String input = "\nCharlie\ncharlie@example.com\n1234567890\n";
        Scanner scanner = new Scanner(input);

        Customer newCustomer = new Customer();
        newCustomer.addCustomer(scanner);

        Assert.assertEquals("Charlie", newCustomer.getName());
        Assert.assertEquals("charlie@example.com", newCustomer.getEmail());
        Assert.assertEquals("1234567890", newCustomer.getPhone());
    }

    @Test
    public void testCustomerCreationLimit() {
        List<Customer> customers = new ArrayList<>();

        // Create 100 Customer instances successfully
        for (int i = 0; i < 98; i++) { // + 1 from set up
            customers.add(new Customer());
        }

        // Ensure we can still create the 100th transaction
        Customer hundredthCustomer = new Customer();
        assertNotNull("100th Customer should be created successfully", hundredthCustomer);
    }

    @Test
    public void testCustomerInstanceLimit() {
        List<Customer> customers = new ArrayList<>();

        // Create 100 Customer instances successfully
        for (int i = 0; i < 99; i++) { // + 1 from set up
            customers.add(new Customer());
        }

        // Try to create the 101st instance and expect an IllegalStateException
        try {
            new Customer();
            fail("Expected IllegalStateException for creating more than 100 Customer instances.");
        } catch (IllegalStateException e) {
            assertEquals("Maximum number of Customer instances (100) reached.", e.getMessage());
        }
    }
}
