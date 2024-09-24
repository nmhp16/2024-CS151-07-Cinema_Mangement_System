public class Transaction implements Billable {
    private String transactionType;
    private boolean holdStatus;
    private Ticket ticket;
    private Customer customer;
    private Movie movie;
    private Showtime showtime;

    // Constructor
    public Transaction() {}

    public Transaction(Customer customer, Movie movie, Showtime showtime, Ticket ticket) {
        this.customer = customer;
        this.ticket = ticket;
        this.movie = movie;
        this.showtime = showtime;

    }
    
    public Transaction(Movie movie, Showtime showtime, Ticket ticket) {
        this.ticket = ticket;
        this.movie = movie;
        this.showtime = showtime;

    }

    // Implementing Billable interface methods
    @Override
    public void processTransaction(Transaction transaction) {
        // Implement logic to process the transaction
        System.out.println("Processing transaction: " + transaction.getTransactionType());
        // Add further transaction processing logic here
    }

    @Override
    public void printReceipt() {
        // Implement receipt printing logic
        System.out.println("Customer: " + customer.getName() + ", Email: " + customer.getEmail() + ", Phone: " + customer.getPhone());
        // Other receipt details
    }

    @Override
    public void printReceipt(Transaction transaction) {
        // Implement logic to print receipt
        System.out.println("Receipt:");
        System.out.println("Customer: " + transaction.customer.getName() +
                           ", Email: " + transaction.customer.getEmail() +
                           ", Phone: " + transaction.customer.getPhone());
        System.out.println("Showtime: ID: " + transaction.ticket.getSeatNumber());
        System.out.println("Movie: " + transaction.ticket.getSeatType());
        System.out.println("Seat: " + transaction.ticket.getSeatNumber() + 
                           ", Type: " + transaction.ticket.getSeatType() +
                           ", Pricing: " + transaction.ticket.getAgePricing());
        // Add additional receipt details and formatting here
    }

    // Other methods
    public void listTransactionTypes() {
        // List available transaction types
        System.out.println("Transaction types: ID: 1, Card; ID: 2, Cash");
    }

    public void selectTransactionType(String type) {
        this.transactionType = type;
        System.out.println("Selected transaction type: " + type);
    }

    public void inputTransactionInfo() {
        // Input relevant transaction information
        System.out.println("Inputting transaction information for type: " + transactionType);
    }

    public void remindCashTransaction() {
        // Remind the user about cash transaction rules
        System.out.println("Cash transactions must be processed at the counter.");
    }

    public void addHoldStatus() {
        holdStatus = true;
        System.out.println("Transaction is on hold.");
    }

    // Getters and Setters
    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public boolean isHoldStatus() { return holdStatus;}
    public void setHoldStatus(boolean holdStatus) { this.holdStatus = holdStatus; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
}
