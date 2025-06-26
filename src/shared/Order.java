package shared;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Order implements Serializable {
    public String orderId;
    public String customerName;
    public String branch;
    public List<Drink> drinksOrdered;
    public double totalPrice;
    public LocalDateTime orderTime;

    public Order(String orderId, String customerName, String branch, List<Drink> drinksOrdered) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.branch = branch;
        this.drinksOrdered = drinksOrdered;
        this.totalPrice = drinksOrdered.stream().mapToDouble(d -> d.price * d.quantity).sum();
        this.orderTime = LocalDateTime.now();
    }
}
