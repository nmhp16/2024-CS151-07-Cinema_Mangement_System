// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.TransactionTest
package testCases;

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
        // Create mock data for testing
        movie = new Movie(1, "Movie A", "Action");
        showtime = new Showtime(1, "12:00 PM"); // Mocked Showtime
        ticket = new Ticket("VIP", "Adult", 1); // Mocked Ticket
        customer = new Customer("John Doe", "john@example.com", "123-456-7890");

        selectedItems = new ArrayList<>();
        selectedItems.add(new FoodAndDrink("Popcorn", 5.99));
        selectedItems.add(new FoodAndDrink("Soda", 2.99));

        transaction = new Transaction(movie, showtime, ticket, customer, selectedItems);
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
    }

    @Test
    public void testProcessRefund_CashTransaction() {
        transaction.selectTransactionType("Cash");

        // Process refund
        transaction.processRefund();

        // The output should state that cash transactions cannot be refunded
    }

    @Test
    public void testTotalItems() {
        int totalItems = transaction.getTotalItems();
        assertEquals("Total items should match the selected items list size", 2, totalItems);
    }

    @Test

    public void testTransactionInstanceLimit() {
        List<Transaction> transactions = new ArrayList<>();

        // Create 99 transactions successfully
        for (int i = 0; i < 99; i++) {
            transactions.add(new Transaction(movie, showtime, ticket, customer, selectedItems));
        }
    }

    public void testTransactionCreationLimit() {
        // Create 100 transactions and verify they are created successfully
        for (int i = 0; i < 100; i++) {
            Transaction newTransaction = new Transaction(movie, showtime, ticket, customer, selectedItems);
            assertNotNull("Transaction should be created", newTransaction);
        }

        // Try to create the 101st transaction and verify it fails (prints the message
        // and doesn't create)
        Transaction excessTransaction = new Transaction(movie, showtime, ticket, customer, selectedItems);
        assertNull("The 101st transaction should not be created", excessTransaction.getCustomer());

    }
}
