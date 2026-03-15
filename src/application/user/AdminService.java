package application.user;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import application.inventory.InventoryService;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.User;
import infrastructure.file.FileManager;

public class AdminService {
    private InventoryService inventoryService;
    private FileManager fileManager;

    public AdminService(InventoryService inventoryService, FileManager fileManager){
        this.inventoryService = inventoryService;
        this.fileManager = fileManager;
    }

    public void addProduct(User user, Product product){
        inventoryService.addProduct(user.getEmail(), product);
    }

    public void updateStock(User user, String id, int newQuantity){
        inventoryService.updateStock(user.getEmail(), id, newQuantity);
    }

    public void removeProduct(User user, String id){
        inventoryService.removeProduct(user.getEmail(), id);
    }

    public void viewInventoryHistory(User user){
        inventoryService.viewInventoryHistory(user.getEmail());
    }

    // public void saveProducts(User user){
    //     fileManager.saveProducts(user.getName(), inventoryService.);
    // }

    public void getProducts(User user){
        List<Product> getProducts = inventoryService.checkInventory(user.getEmail());

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
}
