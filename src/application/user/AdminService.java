package application.user;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import application.inventory.InventoryService;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.User;
import infrastructure.file.FileManager;
import infrastructure.log.LoggerService;

public class AdminService {
    private InventoryService inventoryService;
    private FileManager fileManager;
    private LoggerService loggerService;

    public AdminService(InventoryService inventoryService, LoggerService loggerService, FileManager fileManager){
        this.inventoryService = inventoryService;
        this.fileManager = fileManager;
        this.loggerService = loggerService;
    }

    public void addProduct(User user, Product product){
        boolean isAdded = inventoryService.addProduct(user.getEmail(), product);

        if(isAdded){
            loggerService.logInfo(user.getEmail(), String.format("[INFO]: %s is added.".toUpperCase(), product.getName()).toUpperCase());
        } else {
            loggerService.logInfo(user.getEmail(), String.format("[WARNING]: ADD FAILED.", product.getName()).toUpperCase());
        }
    }

    public void updateStock(User user, String id, int newQuantity){
        boolean isUpdated = inventoryService.updateStock(user.getEmail(), id, newQuantity, loggerService);

        if (isUpdated) {
            loggerService.logInfo(user.getEmail(), String.format("[INFO]: Product Successfully Updated.".toUpperCase()));
        } else {
            loggerService.logWarning(user.getEmail(), String.format("[WARNING]: Amount should not be equal to zero or Product Not Found.".toUpperCase()));
        }
    }

    public void removeProduct(User user, String id){
        boolean isRemoved = inventoryService.removeProduct(user.getEmail(), id);

        if (isRemoved) {
            loggerService.logInfo(user.getEmail(), String.format("[WARNING]: Product NOT found."));
        } else {
            loggerService.logInfo(user.getEmail(), String.format("[INFO]: Product Successfully Removed."));
        }
    }

    public void viewInventoryHistory(User user){
        ArrayList<String> historyList = inventoryService.viewInventoryHistory(user.getEmail());

        if (historyList.isEmpty()) {
            System.out.println("No history available.");
            return;
        }

        for (String h : historyList) {
            System.out.println(h);
        }
    }

    // public void saveProducts(User user){
    //     fileManager.saveProducts(user.getName(), inventoryService.);
    // }

    public void getProducts(User user){
        List<Product> getProducts = inventoryService.checkInventory(user.getEmail());

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

    public void viewLogs(User user){
        loggerService.displayLogs(user.getEmail());
    }
}
