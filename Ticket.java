import java.util.ArrayList;
import java.util.List;

public class Ticket implements Reservable {
    private static int idCounter = 0;
    private static List<Ticket> tickets = new ArrayList<>();
    private int ticketId;
    private String seatType;
    private String agePricing;
    private int seatNumber;
    private boolean reserved;
    private double price;

    // Constructor
    public Ticket() {
    }

    public Ticket(String seatType, String agePricing, int seatNumber) {
        this.seatType = seatType;
        this.agePricing = agePricing;
        this.seatNumber = seatNumber;
        this.reserved = false;
        this.ticketId = generateTicketId();
    }

    public Ticket(String seatType, String agePricing, int seatNumber, double price) {
        this(seatType, agePricing, seatNumber);
        this.price = price;
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
    public double applyDiscount(double price, int discountPercentage) {
        return price - (price * discountPercentage / 100.0);
    }

    public void getSummary() {
        System.out.println("Ticket ID: " + getTicketId());
        System.out.println("Seat: " + this.seatNumber + ", Type: " + this.seatType + ", Pricing: "
                + this.agePricing);
        System.out.println("\nTicket price: " + "$" + this.price);
    }

    // Method to find ticket by its ID
    public static Ticket findTicketById(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                return ticket;
            }
        }
        return null;
    }

    public static boolean ticketIdExists(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticketId == ticket.getTicketId()) {
                return true; // Ticket ID found
            }
        }
        return false; // Ticket ID not found
    }

    public void reserve() {
        this.reserved = true;
        tickets.add(this);
    }

    public void cancelReservation() {
        this.reserved = false;
        tickets.remove(this);
    }

    public boolean isReserved() {
        for (Ticket ticket : tickets) {
            if (ticketId == ticket.getTicketId()) {
                reserved = true;
                return reserved;
            }
        }
        reserved = false;
        return reserved;
    }

    public int generateTicketId() {
        idCounter++;
        return idCounter;
    }

    public int removeTicketId() {
        idCounter--;
        ticketId = idCounter;
        return ticketId;
    }

    // Getters and Setters
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getAgePricing() {
        return agePricing;
    }

    public void setAgePricing(String agePricing) {
        this.agePricing = agePricing;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public static List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId + ", Seat Number: " + seatNumber + ", Seat Type: " + seatType
                + ", Age Pricing: " + agePricing;
    }
}
