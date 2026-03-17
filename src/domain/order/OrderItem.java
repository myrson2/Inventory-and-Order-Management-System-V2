package domain.order;

public class OrderItem {
    private String productId;
    private int quantity;
    private double total;
 
    public OrderItem(String productId, int quantity, double total) {
        this.productId   = productId;
        this.quantity    = quantity;
        this.total = total;
    }

    public String getProductId()   { return productId; }
    public int    getQuantity()    { return quantity; }
    public double getTotal()   { 
        return total; 
    }
}
