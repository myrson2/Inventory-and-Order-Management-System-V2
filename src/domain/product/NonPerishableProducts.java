package domain.product;

public class NonPerishableProducts extends Product{
    private int warrantyMonths;
    public NonPerishableProducts(String id, String name, double price, int quantity, int warrantyMonths) {
        super(id, name, price, quantity);
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    @Override
    public int increaseStock(int amount) {
        int quantity = super.getQuantity();
        quantity += amount;
        super.setQuantity(quantity);
        
        return quantity;
    }

    @Override
    public int decreaseStock(int amount) {
        int quantity = super.getQuantity();
        quantity -= Math.abs(amount);
        super.setQuantity(quantity);
        
        return quantity;
    }

    @Override
    public String getProductDetails() {
        return String.format("ID: %s || Name: %s || Price: %.2f || quantity: %d || Warranty (Months): %d\n", super.getId(), super.getName(), super.getPrice(), super.getQuantity(), warrantyMonths);
    }
}
