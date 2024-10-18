package src;

import java.util.ArrayList;
import java.util.List;

public class Ticket implements Reservable {
    private static int idCounter = 0;
    private static List<Ticket> tickets = new ArrayList<>();

    // Track the number of instances
    private static int instanceCount = 0;
    private static final int MAX_INSTANCES = 100;

    private int ticketId;
    private String seatType;
    private String agePricing;
    private int seatNumber;
    private boolean reserved;
    private double price;
    private Transaction transaction;

    // Constructor
    public Ticket() {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Ticket instances (" + MAX_INSTANCES + ") reached.");
        }
        instanceCount++;
    }

    public Ticket(String seatType, String agePricing, int seatNumber) {
        // Exit constructor if the limit is reached
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Maximum number of Ticket instances (" + MAX_INSTANCES + ") reached.");
        }
        this.seatType = seatType;
        this.agePricing = agePricing;
        this.seatNumber = seatNumber;
        this.reserved = false;
        this.ticketId = generateTicketId();
        instanceCount++;
    }

    public Ticket(String seatType, String agePricing, int seatNumber, double price) {
        this(seatType, agePricing, seatNumber);
        this.price = price;
    }

    // Method to reset the ticket count (for testing purposes)
    public static void resetTicketCount() {
        instanceCount = 0;
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
        // Adjust the instance count when the ticket is removed
        if (instanceCount > 0) {
            instanceCount--;
        }
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId + ", Seat Number: " + seatNumber + ", Seat Type: " + seatType
                + ", Age Pricing: " + agePricing;
    }
}
