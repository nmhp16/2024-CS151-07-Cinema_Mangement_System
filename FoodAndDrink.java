public class FoodAndDrink {
    private int id; // Unique ID for the item
    private String name;
    private double price;
    
    // Constructor'
    public FoodAndDrink() {}
    public FoodAndDrink(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public FoodAndDrink(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getId() { return id; }
    public void setId(int id) {this.id = id;}


    @Override
    public String toString() {
        return "FoodAndDrink [name=" + name + ", price=" + price + "]";
    }
}
