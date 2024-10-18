package src;

public enum AgePricing {
    ADULT(1), CHILD(2), SENIOR(3);

    private final int id;
    private static final int MAX_RETRIEVALS = 100; // Maximum allowed retrievals
    private static int retrievalCount = 0; // Counter for retrieval operations

    AgePricing(int id) {
        this.id = id;
    }

    public static AgePricing getById(int id) {
        if (retrievalCount >= MAX_RETRIEVALS) {
            throw new IllegalStateException("Maximum number of retrievals exceeded: " + MAX_RETRIEVALS);
        }
        for (AgePricing pricing : values()) {
            if (pricing.id == id) {
                retrievalCount++; // Increment the counter on successful retrieval
                return pricing;
            }
        }
        throw new IllegalArgumentException("Invalid AgePricing ID: " + id);
    }

    public static int getRetrievalCount() {
        return retrievalCount; // Getter to check how many times retrieval has been performed
    }
}
