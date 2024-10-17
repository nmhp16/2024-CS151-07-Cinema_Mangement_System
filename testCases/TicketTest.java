import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Ticket;
import src.ReservationException;

public class TicketTest {
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        // Initialize a ticket before each test case
        ticket = new Ticket("Economy", "Adult", 12, 100.0);
    }

    @Test
    public void testTicketInitialization() {
        assertEquals("Economy", ticket.getSeatType());
        assertEquals("Adult", ticket.getAgePricing());
        assertEquals(12, ticket.getSeatNumber());
        assertEquals(100.0, ticket.getPrice());
        assertFalse(ticket.isReserved());
    }

    @Test
    public void testApplyDiscount() {
        double discountedPrice = ticket.applyDiscount(100.0, 10);
        assertEquals(90.0, discountedPrice, 0.01);
    }

    @Test
    public void testReserveTicket() throws ReservationException {
        assertFalse(ticket.isReserved());
        ticket.reserveTicket(ticket);
        assertTrue(ticket.isReserved());
        assertTrue(Ticket.getTickets().contains(ticket));
    }

    @Test
    public void testReserveTicketAlreadyReserved() {
        try {
            ticket.reserveTicket(ticket);
            ticket.reserveTicket(ticket);
            fail("Expected ReservationException");
        } catch (ReservationException e) {
            assertEquals("Ticket is already reserved.", e.getMessage());
        }
    }

    @Test
    public void testCancelReservation() throws ReservationException {
        ticket.reserveTicket(ticket);
        ticket.cancelReservation(ticket);
        assertFalse(ticket.isReserved());
        assertFalse(Ticket.getTickets().contains(ticket));
    }

    @Test
    public void testCancelReservationNotReserved() {
        ticket.cancelReservation(ticket);
        assertFalse(ticket.isReserved());
    }

    @Test
    public void testFindTicketById() {
        int ticketId = ticket.getTicketId();
        assertNotNull(Ticket.findTicketById(ticketId));
        assertEquals(ticket, Ticket.findTicketById(ticketId));
    }

    @Test
    public void testTicketIdExists() {
        int ticketId = ticket.getTicketId();
        assertTrue(Ticket.ticketIdExists(ticketId));
        assertFalse(Ticket.ticketIdExists(ticketId + 1)); // Non-existent ID
    }

    @Test
    public void testRemoveTicketId() {
        int currentId = ticket.getTicketId();
        assertEquals(currentId - 1, ticket.removeTicketId());
    }
}
