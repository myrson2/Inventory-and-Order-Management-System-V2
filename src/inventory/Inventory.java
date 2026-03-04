package inventory;

import java.util.ArrayList;
import java.util.List;

import product.Product;

public class Inventory {
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }
}
