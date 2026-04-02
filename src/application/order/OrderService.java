package application.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.inventory.InventoryService;
import domain.order.Order;
import domain.order.OrderItem;
import domain.order.OrderStatus;
import domain.product.Product;
import domain.user.Admin;
import domain.user.Customer;
import exception.InsufficientStockException;
import infrastructure.file.FileManager;
import infrastructure.history.OrderHistory;
import infrastructure.log.LoggerService;
import infrastructure.notification.NotificationService;
import util.DateUtils;

public class OrderService {
    private InventoryService inventoryService;
    private LoggerService loggerService;
    private FileManager fileManager;
    private OrderHistory orderHistory;
    private NotificationService notificationService;

    private HashMap<String, ArrayList<Order>> orderList = new HashMap<>();
    
    public OrderService(NotificationService notificationService,FileManager fileManager, LoggerService loggerService, InventoryService inventoryService, OrderHistory orderHistory){
        this.inventoryService = inventoryService;
        this.loggerService = loggerService;
        this.fileManager = fileManager;
        this.orderHistory = orderHistory;
        this.notificationService = notificationService;
    }

    public Order createOrder(Customer customer, Admin admin) {
        Order order = new Order(customer.getId());
        orderHistory.recordOrderCreation(order.getOrderId(), DateUtils.timeStamp());
        orderList.put(admin.getEmail(), new ArrayList<>());
        return order;
    }

    public void addItemToOrder(Order order, Product product, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException("Not enough stock");
        }

        OrderItem item = new OrderItem(product, quantity);
        order.getItems().add(item);
        orderHistory.recordItemAdded(order.getOrderId(), product.getId(), quantity, DateUtils.timeStamp());
    }

    public void finalizeOrder(String name, Order order){
        order.updateOrderStatus(OrderStatus.CONFIRMED);
        orderHistory.recordStatusChange(order.getOrderId(), OrderStatus.CONFIRMED, DateUtils.timeStamp());
        notificationService.notifyOrderStatusChange(order);

        fileManager.saveOrder(name, order.getItems());
    }

    public void cancelOrder(Order order){
        order.updateOrderStatus(OrderStatus.CANCELLED);
        orderHistory.recordStatusChange(order.getOrderId(), OrderStatus.CANCELLED, DateUtils.timeStamp());
        notificationService.notifyOrderStatusChange(order);
    }

    public List<String> getHistory(){
        return orderHistory.getHistory();
    }

    public ArrayList<Order> getOrders(String email){
        return orderList.get(email);
    }
}
