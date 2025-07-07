package server;

import shared.Drink;
import shared.Order;

import java.util.*;

public class Branch {
    private String name;
    private Map<String, Drink> stock = new HashMap<>();
    private List<Order> orders = new ArrayList<>();
    private final int MIN_THRESHOLD = 10;

    public Branch(String name) {
        this.name = name;
        addDrink(new Drink("D001", "Coca Cola", 100, 20));
        addDrink(new Drink("D002", "Pepsi", 90, 20));
    }

    public String getName() {
        return name;
    }

    public void addDrink(Drink drink) {
        stock.put(drink.id, drink);
    }

    public boolean processOrder(Order order) {
        for (Drink d : order.drinksOrdered) {
            Drink stockDrink = stock.get(d.id);
            if (stockDrink == null || stockDrink.quantity < d.quantity) return false;
        }
        for (Drink d : order.drinksOrdered) {
            stock.get(d.id).quantity -= d.quantity;
        }
        orders.add(order);
        return true;
    }

    public double getTotalSales() {
        return orders.stream().mapToDouble(o -> o.totalPrice).sum();
    }

    public String getReportText() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Sales Report for Branch: ").append(name).append(" ---\n");
        for (Order o : orders) {
            sb.append("OrderID: ").append(o.orderId)
                    .append(", Customer: ").append(o.customerName)
                    .append(", Total: KES ").append(o.totalPrice).append("\n");
        }
        sb.append("Total Sales: KES ").append(getTotalSales()).append("\n");
        return sb.toString();
    }

    public void reset() {
        stock.clear();
        orders.clear();
        addDrink(new Drink("D001", "Coca Cola", 100, 20));
        addDrink(new Drink("D002", "Pepsi", 90, 20));
    }
}
