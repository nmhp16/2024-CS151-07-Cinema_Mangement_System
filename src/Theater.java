package src;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Theater {
    private int theaterId;
    private String address;
    private List<Movie> movies = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<FoodAndDrink> menu = new ArrayList<>();

    // Constructor
    public Theater() {
    }

    public Theater(int theaterId, String address) {
        this.theaterId = theaterId;
        this.address = address;
        this.movies = new ArrayList<>();
    }

    public Theater(int theaterId, String address, List<Movie> movies) {
        this.theaterId = theaterId;
        this.address = address;
        if (movies == null) {
            this.movies = new ArrayList<>();
        } else {
            this.movies = movies;
        }
        this.menu = new ArrayList<>(); // Initialize menu to avoid null
    }

    public Theater(int theaterId, String address, List<Movie> movies, List<FoodAndDrink> menu) {
        this.theaterId = theaterId;
        this.address = address;
        if (movies == null) {
            this.movies = new ArrayList<>();
        } else {
            this.movies = movies;
        }
        if (menu == null) {
            this.menu = new ArrayList<>();
        } else {
            this.menu = menu;
        }
    }

    // Methods
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void listMovies() {
        System.out.println("\nMovies in " + this.address + ":");
        System.out.println();
        for (Movie movie : movies) {
            System.out.println("Movie ID: " + movie.getMovieId() + ", Title: " + movie.getTitle() + ", Genre: "
                    + movie.getGenre());
        }
    }

    public Movie selectMovie(int movieId) throws MovieNotFoundException {
        for (Movie movie : movies) {
            if (movie.getMovieId() == movieId) {
                return movie;
            }
        }
        throw new MovieNotFoundException("Movie not found with ID: " + movieId);
    }

    public void listFoodAndDrink() {
        for (FoodAndDrink item : menu) {
            System.out.println("Food/Drink: " + item.getName() + ", Price: $" + item.getPrice());
        }
    }

    public void showAllMovieInTheater() {
        if (movies.isEmpty()) {
            System.out.println("No movies currently available in this theater.");
            return;
        }
        for (Movie movie : movies) {
            System.out.println("    Movie ID: " + movie.getMovieId() + ", Title: " + movie.getTitle() + ", Genre: "
                    + movie.getGenre());
        }
    }

    public void showAllMovieInTheater(String genre) {
        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                System.out.println("    Movie ID: " + movie.getMovieId() + ", Title: " + movie.getTitle() + ", Genre: "
                        + movie.getGenre());
            }
        }
    }

    public boolean isValidMovie(int movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId() == movieId) {
                return true;
            }
        }
        return false;
    }

    // Check if there is Movie with specified Genre
    public boolean isMovieShowing(String genre) {
        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                return true;
            }
        }
        return false;
    }

    public Set<String> availableGenresInTheater() {
        Set<String> genres = new HashSet<>();

        for (Movie movie : movies) {
            genres.add(movie.getGenre().trim()); // Set will handle duplicates
        }
        return genres;
    }

    // Getter and Setter Methods
    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<FoodAndDrink> getMenu() {
        return menu;
    }

    public void setMenu(List<FoodAndDrink> menu) {
        this.menu = menu;
    }
}
