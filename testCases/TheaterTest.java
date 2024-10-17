import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void setUp() {
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
        assertEquals(101, theater.getTheaterId());
        assertEquals("123 Main St", theater.getAddress());
        assertEquals(2, theater.getMovies().size());
    }

    @Test
    public void testAddMovie() {
        theater.addMovie(movie3);
        assertEquals(3, theater.getMovies().size());
        assertTrue(theater.getMovies().contains(movie3));
    }

    @Test
    public void testSelectMovieValidId() throws MovieNotFoundException {
        Movie foundMovie = theater.selectMovie(1);
        assertEquals("Inception", foundMovie.getTitle());
    }

    @Test
    public void testSelectMovieInvalidId() {
        assertThrows(MovieNotFoundException.class, () -> {
            theater.selectMovie(99);
        });
    }

    @Test
    public void testIsValidMovie() {
        assertTrue(theater.isValidMovie(1));
        assertFalse(theater.isValidMovie(99));
    }

    @Test
    public void testIsMovieShowing() {
        assertTrue(theater.isMovieShowing("Sci-Fi"));
        assertFalse(theater.isMovieShowing("Comedy"));
    }

    @Test
    public void testAvailableGenresInTheater() {
        Set<String> genres = theater.availableGenresInTheater();
        assertEquals(2, genres.size());
        assertTrue(genres.contains("Sci-Fi"));
        assertTrue(genres.contains("Action"));
    }

    @Test
    public void testShowAllMovieInTheater() {
        // Test if the method shows movies correctly (no return value, so verify manually if needed)
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
        // Test if the method lists food and drinks (no return value, so verify manually if needed)
        theater.listFoodAndDrink();
    }

    @Test
    public void testSettersAndGetters() {
        theater.setTheaterId(202);
        assertEquals(202, theater.getTheaterId());

        theater.setAddress("456 Another St");
        assertEquals("456 Another St", theater.getAddress());

        List<Movie> newMovies = new ArrayList<>();
        newMovies.add(movie3);
        theater.setMovies(newMovies);
        assertEquals(1, theater.getMovies().size());
        assertTrue(theater.getMovies().contains(movie3));
    }
}
