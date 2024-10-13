package src;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create some sample data
        List<Theater> theaters = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        // Sample movies
        Movie movie1 = new Movie(1, "Movie A", "Action");
        Movie movie2 = new Movie(2, "Movie B", "Comedy");

        // Sample showtimes
        Showtime showtime1 = new Showtime(1, "10:00 AM");
        Showtime showtime2 = new Showtime(2, "02:00 PM");

        movie1.addShowtime(showtime1);
        movie2.addShowtime(showtime2);

        movies.add(movie1);
        movies.add(movie2);

        // Sample food and drinks
        List<FoodAndDrink> menu1 = new ArrayList<>();
        menu1.add(new FoodAndDrink(1, "Popcorn", 5.00));
        menu1.add(new FoodAndDrink(2, "Soda", 3.00));
        menu1.add(new FoodAndDrink(3, "Nachos", 6.00));

        List<FoodAndDrink> menu2 = new ArrayList<>();
        menu2.add(new FoodAndDrink(1, "Hot Dog", 4.00));
        menu2.add(new FoodAndDrink(2, "Water", 2.00));
        menu2.add(new FoodAndDrink(3, "Candy", 2.50));

        // Sample theaters with food and drink menu
        Theater theater1 = new Theater(1, "123 Main St", movies, menu1);
        Theater theater2 = new Theater(2, "456 Elm St", movies, menu2);

        theaters.add(theater1);
        theaters.add(theater2);

        // Create Cinema instance with theaters
        Cinema cinema = new Cinema(theaters);

        // Create CinemaUI instance
        CinemaUI cinemaUI = new CinemaUI(cinema);

        // Display the menu to the user
        cinemaUI.displayMenu();
    }
}
