package testCases;

import org.junit.Before;
import org.junit.Test;
import src.Movie;
import src.Showtime;
import src.ShowtimeNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MovieTest {

    private Movie movie;

    @Before
    public void setUp() {
        movie = new Movie(1, "Inception", "Sci-Fi"); // Initialize a new Movie instance before each test
        System.out.println("Movie setup completed.");
    }

    @Test
    public void testAddShowtime() {
        Showtime showtime = new Showtime(1, "10:00 AM", 50); // Example showtime with available seats
        movie.addShowtime(showtime);
        assertEquals(1, movie.getShowtimes().size()); // Ensure showtime is added
        System.out.println("Showtime added successfully. Total showtimes: " + movie.getShowtimes().size());
    }

    @Test
    public void testListGenres() {
        // Redirect output to a ByteArrayOutputStream to capture print statements
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save original output stream
        System.setOut(new PrintStream(outContent)); // Redirect output

        movie.listGenres(); // Test listing genres

        // Expected output
        String expectedOutput = "Genre: Sci-Fi\n";
        String actualOutput = outContent.toString();

        assertEquals(expectedOutput, actualOutput); // Check the output matches

        // Reset the output stream
        System.setOut(originalOut);
        System.out.println("List genres method tested successfully.");
    }

    @Test
    public void testListShowtimes() {
        // Redirect output to a ByteArrayOutputStream to capture print statements
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save original output stream
        System.setOut(new PrintStream(outContent)); // Redirect output

        Showtime showtime1 = new Showtime(1, "10:00 AM", 50);
        Showtime showtime2 = new Showtime(2, "1:00 PM", 0); // Sold out showtime
        movie.addShowtime(showtime1);
        movie.addShowtime(showtime2);
        movie.listShowtimes(); // List showtimes

        // Expected output
        String expectedOutput = "\nShowtimes for Inception:\n" +
                "Genre: Sci-Fi\n\n" +
                "Showtime ID: 1, Time: 10:00 AM\n\n" +
                "Showtime ID: 2, Time: 1:00 PM\n\n";

        String actualOutput = outContent.toString().replace("\r\n", "\n"); // Normalize line endings

        assertEquals(expectedOutput.trim(), actualOutput.trim()); // Check the output matches

        // Reset the output stream
        System.setOut(originalOut);
        System.out.println("List showtimes method tested successfully.");
    }

    @Test
    public void testSelectShowtimeValid() throws ShowtimeNotFoundException {
        Showtime showtime = new Showtime(1, "10:00 AM", 50);
        movie.addShowtime(showtime);
        Showtime selected = movie.selectShowtime(1);
        assertEquals(showtime, selected); // Ensure the selected showtime is correct
        System.out.println("Showtime selected successfully: " + selected.getShowtimeId());
    }

    @Test(expected = ShowtimeNotFoundException.class)
    public void testSelectShowtimeInvalid() throws ShowtimeNotFoundException {
        Showtime showtime = new Showtime(1, "10:00 AM", 50);
        movie.addShowtime(showtime);
        movie.selectShowtime(2); // Attempt to select a showtime that does not exist
    }

    @Test
    public void testRemoveShowtime() throws ShowtimeNotFoundException {
        Showtime showtime = new Showtime(1, "10:00 AM", 50);
        movie.addShowtime(showtime);
        movie.removeShowtime(1); // Remove the showtime
        assertEquals(0, movie.getShowtimes().size()); // Ensure no showtimes remain
        System.out.println("Showtime removed successfully. Total showtimes: " + movie.getShowtimes().size());
    }

    @Test(expected = ShowtimeNotFoundException.class)
    public void testRemoveShowtimeInvalid() throws ShowtimeNotFoundException {
        movie.removeShowtime(1); // Attempt to remove a showtime that does not exist
    }

    @Test
    public void testIsValidShowtimeTrue() {
        Showtime showtime = new Showtime(1, "10:00 AM", 50);
        movie.addShowtime(showtime);
        assertTrue(movie.isValidShowtime(1)); // Should return true for valid showtime ID
        System.out.println("Valid showtime ID check passed for: " + showtime.getShowtimeId());
    }

    @Test
    public void testIsValidShowtimeFalse() {
        Showtime showtime = new Showtime(1, "10:00 AM", 50);
        movie.addShowtime(showtime);
        assertFalse(movie.isValidShowtime(2)); // Should return false for invalid showtime ID
        System.out.println("Invalid showtime ID check passed for ID: 2");
    }

    @Test
    public void testCheckSeatOccupancy() {
        // Redirect output to a ByteArrayOutputStream to capture print statements
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save original output stream
        System.setOut(new PrintStream(outContent)); // Redirect output

        Showtime showtime = new Showtime(1, "10:00 AM", 50);
        movie.addShowtime(showtime);
        movie.checkSeatOccupancy(showtime); // Check seat occupancy

        // Expected output
        String expectedOutput = "Available Seats: 50\n";
        String actualOutput = outContent.toString();

        assertEquals(expectedOutput, actualOutput); // Check the output matches

        // Reset the output stream
        System.setOut(originalOut);
        System.out.println("Check seat occupancy method tested successfully.");
    }
}
