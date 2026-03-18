package domain.order;

import domain.product.Product;

public class OrderItem {
    private String productId;
    private int quantity;
    private double total;
 
    public OrderItem(Product product, int quantity) {
        this.productId   = product.getId();
        this.quantity    = quantity;
        this.total = calculateSubTotal(product);
    }

    public String getProductId()   { return productId; }
    public int    getQuantity()    { return quantity; }
    public double getTotal()   { 
        return total; 
    }

    private double calculateSubTotal(Product product){
        return product.getPrice() * quantity;
    }

    public String getItemDetails() {
        return "Product ID: " + productId + " | Quantity: " + quantity + " | Subtotal: " + total;
    }
}
