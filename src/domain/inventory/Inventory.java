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
        if(products == null) return;

        System.out.println("==== Non-Persishable Products ====");
        for (Product product : products) {
            if(product instanceof NonPerishableProducts){
                NonPerishableProducts nonPerishableProducts = (NonPerishableProducts) product;
               System.out.println( nonPerishableProducts.getProductDetails());
            }
        }

        System.out.println("==== Persishable Products ====");
        for (Product product : products) {
            if(product instanceof PerishableProducts){
                PerishableProducts perishableProducts = (PerishableProducts) product;
                System.out.println(perishableProducts.getProductDetails());
            }
        }
        System.out.println();
    }

    public Product getProductByID(String id){
        for (Product product : products) {
            if(product.getId().contains(id)){
                return product;
            }   
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }
}
