package domain.inventory;

import java.util.ArrayList;
import java.util.List;

import domain.product.Product;

public class Inventory {
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
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
