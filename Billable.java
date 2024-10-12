import java.util.List;

public interface Billable {
    void processTransaction(Customer customer, Movie movie, Showtime showtime, Ticket ticket,
            List<FoodAndDrink> selectedItems);

    void printReceipt();
}
