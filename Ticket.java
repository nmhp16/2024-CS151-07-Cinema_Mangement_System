public class Ticket implements Reservable {
    private int ticketId;
    private String seatType;
    private String agePricing;
    private int seatNumber;
    private boolean reserved;
    private double price;

    // Constructor
    public Ticket() {}

    public Ticket(int ticketId, String seatType, String agePricing, int seatNumber) {
        this.ticketId = ticketId;
        this.seatType = seatType;
        this.agePricing = agePricing;
        this.seatNumber = seatNumber;
        this.reserved = false;
    }

    // Implementing Reservable interface methods
    @Override
    public void reserveTicket(Ticket ticket) throws ReservationException {
        if (ticket.isReserved()) {
            throw new ReservationException("Ticket is already reserved.");
        }
        ticket.reserve();
        System.out.println("Ticket " + ticket.getTicketId() + " reserved.");
    }

    @Override
    public void cancelReservation(Ticket ticket) {
        if (!ticket.isReserved()) {
            System.out.println("Ticket " + ticket.getTicketId() + " is not reserved.");
            return;
        }
        ticket.cancelReservation();
        System.out.println("Reservation for ticket " + ticket.getTicketId() + " canceled.");
    }

    // Other methods
    public void reserve() {
        this.reserved = true;
    }

    public void cancelReservation() {
        this.reserved = false;
    }

    public boolean isReserved() {
        return reserved;
    }

    // Getters and Setters
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public String getSeatType() { return seatType; }
    public void setSeatType(String seatType) { this.seatType = seatType; }

    public String getAgePricing() { return agePricing; }
    public void setAgePricing(String agePricing) { this.agePricing = agePricing; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId + ", Seat Number: " + seatNumber + ", Seat Type: " + seatType + ", Age Pricing: " + agePricing;
    }
}
