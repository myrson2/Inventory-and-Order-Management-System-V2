package util;

import java.util.HashSet;
import java.util.Set;

public class IdGenerator {
    private static final Set<Integer> userUsedIds = new HashSet<>();
    private static final Set<Integer> productUsedIds = new HashSet<>();
    
    public static String userIDenerateID(){
        int currentId = 0;
        final int MAX_ID = 1000;

        if (currentId > MAX_ID) {
            throw new IllegalStateException("Maximum ID limit reached.");
        }

        while (userUsedIds.contains(currentId)) {
            currentId++;
        }

        int id = currentId;
        userUsedIds.add(id);

        String userId = String.valueOf(id);
        currentId++;

        return userId;
    }

    public static String productIDGenerator(){
        int currentId = 0;
        final int MAX_ID = 1000;

        if (currentId > MAX_ID) {
            throw new IllegalStateException("Maximum ID limit reached.");
        }

        // Ensure product IDs are unique across all products.
        while (productUsedIds.contains(currentId)) {
            currentId++;
        }

        int id = currentId;
        productUsedIds.add(id);

        String productId = String.valueOf(id);

        return productId;
    }
}
