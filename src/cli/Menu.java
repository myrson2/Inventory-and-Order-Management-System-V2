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
                3. View All Orders
                4. View Logs
                5. View Inventory History
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
