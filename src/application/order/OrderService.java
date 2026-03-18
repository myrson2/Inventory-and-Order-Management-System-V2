package application.order;

import java.util.List;

import application.inventory.InventoryService;
import domain.order.Order;
import domain.order.OrderItem;
import domain.order.OrderStatus;
import domain.product.Product;
import domain.user.Customer;
import exception.InsufficientStockException;
import infrastructure.file.FileManager;
import infrastructure.history.OrderHistory;
import infrastructure.log.LoggerService;
import util.DateUtils;

public class OrderService {
    private InventoryService inventoryService;
    private LoggerService loggerService;
    private FileManager fileManager;
    private OrderHistory orderHistory;

    public OrderService(FileManager fileManager, LoggerService loggerService, InventoryService inventoryService, OrderHistory orderHistory){
        this.inventoryService = inventoryService;
        this.loggerService = loggerService;
        this.fileManager = fileManager;
        this.orderHistory = orderHistory;
    }

    public Order createOrder(Customer customer) {
        Order order = new Order(customer.getId());
        orderHistory.recordOrderCreation(order.getOrderId(), DateUtils.timeStamp());
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

        order.addItem(item);

        orderHistory.recordItemAdded(order.getOrderId(), product.getId(), quantity, DateUtils.timeStamp());
    }

    public void finalizeOrder(String name, Order order){
        order.updateOrderStatus(OrderStatus.CONFIRMED);
        orderHistory.recordStatusChange(order.getOrderId(), OrderStatus.CONFIRMED, DateUtils.timeStamp());

        fileManager.saveOrder(name, order.getItems());
    }

    public void cancelOrder(Order order){
        order.updateOrderStatus(OrderStatus.CANCELLED);
        orderHistory.recordStatusChange(order.getOrderId(), OrderStatus.CANCELLED, DateUtils.timeStamp());
    }

    public List<String> getHistory(){
        return orderHistory.getHistory();
    }

}
