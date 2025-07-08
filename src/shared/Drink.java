package shared;

import java.io.Serializable;

public class Drink implements Serializable {
    public String name;
    public double price;
    public int quantity;

    public Drink(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
