// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.TicketTest
package testCases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.Ticket;
import src.ReservationException;

public class TicketTest {
    private Ticket ticket;

    @Before
    public void setUp() {
        // Initialize a ticket before each test case
        ticket = new Ticket("Economy", "Adult", 12, 100.0);

    }

    @Test
    public void testTicketInitialization() {
        Assert.assertEquals("Economy", ticket.getSeatType());
        Assert.assertEquals("Adult", ticket.getAgePricing());
        Assert.assertEquals(12, ticket.getSeatNumber());
        Assert.assertFalse(ticket.isReserved());
    }

    @Test
    public void testApplyDiscount() {
        double discountedPrice = ticket.applyDiscount(100.0, 10);
        Assert.assertEquals(90.0, discountedPrice, 0.01);
    }

    @Test
    public void testReserveTicket() throws ReservationException {
        Assert.assertFalse(ticket.isReserved());
        ticket.reserveTicket(ticket);
        Assert.assertTrue(ticket.isReserved());
        Assert.assertTrue(Ticket.getTickets().contains(ticket));
    }

    @Test
    public void testReserveTicketAlreadyReserved() {
        try {
            ticket.reserveTicket(ticket);
            ticket.reserveTicket(ticket); // Trying to reserve an already reserved ticket
            Assert.fail("Expected ReservationException");
        } catch (ReservationException e) {
            Assert.assertEquals("Ticket is already reserved.", e.getMessage());
        }
    }

    @Test
    public void testCancelReservation() throws ReservationException {
        ticket.reserveTicket(ticket);
        ticket.cancelReservation(ticket);
        Assert.assertFalse(ticket.isReserved());
        Assert.assertFalse(Ticket.getTickets().contains(ticket));
    }

    @Test
    public void testCancelReservationNotReserved() {
        ticket.cancelReservation(ticket); // Cancelling a ticket that was never reserved
        Assert.assertFalse(ticket.isReserved());
    }

    @Test
    public void testFindTicketById() {
        ticket.setTicketId(1);
        int ticketId = ticket.getTicketId();
        ticket.reserveTicket(ticket);
        Ticket foundTicket = Ticket.findTicketById(ticketId);
        Assert.assertEquals(ticket, foundTicket); // Ensure the found ticket matches the original
    }

    @Test
    public void testTicketIdExists() {
        ticket.setTicketId(1);
        int ticketId = ticket.getTicketId();
        Assert.assertTrue(Ticket.ticketIdExists(ticketId));
        Assert.assertFalse(Ticket.ticketIdExists(ticketId + 1)); // Non-existent ID
    }

     // New test to ensure no more than 100 tickets can be created
     @Test
     public void testTicketCreationLimit() {
         for (int i = 0; i < 100; i++) {
             Ticket newTicket = new Ticket("Economy", "Adult", i + 1, 100.0);
             // Ensure tickets are created successfully up to the limit
             Assert.assertNotNull(newTicket);  
         
         }
         // Create the 101st ticket and verify it fails
         Ticket excessTicket = new Ticket("Economy", "Adult", 101, 100.0);
         Assert.assertNull(excessTicket.getSeatType());  
        }
}
