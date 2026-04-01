package application.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.inventory.Inventory;
import domain.product.Product;
import exception.EntityNotFountException;
import infrastructure.file.FileManager;
import infrastructure.history.InventoryHistory;
import infrastructure.log.LoggerService;
import infrastructure.notification.NotificationService;
import util.DateUtils;

public class InventoryService {
    private FileManager fileManager;
    private LoggerService loggerService;
    private InventoryHistory inventoryHistory;
    private NotificationService notificationService;

    // 1. HashMaps to act as your "database" for each admin
    private Map<String, Inventory> adminInventories = new HashMap<>();
    private Map<String, InventoryHistory> adminHistories = new HashMap<>();

    public InventoryService(FileManager fileManager, LoggerService loggerService, InventoryHistory inventoryHistory, NotificationService notificationService) {
        this.fileManager = fileManager;
        this.loggerService = loggerService;
        this.inventoryHistory = inventoryHistory;
        this.notificationService = notificationService;
    } 

    // 2. Helper Method: Gets the admin's inventory, or creates a new one if it doesn't exist yet
    private Inventory getInventory(String adminEmail) 
    {
        adminInventories.putIfAbsent(adminEmail, new Inventory());
        return adminInventories.get(adminEmail); // return inventory associated to admin email 
    }
    // 3. Helper Method: Gets the admin's history, or creates a new one if it doesn't exist yet
    private InventoryHistory getHistory(String adminEmail) {
        adminHistories.putIfAbsent(adminEmail, inventoryHistory);
        return adminHistories.get(adminEmail);
    }

    // 4. Update all methods to require 'adminEmail' to find the correct data
    public boolean addProduct(String adminEmail, Product product)
    {
        try {
            getInventory(adminEmail).addProduct(product); 
            getHistory(adminEmail).recordAddProduct(product, DateUtils.timeStamp());
            notificationService.notify("[NOTIFICATION]: Successfully Added a Product\n");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateStock(String adminEmail, String id, int quantity, LoggerService loggerService) 
    {
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

            if(product.getQuantity() < 2){
                notificationService.notifyLowStock(product);
                loggerService.logWarning(adminEmail, String.format("[WARNING]: LOW STOCK IN %s", product.getName().toUpperCase()));
            }

            notificationService.notify("[NOTIFICATION]: Successfully Updated");
            return true;
        } catch (EntityNotFountException e) {
            notificationService.notify("[NOTIFICATION]: Failed To Update");
            e.getMessage();
            // e.printStackTrace();
            return false;
        }
    }

    public boolean removeProduct(String adminEmail, String id) 
    {
    try {
        Inventory inventory = getInventory(adminEmail);
        InventoryHistory history = getHistory(adminEmail);
        Product product = inventory.getProductByID(id);

        if (product == null) return false;

        inventory.getProducts().remove(product);
        history.recordProductRemoval(id, DateUtils.timeStamp());
        notificationService.notify("[NOTIFICATION]: Successfully Remove");
        return true;
    } catch (EntityNotFountException e) {
        notificationService.notify("[NOTIFICATION]: Failed To Remove");
        e.getMessage();
        return false;
    }
}

    public ArrayList<String> viewInventoryHistory(String adminEmail){
        return getHistory(adminEmail).getHistory();
    }

    public List<Product> getListOfProducts(String adminEmail) throws EntityNotFountException{
        List<Product> product = getInventory(adminEmail).getProducts();
        if(product == null) throw new EntityNotFountException("Admin not Found.");
        return product;
    }
}