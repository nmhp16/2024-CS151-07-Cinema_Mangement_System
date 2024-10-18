package src;

public class Showtime {
    private static int instanceCount = 0;
    private static final int MAX_INSTANCES = 100;

    private int showtimeId;
    private String time;
    private boolean[] seatArray;

    // Constructor
    public Showtime(int showtimeId, String time) {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Cannot create more than " + MAX_INSTANCES + " showtimes.");
        }
        this.showtimeId = showtimeId;
        this.time = time;
        this.seatArray = new boolean[31];

        initializeSeatAvailability();
        instanceCount++;
    }

    // Method to reset the showtime count (for testing purposes)
    public static void resetShowtimeCount() {
        instanceCount = 0;
    }

    // Method
    public int getAvailableSeats() {
        int count = 30;

        for (int i = 1; i < 31; i++) {
            if (seatArray[i] == false) {
                count--;
            }
        }
        return count;
    }

    public boolean isSeatTaken(int seatNumber, String seatType) {
        return !seatArray[seatNumber];
    }

    // Initialize seat availability for this showtime
    private void initializeSeatAvailability() {
        for (int i = 1; i < seatArray.length; i++) { // This is now actual number, fix according to CinemaUI
            seatArray[i] = true; // All seats start as available
        }
    }

    // Method to release the seat
    public void releaseSeat(int seatNumber) {
        seatArray[seatNumber] = true;
    }

    // Method to check if a seat number is valid for the selected seat type
    public boolean selectSeat(int seatNumber, String category) {
        if (seatNumber < 1 || seatNumber > 30) {
            System.out.println("Invalid seat number. Please select a seat between 1 and 30");
            return false;
        }

        // Check for category-based seat selection
        if (category.equalsIgnoreCase("VIP") && (seatNumber < 1 || seatNumber > 10)) {
            System.out.println("VIP seats can only be selected from 1 to 10.");
            return false;
        } else if (category.equalsIgnoreCase("Premium") && (seatNumber < 11 || seatNumber > 20)) {
            System.out.println("Premium seats can only be selected from 11 to 20.");
            return false;
        } else if (category.equalsIgnoreCase("Regular") && (seatNumber < 21 || seatNumber > 30)) {
            System.out.println("Regular seats can only be selected from 21 to 30.");
            return false;
        }
        // Check if the seat is already taken
        if (seatArray[seatNumber] == false) {
            System.out.println("Seat " + seatNumber + " is already taken.");
            return false;
        }

        // Mark the seat as taken
        seatArray[seatNumber] = false;
        System.out.println("Seat " + seatNumber + " has been successfully reserved.");
        return true;
    }

    // Method to display all reserved seats
    public void displayReservedSeats() {
        System.out.print("Reserved Seat: ");
        boolean hasReservedSeats = false;

        for (int i = 1; i < seatArray.length; i++) {
            if (!seatArray[i]) { // If the seat is taken
                System.out.print(i + " ");
                hasReservedSeats = true;
            }
        }

        if (!hasReservedSeats) {
            System.out.println("None");
        } else {
            System.out.println(); // Move to next line after printing reserved seats
        }
    }

    // Getters and Setters
    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
