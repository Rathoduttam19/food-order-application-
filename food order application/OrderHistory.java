import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderHistory {
    private List<Order> orders;

    public OrderHistory() {
        orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void displayOrderHistory() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("Order History:");
        for (Order order : orders) {
            order.displayOrderDetails();
            System.out.println("----------------------------------------");
        }
    }

    public boolean cancelOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId) &&
                    (order.getStatus().equals(Order.STATUS_PLACED) || order.getStatus().equals(Order.STATUS_PENDING))) {
                order.setStatus(Order.STATUS_CANCELLED_BY_CUSTOMER);
                System.out.println("Order " + orderId + " marked as 'Cancelled by Customer'.");
                return true;
            }
        }
        System.out.println("Order not found or already completed.");
        return false;
    }

    public List<Order> getCustomerCancelledOrders() {
        List<Order> customerCancelledOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.isCustomerCanceled() && !order.isRefundProcessed()) {
                System.out.println("Found customer-canceled order awaiting refund: " + order.getOrderId());
                customerCancelledOrders.add(order);
            }
        }
        return customerCancelledOrders;
    }

    public Order getOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        System.out.println("Order ID " + orderId + " not found in order history.");
        return null;
    }

    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("Pending")) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }
    public void generateDailySalesReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter report date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        LocalDate reportDate = LocalDate.parse(dateInput);

        double totalSales = 0.0;
        int totalItemsSold = 0;
        int completedOrders = 0;

        for (Order order : orders) {
            if (order.getDate().equals(reportDate) && "Delivered".equals(order.getStatus())) {
                totalSales += order.getTotal();
                completedOrders++;
                for (HashMap.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                    int quantity = entry.getValue();
                    totalItemsSold += quantity;
                }
            }
        }

        System.out.println("\nDaily Sales Report for " + reportDate + ":");
        System.out.println("Total Sales: Rs" + totalSales);
        System.out.println("Total Items Sold: " + totalItemsSold);
        System.out.println("Total Completed Orders: " + completedOrders);
    }


    public boolean updateOrderStatus(String orderId, String newStatus) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(newStatus);
                System.out.println("Order " + orderId + " status updated to: " + newStatus);
                return true;
            }
        }
        System.out.println("Order ID not found.");
        return false;
    }
}
