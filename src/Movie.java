package src;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private List<Showtime> showtimes = new ArrayList<>();
    private boolean isSoldOut;

    // Constructors
    public Movie() {
    }

    public Movie(int movieId, String title, String genre) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
    }

    // Methods
    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    // Implement into CinemaUI or Figure out better methods
<<<<<<< HEAD:Movie.java
    public static void listGenres() {

        // This could be replaced with a dynamic list if genres are managed separately
        System.out.println("Available genres: Action, Comedy, Drama, Sci-Fi, etc.");
    }

    // Implement into CinemaUI or Figure out better methods
    public void selectGenre(String genre) {
        this.genre = genre;
        System.out.println("Selected genre: " + genre);
=======
    public void listGenres() {
        System.out.println("Genre: " + this.genre);
>>>>>>> dce1abc353637f9236d3e7104f7130c6241a0f2d:src/Movie.java
    }

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

    public Showtime selectShowtime(int showtimeId) throws ShowtimeNotFoundException {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId() == showtimeId) {
                return showtime;
            }
        }
        throw new ShowtimeNotFoundException("Showtime not found with ID: " + showtimeId);
    }

    public void checkSeatOccupancy(Showtime showtime) {
        System.out.println("Available Seats: " + showtime.getAvailableSeats());
    }

    public boolean isValidShowtime(int showtimeId) {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId() == showtimeId) {
                return true;
            }
        }
        return false;
    }

    // Check if Showtime is sold out
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

    // Remove showtime with ID
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
