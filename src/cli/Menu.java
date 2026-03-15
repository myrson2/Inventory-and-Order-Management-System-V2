package cli;

public class Menu {
    public static void displayMainMenu(){
        System.out.print("""
                1 - login 
                2 - logout
                """);
    }

    public static void AdminOptions(){
        System.out.println("""
                0. Exit
                1. Add Product
                2. Update Stock
                3. Remove Product
                4. View All Orders
                5. View Logs
                6. View Inventory History
                7. Save To File
                8. Load To File
                """);
    }
    public static void CustomerOptions(){
        System.out.println("""
            0. Exit
            1. Browse Products
            2. Create Order
            3. Add Item to Order
            4. Finalize Order
            5. Cancel Order
            6. View Order History    
        """);
    }
}
