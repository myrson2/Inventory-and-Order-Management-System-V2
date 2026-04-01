package domain.inventory;

import java.util.ArrayList;
import java.util.List;

import domain.product.Product;
import exception.EntityNotFountException;

public class Inventory {
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public Product getProductByID(String id) throws EntityNotFountException{
        for (Product product : products) {
            if(product.getId().contains(id)){
                return product;
            }   
        }
        throw new EntityNotFountException("[Error]: Product Not Found.");
    }

    public List<Product> getProducts() {
        return products;
    }
}
