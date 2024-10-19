package src;

import java.util.ArrayList;
import java.util.List;

/**
 * The Movie class represents a movie with its ID, title, genre, showtimes, and sold-out status.
 * It also enforces a limit on the number of movie instances.
 */
public class Movie {
    private int movieId; // ID of the movie
    private String title; // Title of the movie
    private String genre; // Genre of the movie
    private List<Showtime> showtimes = new ArrayList<>(); // List of showtimes for the movie
    private boolean isSoldOut; // Whether the movie is sold out

    private static final int MAX_INSTANCES = 100; // Maximum number of allowed instances
    private static int instanceCount = 0; // Track the current instance count

    // Constructors

    /**
     * Default constructor for the Movie class.
     * Ensures that the number of instances does not exceed the defined limit.
     */
    public Movie() {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Movie instances (" + MAX_INSTANCES + ") reached.");
        }
        instanceCount++;
    }

    /**
     * Constructor that initializes the movie with an ID, title, and genre.
     * @param movieId The ID of the movie.
     * @param title The title of the movie.
     * @param genre The genre of the movie.
     */
    public Movie(int movieId, String title, String genre) {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Movie instances (" + MAX_INSTANCES + ") reached.");
        }
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        instanceCount++;
    }

    /**
     * Resets the Movie instance count. Useful for testing purposes.
     */
    public static void resetMovieCount() {
        instanceCount = 0;
    }

    // Methods

    /**
     * Adds a showtime to the movie's list of showtimes.
     * @param showtime The showtime to add.
     */
    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    /**
     * Prints the genre of the movie.
     */
    public void listGenres() {
        System.out.println("Genre: " + this.genre);
    }

    /**
     * Lists all showtimes for the movie, along with seat availability.
     */
    public void listShowtimes() {
        System.out.println("\nShowtimes for " + this.title + ":");
        listGenres();
        System.out.println();

        for (Showtime showtime : showtimes) {
            checkSeatOccupancy(showtime);

            System.out.println(
                    "Showtime ID: " + showtime.getShowtimeId() + ", Time: " + showtime.getTime());
            System.out.println();
        }
    }

    /**
     * Selects a showtime by its ID.
     * @param showtimeId The ID of the showtime to select.
     * @return The showtime matching the provided ID.
     * @throws ShowtimeNotFoundException If the showtime is not found.
     */
    public Showtime selectShowtime(int showtimeId) throws ShowtimeNotFoundException {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId() == showtimeId) {
                return showtime;
            }
        }
        throw new ShowtimeNotFoundException("Showtime not found with ID: " + showtimeId);
    }

    /**
     * Prints the available seats for a given showtime.
     * @param showtime The showtime to check for seat occupancy.
     */
    public void checkSeatOccupancy(Showtime showtime) {
        System.out.println("Available Seats: " + showtime.getAvailableSeats());
    }

    /**
     * Checks if a given showtime ID is valid.
     * @param showtimeId The ID of the showtime to check.
     * @return True if the showtime is valid, otherwise false.
     */
    public boolean isValidShowtime(int showtimeId) {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId() == showtimeId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all sold-out showtimes from the list.
     */
    public void isShowtimeSoldOut() {
        List<Showtime> soldOutShowtimes = new ArrayList<>();

        for (Showtime showtime : showtimes) {
            if (showtime.getAvailableSeats() == 0) {
                soldOutShowtimes.add(showtime); // Collect sold-out showtimes to remove
            }
        }

        // Remove all sold-out showtimes
        for (Showtime soldOutShowtime : soldOutShowtimes) {
            try {
                removeShowtime(soldOutShowtime.getShowtimeId());
            } catch (ShowtimeNotFoundException e) {
                System.out.println("Error removing sold-out showtime: " + e.getMessage());
            }
        }
    }

    /**
     * Removes a showtime by its ID.
     * @param showtimeId The ID of the showtime to remove.
     * @throws ShowtimeNotFoundException If the showtime is not found.
     */
    public void removeShowtime(int showtimeId) throws ShowtimeNotFoundException {
        Showtime showtimeToRemove = selectShowtime(showtimeId);
        showtimes.remove(showtimeToRemove);
        System.out.println("Showtime removed successfully.");
    }

    // Getters and Setters

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }
}
