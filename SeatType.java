public enum SeatType {
    REGULAR(10.0), PREMIUM(20.0), VIP(30.0);

    private final double price;

    SeatType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
