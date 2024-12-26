import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<FoodItem, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addItem(FoodItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void updateQuantity(FoodItem item, int quantity) {
        if (items.containsKey(item)) {
            items.put(item, quantity);
        } else {
            System.out.println("Item not in cart.");
        }
    }

    public void removeItem(FoodItem item) {
        items.remove(item);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void displayCart() {
        System.out.println("Your Cart:");
        System.out.printf("%-15s %-10s %-10s\n", "Name", "Price", "Quantity");
        System.out.println("----------------------------------------");
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            System.out.printf("%-15s %-10.2f %-10d\n", entry.getKey().getName(), entry.getKey().getPrice(), entry.getValue());
        }
        System.out.println("----------------------------------------");
        System.out.printf("Total: %.2f\n", calculateTotal());
    }

    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}
