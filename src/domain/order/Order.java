package domain.order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private OrderStatus status;
    private List<OrderItem> items;
 
    public Order(String orderId, String customerId) {
        this.orderId    = orderId;
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
}
