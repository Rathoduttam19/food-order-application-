public class FoodItem {
    private String id;
    private String name;
    private String category;
    private double price;
    private boolean available;

    public FoodItem(String id, String name, String category, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Category: %s, Price: %.2f, Available: %s",
                id, name, category, price, available ? "Yes" : "No");
    }
}

