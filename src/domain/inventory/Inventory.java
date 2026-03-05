package domain.inventory;

import java.util.ArrayList;
import java.util.List;

import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;

public class Inventory {
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public void checkInventory(){
        System.out.println("==== Non-Persishable Products ====");
        for (Product product : products) {
            if(product instanceof NonPerishableProducts){
                NonPerishableProducts nonPerishableProducts = (NonPerishableProducts) product;
                System.out.printf("ID: %s || Name: %s || Price: %.2f || quantity: %d || Warranty (Months): %d", nonPerishableProducts.getId(), nonPerishableProducts.getName(), nonPerishableProducts.getPrice(), nonPerishableProducts.getQuantity(), nonPerishableProducts.getWarrantyMonths());
            }
        }

        System.out.println("==== Persishable Products ====");
        for (Product product : products) {
            if(product instanceof PerishableProducts){
                PerishableProducts perishableProducts = (PerishableProducts) product;

                System.out.printf("ID: %s || Name: %s || Price: %.2f || quantity: %d || Expiration Date: %s", perishableProducts.getId(), perishableProducts.getName(), perishableProducts.getPrice(), perishableProducts.getQuantity(), perishableProducts.getExpirationDate());
            }
        }
    }
}
