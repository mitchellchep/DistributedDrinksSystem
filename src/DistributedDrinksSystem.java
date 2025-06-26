import java.time.LocalDateTime;
import java.util.*;
import java.time.localDateTime;

class Drink {
    String id;
    String name;
    double price;
    int quantity;
    public Drink(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

class Order {
    String orderId;
    String customerName;
    String branch;
    List<Drink> drinksOrdered;
    double totalPrice;
    LocalDateTime orderTime;

    public Order(String orderId, String customerName, String branch, List<Drink> drinksOrdered, double totalPrice){
        this.orderId = orderId;
        this.customerName = customerName;
        this.branch = branch;
        this.drinksOrdered = drinksOrdered;
        this.totalPrice = totalPrice;
        this.orderTime = LocalDateTime;
    }
}