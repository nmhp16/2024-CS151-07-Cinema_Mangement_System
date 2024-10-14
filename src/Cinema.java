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
    public void listTheaters() {
        for (Theater theater : theaters) {
            System.out.println("Theater ID: " + theater.getTheaterId() + ", Address: " + theater.getAddress());
            theater.showAllMovieInTheater();
            System.out.println("");
        }
    }

    public void addTheater(Theater theater) {
        theaters.add(theater);
    }

    public void listMoviesInAllTheater() {
        for (Theater theater : theaters) {
            theater.listMovies(); // Call listMovies for each theater
        }
    }

    public Theater selectTheater(int theaterId) throws TheaterNotFoundException {

        for (Theater theater : theaters) {
            if (theater.getTheaterId() == theaterId) {
                return theater;
            }
        }

        throw new TheaterNotFoundException("Theater not found with ID: " + theaterId);
    }

    public Set<String> availableGenresInCinema() {
        Set<String> genres = new HashSet<>();

        for (Theater theater : theaters) {
            genres.addAll(theater.availableGenresInTheater());
        }
        return genres;
    }

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
