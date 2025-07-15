package shared;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private String customerName;
    private String branch;
    private List<Drink> drinks;

    public Order(String orderId, String customerName, String branch, List<Drink> drinks) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.branch = branch;
        this.drinks = drinks;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBranch() {
        return branch;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", branch='" + branch + '\'' +
                ", drinks=" + drinks +
                '}';
    }
}
