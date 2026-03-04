package product;

public class NonPerishableProducts extends Product{
    private int warrantyMonths;
    public NonPerishableProducts(String id, String name, double price, int quantity, int warrantyMonths) {
        super(id, name, price, quantity);
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }
}
