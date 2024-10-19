package src;

/**
 * Custom exception thrown when a movie is not found.
 */
public class MovieNotFoundException extends Exception {
    /**
     * Constructor that accepts a message to describe the exception.
     * @param message The detail message about the movie not found.
     */
    public MovieNotFoundException(String message) {
        super(message);
    }
}
