package src;

/**
 * Enum representing different age-based pricing categories.
 * Each category is associated with a unique ID.
 */
public enum AgePricing {
    /**
     * Represents pricing for adults.
     * ID: 1
     */
    ADULT(1),

    /**
     * Represents pricing for children.
     * ID: 2
     */
    CHILD(2),

    /**
     * Represents pricing for seniors.
     * ID: 3
     */
    SENIOR(3);

    // Unique identifier for each pricing category
    private final int id;

    /**
     * Constructor for AgePricing enum.
     * Associates an ID with the pricing category.
     * 
     * @param id the unique identifier for the pricing category
     */
    AgePricing(int id) {
        this.id = id;
    }

    /**
     * Retrieves the AgePricing category by its unique ID.
     * 
     * @param id the ID to search for
     * @return the corresponding AgePricing category
     * @throws IllegalArgumentException if no category with the given ID exists
     */
    public static AgePricing getById(int id) {
        // Loop through all enum values and find the matching ID
        for (AgePricing pricing : values()) {
            if (pricing.id == id) {
                return pricing;
            }
        }
        // Throw an exception if the ID is invalid
        throw new IllegalArgumentException("Invalid AgePricing ID: " + id);
    }
}
