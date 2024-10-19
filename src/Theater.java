package src;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Theater {
    private static int instanceCount = 0;
    private static final int MAX_INSTANCES = 100;

    private int theaterId;
    private String address;
    private List<Movie> movies = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<FoodAndDrink> menu = new ArrayList<>();

    // Default Constructor
    public Theater() {
        if (instanceCount >= MAX_INSTANCES) {
            System.out.println("Cannot create more than " + MAX_INSTANCES + " theaters.");
            // Exit constructor if the limit is reached
            return;
        }
        // Increment instance count when a new theater is created
        instanceCount++;
    }

    // Overloaded Constructor
    public Theater(int theaterId, String address) {
        if (instanceCount >= MAX_INSTANCES) {
            System.out.println("Cannot create more than " + MAX_INSTANCES + " theaters.");
            return; // Exit constructor if the limit is reached
        }
        this.theaterId = theaterId;
        this.address = address;
        this.movies = new ArrayList<>();
        instanceCount++;
    }

    // Overloaded Constructor
    public Theater(int theaterId, String address, List<Movie> movies) {
        if (instanceCount >= MAX_INSTANCES) {
            System.out.println("Cannot create more than " + MAX_INSTANCES + " theaters.");
            // Exit constructor if the limit is reached
            return;
        }
        this.theaterId = theaterId;
        this.address = address;
        if (movies == null) {
            this.movies = new ArrayList<>();
        } else {
            this.movies = movies;
        }
        this.menu = new ArrayList<>(); // Initialize menu to avoid null
        instanceCount++;
    }

    // Overloaded Constructor
    public Theater(int theaterId, String address, List<Movie> movies, List<FoodAndDrink> menu) {
        if (instanceCount >= MAX_INSTANCES) {
            System.out.println("Cannot create more than " + MAX_INSTANCES + " theaters.");
            return;
        }
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
        instanceCount++;
    }

    // Method to reset the theater count (for testing purposes)
    public static void resetTheaterCount() {
        instanceCount = 0;
    }

    // Method to add movie to the theater's movie list
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    // Method to list all movies currently in the theater
    public void listMovies() {
        System.out.println("\nMovies in " + this.address + ":");
        System.out.println();
        for (Movie movie : movies) {
            System.out.println("Movie ID: " + movie.getMovieId() + ", Title: " + movie.getTitle() + ", Genre: "
                    + movie.getGenre());
        }
    }

    // Method to select a movie based on its ID
    public Movie selectMovie(int movieId) throws MovieNotFoundException {
        for (Movie movie : movies) {
            if (movie.getMovieId() == movieId) { // Check for matching movie ID
                return movie;
            }
        }
        throw new MovieNotFoundException("Movie not found with ID: " + movieId);
    }

    // Method to list all food and drink items available in the theater
    public void listFoodAndDrink() {
        for (FoodAndDrink item : menu) {
            System.out.println("Food/Drink: " + item.getName() + ", Price: $" + item.getPrice());
        }
    }

    // Method to show all movies currently available in the theater
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

    // Method to show movies of a specific genre currently available in the theater
    public void showAllMovieInTheater(String genre) {
        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                System.out.println("    Movie ID: " + movie.getMovieId() + ", Title: " + movie.getTitle() + ", Genre: "
                        + movie.getGenre());
            }
        }
    }

    // Method to check if a movie with the specified ID is valid
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

    // Method to get a set of unique genres available in the theater
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
