package domain.order;

import java.util.ArrayList;
import java.util.List;

import util.IdGenerator;

public class Order {
    private String orderId;
    private String customerId;
    private OrderStatus status;
    private List<OrderItem> items;
 
    public Order(String customerId) {
        this.orderId    = IdGenerator.generateOrderID();
        this.customerId = customerId;
        this.status     = OrderStatus.PENDING;
        this.items      = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }
    public List<OrderItem> getItems() {
        return items;
    }
    public String getOrderId() {
        return orderId;
    }
    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(OrderItem orderItem){
        getItems().add(orderItem);
    }

    public double calculateTotal(){
        double total = 0;
        for (OrderItem orderItem : items) {
            total += orderItem.getTotal();
        }

        return total;
    }

    public void updateOrderStatus(OrderStatus action) {

        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Order already finalized.");
        }

        if (action == OrderStatus.CONFIRMED) {
            status = OrderStatus.CONFIRMED;

        } else if (action == OrderStatus.CANCELLED) {
            status = OrderStatus.CANCELLED;

        } else {
            throw new IllegalArgumentException("Invalid action.");
        }
    }
}
