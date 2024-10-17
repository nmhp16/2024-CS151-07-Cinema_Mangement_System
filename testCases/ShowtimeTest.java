import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Showtime;

public class ShowtimeTest {
    private Showtime showtime;

    @BeforeEach
    public void setUp() {
        // Initialize a Showtime instance before each test case
        showtime = new Showtime(1, "18:00");
    }

    @Test
    public void testShowtimeInitialization() {
        assertEquals(1, showtime.getShowtimeId());
        assertEquals("18:00", showtime.getTime());
        assertEquals(30, showtime.getAvailableSeats()); // All seats should be available initially
    }

    @Test
    public void testSelectSeatValid() {
        boolean seatSelected = showtime.selectSeat(5, "VIP");
        assertTrue(seatSelected);
        assertEquals(29, showtime.getAvailableSeats()); // One seat should be taken
        assertFalse(showtime.isSeatTaken(5, "VIP")); // Seat 5 should now be taken
    }

    @Test
    public void testSelectSeatInvalidSeatNumber() {
        boolean seatSelected = showtime.selectSeat(31, "Regular");
        assertFalse(seatSelected);
        assertEquals(30, showtime.getAvailableSeats()); // No seats should be taken
    }

    @Test
    public void testSelectSeatInvalidCategory() {
        boolean seatSelectedVIP = showtime.selectSeat(11, "VIP"); // Out of VIP range
        boolean seatSelectedPremium = showtime.selectSeat(21, "Premium"); // Out of Premium range
        boolean seatSelectedRegular = showtime.selectSeat(10, "Regular"); // Out of Regular range

        assertFalse(seatSelectedVIP);
        assertFalse(seatSelectedPremium);
        assertFalse(seatSelectedRegular);
        assertEquals(30, showtime.getAvailableSeats()); // No seats should be taken
    }

    @Test
    public void testSelectSeatAlreadyTaken() {
        // Reserve a seat first
        showtime.selectSeat(5, "VIP");
        // Try to reserve the same seat again
        boolean seatSelectedAgain = showtime.selectSeat(5, "VIP");

        assertFalse(seatSelectedAgain);
        assertEquals(29, showtime.getAvailableSeats()); // Only one seat should be taken
    }

    @Test
    public void testIsSeatTaken() {
        // Initially, all seats should be available
        assertFalse(showtime.isSeatTaken(5, "VIP"));
        assertFalse(showtime.isSeatTaken(15, "Premium"));
        assertFalse(showtime.isSeatTaken(25, "Regular"));

        // Reserve a seat and check again
        showtime.selectSeat(5, "VIP");
        assertTrue(showtime.isSeatTaken(5, "VIP"));
    }

@Test
    public void testGetAvailableSeats() {
        // Initially, all 30 seats should be available
        assertEquals(30, showtime.getAvailableSeats());

        // Reserve a few seats
        showtime.selectSeat(1, "VIP");
        s
