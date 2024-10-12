import java.util.Map;
import java.util.HashMap;

public class Showtime {
    private int showtimeId;
    private String time;
    private Map<Integer, Boolean> seatMap; // Seat number and availability

    // Constructor
    public Showtime(int showtimeId, String time) {
        this.showtimeId = showtimeId;
        this.time = time;
        this.seatMap = new HashMap<>();

        initializeSeatAvailability();
    }

    // Method
    public boolean isSeatAvailable(int seatNumber) {
        return seatMap.getOrDefault(seatNumber, false); // True if available, false otherwise
    }

    public int getAvailableSeats() {
        int result = 0;
        for (Map.Entry<Integer, Boolean> curSeat : this.seatMap.entrySet()) {
            if (curSeat.getValue() == true) {
                result += 1;
            }
        }
        return result;
    }

    // Initialize seat availability for this showtime
    private void initializeSeatAvailability() {
        for (int i = 1; i < 30; i++) { // This is now actual number, fix according to CinemaUI
            seatMap.put(i, true); // All seats start as available
        }
    }

    // Method to reserve a seat
    public void reserveSeat(int seatNumer) {
        if (isSeatAvailable(seatNumer)) {
            seatMap.put(seatNumer, false); // Mark the seat as reserved
            System.out.println("Seat " + seatNumer + " has been successfully reserved.");
        } else {
            System.out.println("Seat " + seatNumer + " is already reserved or invalid.");
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
