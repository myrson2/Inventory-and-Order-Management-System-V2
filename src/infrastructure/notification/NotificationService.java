package infrastructure.notification;

import domain.order.Order;
import domain.product.Product;

public class NotificationService {
    public void notify(String message) 
    {
        System.out.println("[NOTIFICATION]: " + message);
    }

    public void notifyLowStock(Product product)
    {
        System.out.printf("[NOTIFICATION]: Item (%s) is in Low Stock. (Stock: %d)\n", product.getName(), product.getQuantity());
    }

    public void notifyOrderStatusChange(Order order)
    {
        System.out.println("[NOTIFICATION]: " + );
    }
}
