package util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IdGenerator {
    private static final Set<String> adminIDs = new HashSet<>();
     private static final Set<String> customerIDs = new HashSet<>();
    private static final Set<String> productUsedIds = new HashSet<>();
    private static final Set<String> orderUsedIds = new HashSet<>();
    private static final Random random = new Random();

    private static String generate(Set<String> usedIds) {
        final int MAX_ATTEMPTS = 10000; // 0000–9999 = 10000 possible IDs

        for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
            String id = String.format("%04d", random.nextInt(10000));
            if (!usedIds.contains(id)) {
                usedIds.add(id);
                return id;
            }
        }

        throw new IllegalStateException("Maximum ID limit reached.");
    }

    public static String generateAdminID() {
        return "A-" + generate(adminIDs);
    }

    public static String generateCustomerID() {
        return "C-" + generate(customerIDs);
    }

    public static String generateProductID() {
        return "P-" + generate(productUsedIds);
    }

    public static String generateOrderID(){
        return "O-" + generate(orderUsedIds);
    }
}