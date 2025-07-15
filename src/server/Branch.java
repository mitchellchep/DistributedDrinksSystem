package server;

import shared.Drink;
import shared.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Branch {
    private String name;
    private Map<String, Drink> drinks = new HashMap<>();

    public Branch(String name) {
        this.name = name;

        // Add drinks using their names as keys
        drinks.put("Coca Cola", new Drink("D001", "Coca Cola", 100.0, 10));
        drinks.put("Pepsi", new Drink("D002", "Pepsi", 90.0, 15));
    }

    public boolean processOrder(Order order) {
        List<Drink> orderedDrinks = order.getDrinks();

        // Check availability
        for (Drink ordered : orderedDrinks) {
            Drink stock = drinks.get(ordered.getName());
            if (stock == null || stock.getQuantity() < ordered.getQuantity()) {
                return false;
            }
        }

        // Deduct stock
        for (Drink ordered : orderedDrinks) {
            Drink stock = drinks.get(ordered.getName());
            stock.setQuantity(stock.getQuantity() - ordered.getQuantity());
        }

        return true;
    }

    public String getReportText() {
        StringBuilder sb = new StringBuilder("[" + name + "] Sales Report:\n");
        for (Drink d : drinks.values()) {
            sb.append(d.getName()).append(" - Remaining Stock: ").append(d.getQuantity()).append("\n");
        }
        return sb.toString();
    }

    public double getTotalSales() {
        // Optional logic can go here (e.g., price * quantity sold)
        return 0;
    }

    public void reset() {
        drinks.get("Coca Cola").setQuantity(10);
        drinks.get("Pepsi").setQuantity(15);
    }
}
