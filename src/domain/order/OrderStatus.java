package domain.order;

public enum OrderStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELLED("cancelled");

    private String description;

  // Constructor (runs once for each constant above)
    private OrderStatus(String description) {
        this.description = description;
    }

    // Getter method to read the description
    public String getDescription() {
        return description;
    }
}
