package src;

import java.util.*;

/**
 * The CinemaUI class represents the user interface for cinema application
 * functionality for display menu, select movies, showtimes, seat types
 * customer information, managing transaction
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
    private boolean genreOption = false;
    // Map to store customer, this keep multiple customer with same phone number
    private Map<String, List<Customer>> customerMap = new HashMap<>();
    private boolean exitRequest = false;

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

        System.out.println("\n1: Reserve a ticket");
        System.out.println("2: Cancel a reservation");
        System.out.println("3: View transaction history");
        System.out.println("4: Exit");
        System.out.println("\nPlease select an option:");

        while (true) {
            if (scanner.hasNextInt()) {
                int chooseOption = scanner.nextInt();
                System.out.println("----------------------------------------------");
                System.out.println("----------------------------------------------");

                switch (chooseOption) {
                    case 1:
                        showTheaterOptions();
                        return; // Exit the method after handling
                    case 2:
                        cancelTicketReservation();
                        return; // Exit the method after handling
                    case 3:
                        viewCustomerInfo();
                        return; // Exit the method after handling
                    case 4:
                        System.out.println("Exiting... Thanks for visiting ASAN Cinema!");
                        return; // Exit the method
                    default:
                        System.out.println("Invalid option, please select again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid numeric option.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    /**
     * Options to display all theaters or display by Genre
     */
    private void showTheaterOptions() {
        int choice;

        while (true) {
            System.out.println("\nSelect an option by ID or type '0' to go back:");
            System.out.println("\n1: Display all movies");
            System.out.println("2: Show by Genre");
            System.out.println("\nPlease select the options:");

            // Check if input is an integer
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

                System.out.println("----------------------------------------------");
                System.out.println("----------------------------------------------");

                switch (choice) {
                    case 0:
                        displayMenu();
                        return; // Exit the method
                    case 1:
                        showTheaters();
                        return; // Exit the method
                    case 2:
                        genreOption = true;
                        showTheaterByGenre();
                        return; // Exit the method
                    default:
                        System.out.println("Invalid option. Please select a valid option.");
                }
            } else {
                scanner.nextLine();
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    exitRequest = true;
                    break;
                }
                // If input is not an integer, show an error message
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.next(); // Consume the invalid input
            }
        }
        if (exitRequest == true) {
            exitProgram();
        }
    }

    /**
     * Display theater with genre
     */
    private void showTheaterByGenre() {
        String genre;
        Set<String> availableGenres = cinema.availableGenresInCinema();

        scanner.nextLine(); // Clear input buffer

        while (true) {
            System.out.println("\nPlease select the genre available:");

            for (String availableGenre : availableGenres) {
                System.out.println(availableGenre);
            }

            System.out.println("\nPlease type your genre or '0' to go back: ");

            genre = scanner.nextLine().trim(); // Avoid leading/trilling spaces

            // Check if the input genre is valid
            boolean isValidGenre = false;

            for (String availableGenre : availableGenres) {
                if (availableGenre.equalsIgnoreCase(genre)) {
                    isValidGenre = true;
                    break;
                }
            }

            if (isValidGenre) {
                cinema.findTheatersByMovieGenre(genre);

                // Select Theater
                System.out.println("\nSelect a theater by ID or type '0' to go back:");

                int theaterId;

                System.out.println("----------------------------------------------");
                System.out.println("----------------------------------------------");

                while (true) {
                    if (scanner.hasNextInt()) {
                        theaterId = scanner.nextInt();

                        if (theaterId == 0) {
                            showTheaterByGenre(); // Return to show theater options
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

                        System.out.println("----------------------------------------------");
                        System.out.println("----------------------------------------------");
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid numeric ID.");
                        scanner.next(); // Consume the invalid input
                    }
                }

            } else {
                if (genre.equalsIgnoreCase("0")) {
                    showTheaterOptions();
                }
                System.out.println("Invalid genre. Please try again.");
                System.out.println("----------------------------------------------");
                System.out.println("----------------------------------------------");
            }
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

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        System.out.println("\nSelect a theater by ID or type '0' to go back:");

        int theaterId;

        while (true) {
            if (scanner.hasNextInt()) {
                theaterId = scanner.nextInt();

                if (theaterId == 0) {
                    showTheaterOptions(); // Return to show theater options
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
            } else {
                scanner.nextLine();
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    exitRequest = true;
                    break;
                }
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.next(); // Consume invalid input
            }
        }
        if (exitRequest = true) {
            exitProgram();
        }
    }

    /**
     * Displays list of movies for selected theater and allows user to select one
     */
    private void showMovies() {

        // Get movies from the selected theater
        selectedTheater.listMovies();

        System.out.println("\nSelect a movie by ID or type '0' to go back:");

        int movieId;

        while (true) {
            if (scanner.hasNextInt()) {
                movieId = scanner.nextInt();

                if (movieId == 0) {
                    if (genreOption == false) {
                        selectedTheater = null; // Clear selected Theater
                        showTheaters(); // Return to show theaters
                        return;

                    } else {
                        selectedTheater = null;
                        showTheaterByGenre();
                        return;
                    }
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
            } else {
                scanner.nextLine();
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    exitRequest = true;
                    break;
                }
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.next(); // Consume the invalid input
            }
        }
        if (exitRequest == true) {
            exitProgram();
        }
    }

    /**
     * Displays the showtimes for selected movie and allow user to select one
     */
    private void showShowtimes() {

        // List showtimes for the selected movie
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");
        selectedMovie.isShowtimeSoldOut(); // Check if showtime is sold out
        selectedMovie.listShowtimes();
        System.out.println("\nSelect a showtime by ID or type '0' to go back:");

        int showtimeId;
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        while (true) {
            if (scanner.hasNextInt()) {
                showtimeId = scanner.nextInt();

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
            } else {
                scanner.nextLine();
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    exitRequest = true;
                    break;
                }
                System.out.println("Invalid input. Please enter a valid numeric ID");
                scanner.next(); // Consume the invalid input
            }
        }
        if (exitRequest == true) {
            exitProgram();
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
        int choice;

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        SeatType seatType = null;

        while (seatType == null) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

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
                        seatType = null;
                        break;

                }
            } else {scanner.nextLine();
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    exitRequest = true;
                    break;
                }
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.next(); // Consume the invalid input
            }
        }
        if (exitRequest == true) {
            exitProgram();
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
        int seatNumber;
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        while (true) {
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();

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

                    // Try to reserve the ticket
                    try {
                        selectedTicket.reserveTicket(selectedTicket);
                    } catch (ReservationException e) {
                        System.out.println("Reservation failed: " + e.getMessage());
                    }

                    selectFoodAndDrinks(); // Proceed to food selection

                } else {
                    System.out.println("Invalid or unavailable seat. Please try again.");
                    showSeatAvailability(seatType); // Retry seat selection
                }
            } else {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.next(); // Consume the invalid input
            }
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
        System.out.println("Please enter customer information.");

        // Create a new customer instance
        Customer customer = new Customer();

        // Add customer using the scanner input from Customer class
        customer.addCustomer(scanner);

        this.customer = customer;
        System.out.println("Customer information recorded succesfully.");
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

                System.out.println("----------------------------------------------");
                System.out.println("----------------------------------------------");

                isValid = transaction.inputTransactionInfo(cardNumber);
            }

        }

        System.out.println("\nSelection complete. Show receipt:");

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");

        transaction.processTransaction(customer, selectedMovie, selectedShowtime, selectedTicket, selectedItems);
        customer.addTransaction(transaction);

        // Check if the customer phone exists; if so add to the list
        customerMap.putIfAbsent(customer.getPhone(), new ArrayList<>());
        customerMap.get(customer.getPhone()).add(customer);

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
        int choice;

        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        return "Cash";
                    case 2:
                        return "Credit Card";
                    default:
                        System.out.println("Invalid input. Please select again: ");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    private void cancelTicketReservation() {
        System.out.println("Enter the ticket ID to cancel the reservation or '0' to go back: ");

        while (true) {
            try {
                // Check if input is an integer
                if (scanner.hasNextInt()) {
                    int ticketId = scanner.nextInt();
                    Ticket ticketToCancel = Ticket.findTicketById(ticketId);

                    if (ticketToCancel != null) {
                        ticketToCancel.cancelReservation(ticketToCancel);
                        transaction.processRefund();
                        displayMenu();
                        break;

                    } else if (ticketId == 0) {
                        displayMenu(); // Exit loop when user chooses to go back

                    } else {
                        System.out.println("Ticket with ID " + ticketId + " not found.");
                        System.out.println("Please enter valid ID or '0' to go back:");
                    }
                } else {
                    // If input is not an integer, show an error message
                    System.out.println("Invalid input, Please enter a valid numeric ID or '0' to go back.");
                    scanner.next(); // Consume the invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
        }

    }

    private void viewCustomerInfo() {
        scanner.nextLine();

        while (true) { // Loop until valid input or user decides to go back
            System.out.println("Please enter customer phone number or '0' to go back: ");
            String phone = scanner.nextLine();

            // Check if the input is "0" to go back
            if (phone.equals("0")) {
                displayMenu();
                return; // Exit the method after calling displayMenu
            }

            // Check if the input is numeric using regex
            if (phone.matches("\\d+")) { // Ensure the phone number is numeric
                // If input is numeric, check for customer information
                if (customerMap.get(phone) != null) {
                    System.out.println("\nDisplaying customer info: ");
                    for (Customer customer : customerMap.get(phone)) {
                        customer.searchTransactionHistory();
                    }
                } else {
                    System.out.println("No customer information available.");
                    System.out.println("----------------------------------------------");
                    System.out.println("----------------------------------------------");
                }
            } else {
                System.out.println("Invalid phone number. Please enter a numeric phone number.");
                System.out.println();
            }
        }
    }
    private void exitProgram() {
        if (scanner != null) {
            scanner.close();
        }
        System.out.println("Exiting... Thanks for visiting ASAN Cinema!");
        System.exit(0);
    }

}