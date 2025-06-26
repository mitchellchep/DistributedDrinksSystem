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
    }

    public String getName() {
        return name;
    }

    public void addDrink(Drink drink) {
        stock.put(drink.id, drink);
    }

    public boolean processOrder(Order order) {
        for (Drink d : order.drinksOrdered) {
            Drink branchDrink = stock.get(d.id);
            if (branchDrink == null || branchDrink.quantity < d.quantity) return false;
        }
        for (Drink d : order.drinksOrdered) {
            stock.get(d.id).quantity -= d.quantity;
        }
        orders.add(order);
        checkStockLevels();
        return true;
    }

    private void checkStockLevels() {
        for (Drink d : stock.values()) {
            if (d.quantity < MIN_THRESHOLD) {
                System.out.println("[LOW STOCK ALERT] " + d.name + " at " + name);
            }
        }
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
}
