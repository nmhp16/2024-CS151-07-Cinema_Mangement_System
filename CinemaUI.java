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

        switch (chooseOption) {
            case 1:
                showTheaters();
                break;
            default:
                System.out.println("Invalid option, Please select again: ");
                chooseOption = scanner.nextInt();

                while (chooseOption != 1) {
                    System.out.println("Invalid option, Please select again: ");
                    chooseOption = scanner.nextInt();
                }
                showTheaters();
        }
    }

    /**
     * Display the list of theaters and allows user to select one
     */
    private void showTheaters() {

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
            while (cinema.isValidTheater(theaterId) == false) {
                System.out.println("Invalid option, Please select again: ");
                theaterId = scanner.nextInt();
            }
            selectedTheater = cinema.selectTheater(theaterId);
            // list the genres by the if statements
            showMovies();
        } catch (TheaterNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays list of movies for selected theater and allows user to select one
     */
    private void showMovies() {

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
            while (selectedTheater.isValidMovie(movieId) == false) {
                System.out.println("Invalid option, Please select again: ");
                movieId = scanner.nextInt();
            }
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
            while (selectedMovie.isValidShowtime(showtimeId) == false) {
                System.out.println("Invalid option, Please select again: ");
                showtimeId = scanner.nextInt();
            }
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

        SeatType seatType = null;

        while (seatType == null) {
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
                case 0:
                    seatType = null;
                    showShowtimes();
                    break;
                default:
                    System.out.println("Invalid option, Please select again: ");
                    choice = scanner.nextInt();
                    seatType = null;
                    break;

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
        int seatNumber = scanner.nextInt();
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        if (seatNumber == 0) {
            selectSeatType(); // Go back to select seat
            return;
        }

        // Check if the seat number is valid
        if (selectedShowtime.selectSeat(seatNumber, seatType.name())) {
            // Create the ticket
            selectedTicket = new Ticket(seatType.name(), "Adult", seatNumber);

            // Now apply the age pricing (this will update the price of the ticket)
            String agePricing = selectAgePricing(seatType);

            // After selecting age pricing, set the correct age category
            selectedTicket.setAgePricing(agePricing); // Update ticket's age pricing

            selectFoodAndDrinks(); // Proceed to food selection
        } else {
            System.out.println("Invalid or unavailable seat. Please try again.");
            showSeatAvailability(seatType); // Retry seat selection
        }
    }

    // DISPLAY SEATING CHART
    private void displaySeatingChart(String seatType) {
        // Display seating chart based on seat type
        selectedShowtime.displayReservedSeats();
        System.out.println("Seating Chart for " + seatType + " seats:");

        int startSeatNumber;
        int endSeatNumber;

        // Determine the seat number range based on seat type
        if (seatType.equalsIgnoreCase("VIP")) { // Use equalsIgnoreCase for case-insensitive comparison
            startSeatNumber = 1;
            endSeatNumber = 10;
        } else if (seatType.equalsIgnoreCase("PREMIUM")) { // Use equalsIgnoreCase
            startSeatNumber = 11;
            endSeatNumber = 20;
        } else { // Default to Regular seat type
            startSeatNumber = 21;
            endSeatNumber = 30;
        }

        // Loop through the seat numbers and print them
        for (int seatNumber = startSeatNumber; seatNumber <= endSeatNumber; seatNumber++) {
            // Create the seat label without checking if it is reserved
            String seatLabel = String.format("%2d", seatNumber);
            if (selectedShowtime.isSeatTaken(seatNumber, seatType)) {
                seatLabel = String.format("%2s", "X"); // Mark as taken
            } else {
                seatLabel = String.format("%2d", seatNumber);
            }
            // Print the seat label
            System.out.print(seatLabel + " ");

            // New line after every 5 seats for better readability
            if ((seatNumber - startSeatNumber + 1) % 5 == 0) {
                System.out.println(); // New line after every 5 seats
            }
        }
    }

    // SELECT AGE PRICING
    private String selectAgePricing(SeatType seatType) {
        System.out.println("\nSelect Age Pricing:");
        System.out.println("1. Adult");
        System.out.println("2. Child (20% discount)");
        System.out.println("3. Senior (20% discount)");

        int choice = 0;

        while (choice < 1 || choice > 3) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 3) {
                    System.out.println("Please choose 1 for Adult, 2 for Child, or 3 for Senior.");
                }
            } else {
                System.out.println("Invalid input. Please choose 1 for Adult, 2 for Child, or 3 for Senior.");
                scanner.next();
            }

        }

        double finalPrice = seatType.getPrice(); // Default price from SeatType
        String ageCategory;

        switch (choice) {
            case 2:
                ageCategory = "Child";
                finalPrice = selectedTicket.applyDiscount(seatType.getPrice(), 20); // 20% discount for children
                break;
            case 3:
                ageCategory = "Senior";
                finalPrice = selectedTicket.applyDiscount(seatType.getPrice(), 20); // 20% discount for seniors
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
        String cardNumber;
        boolean isValid = false;

        // Finalize transaction
        transaction.setTransactionType(selectTransactionType());

        if (transaction.getTransactionType() == "Cash") {
            transaction.remindCashTransaction();
        } else if (transaction.getTransactionType() == "Credit Card") {
            System.out.println("Please enter your card number: ");
            while (isValid == false) {
                cardNumber = scanner.next();
                isValid = transaction.inputTransactionInfo(cardNumber);
            }
        }

        System.out.println("\nSelection complete. Show receipt:");

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        transaction.processTransaction(customer, selectedMovie, selectedShowtime, selectedTicket, selectedItems);
        transaction.printReceipt();

        // Optionally clear selected items
        selectedItems.clear();

        // Wait for user input to return to the main menu
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine(); // Consume newline left-over
        scanner.nextLine(); // Wait for Enter key
        displayMenu();
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
