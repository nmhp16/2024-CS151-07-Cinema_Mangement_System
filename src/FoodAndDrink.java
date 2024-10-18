package src;

public class FoodAndDrink {
    private int id; // Unique ID for the item
    private String name;
    private double price;
    private static final int MAX_INSTANCES = 100;  // Maximum number of allowed instances
    private static int instanceCount = 0;          // Track the current instance count


    // Constructor
    public FoodAndDrink() {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Cannot create more than " + MAX_INSTANCES + " instances of FoodAndDrink.");
        }
        instanceCount++;
    }

    public FoodAndDrink(String name, double price) {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Cannot create more than " + MAX_INSTANCES + " instances of FoodAndDrink.");
        }
        this.name = name;
        this.price = price;
        instanceCount++;
    }

    public FoodAndDrink(int id, String name, double price) {
        if (instanceCount >= MAX_INSTANCES) {
            throw new IllegalStateException("Cannot create more than " + MAX_INSTANCES + " instances of FoodAndDrink.");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        instanceCount++;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FoodAndDrink [name=" + name + ", price=" + price + "]";
    }
}
