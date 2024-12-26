import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private List<FoodItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void addItem(FoodItem item) {
        items.add(item);
    }

    public void updateItem(String id, double newPrice, boolean available) {
        for (FoodItem item : items) {
            if (item.getId().equals(id)) {
                item.setPrice(newPrice);
                item.setAvailable(available);
                return;
            }
        }
        System.out.println("Item not found!");
    }

    public void removeItem(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public void displayMenu() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-15s %-15s\n", "Name", "Price", "Category", "Availability");
        System.out.println("--------------------------------------------------------------------------");

        for (FoodItem item : items) {
            String availability = item.isAvailable() ? "Available" : "Unavailable";
            System.out.printf("%-15s %-10.2f %-15s %-15s\n", item.getName(), item.getPrice(), item.getCategory(), availability);
        }
        System.out.println("--------------------------------------------------------------------------");
    }

    public void displayMenu(List<FoodItem> filteredItems) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-15s %-15s\n", "Name", "Price", "Category", "Availability");
        System.out.println("--------------------------------------------------------------------------");

        for (FoodItem item : filteredItems) {
            String availability = item.isAvailable() ? "Available" : "Unavailable";
            System.out.printf("%-15s %-10.2f %-15s %-15s\n", item.getName(), item.getPrice(), item.getCategory(), availability);
        }
        System.out.println("--------------------------------------------------------------------------");
    }

    // Filter items by category
    public List<FoodItem> filterByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Sort items by price (ascending or descending)
    public List<FoodItem> sortByPrice(boolean ascending) {
        return items.stream()
                .sorted(ascending ? Comparator.comparing(FoodItem::getPrice) : Comparator.comparing(FoodItem::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public FoodItem getItemById(String id) {
        for (FoodItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}
