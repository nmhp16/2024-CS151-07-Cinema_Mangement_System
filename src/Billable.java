package src;

import java.util.List;

/**
 * The Billable interface represents an entity capable of handling transactions
 * for a customer purchasing a movie ticket and other items, such as food and drinks.
 */
public interface Billable {

    /**
     * Processes a transaction for a customer purchasing a movie ticket and any additional
     * selected food and drink items.
     * 
     * @param customer      The customer making the purchase.
     * @param movie         The movie for which the ticket is being purchased.
     * @param showtime      The showtime of the movie.
     * @param ticket        The ticket being purchased.
     * @param selectedItems A list of food and drink items selected by the customer.
     */
    void processTransaction(Customer customer, Movie movie, Showtime showtime, Ticket ticket,
            List<FoodAndDrink> selectedItems);

    /**
     * Prints a receipt for the processed transaction, detailing the purchased ticket,
     * selected food and drink items, and the total amount charged.
     */
    void printReceipt();
}
