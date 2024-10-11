
public class Showtime {
    private int showtimeId;
    private String time;
    private boolean[] seatArray;

    // Constructor
    public Showtime(int showtimeId, String time) {
        this.showtimeId = showtimeId;
        this.time = time;
        this.seatArray = new boolean[31];

        initializeSeatAvailability();
    }

    // Method
    public boolean isSeatAvailable(int seatNumber) {
        return seatArray[seatNumber];
    }

    public int isSeatAvailable() {
        int count = 30;

        for (int i = 1; i < 31; i++) {
            if (seatArray[i] == false) {
                count--;
            }
        }
        return count;
    }

    // Initialize seat availability for this showtime
    private void initializeSeatAvailability() {
        for (int i = 1; i < seatArray.length; i++) { // This is now actual number, fix according to CinemaUI
            seatArray[i] = true; // All seats start as available
        }
    }

    // Method to reserve a seat
    public void reserveSeat(int seatNumber) {
        if (isSeatAvailable(seatNumber)) {
            seatArray[seatNumber] = false; // Mark the seat as reserved
            System.out.println("Seat " + seatNumber + " has been successfully reserved.");
        } else {
            System.out.println("Seat " + seatNumber + " is already reserved or invalid.");
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
