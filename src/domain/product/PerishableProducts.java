package domain.product;

import java.time.LocalDate;

public class PerishableProducts extends Product{
    private LocalDate expirationDate;
    public PerishableProducts(String id, String name, double price, int quantity, LocalDate expirationDate) {
        super(id, name, price, quantity);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
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
        String details = String.format("ID: %s || Name: %s || Price: %.2f || quantity: %d || Expiration Date: %s", super.getId(), super.getName(), super.getPrice(), super.getQuantity(), expirationDate);
       return details;
    }
}