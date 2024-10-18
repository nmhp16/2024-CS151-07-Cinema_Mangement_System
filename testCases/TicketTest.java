// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.TicketTest
package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.Ticket;
import src.ReservationException;

import java.util.List;
import java.util.ArrayList;

public class TicketTest {
    private Ticket ticket;

    @Before
    public void setUp() {
        Ticket.resetTicketCount();

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

    @Test
    public void testTicketCreationLimit() {
        List<Ticket> tickets = new ArrayList<>();

        // Create 100 instances successfully
        for (int i = 0; i < 98; i++) { // + 1 from set up
            tickets.add(new Ticket());
        }

        // Ensure we can still create the 100th
        Ticket hundredthTicket = new Ticket();
        assertNotNull("100th Ticket should be created successfully", hundredthTicket);
    }

    @Test
    public void testTicketInstanceLimit() {
        List<Ticket> tickets = new ArrayList<>();

        // Create 100 instances successfully
        for (int i = 0; i < 99; i++) { // + 1 from set up
            tickets.add(new Ticket());
        }

        // Try to create the 101st instance and expect an IllegalStateException
        try {
            new Ticket();
            fail("Expected IllegalStateException for creating more than 100 Ticket instances.");
        } catch (IllegalStateException e) {
            assertEquals("Maximum number of Ticket instances (100) reached.", e.getMessage());
        }
    }
}