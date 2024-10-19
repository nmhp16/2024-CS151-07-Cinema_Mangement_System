// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.TheaterTest
package testCases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import src.Movie;
import src.Theater;
import src.MovieNotFoundException;

public class TheaterTest {
    private Theater theater;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;

    @Before
    public void setUp() {
        // Reset instance count
        Theater.resetTheaterCount();

        // Initialize some movies
        movie1 = new Movie(1, "Inception", "Sci-Fi");
        movie2 = new Movie(2, "The Dark Knight", "Action");
        movie3 = new Movie(3, "Finding Nemo", "Animation");

        // Initialize the theater with movies
        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        theater = new Theater(101, "123 Main St", movies);
    }

    @Test
    public void testTheaterInitialization() {
        Assert.assertEquals(101, theater.getTheaterId());
        Assert.assertEquals("123 Main St", theater.getAddress());
        Assert.assertEquals(2, theater.getMovies().size());
    }

    @Test
    public void testAddMovie() {
        theater.addMovie(movie3);
        Assert.assertEquals(3, theater.getMovies().size());
        Assert.assertTrue(theater.getMovies().contains(movie3));
    }

    @Test
    public void testSelectMovieValidId() throws MovieNotFoundException {
        Movie foundMovie = theater.selectMovie(1);
        System.out.println(foundMovie.getTitle());
        Assert.assertEquals("Inception", foundMovie.getTitle());
    }

    @Test
    public void testSelectMovieInvalidId() {
        Assert.assertThrows(MovieNotFoundException.class, () -> {
            theater.selectMovie(99);
        });
    }

    @Test
    public void testIsValidMovie() {
        Assert.assertTrue(theater.isValidMovie(1));
        Assert.assertFalse(theater.isValidMovie(99));
    }

    @Test
    public void testIsMovieShowing() {
        Assert.assertTrue(theater.isMovieShowing("Sci-Fi"));
        Assert.assertFalse(theater.isMovieShowing("Comedy"));
    }

    @Test
    public void testAvailableGenresInTheater() {
        Set<String> genres = theater.availableGenresInTheater();
        Assert.assertEquals(2, genres.size());
        Assert.assertTrue(genres.contains("Sci-Fi"));
        Assert.assertTrue(genres.contains("Action"));
    }

    @Test
    public void testShowAllMovieInTheater() {
        // Test if the method shows movies correctly (no return value, so verify
        // manually if needed)
        theater.showAllMovieInTheater();
    }

    @Test
    public void testShowAllMovieInTheaterByGenre() {
        // Test with existing genre
        theater.showAllMovieInTheater("Sci-Fi");

        // Test with non-existing genre (should not show anything)
        theater.showAllMovieInTheater("Comedy");
    }

    @Test
    public void testListFoodAndDrink() {
        // Test if the method lists food and drinks (no return value, so verify manually
        // if needed)
        theater.listFoodAndDrink();
    }

    @Test
    public void testSettersAndGetters() {
        theater.setTheaterId(202);
        Assert.assertEquals(202, theater.getTheaterId());

        theater.setAddress("456 Another St");
        Assert.assertEquals("456 Another St", theater.getAddress());

        List<Movie> newMovies = new ArrayList<>();
        newMovies.add(movie3);
        theater.setMovies(newMovies);
        Assert.assertEquals(1, theater.getMovies().size());
        Assert.assertTrue(theater.getMovies().contains(movie3));
    }

    @Test
    public void testTheaterCreationLimit() {
        List<Theater> theaters = new ArrayList<>();

        // Create 100 Cinema instances successfully
        for (int i = 0; i < 98; i++) { // + 1 from set up
            theaters.add(new Theater());
        }

        // Ensure we can still create the 100th transaction
        Theater hundredthTheater = new Theater();
        assertNotNull("100th Theater should be created successfully", hundredthTheater);
    }

    @Test
    public void testTheaterInstanceLimit() {
        List<Theater> theaters = new ArrayList<>();

        // Create 100 theater instances
        for (int i = 0; i < 100; i++) {
            theaters.add(new Theater(i + 1, "Address " + i));
        }

        // Create the 101st theater and verify it fails (prints the message but doesn't
        // create)
        Theater excessTheater = new Theater(101, "Excess Theater");
        Assert.assertNull("The 101st theater should not be created", excessTheater.getAddress());
    }
}
