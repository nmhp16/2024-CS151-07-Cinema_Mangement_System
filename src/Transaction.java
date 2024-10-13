package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Transaction implements Billable {
    private String transactionType;
    private boolean holdStatus;
    private Ticket ticket;
    private Customer customer;
    private Movie movie;
    private Showtime showtime;
    private String cardNumber;
    private List<FoodAndDrink> selectedItems;
    private LocalDateTime transactionTime;

    // Constructor
    public Transaction() {
        this.transactionTime = LocalDateTime.now();
        this.selectedItems = new ArrayList<>();
    }

    public Transaction(Movie movie, Showtime showtime, Ticket ticket, Customer customer,
            List<FoodAndDrink> selectedItems) {
        this(movie, showtime, ticket);
        this.customer = customer;
        this.selectedItems = new ArrayList<>(selectedItems);
    }

    public Transaction(Movie movie, Showtime showtime, Ticket ticket) {
        this.ticket = ticket;
        this.movie = movie;
        this.showtime = showtime;
        this.transactionTime = LocalDateTime.now();
    }

    // Implementing Billable interface methods
    @Override
    public void processTransaction(Customer customer, Movie movie, Showtime showtime, Ticket ticket,
            List<FoodAndDrink> selectedItems) {
        this.customer = customer;
        this.movie = movie;
        this.showtime = showtime;
        this.ticket = ticket;
        this.selectedItems = new ArrayList<>(selectedItems);

        System.out.println("Transaction processed successfuly.");

        if (transactionType == "Cash") {
            remindCashTransaction();
        } else {
            System.out.println("Card: " + cardNumber);
        }
    }

    @Override
    public void printReceipt() {
        // Print customer details
        System.out.println("Transaction completed on: " + getFormattedTransactionTime());
        System.out.println("\nCustomer: " + customer.getName() + ", Email: " + customer.getEmail() + ", Phone: "
                + customer.getPhone());
        // Print showtime details
        System.out.println("Showtime: ID: " + showtime.getShowtimeId() + ", Time: " + showtime.getTime());
        // Print movie and ticket details
        System.out.println("Movie: " + movie.getTitle());
        ticket.getSummary();

        // Print selected food and drinks
        System.out.println("\nYou selected " + getTotalItems() + " items:");
        for (FoodAndDrink item : selectedItems) {
            System.out.printf("%-10s - $%.2f%n", item.getName(), item.getPrice());
        }

        // Get total cost
        double totalCost = calculateTotalCost();

        System.out.printf("%nTotal Cost: $%.2f%n", totalCost);
        System.out.println("----------------------------------------------");
    }

    // Other methods
    public void listTransactionTypes() {
        // List available transaction types
        System.out.println("Transaction types: ID: 1, Card; ID: 2, Cash");
    }

    public double calculateTotalCost() {
        double totalCost = ticket.getPrice(); // Start with ticket price

        // Add the price of each selected food and drink item
        for (FoodAndDrink item : selectedItems) {
            totalCost += item.getPrice();
        }

        return totalCost;
    }

    public int getTotalItems() {
        return selectedItems.size();
    }

    public void selectTransactionType(String type) {
        this.transactionType = type;
        System.out.println("Selected transaction type: " + type);
    }

    public boolean inputTransactionInfo(String cardNumber) {
        this.cardNumber = cardNumber;

        // Input relevant transaction information
        System.out.println("Processing your transaction information for type: " + transactionType);
        validateCard();
        if (holdStatus == true) {
            System.out.println("Please enter your card info again with 10 digits: \n");
            return false;
        }
        System.out.println("Account number: " + cardNumber + " processed successfully.");
        return true;

    }

    public String getFormattedTransactionTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return transactionTime.format(formatter);
    }

    public void processRefund() {
        if (ticket.isReserved()) {
            if (transactionType == "Credit Card") {
                System.out.println("Refunding transaction for ticket " + ticket.getTicketId());
            } else {
                System.out.println("Cannot refund. Cash transaction was not collected.");
            }
        }
    }

    public void validateCard() {
        if (cardNumber == null || !cardNumber.matches("\\d{10}")) {
            addHoldStatus();
        } else {
            holdStatus = false;
        }
    }

    public void remindCashTransaction() {
        // Remind the user about cash transaction rules
        System.out.println("Cash transactions must be processed at the counter.\n");
    }

    public void addHoldStatus() {
        holdStatus = true;
        System.out.println("Transaction is on hold.");
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isHoldStatus() {
        return holdStatus;
    }

    public void setHoldStatus(boolean holdStatus) {
        this.holdStatus = holdStatus;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getCard() {
        return cardNumber;
    }

    public void setCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public List<FoodAndDrink> getItems() {
        return selectedItems;
    }

    public void setItems(List<FoodAndDrink> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
}
