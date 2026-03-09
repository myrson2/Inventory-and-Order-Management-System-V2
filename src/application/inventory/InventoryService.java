package application.inventory;

import java.util.ArrayList;
import java.util.List;

import domain.inventory.Inventory;
import domain.product.Product;
import infrastructure.history.InventoryHistory;
import util.DateUtils;

public class InventoryService {
   private Inventory inventory;
   private InventoryHistory inventoryHistory;

   public InventoryService(Inventory inventory, InventoryHistory inventoryHistory) {
      this.inventory = inventory;
      this.inventoryHistory = inventoryHistory;
   } 

   public void addProduct(Product product){
        inventory.addProduct(product);
        inventoryHistory.recordAddProduct(product, DateUtils.timeStamp());
   }

   public void updateStock(String id, int quantity){
      Product product = inventory.getProductByID(id);

      if(product == null) {
            System.out.println("Not found.");
            return;
      }

      if(quantity > 0){
            product.increaseStock(quantity);
            inventoryHistory.recordStockIncrease(id, quantity, DateUtils.timeStamp());
      } else if (quantity < 0){
            product.decreaseStock(quantity);
            inventoryHistory.recordStockDecrease(id, quantity, DateUtils.timeStamp());
      } else {
            System.out.println("Amount should not be equal to zero.");
            return;
      }
   }

   public void removeProduct(String id){
      Product product = inventory.getProductByID(id);
      inventory.getProducts().remove(product);
      inventoryHistory.recordProductRemoval(id, DateUtils.timeStamp());
   }

   public void viewInventoryHistory(){
      ArrayList<String> history = inventoryHistory.getHistory();

      for (String h : history) {
            System.out.println(h);
      }
   }

   public void checkInventory(){
      inventory.checkInventory();
   }
}
