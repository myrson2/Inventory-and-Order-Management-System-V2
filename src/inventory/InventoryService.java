package inventory;

public class InventoryService {
    private Inventory inventory;
    private InventoryHistory inventoryHistory;

     public InventoryService(InventoryHistory inventoryHistory) {
        this.inventoryHistory = inventoryHistory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryHistory getInventoryHistory() {
        return inventoryHistory;
    }

}
