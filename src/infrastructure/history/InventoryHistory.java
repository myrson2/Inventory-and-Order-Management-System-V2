package infrastructure.history;

import java.util.ArrayList;

import domain.product.Product;

public class InventoryHistory {

    private ArrayList<String> history = new ArrayList<>();

    public void recordAddProduct(Product product, String date){
        history.add("New Added Product: " + product.getName() + " || Time Stamp: " + date);
    }

    public void recordStockIncrease(String productId, int amount, String date) {
        history.add("Stock increased: " + productId + " + " + amount + " || Time Stamp: " + date);
    }

    public void recordStockDecrease(String productId, int amount, String date) {
        history.add("Stock decreased: " + productId + " - " + amount + " || Time Stamp: " + date);
    }

    public void recordProductRemoval(String productId, String date) {
        history.add("Product removed: " + productId + " || Time Stamp: " + date);
    }

    public ArrayList<String> getHistory() {
        return history;
    }
}
