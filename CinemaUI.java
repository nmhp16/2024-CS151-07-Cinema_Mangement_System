import java.util.*;

/**
 * The CinemaUI class represents the user interface for cinema application
 * functionality for display menu, select movies, showtimes, seat types
 * customer information, manging transaction
 */
public class CinemaUI {
    private Scanner scanner = new Scanner(System.in);
    private Cinema cinema;
    private Theater selectedTheater;
    private Movie selectedMovie;
    private Showtime selectedShowtime;
    private Ticket selectedTicket;
    private List<FoodAndDrink> selectedItems = new ArrayList<>();
    private Transaction transaction = new Transaction();
    private Customer customer;

    private Stack<Runnable> pageStack = new Stack<>(); // Tracks pages by pushing to a stack after every display
                                                       // pops the page to go back when 0 is entered

    private void previousPage() {
        if (pageStack.isEmpty()) {
            Runnable goBack = pageStack.pop();
            goBack.run();
        } else {
            System.out.println("No previous page to go back to.");
        }
    }

    /**
     * Constructor that initializes the CinemaUI without a Cinema
     */
    public CinemaUI() {
    }

    /**
     * Constructor that initializes the CinemaUI with specific cinema
     * 
     * @param cinema The cinema to be used in UI
     */
    public CinemaUI(Cinema cinema) {
        this.cinema = cinema;
    }

    /**
     * Starts the application by displaying main menu
     */
    public void start() {
        displayMenu(); // Display the main menu
    }

    /**
     * Displays the main menu options and processes user selection
     */
    public void displayMenu() {
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");
        System.out.println("\nWELCOME TO ASAN CINEMA!!");
        System.out.println("\n1: Show all theaters");
        System.out.println("\nPlease select the options:");
        int chooseOption = scanner.nextInt();
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        // Push the displayMenu onto the pageStack
        pageStack.push(this::displayMenu);

        switch (chooseOption) {
            case 1:
                showTheaters();
                break;
            default:
                break;
        }
    }

    /**
     * Display the list of theaters and allows user to select one
     */
    private void showTheaters() {
        // Push the showTheaters onto the pageStack
        pageStack.push(this::showTheaters);

        // Show total of theater within cinema
        System.out.println("THERE ARE " + cinema.getTotalTheaters() + " THEATERS IN OUR SYSTEM:");
        System.out.println("");
        cinema.listTheaters(); // List all theaters

        System.out.println("\nSelect a theater by ID or type '0' to exit:");
        int theaterId = scanner.nextInt();
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        if (theaterId == 0) {
            displayMenu(); // Return to main menu
            return;
        }

        try {
            // Select a theater
            selectedTheater = cinema.selectTheater(theaterId);
            showMovies();
        } catch (TheaterNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays list of movies for selected theater and allows user to select one
     */
    private void showMovies() {
        // Push the showMovies onto the pageStack
        pageStack.push(this::showMovies);

        // Get movies from the selected theater
        selectedTheater.listMovies();

        System.out.println("\nSelect a movie by ID or type '0' to go back:");

        int movieId = scanner.nextInt();
        if (movieId == 0) {
            selectedTheater = null; // Clear selected Theater
            showTheaters(); // Return to show theaters
            return;
        }

        try {
            selectedMovie = selectedTheater.selectMovie(movieId); // Select a movie
            showShowtimes();
        } catch (MovieNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the showtimes for selected movie and allow user to select one
     */
    private void showShowtimes() {
        // Push the showShowtimes onto the pageStack
        pageStack.push(this::showShowtimes);

        // List showtimes for the selected movie
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");
        selectedMovie.listShowtimes();
        System.out.println("\nSelect a showtime by ID or type '0' to go back:");

        int showtimeId = scanner.nextInt();
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        if (showtimeId == 0) {
            selectedMovie = null; // Clear selected movie
            showMovies(); // Return to show movie
            return;
        }

        try {
            selectedShowtime = selectedMovie.selectShowtime(showtimeId); // Select a showtime
            selectSeatType(); // Ask for seat type first
        } catch (ShowtimeNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Allows user to select a seat type for ticket
     */
    private void selectSeatType() {
        System.out.println("\nSelect Seat Type:");
        System.out.println("1. Regular ($" + SeatType.REGULAR.getPrice() + ")");
        System.out.println("2. Premium ($" + SeatType.PREMIUM.getPrice() + ")");
        System.out.println("3. VIP ($" + SeatType.VIP.getPrice() + ")");

        System.out.println("\nSelect a seat type by ID or type '0' to go back:");
        int choice = scanner.nextInt();

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        SeatType seatType;
        switch (choice) {
            case 1:
                seatType = SeatType.REGULAR;
                break;
            case 2:
                seatType = SeatType.PREMIUM;
                break;
            case 3:
                seatType = SeatType.VIP;
                break;
            default:
                if (choice == 0) {
                    seatType = null;
                    showShowtimes();
                } else {
                    System.out.println("\nPlease select a correct option!");
                    seatType = null;
                    selectSeatType();
                }
        }

        showSeatAvailability(seatType); // Show seat availability based on the selected seat type
    }

    /**
     * Displays the available seats based on selected seat type
     * 
     * @param seatType The type of seat selected by user
     */
    private void showSeatAvailability(SeatType seatType) {
        System.out.println("\nAvailable Seats (" + seatType + "): Price: $" + seatType.getPrice());
        displaySeatingChart(seatType.name()); // Show seating chart for the selected seat type

        System.out.println("\nSelect a seat by entering seat number or type '0' to go back:");
        String seatNumber = scanner.next();
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        if (seatNumber.equals("0")) {
            showShowtimes(); // Go back to showtimes stage
            return;
        }

        // Check if the seat number is valid
        if (isSeatAvailable(seatNumber, seatType.name())) {
            // Generate Ticket ID
            Ticket ticket = new Ticket();
            int ticketId = ticket.generateTicketId();

            // Create the ticket
            selectedTicket = new Ticket(ticketId, seatType.name(), "Adult", Integer.parseInt(seatNumber));

            // Now apply the age pricing (this will update the price of the ticket)
            String agePricing = selectAgePricing(seatType);

            // After selecting age pricing, set the correct age category
            selectedTicket.setAgePricing(agePricing); // Update ticket's age pricing

            selectFoodAndDrinks(); // Proceed to food selection
        }

        else {
            System.out.println("Invalid or unavailable seat. Please try again.");
            showSeatAvailability(seatType); // Retry seat selection
        }
    }

    /**
     * Checks if a seat is available based on the seat number and type
     * 
     * @param seatNumber The seat number to check
     * @param seatType   The type of seat (Regular, Premium, VIP)
     * @return True if the seat is available, false otherwise
     */
    private boolean isSeatAvailable(String seatNumber, String seatType) {
        // Implement logic to check seat availability for the selected showtime and seat
        // type
        return true;
    }

    // DISPLAY SEATING CHART
    private void displaySeatingChart(String seatType) {
        // Display seating chart based on seat type
        System.out.println("Seating Chart:");
        int cols = 10;
        char startRow;
        char endRow;

        // Determine the start and end rows based on seat type
        if (seatType.equals("VIP")) {
            startRow = 'H';
            endRow = 'J';
        }
        // If seat type is Premium
        else if (seatType.equals("PREMIUM")) {
            startRow = 'D';
            endRow = 'G';
        }

        else { // Default to Regular seat type
            startRow = 'A';
            endRow = 'C';
        }

        for (char rowLetter = startRow; rowLetter <= endRow; rowLetter++) {
            System.out.printf("%-2c ", rowLetter); // Row label
            for (int col = 1; col <= cols; col++) {
                boolean isReserved = isSeatReserved(rowLetter, col);
                String seatLabel;
                if (isReserved) {
                    seatLabel = " x ";
                } else {
                    seatLabel = String.format("%2d", (rowLetter - startRow) * cols + col);
                }
                System.out.print(seatLabel + " ");
            }
            System.out.println(); // New line
        }
    }

    // CHECK IF SEAT IS RESERVED
    private boolean isSeatReserved(char row, int col) {
        // Implement logic to check if the seat is reserved
        return false;
    }

    // SELECT AGE PRICING
    private String selectAgePricing(SeatType seatType) {
        System.out.println("\nSelect Age Pricing:");
        System.out.println("1. Adult");
        System.out.println("2. Child (20% discount)");
        System.out.println("3. Senior (20% discount)");
        int choice = scanner.nextInt();

        double finalPrice = seatType.getPrice(); // Default price from SeatType
        String ageCategory;

        switch (choice) {
            case 2:
                ageCategory = "Child";
                finalPrice = applyDiscount(seatType.getPrice(), 20); // 20% discount for children
                break;
            case 3:
                ageCategory = "Senior";
                finalPrice = applyDiscount(seatType.getPrice(), 20); // 20% discount for seniors
                break;
            default:
                ageCategory = "Adult";
                break;
        }

        // Now that the ticket has been created, you can set the final price
        selectedTicket.setPrice(finalPrice); // Update the ticket price based on age pricing

        System.out.printf("Selected %s ticket. Final price: $%.2f%n", ageCategory, finalPrice);
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");
        return ageCategory;
    }

    // APPLY DISCOUNT
    private double applyDiscount(double price, int discountPercentage) {
        return price - (price * discountPercentage / 100.0);
    }

    // SELECT FOOD AND
    private void selectFoodAndDrinks() {
        List<FoodAndDrink> foodAndDrinks = selectedTheater.getMenu(); // Get available food and drinks from the selected
                                                                      // theater
        System.out.println("\nAvailable Food and Drinks:");

        // Display available items with ID
        for (FoodAndDrink item : foodAndDrinks) {
            System.out.printf("ID: %d, Item: %-10s, Price: $%.2f%n", item.getId(), item.getName(), item.getPrice());
        }

        System.out.println("\nSelect food and drink by ID or type '0' to skip:");

        int itemId;

        while ((itemId = getValidItemId()) != 0) {
            FoodAndDrink selectedItem = findItemById(foodAndDrinks, itemId);

            if (selectedItem != null) {
                // Valid selection
                selectedItems.add(selectedItem);
                System.out.println("Added: " + selectedItem.getName());
                System.out.println("----------------------------------------------");
                System.out.println("----------------------------------------------");

            } else {
                // Invalid selection
                System.out.println("Invalid ID. Please try again.");
            }

            System.out.println("\nSelect more food and drinks by ID or type '0' to skip:");
        }

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        // Collect customer information
        collectCustomerInfo();

        // Complete transaction after food and drink selection
        completeTransaction();
    }

    /**
     * Get valid item ID input from the user.
     */
    private int getValidItemId() {
        int itemId;
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear the invalid input
        }
        itemId = scanner.nextInt();
        return itemId;
    }

    /**
     * Find a FoodAndDrink item by its ID.
     */
    private FoodAndDrink findItemById(List<FoodAndDrink> foodAndDrinks, int itemId) {
        for (FoodAndDrink item : foodAndDrinks) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        return null;
    }

    // COLLECT CUSTOMER INFO
    private void collectCustomerInfo() {
        if (this.customer == null) {
            this.customer = new Customer();
        }
        customer.addCustomer();
    }

    // COMPLETE TRANSACTION
    private void completeTransaction() {
        // Push the completeTransaction method onto the pageStack
        pageStack.push(this::showTheaters);

        System.out.println("\nSelection complete. Show receipt:");

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        // Show receipt details before finalizing transaction
        printReceipt();

        // Finalize transaction
        transaction.setTransactionType(selectTransactionType());

        if (transaction.getTransactionType() == "Cash") {
            transaction.remindCashTransaction();
        }

        transaction.setTicket(selectedTicket);
        transaction.setCustomer(customer);
        transaction.printReceipt();

        // Optionally clear selected items
        selectedItems.clear();

        // Wait for user input to return to the main menu
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine(); // Consume newline left-over
        scanner.nextLine(); // Wait for Enter key
        displayMenu();
    }

    private void printReceipt() {
        System.out.println("\nReceipt:");
        System.out.println("Customer: " + customer.getName() + ", Email: " + customer.getEmail() + ", Phone: "
                + customer.getPhone());
        System.out
                .println("Showtime: ID: " + selectedShowtime.getShowtimeId() + ", Time: " + selectedShowtime.getTime());
        System.out.println("Movie: " + selectedMovie.getTitle());
        System.out.println("Seat: " + selectedTicket.getSeatNumber() + ", Type: " + selectedTicket.getSeatType()
                + ", Pricing: " + selectedTicket.getAgePricing());

        double totalCost = 0;
        System.out.println("\nSelected Food and Drinks:");
        for (FoodAndDrink item : selectedItems) {
            System.out.printf("%-10s - $%.2f%n", item.getName(), item.getPrice());
            totalCost += item.getPrice();
        }

        totalCost = totalCost + selectedTicket.getPrice();

        System.out.printf("%nTotal Cost: $%.2f%n", totalCost);
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");
    }

    private String selectTransactionType() {
        System.out.println("\nSelect Transaction Type:");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return "Cash";
            case 2:
                return "Credit Card";
            default:
                return "Cash";
        }
    }

}
