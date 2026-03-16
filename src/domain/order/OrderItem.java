package domain.order;

public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double total;
 
    public OrderItem(String productId, String productName, int quantity, double total) {
        this.productId   = productId;
        this.productName = productName;
        this.quantity    = quantity;
        this.total = total;
    }

    public String getProductId()   { return productId; }
    public String getProductName() { return productName; }
    public int    getQuantity()    { return quantity; }
    public double getTotal()   { 
        return total; 
    }
}
