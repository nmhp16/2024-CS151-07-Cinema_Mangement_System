import java.util.*;

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

    // Constructor
    public CinemaUI() {}
    public CinemaUI(Cinema cinema) {
        this.cinema = cinema;
    }

    // Methods
    // INITIATE START
    public void start() {
        displayMenu();  // Display the main menu
    }
    
    // DISPLAY MAIN MENU
    public void displayMenu() {
        System.out.println("=============================================");
        System.out.println("\nWELCOME TO ASAN CINEMA!!");
        System.out.println("\n1: Show all theaters");
        System.out.println("\nPlease select the options:");
        int chooseOption = scanner.nextInt();

        switch (chooseOption) {
            case 1:
                showTheaters();
                break;
            default:
                break;
        }
    }

    // SHOW THEATERS
    private void showTheaters() {
        cinema.listTheaters();  // List all theaters
        System.out.println("\nSelect a theater by ID or type '0' to exit:");

        int theaterId = scanner.nextInt();
        if (theaterId == 0) return;

        try {
            selectedTheater = cinema.selectTheater(theaterId);  // Select a theater
            showMovies();
        } catch (TheaterNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // SHOW MOVIES
    private void showMovies() {
        List<Movie> movies = selectedTheater.getMovies();  // Get movies from the selected theater
        System.out.println("\nMovies in " + selectedTheater.getAddress() + ":");
        for (Movie movie : movies) {
            System.out.println("ID: " + movie.getMovieId() + ", Title: " + movie.getTitle() + ", Genre: " + movie.getGenre());
        }
        System.out.println("\nSelect a movie by ID or type '0' to go back:");
    
        int movieId = scanner.nextInt();
        if (movieId == 0) {
            selectedTheater = null; // Clear selected theater
            return;
        }
    
        try {
            selectedMovie = findMovieById(movies, movieId);  // Select a movie
            showShowtimes();
        } catch (MovieNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // FIND MOVIE BY ID
    private Movie findMovieById(List<Movie> movies, int movieId) throws MovieNotFoundException {
        for (Movie movie : movies) {
            if (movie.getMovieId() == movieId) {
                return movie;
            }
        }
        throw new MovieNotFoundException("Movie not found with ID: " + movieId);
    }
    
    // DISPLAY SHOW TIMES
    private void showShowtimes() {
        System.out.println("\nShowtimes for " + selectedMovie.getTitle() + ":");
        selectedMovie.listShowtimes();  // List showtimes for the selected movie
        System.out.println("\nSelect a showtime by ID or type '0' to go back:");

        int showtimeId = scanner.nextInt();
        if (showtimeId == 0) {
            selectedMovie = null; // Clear selected movie
            return;
        }

        try {
            selectedShowtime = selectedMovie.selectShowtime(showtimeId);  // Select a showtime
            selectSeatType();  // Ask for seat type first
        } catch (ShowtimeNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // SELECT SEAT TYPE
    private void selectSeatType() {
        System.out.println("\nSelect Seat Type:");
        System.out.println("1. Regular ($" + SeatType.REGULAR.getPrice() + ")");
        System.out.println("2. Premium ($" + SeatType.PREMIUM.getPrice() + ")");
        System.out.println("3. VIP ($" + SeatType.VIP.getPrice() + ")");
        int choice = scanner.nextInt();

        SeatType seatType;
        switch (choice) {
            case 2:
                seatType = SeatType.PREMIUM;
                break;
            case 3:
                seatType = SeatType.VIP;
                break;
            default:
                seatType = SeatType.REGULAR;
        }

        showSeatAvailability(seatType);  // Show seat availability based on the selected seat type
    }

    // SHOW SEAT AVAILABILITY
    private void showSeatAvailability(SeatType seatType) {
        System.out.println("\nAvailable Seats (" + seatType + "): Price: $" + seatType.getPrice());
        displaySeatingChart(seatType.name());  // Show seating chart for the selected seat type
    
        System.out.println("\nSelect a seat by entering seat number or type '0' to go back:");
        String seatNumber = scanner.next();
    
        if (seatNumber.equals("0")) {
            showShowtimes();  // Go back to showtimes stage
            return;
        }
    
        // Check if the seat number is valid
        if (isSeatAvailable(seatNumber, seatType.name())) {
            // Create the ticket first
            selectedTicket = new Ticket(generateTicketId(), seatType.name(), "Adult", Integer.parseInt(seatNumber));
    
            // Now apply the age pricing (this will update the price of the ticket)
            String agePricing = selectAgePricing(seatType);
    
            // After selecting age pricing, set the correct age category
            selectedTicket.setAgePricing(agePricing);  // Update ticket's age pricing
    
            selectFoodAndDrinks();  // Proceed to food selection
        } 

        else {
            System.out.println("Invalid or unavailable seat. Please try again.");
            showSeatAvailability(seatType);  // Retry seat selection
        }
    }
    
    // CHECK IF SEAT AVAILABLE
    private boolean isSeatAvailable(String seatNumber, String seatType) {
        // Implement logic to check seat availability for the selected showtime and seat type
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
        
        else {  // Default to Regular seat type
            startRow = 'A';
            endRow = 'C';
        }
    
        for (char rowLetter = startRow; rowLetter <= endRow; rowLetter++) {
            System.out.printf("%-2c ", rowLetter);  // Row label
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
        
        double finalPrice = seatType.getPrice();  // Default price from SeatType
        String ageCategory;
        
        switch (choice) {
            case 2:
                ageCategory = "Child";
                finalPrice = applyDiscount(seatType.getPrice(), 20);  // 20% discount for children
                break;
            case 3:
                ageCategory = "Senior";
                finalPrice = applyDiscount(seatType.getPrice(), 20);  // 20% discount for seniors
                break;
            default:
                ageCategory = "Adult";
                break;
        }
    
        // Now that the ticket has been created, you can set the final price
        selectedTicket.setPrice(finalPrice);  // Update the ticket price based on age pricing
    
        System.out.printf("Selected %s ticket. Final price: $%.2f%n", ageCategory, finalPrice);
        return ageCategory;
    }
    
    // APPLY DISCOUNT
    private double applyDiscount(double price, int discountPercentage) {
        return price - (price * discountPercentage / 100.0);
    }

    // Currently having error for selecting with ItemId
    private void selectFoodAndDrinks() {
        List<FoodAndDrink> foodAndDrinks = selectedTheater.getMenu(); // Get available food and drinks from the selected theater
        System.out.println("\nAvailable Food and Drinks:");
        for (int i = 0; i < foodAndDrinks.size(); i++) {
            FoodAndDrink item = foodAndDrinks.get(i);
            System.out.printf("ID: %d, Item: %-10s, Price: $%.2f%n", i + 1, item.getName(), item.getPrice());
        }
        System.out.println("\nSelect food and drink by ID or type '0' to skip:");

        int itemId = scanner.nextInt();
        while (itemId != 0) {
            if (itemId >= 0 && itemId < foodAndDrinks.size()) {
                selectedItems.add(foodAndDrinks.get(itemId - 1));
                System.out.println("Added: " + foodAndDrinks.get(itemId - 1).getName());
            } else {
                System.out.println("Invalid ID. Please try again.");
            }
            System.out.println("\nSelect more food and drinks by ID or type '0' to skip:");
            itemId = scanner.nextInt();
            scanner.nextLine();
        }

        // Collect customer information
        collectCustomerInfo();

        // Complete transaction after food and drink selection
        completeTransaction();
    }

    // COLLECT CUSTOMER INFO
    private void collectCustomerInfo() {
        System.out.println("\nEnter Customer Information:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        customer = new Customer(name, email, phone); // Include phone number
    }

    // COMPLETE TRANSACTION
    private void completeTransaction() {
        System.out.println("\nSelection complete. Show receipt:");

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
        System.out.println("Customer: " + customer.getName() + ", Email: " + customer.getEmail() + ", Phone: " + customer.getPhone());
        System.out.println("Showtime: ID: " + selectedShowtime.getShowtimeId() + ", Time: " + selectedShowtime.getTime());
        System.out.println("Movie: " + selectedMovie.getTitle());
        System.out.println("Seat: " + selectedTicket.getSeatNumber() + ", Type: " + selectedTicket.getSeatType() + ", Pricing: " + selectedTicket.getAgePricing());

        double totalCost = 0;
        System.out.println("\nSelected Food and Drinks:");
        for (FoodAndDrink item : selectedItems) {
            System.out.printf("%-10s - $%.2f%n", item.getName(), item.getPrice());
            totalCost += item.getPrice();
        }

        totalCost = totalCost + selectedTicket.getPrice();
        
        System.out.printf("%nTotal Cost: $%.2f%n", totalCost);
    }

    private String selectTransactionType() {
        System.out.println("\nSelect Transaction Type:");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: return "Cash";
            case 2: return "Credit Card";
            default: return "Cash";
        }
    }

    private int generateTicketId() {
        // Implement logic to generate a unique ticket ID
        return new Random().nextInt(1000);
    }
}
