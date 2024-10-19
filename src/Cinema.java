package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Cinema that manages multiple theaters.
 */
public class Cinema {
    private List<Theater> theaters;
    private static int instanceCount = 0; // Class-level variable to track instances
    private static final int MAX_INSTANCES = 100; // Maximum allowed instances

    // constructor
    public Cinema() {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Cinema instances (" + MAX_INSTANCES + ") reached.");
        }
        instanceCount++; // Increment the instance count on successful creation
        this.theaters = new ArrayList<>();
    }

    public Cinema(List<Theater> theaters) {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Cinema instances (" + MAX_INSTANCES + ") reached.");
        }
        instanceCount++; // Increment the instance count on successful creation
        this.theaters = theaters;
    }

    // Method to reset the cinema count (for testing purposes)
    public static void resetCinemaCount() {
        instanceCount = 0;
    }

    // Methods
    /**
     * Display theaters in cinema.
     */
    public void listTheaters() {
        for (Theater theater : theaters) {
            System.out.println("Theater ID: " + theater.getTheaterId() + ", Address: " + theater.getAddress());
            theater.showAllMovieInTheater();
            System.out.println("");
        }
    }

    /**
     * Add theater to cinema.
     * 
     * @param theater Theater to add to cinema
     */
    public void addTheater(Theater theater) {
        theaters.add(theater);
    }

    /**
     * Display all movies in all theaters.
     */
    public void listMoviesInAllTheater() {
        for (Theater theater : theaters) {
            theater.listMovies(); // Call listMovies for each theater
        }
    }

    /**
     * Select and return a theater by its ID.
     * 
     * @param theaterId The ID of the theater
     * @return Theater that matches with the ID
     * @throws TheaterNotFoundException if no theater matches the given ID
     */
    public Theater selectTheater(int theaterId) throws TheaterNotFoundException {

        for (Theater theater : theaters) {
            if (theater.getTheaterId() == theaterId) {
                return theater;
            }
        }

        throw new TheaterNotFoundException("Theater not found with ID: " + theaterId);
    }

    /**
     * Return all available genres in the cinema.
     * 
     * @return Set of available genres
     */
    public Set<String> availableGenresInCinema() {
        Set<String> genres = new HashSet<>();

        for (Theater theater : theaters) {
            genres.addAll(theater.availableGenresInTheater());
        }
        return genres;
    }

    /**
     * Find all theaters showing movies of the specified genre.
     * 
     * @param genre The genre to search for
     */
    public void findTheatersByMovieGenre(String genre) {
        List<Theater> theatersWithMovie = new ArrayList<>();

        for (Theater theater : theaters) {
            if (theater.isMovieShowing(genre)) {
                theatersWithMovie.add(theater);
            }
        }

        for (Theater theater : theatersWithMovie) {
            System.out.println("Theater ID: " + theater.getTheaterId() + ", Address: " + theater.getAddress());
            theater.showAllMovieInTheater(genre);
            System.out.println("");
        }
    }

    /**
     * Get total number of theaters in the cinema.
     * 
     * @return Number of theaters
     */
    public int getTotalTheaters() {
        return theaters.size();
    }

    /**
     * Check if a theater ID is valid.
     * 
     * @param theaterId The ID of the theater
     * @return true if valid, false otherwise
     */
    public boolean isValidTheater(int theaterId) {
        for (Theater theater : theaters) {
            if (theater.getTheaterId() == theaterId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cinema with ").append(theaters.size()).append(" theater(s):\n");

        for (Theater theater : theaters) {
            sb.append("Theater ID: ").append(theater.getTheaterId())
                    .append(", Address: ").append(theater.getAddress())
                    .append("\n");
        }

        return sb.toString();
    }
}
