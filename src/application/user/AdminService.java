package application.user;

import application.inventory.InventoryService;
import domain.product.Product;

public class AdminService {
    private InventoryService inventoryService;

    public AdminService(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    public void addProduct(Product product){
        inventoryService.addProduct(product);
    }

    public void updateProduct(String id, int newQuantity){
        inventoryService.updateProduct(id, newQuantity);
    }
}
