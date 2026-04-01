package infrastructure.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import domain.order.OrderItem;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;

public class FileManager {
    private File createFile(String name){
        File adminFile = new File(name + ".txt"); // Create File object 
        try {
            if (adminFile.createNewFile()) {           // Try to create the file
                System.out.println("File created: " + adminFile.getName());
            } else {
                System.out.println("File already exists.");
                 return adminFile;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace(); // Print error details
        }
        return adminFile;
    }
    
    public void saveProducts(String name, List<Product> AdminProducts) throws IOException{
       File adminFile = createFile(name);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(adminFile))) {
            for (Product product : AdminProducts) {
                if(product instanceof NonPerishableProducts) writer.write(product.getProductDetails());
            }

            for (Product product : AdminProducts) {
                if(product instanceof PerishableProducts) writer.write(product.getProductDetails());
            }

            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving Products.");
             e.printStackTrace(); // Print error details
        }
    }

    public void loadProducts(String name) throws IOException{
        File adminFile = createFile(name);

        try(BufferedReader reader = new BufferedReader(new FileReader(adminFile))){
            String adminFIles;

            while((adminFIles = reader.readLine()) != null){
                System.out.println(adminFIles);
            }
        } catch (IOException e) {
            System.out.println("Error loading Products.");
             e.printStackTrace(); // Print error details
        }
    }

    public void saveOrder(String name, List<OrderItem> items){
        File customerFile = createFile(name);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customerFile))) {
            writer.write(name + "'s Cart: \n");
            for (OrderItem orderItem : items) {
                writer.write(orderItem.getItemDetails());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving Order.");
             e.printStackTrace(); // Print error details
        }
    }

    public void loadOrder(String name) throws IOException{
        File adminFile = createFile(name);

        try(BufferedReader reader = new BufferedReader(new FileReader(adminFile))){
            String adminFIles;

            while((adminFIles = reader.readLine()) != null){
                System.out.println(adminFIles);
            }
        } catch (IOException e) {
            System.out.println("Error loading Order.");
             e.printStackTrace(); // Print error details
        }
    }

}
    