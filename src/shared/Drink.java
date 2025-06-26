package shared;

import java.io.Serializable;

public class Drink implements Serializable {
    public String id;
    public String name;
    public double price;
    public int quantity;

    public Drink(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
