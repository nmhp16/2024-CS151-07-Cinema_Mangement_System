public enum AgePricing {
    ADULT(1), CHILD(2), SENIOR(3);

    private final int id;

    AgePricing(int id) {
        this.id = id;
    }

    public static AgePricing getById(int id) {
        for (AgePricing pricing : values()) {
            if (pricing.id == id) {
                return pricing;
            }
        }
        throw new IllegalArgumentException("Invalid AgePricing ID: " + id);
    }
}


//hello joe mama