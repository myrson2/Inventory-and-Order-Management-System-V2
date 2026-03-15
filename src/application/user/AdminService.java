package application.user;

import application.inventory.InventoryService;
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

    public void saveProducts(User user){
        fileManager.saveProducts(user.getName(), inventoryService.);
    }

    public void checkInventory(User user){
        inventoryService.checkInventory(user.getEmail());
    }
}
