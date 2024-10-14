package src;

public interface Reservable {
    void reserveTicket(Ticket ticket) throws ReservationException;

    void cancelReservation(Ticket ticket);
}
