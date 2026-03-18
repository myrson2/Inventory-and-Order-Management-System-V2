package application.user;

import java.util.List;

import application.inventory.InventoryService;
import application.order.OrderService;
import domain.order.Order;
import domain.order.OrderItem;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.Admin;
import domain.user.Customer;
import domain.user.User;
import infrastructure.log.LoggerService;

public class CustomerService {
    private OrderService orderService;
    private InventoryService inventoryService;
    private LoggerService loggerService;

    public CustomerService(OrderService orderService, InventoryService inventoryService, LoggerService loggerService){
        this.orderService = orderService;
        this.inventoryService = inventoryService;
        this.loggerService = loggerService;
    }

    public void browseProducts(Admin choosenAdmin){
        List<Product> getProducts = inventoryService.getListOfProducts(choosenAdmin.getEmail());

        if(getProducts.isEmpty()) return;

        System.out.println("Non-Perishable Products: ");
        for (Product product : getProducts) {
            if (product instanceof NonPerishableProducts) {
                System.out.println(product.getProductDetails());
            }
        }

        System.out.println();

        System.out.println("Perishable Products: ");
        for (Product product : getProducts) {
            if (product instanceof PerishableProducts) {
                System.out.println(product.getProductDetails());
            }
        }
    }

    public Product getProducts(String productId, User user){ 
        List<Product> adminProducts = inventoryService.getListOfProducts(user.getEmail()); 

        for (Product product : adminProducts) {
            if (productId.equalsIgnoreCase(product.getId())) {
                return product;
            }
        }

        throw new IllegalArgumentException("Product with id " + productId + " not found");
    }

    public Order createOrder(Customer customer) {
        return orderService.createOrder(customer);
    }

   public void addItemToOrder(Order order, Product product, int quantity) {
       orderService.addItemToOrder(order, product, quantity);
    }

    public void finalizeOrder(String name, Order order){
        orderService.finalizeOrder(name, order);
    }

    public void cancelOrder(Order order){
        orderService.cancelOrder(order);
    }

    public void viewOrderHistory(){
        List<String> orderHistory =  orderService.getHistory();
        for (String history : orderHistory) {
            System.out.println(history);
        }

    }
}
