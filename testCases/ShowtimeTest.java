package testCases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.Showtime;

public class ShowtimeTest {
    private Showtime showtime;

    @Before
    public void setUp() {
        // Initialize a Showtime instance before each test case
        showtime = new Showtime(1, "18:00");
    }

    @Test
    public void testShowtimeInitialization() {
        Assert.assertEquals(1, showtime.getShowtimeId());
        Assert.assertEquals("18:00", showtime.getTime());
        Assert.assertEquals(30, showtime.getAvailableSeats()); // All seats should be available initially
    }

    @Test
    public void testSelectSeatValid() {
        boolean seatSelected = showtime.selectSeat(5, "VIP");
        Assert.assertTrue(seatSelected); // Check if the seat was successfully selected

        // After selecting the seat, it should be marked as unavailable
        Assert.assertEquals(29, showtime.getAvailableSeats()); // One seat should now be unavailable (taken)

        // Verify that seat 5 is indeed taken now
        Assert.assertTrue(showtime.isSeatTaken(5, "VIP"));
    }

    @Test
    public void testSelectSeatInvalidSeatNumber() {
        boolean seatSelected = showtime.selectSeat(31, "Regular");
        Assert.assertFalse(seatSelected);
        Assert.assertEquals(30, showtime.getAvailableSeats()); // No seats should be taken
    }

    @Test
    public void testSelectSeatInvalidCategory() {
        boolean seatSelectedVIP = showtime.selectSeat(11, "VIP"); // Out of VIP range
        boolean seatSelectedPremium = showtime.selectSeat(21, "Premium"); // Out of Premium range
        boolean seatSelectedRegular = showtime.selectSeat(10, "Regular"); // Out of Regular range

        Assert.assertFalse(seatSelectedVIP);
        Assert.assertFalse(seatSelectedPremium);
        Assert.assertFalse(seatSelectedRegular);
        Assert.assertEquals(30, showtime.getAvailableSeats()); // No seats should be taken
    }

    @Test
    public void testSelectSeatAlreadyTaken() {
        // Reserve a seat first
        showtime.selectSeat(5, "VIP");
        // Try to reserve the same seat again
        boolean seatSelectedAgain = showtime.selectSeat(5, "VIP");

        Assert.assertFalse(seatSelectedAgain);
        Assert.assertEquals(29, showtime.getAvailableSeats()); // Only one seat should be taken
    }

    @Test
    public void testIsSeatTaken() {
        // Initially, all seats should be available
        Assert.assertFalse(showtime.isSeatTaken(5, "VIP"));
        Assert.assertFalse(showtime.isSeatTaken(15, "Premium"));
        Assert.assertFalse(showtime.isSeatTaken(25, "Regular"));

        // Reserve a seat and check again
        showtime.selectSeat(5, "VIP");
        Assert.assertTrue(showtime.isSeatTaken(5, "VIP"));
    }

    @Test
    public void testGetAvailableSeats() {
        // Initially, all 30 seats should be available
        Assert.assertEquals(30, showtime.getAvailableSeats());

        // Reserve a few seats
        showtime.selectSeat(1, "VIP");
    }
}
