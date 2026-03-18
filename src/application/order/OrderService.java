package application.order;

import java.util.List;

import application.inventory.InventoryService;
import domain.order.Order;
import domain.order.OrderItem;
import domain.order.OrderStatus;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.Admin;
import domain.user.Customer;
import exception.InsufficientStockException;
import infrastructure.file.FileManager;
import infrastructure.history.OrderHistory;
import infrastructure.log.LoggerService;

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
    }

    public void finalizeOrder(Order order){
        order.updateOrderStatus(OrderStatus.CONFIRMED);
    }

    public void cancelOrder(Order order){
        order.updateOrderStatus(OrderStatus.CANCELLED);
    }

}
