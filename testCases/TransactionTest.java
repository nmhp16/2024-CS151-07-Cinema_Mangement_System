// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.TransactionTest

package testCases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TransactionTest {

    private Transaction transaction;
    private Movie movie;
    private Showtime showtime;
    private Ticket ticket;
    private Customer customer;
    private List<FoodAndDrink> selectedItems;

    @Before
    public void setUp() {
        // Reset the transaction count before each test
        Transaction.resetTransactionCount(); // Ensure this method exists in your Transaction class

        // Create mock data for testing
        movie = new Movie(1, "Movie A", "Action");
        showtime = new Showtime(1, "12:00 PM"); // Mocked Showtime
        ticket = new Ticket("VIP", "Adult", 1); // Mocked Ticket
        customer = new Customer("John Doe", "john@example.com", "123-456-7890");

        selectedItems = new ArrayList<>();
        selectedItems.add(new FoodAndDrink("Popcorn", 5.99));
        selectedItems.add(new FoodAndDrink("Soda", 2.99));

        // Create the transaction
        transaction = new Transaction(movie, showtime, ticket, customer, selectedItems);
    }

    @After
    public void tearDown() {
        // Optionally, perform cleanup if necessary
    }

    @Test
    public void testProcessTransaction() {
        transaction.processTransaction(customer, movie, showtime, ticket, selectedItems);

        assertEquals("Customer should be set correctly", customer, transaction.getCustomer());
        assertEquals("Movie should be set correctly", movie, transaction.getMovie());
        assertEquals("Showtime should be set correctly", showtime, transaction.getShowtime());
        assertEquals("Ticket should be set correctly", ticket, transaction.getTicket());
        assertEquals("Selected items should be set correctly", selectedItems.size(), transaction.getItems().size());
    }

    @Test
    public void testCalculateTotalCost() {
        double totalCost = transaction.calculateTotalCost();
        double expectedCost = ticket.getPrice() + selectedItems.get(0).getPrice() + selectedItems.get(1).getPrice();

        assertEquals("Total cost should be calculated correctly", expectedCost, totalCost, 0.01);
    }

    @Test
    public void testCardValidation_Success() {
        transaction.selectTransactionType("Credit Card");
        transaction.inputTransactionInfo("1234567890");

        assertFalse("Hold status should be false for valid card", transaction.isHoldStatus());
    }

    @Test
    public void testCardValidation_Failure() {
        transaction.selectTransactionType("Credit Card");
        transaction.inputTransactionInfo("12345"); // Invalid card number

        assertTrue("Hold status should be true for invalid card", transaction.isHoldStatus());
    }

    @Test
    public void testGetFormattedTransactionTime() {
        String formattedTime = transaction.getFormattedTransactionTime();
        assertNotNull("Formatted transaction time should not be null", formattedTime);
        assertTrue("Formatted transaction time should be in the correct format",
                formattedTime.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    public void testPrintReceipt() {
        transaction.printReceipt();

        // This test would check the output manually since printing to console is
        // involved.
        // You can redirect output stream to capture and verify the output.
    }

    @Test
    public void testProcessRefund_CardTransaction() {
        transaction.selectTransactionType("Credit Card");
        transaction.inputTransactionInfo("1234567890");

        // Process refund
        transaction.processRefund();

        // The output should state the transaction has been refunded to the card
        // Consider capturing output for validation if necessary
    }

    @Test
    public void testProcessRefund_CashTransaction() {
        transaction.selectTransactionType("Cash");

        // Process refund
        transaction.processRefund();

        // The output should state that cash transactions cannot be refunded
        // Consider capturing output for validation if necessary
    }

    @Test
    public void testTotalItems() {
        int totalItems = transaction.getTotalItems();
        assertEquals("Total items should match the selected items list size", 2, totalItems);
    }

    @Test
    public void testTransactionCreationLimit() {
        List<Transaction> transactions = new ArrayList<>();

        // Create 99 transactions successfully 98 + 1 from set up
        for (int i = 0; i < 98; i++) {
            transactions.add(new Transaction(movie, showtime, ticket, customer, selectedItems));
        }

        // Ensure we can still create the 100th transaction
        Transaction hundredthTransaction = new Transaction(movie, showtime, ticket, customer, selectedItems);
        assertNotNull("100th Transaction should be created successfully", hundredthTransaction);
    }

    @Test
    public void testTransactionCreationExceedLimit() {
        // Create the maximum number of transactions
        for (int i = 0; i < 99; i++) { // Use 99 + 1 from set up
            new Transaction(movie, showtime, ticket, customer, selectedItems);
        }

        // Attempt to create the 101st transaction
        try {
            new Transaction(movie, showtime, ticket, customer, selectedItems);
            fail("Should have thrown IllegalStateException for exceeding transaction limit");
        } catch (IllegalStateException e) {
            assertEquals("Cannot create more than " + 100 + " instances of Transaction.",
                    e.getMessage());
        }
    }
}
