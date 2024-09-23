public class Showtime {
    private int showtimeId;
    private String time;
    private int availableSeats;
    private boolean isPastTime;

    // Constructor
    public Showtime(int showtimeId, String time, int availableSeats) {
        this.showtimeId = showtimeId;
        this.time = time;
        this.availableSeats = availableSeats;
    }
    

    public Showtime(int showtimeId, String time, int availableSeats, boolean isPastTime) {
        this.showtimeId = showtimeId;
        this.time = time;
        this.availableSeats = availableSeats;
        this.isPastTime = isPastTime;
    }

    // Getters and Setters
    public int getShowtimeId() { return showtimeId; }
    public void setShowtimeId(int showtimeId) { this.showtimeId = showtimeId; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    
    public boolean isPastTime() { return isPastTime; }
    public void setPastTime(boolean isPastTime) { this.isPastTime = isPastTime; }
}
