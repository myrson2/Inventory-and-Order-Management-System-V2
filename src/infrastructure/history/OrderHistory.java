package infrastructure.history;

import java.util.ArrayList;

import domain.order.OrderStatus;

public class OrderHistory {

    private ArrayList<String> history = new ArrayList<>();

    public void recordOrderCreation(String orderId, String date) {
        history.add("Order Created: " + orderId + " || Time Stamp: " + date);
    }

    public void recordItemAdded(String orderId, String productId, int quantity, String date) {
        history.add("Item Added: " + productId + " x " + quantity +
                    " in Order: " + orderId +
                    " || Time Stamp: " + date);
    }

    public void recordStatusChange(String orderId, OrderStatus status, String date) {
        history.add("Order Status Changed: " + orderId +
                    " -> " + status +
                    " || Time Stamp: " + date);
    }

    public void recordCancellation(String orderId, String date) {
        history.add("Order Cancelled: " + orderId +
                    " || Time Stamp: " + date);
    }

    public ArrayList<String> getHistory() {
        return history;
    }
}