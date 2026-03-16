package application.user;

import java.util.List;

import application.inventory.InventoryService;
import application.order.OrderService;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.Admin;
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

    public double getProducts(String productId, User user){ 
        List<Product> adminProducts = inventoryService.getListOfProducts(user.getEmail()); 

        for (Product product : adminProducts) {
            if (productId.equalsIgnoreCase(product.getId())) {
                return product.getPrice();
            }
        }

        throw new IllegalArgumentException("Product with id " + productId + " not found");
    }
}
