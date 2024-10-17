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
            ticket.reserveTicket(ticket);
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
        ticket.cancelReservation(ticket);
        Assert.assertFalse(ticket.isReserved());
    }

    @Test
    public void testFindTicketById() {
        int ticketId = ticket.getTicketId();
        Assert.assertNotNull(Ticket.findTicketById(ticketId));
        Assert.assertEquals(ticket, Ticket.findTicketById(ticketId));
    }

    @Test
    public void testTicketIdExists() {
        int ticketId = ticket.getTicketId();
        Assert.assertTrue(Ticket.ticketIdExists(ticketId));
        Assert.assertFalse(Ticket.ticketIdExists(ticketId + 1)); // Non-existent ID
    }

    @Test
    public void testRemoveTicketId() {
        int currentId = ticket.getTicketId();
        Assert.assertEquals(currentId - 1, ticket.removeTicketId());
    }
}
