package server;

import shared.Drink;
import shared.Order;

import java.util.HashMap;
import java.util.Map;

public class Branch {
    private String name;
    private Map<String, Drink> drinks = new HashMap<>();

    public Branch(String name) {
        this.name = name;

        // ✅ Fixed constructor calls (only name, price, quantity)
        drinks.put("Coca Cola", new Drink("Coca Cola", 100.0, 10));
        drinks.put("Pepsi", new Drink("Pepsi", 90.0, 15));
    }

    public boolean processOrder(Order order) {
        for (Drink ordered : order.drinksOrdered) {
            Drink stock = drinks.get(ordered.name);
            if (stock == null || stock.quantity < ordered.quantity) {
                return false;
            }
        }

        for (Drink ordered : order.drinksOrdered) {
            Drink stock = drinks.get(ordered.name);
            stock.quantity -= ordered.quantity;
        }

        return true;
    }

    public String getReportText() {
        StringBuilder sb = new StringBuilder("[" + name + "] Sales Report:\n");
        for (Drink d : drinks.values()) {
            sb.append(d.name).append(" - Remaining Stock: ").append(d.quantity).append("\n");
        }
        return sb.toString();
    }

    public double getTotalSales() {
        // Optional: Implement logic if needed
        return 0;
    }

    public void reset() {
        drinks.get("Coca Cola").quantity = 10;
        drinks.get("Pepsi").quantity = 15;
    }
}
