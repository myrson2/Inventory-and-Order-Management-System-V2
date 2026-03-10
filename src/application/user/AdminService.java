package application.user;

import application.inventory.InventoryService;
import domain.product.Product;
import domain.user.User;

public class AdminService {
    private InventoryService inventoryService;

    public AdminService(InventoryService inventoryService){
        this.inventoryService = inventoryService;
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

    public void checkInventory(User user){
        inventoryService.checkInventory(user.getEmail());
    }
}
