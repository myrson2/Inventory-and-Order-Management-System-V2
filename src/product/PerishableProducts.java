package product;

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
}