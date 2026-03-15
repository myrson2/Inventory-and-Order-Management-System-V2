package application.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.inventory.Inventory;
import domain.product.Product;
import infrastructure.history.InventoryHistory;
import infrastructure.log.LoggerService;
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
    public boolean addProduct(String adminEmail, Product product){
        try {
            getInventory(adminEmail).addProduct(product);
            getHistory(adminEmail).recordAddProduct(product, DateUtils.timeStamp());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateStock(String adminEmail, String id, int quantity, LoggerService loggerService) {
        try {
            Inventory inventory = getInventory(adminEmail);
            InventoryHistory history = getHistory(adminEmail);
            Product product = inventory.getProductByID(id);

            if (product == null) {
                return false;
            }
            if (quantity == 0) return false;

            if (quantity > 0) {
                product.increaseStock(quantity);
                history.recordStockIncrease(id, quantity, DateUtils.timeStamp());
            } else {
                product.decreaseStock(quantity);
                history.recordStockDecrease(id, quantity, DateUtils.timeStamp());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeProduct(String adminEmail, String id) {
    try {
        Inventory inventory = getInventory(adminEmail);
        InventoryHistory history = getHistory(adminEmail);
        Product product = inventory.getProductByID(id);

        if (product == null) return false;

        inventory.getProducts().remove(product);
        history.recordProductRemoval(id, DateUtils.timeStamp());
        return true;
    } catch (Exception e) {
        return false;
    }
}

    public ArrayList<String> viewInventoryHistory(String adminEmail){
        ArrayList<String> historyList = getHistory(adminEmail).getHistory();

        return historyList;
    }

    public List<Product> checkInventory(String adminEmail){
        return getInventory(adminEmail).getProducts();
    }
}