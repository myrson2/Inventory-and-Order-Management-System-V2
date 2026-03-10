package application.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domain.inventory.Inventory;
import domain.product.Product;
import infrastructure.history.InventoryHistory;
import util.DateUtils;

public class InventoryService {
    
    // 1. HashMaps to act as your "database" for each admin
    private Map<String, Inventory> adminInventories = new HashMap<>();
    private Map<String, InventoryHistory> adminHistories = new HashMap<>();

    public InventoryService() {
        // We no longer need to pass InventoryHistory in the constructor 
        // because we create them automatically in the helper method below.
    } 

    // 2. Helper Method: Gets the admin's inventory, or creates a new one if it doesn't exist yet
    private Inventory getInventory(String adminEmail) {
        adminInventories.putIfAbsent(adminEmail, new Inventory());
        return adminInventories.get(adminEmail);
    }

    // 3. Helper Method: Gets the admin's history, or creates a new one if it doesn't exist yet
    private InventoryHistory getHistory(String adminEmail) {
        adminHistories.putIfAbsent(adminEmail, new InventoryHistory());
        return adminHistories.get(adminEmail);
    }

    // 4. Update all methods to require 'adminEmail' to find the correct data
    public void addProduct(String adminEmail, Product product){
        getInventory(adminEmail).addProduct(product);
        getHistory(adminEmail).recordAddProduct(product, DateUtils.timeStamp());
    }

    public void updateStock(String adminEmail, String id, int quantity){
        Inventory inventory = getInventory(adminEmail);
        InventoryHistory history = getHistory(adminEmail);
        Product product = inventory.getProductByID(id);

        if(product == null) {
            System.out.println("Not found.");
            return;
        }

        if(quantity > 0){
            product.increaseStock(quantity);
            history.recordStockIncrease(id, quantity, DateUtils.timeStamp());
        } else if (quantity < 0){
            product.decreaseStock(quantity);
            history.recordStockDecrease(id, quantity, DateUtils.timeStamp());
        } else {
            System.out.println("Amount should not be equal to zero.");
            return;
        }
    }

    public void removeProduct(String adminEmail, String id){
        Inventory inventory = getInventory(adminEmail);
        InventoryHistory history = getHistory(adminEmail);
        Product product = inventory.getProductByID(id);
        
        if(product != null) {
            inventory.getProducts().remove(product);
            history.recordProductRemoval(id, DateUtils.timeStamp());
        } else {
            System.out.println("Product not found.");
        }
    }

    public void viewInventoryHistory(String adminEmail){
        ArrayList<String> historyList = getHistory(adminEmail).getHistory();

        if (historyList.isEmpty()) {
            System.out.println("No history available.");
            return;
        }

        for (String h : historyList) {
            System.out.println(h);
        }
    }

    public void checkInventory(String adminEmail){
        getInventory(adminEmail).checkInventory();
    }
}