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

    public void updateStock(String id, int newQuantity){
        inventoryService.updateStock(id, newQuantity);
    }

    public void removeProduct(String id){
        inventoryService.removeProduct(id);
    }

    public void viewInventoryHistory(){
        inventoryService.viewInventoryHistory();
    }

    public void checkInventory(){
        inventoryService.checkInventory();
    }
}
