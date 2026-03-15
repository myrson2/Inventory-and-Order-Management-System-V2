package infrastructure.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;

public class FileManager {
    private File createFile(String name){
        File adminFile = new File("/src/infrastructure/file/" + name + ".txt"); // Create File object 
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(adminFile, true))) {
            for (Product product : AdminProducts) {
                if(product instanceof NonPerishableProducts) writer.write(product.getProductDetails());
            }

            for (Product product : AdminProducts) {
                if(product instanceof PerishableProducts) writer.write(product.getProductDetails());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing file.");
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
            System.out.println("Error writing file.");
        }
    }
}
    