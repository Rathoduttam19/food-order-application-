import java.util.HashMap;
import java.util.Scanner;
import java.util.List;


public class ByteMeApp {
    private Menu menu = new Menu();
    private OrderHistory orderHistory = new OrderHistory();
    private Scanner scanner = new Scanner(System.in);
    private HashMap<String, OrderHistory> customerOrders = new HashMap<>();

    public ByteMeApp() {
        // Initialize the menu with 5 items
        menu.addItem(new FoodItem("1", "Plain Maggi", "Snacks", 20.0, true));
        menu.addItem(new FoodItem("2", "Bread Omelet", "Breakfast", 25.0, true));
        menu.addItem(new FoodItem("3", "Paratha", "Breakfast", 30.0, true));
        menu.addItem(new FoodItem("4", "Sandwich", "Snacks", 35.0, true));
        menu.addItem(new FoodItem("5", "Coffee", "Beverages", 15.0, true));
    }



    public void adminInterface() {
        System.out.println("Admin Interface:");
        System.out.println("1. Add item\n2. Update item\n3. Remove item\n4. View menu\n5. View pending orders\n6. Update order status\n7. Confirm customer refund\n8. Handle special requests\n9. Generate daily sales report\n10. Exit");

        while (true) {
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    updateItem();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    menu.displayMenu();
                    break;
                case 5:
                    viewPendingOrders();
                    break;
                case 6:
                    updateOrderStatus();
                    break;
                case 7:
                    confirmCustomerRefund();
                    break;
                case 8:
                    handleSpecialRequests();
                    break;
                case 9:
                    orderHistory.generateDailySalesReport();
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public void confirmCustomerRefund() {
        System.out.print("Enter customer ID to view canceled orders: ");
        String customerId = scanner.nextLine();
        OrderHistory customerOrderHistory = customerOrders.get(customerId);

        if (customerOrderHistory == null) {
            System.out.println("No orders found for this customer.");
            return;
        }

        List<Order> customerCancelledOrders = customerOrderHistory.getCustomerCancelledOrders();
        if (customerCancelledOrders.isEmpty()) {
            System.out.println("No customer-cancelled orders awaiting refund.");
            return;
        }

        for (Order order : customerCancelledOrders) {
            order.displayOrderDetails();
        }

        System.out.print("Enter order ID to confirm refund: ");
        String orderId = scanner.nextLine();
        Order orderToRefund = customerOrderHistory.getOrder(orderId);

        if (orderToRefund != null && orderToRefund.isCustomerCanceled()) {
            orderToRefund.processRefund();
        } else {
            System.out.println("Order not found, or refund already processed.");
        }
    }

    private void viewPendingOrders() {
        List<Order> pendingOrders = orderHistory.getPendingOrders();
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            System.out.println("Pending Orders:");
            for (Order order : pendingOrders) {
                order.displayOrderDetails();
                System.out.println("----------------------------------------");
            }
        }
    }

    private void updateOrderStatus() {
        System.out.print("Enter order ID to update status: ");
        String orderId = scanner.nextLine();
        Order order = orderHistory.getOrder(orderId);

        if (order != null) {
            System.out.print("Enter new status (Delivered, Cancelled by Customer, Refunded): ");
            String status = scanner.nextLine();

            if (Order.STATUS_DELIVERED.equals(status) ||
                    Order.STATUS_CANCELLED_BY_CUSTOMER.equals(status) ||
                    Order.STATUS_REFUNDED.equals(status)) {

                if (Order.STATUS_REFUNDED.equals(status) && order.isCustomerCanceled()) {
                    order.processRefund();
                } else {
                    order.setStatus(status);
                    System.out.println("Order status updated to: " + status);
                }

            } else {
                System.out.println("Invalid status. Choose from Delivered, Cancelled by Customer, or Refunded.");
            }
        } else {
            System.out.println("Order not found.");
        }
    }


    private void processRefunds() {
        System.out.print("Enter order ID to process refund: ");
        String orderId = scanner.nextLine();
        Order order = orderHistory.getOrder(orderId);
        if (order != null && !order.isRefundProcessed()) {
            order.processRefund();
        } else {
            System.out.println("Order not found or refund already processed.");
        }
    }

    private void handleSpecialRequests() {
        System.out.print("Enter order ID to add special request: ");
        String orderId = scanner.nextLine();
        Order order = orderHistory.getOrder(orderId);
        if (order != null) {
            System.out.print("Enter special request: ");
            String request = scanner.nextLine();
            order.setSpecialRequest(request);
            System.out.println("Special request added to order.");
        } else {
            System.out.println("Order not found.");
        }
    }

    public void customerInterface(User customer) {
        Cart cart = new Cart();
        OrderHistory customerOrderHistory = customerOrders.getOrDefault(customer.getUserId(), new OrderHistory());
        customerOrders.put(customer.getUserId(), customerOrderHistory);

        boolean stayInCustomerMenu = true;

        while (stayInCustomerMenu) {
            System.out.println("Customer Interface:");
            System.out.println("1. View menu");
            System.out.println("2. Filter menu by category");
            System.out.println("3. Sort menu by price");
            System.out.println("4. Add to cart");
            System.out.println("5. Modify cart");
            System.out.println("6. Checkout");
            System.out.println("7. View order history");
            System.out.println("8. Cancel an order");
            System.out.println("9. Return to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    menu.displayMenu();
                    break;
                case 2:
                    System.out.print("Enter category to filter by: ");
                    String category = scanner.nextLine();
                    List<FoodItem> filteredItems = menu.filterByCategory(category);
                    if (filteredItems.isEmpty()) {
                        System.out.println("No items found in this category.");
                    } else {
                        menu.displayMenu(filteredItems);
                    }
                    break;
                case 3:
                    System.out.print("Sort by price (1 for Low-High, 2 High-Low): ");
                    int sortChoice = scanner.nextInt();
                    List<FoodItem> sortedItems = menu.sortByPrice(sortChoice == 1);
                    menu.displayMenu(sortedItems);
                    break;
                case 4:
                    System.out.print("Enter item ID to add: ");
                    String itemId = scanner.nextLine();
                    FoodItem item = menu.getItemById(itemId);
                    if (item != null && item.isAvailable()) {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        cart.addItem(item, quantity);
                    } else {
                        System.out.println("Item unavailable or not found.");
                    }
                    break;
                case 5:
                    cart.displayCart();
                    System.out.print("Enter item ID to update: ");
                    itemId = scanner.nextLine();
                    item = menu.getItemById(itemId);
                    if (item != null && cart.getItems().containsKey(item)) {
                        System.out.print("Enter new quantity (0 to remove): ");
                        int quantity = scanner.nextInt();
                        if (quantity == 0) {
                            cart.removeItem(item);
                        } else {
                            cart.updateQuantity(item, quantity);
                        }
                    } else {
                        System.out.println("Item not found in cart.");
                    }
                    break;
                case 6: // Checkout
                    double total = cart.calculateTotal();
                    if (total > 0) {
                        Order order = new Order((HashMap<FoodItem, Integer>) cart.getItems(), total);
                        customerOrderHistory.addOrder(order);
                        orderHistory.addOrder(order);

                        System.out.println("Order placed successfully!");
                        order.displayOrderDetails();
                        cart.clearCart();
                    } else {
                        System.out.println("Cart is empty.");
                    }
                    break;
                case 7:
                    // Display order history
                    customerOrderHistory.displayOrderHistory();
                    break;
                case 8:
                    System.out.print("Enter order ID to cancel: ");
                    String orderId = scanner.nextLine();
                    if (customerOrderHistory.cancelOrder(orderId)) {
                        System.out.println("Order canceled successfully.");
                    } else {
                        System.out.println("Order not found or already completed.");
                    }
                    break;
                case 9:
                    stayInCustomerMenu = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addItem() {
        System.out.print("Enter item ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Is available (true/false): ");
        boolean available = scanner.nextBoolean();

        FoodItem item = new FoodItem(id, name, category, price, available);
        menu.addItem(item);
    }

    private void updateItem() {
        System.out.print("Enter item ID to update: ");
        String id = scanner.nextLine();
        System.out.print("Enter new price: ");
        double newPrice = scanner.nextDouble();
        System.out.print("Is available (true/false): ");
        boolean available = scanner.nextBoolean();

        menu.updateItem(id, newPrice, available);
    }

    private void removeItem() {
        System.out.print("Enter item ID to remove: ");
        String id = scanner.nextLine();
        menu.removeItem(id);
    }

    public static void main(String[] args) {
        ByteMeApp app = new ByteMeApp();
        app.mainMenu();
    }

    private void mainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("Enter user type:\n1 for Admin\n2 for Customer\n3 to Exit");
            int userType = scanner.nextInt();
            scanner.nextLine();

            switch (userType) {
                case 1:
                    boolean isAuthorized = false;
                    int attempts = 0;

                    while (attempts < 5) {
                        System.out.print("Enter admin username: ");
                        String adminName = scanner.nextLine();

                        if (adminName.equals("admincanteen")) {
                            isAuthorized = true;
                            break;
                        } else {
                            attempts++;
                            System.out.println("Incorrect username. Attempts remaining: " + (5 - attempts));
                        }
                    }

                    if (isAuthorized) {
                        adminInterface();
                    } else {
                        System.out.println("Unauthorized user. Returning to Main Menu.");
                    }
                    break;

                case 2:
                    System.out.print("Enter customer ID: ");
                    String customerId = scanner.nextLine();
                    User customer = new User(customerId, "Customer");
                    customerInterface(customer);
                    break;

                case 3:
                    System.out.println("Exiting the system. Visit again.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid user type. Please select again.");
            }
        }
    }
}
