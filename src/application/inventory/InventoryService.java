package application.inventory;

import domain.inventory.Inventory;
import domain.product.Product;

public class InventoryService {
   private Inventory inventory;

   public InventoryService(Inventory inventory) {
      this.inventory = inventory;
}

   public void addProduct(Product product){
        inventory.addProduct(product);
   }

   public void updateStock(String id, int quantity){
      Product product = inventory.getProductByID(id);

      if(product == null) {
            System.out.println("Not found.");
            return;
      }

      if(quantity > 0){
            product.increaseStock(quantity);
      } else if (quantity < 0){
            product.decreaseStock(quantity);
      } else {
            System.out.println("Amount should not be equal to zero.");
            return;
      }
   }

   public void checkInventory(){
      inventory.checkInventory();
   }
}
