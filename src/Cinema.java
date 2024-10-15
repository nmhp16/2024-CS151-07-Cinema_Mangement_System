package src;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Cinema {
    private List<Theater> theaters;

    // Constructor
    public Cinema() {
        this.theaters = new ArrayList<>();
    }

    public Cinema(List<Theater> theaters) {
        this.theaters = theaters;
    }

    // Methods
    /**
     * Display theaters in cinema
     */
    public void listTheaters() {
        for (Theater theater : theaters) {
            System.out.println("Theater ID: " + theater.getTheaterId() + ", Address: " + theater.getAddress());
            theater.showAllMovieInTheater();
            System.out.println("");
        }
    }

    /**
     * Add theater to cinema
     * @param theater Theater to add to cinema
     */
    public void addTheater(Theater theater) {
        theaters.add(theater);
    }

    /**
     * Display all movies in all theater
     */
    public void listMoviesInAllTheater() {
        for (Theater theater : theaters) {
            theater.listMovies(); // Call listMovies for each theater
        }
    }

    /**
     * Select and return Theater from list based on ID
     * @param theaterId The ID for theater
     * @return Theater matches with ID
     * @throws TheaterNotFoundException
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
     * Return all available genres in Cinema
     * @return Set of genres available
     */
    public Set<String> availableGenresInCinema() {
        Set<String> genres = new HashSet<>();

        for (Theater theater : theaters) {
            genres.addAll(theater.availableGenresInTheater());
        }
        return genres;
    }

    /**
     * Find all theaters with specified genre by customer
     * @param genre Genre that the customer want
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

    public int getTotalTheaters() {
        return theaters.size();
    }

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
