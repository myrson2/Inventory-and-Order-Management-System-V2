package application.inventory;

import domain.inventory.Inventory;
import domain.product.Product;

public class InventoryService {
   private Inventory inventory;

   public void addProduct(Product product){
        inventory.addProduct(product);
   }
}
