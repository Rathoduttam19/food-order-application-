import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDate;

public class Order {
    public static final String STATUS_PLACED = "Placed";
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_DELIVERED = "Delivered";
    public static final String STATUS_CANCELLED_BY_CUSTOMER = "Cancelled by Customer";
    public static final String STATUS_REFUNDED = "Refunded";

    private String orderId;
    private HashMap<FoodItem, Integer> items;
    private double total;
    private String status; // "Placed", "Cancelled by Customer", "Refunded"
    private boolean refundProcessed;
    private String specialRequest;
    private LocalDate date;

    public Order(HashMap<FoodItem, Integer> items, double total) {
        this.orderId = UUID.randomUUID().toString();
        this.items = items;
        this.total = total;
        this.status = "Pending"; ;
        this.refundProcessed = false;
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public boolean isCustomerCanceled() {
        return "Cancelled by Customer".equals(status);
    }
    public boolean isRefundProcessed() {
        return refundProcessed;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void processRefund() {
        if (isCustomerCanceled() && !refundProcessed) {
            refundProcessed = true;
            setStatus(STATUS_REFUNDED);
            System.out.println("Refund processed for Order ID: " + orderId);
        } else {
            System.out.println("Refund already processed or order not canceled by customer.");
        }
    }

    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Status: " + status);
        System.out.println("Items Ordered:");
        for (HashMap.Entry<FoodItem, Integer> entry : items.entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("- " + item.getName() + " x " + quantity);
        }
        System.out.println("Total: " + total);
    }


    public void setSpecialRequest(String request) {
        this.specialRequest = request;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public double getTotal() {
        return total;
    }
    public HashMap<FoodItem, Integer> getItems() {
        return items;
    }
}
