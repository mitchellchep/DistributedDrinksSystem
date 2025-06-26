// ======================
// Core Models
// ======================

import java.util.*;
import java.time.LocalDateTime;

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

    public Order(String orderId, String customerName, String branch, List<Drink> drinksOrdered) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.branch = branch;
        this.drinksOrdered = drinksOrdered;
        this.totalPrice = drinksOrdered.stream().mapToDouble(d -> d.price * d.quantity).sum();
        this.orderTime = LocalDateTime.now();
    }
}

// ======================
// Branch Management
// ======================

class Branch {
    String name;
    Map<String, Drink> stock = new HashMap<>();
    List<Order> orders = new ArrayList<>();
    final int MIN_THRESHOLD = 10;

    public Branch(String name) {
        this.name = name;
    }

    public void addDrink(Drink drink) {
        stock.put(drink.id, drink);
    }

    public boolean processOrder(Order order) {
        for (Drink d : order.drinksOrdered) {
            Drink branchDrink = stock.get(d.id);
            if (branchDrink == null || branchDrink.quantity < d.quantity) return false;
        }
        // Deduct stock and store order
        for (Drink d : order.drinksOrdered) {
            stock.get(d.id).quantity -= d.quantity;
        }
        orders.add(order);
        checkStockLevels();
        return true;
    }

    public void checkStockLevels() {
        for (Drink d : stock.values()) {
            if (d.quantity < MIN_THRESHOLD) {
                System.out.println("[LOW STOCK ALERT] Drink: " + d.name + " at " + name);
            }
        }
    }

    public double getTotalSales() {
        return orders.stream().mapToDouble(o -> o.totalPrice).sum();
    }

    public void printBranchReport() {
        System.out.println("\n--- Sales Report for Branch: " + name + " ---");
        for (Order o : orders) {
            System.out.println("OrderID: " + o.orderId + ", Customer: " + o.customerName + ", Total: KES " + o.totalPrice);
        }
        System.out.println("Total Sales: KES " + getTotalSales());
    }
}

// ======================
// Headquarters Management
// ======================

class Headquarters {
    Map<String, Branch> branches = new HashMap<>();

    public void registerBranch(Branch b) {
        branches.put(b.name, b);
    }

    public void distributeStock(String drinkId, String drinkName, double price, int quantity, List<String> branchNames) {
        for (String branchName : branchNames) {
            Branch b = branches.get(branchName);
            if (b != null) {
                Drink drink = new Drink(drinkId, drinkName, price, quantity);
                b.addDrink(drink);
            }
        }
    }

    public void printAllReports() {
        double total = 0;
        for (Branch b : branches.values()) {
            b.printBranchReport();
            total += b.getTotalSales();
        }
        System.out.println("\n=== Final Business Report ===");
        System.out.println("Total Business Sales: KES " + total);
    }
}

// ======================
// Main Simulation for Demo
// ======================

public class DistributedDrinksSystem {
    public static void main(String[] args) {
        // Create HQ
        Headquarters hq = new Headquarters();

        // Create Branches
        Branch nakuru = new Branch("NAKURU");
        Branch mombasa = new Branch("MOMBASA");
        Branch kisumu = new Branch("KISUMU");
        Branch nairobi = new Branch("NAIROBI");

        hq.registerBranch(nakuru);
        hq.registerBranch(mombasa);
        hq.registerBranch(kisumu);
        hq.registerBranch(nairobi);

        // Distribute stock
        hq.distributeStock("D001", "Coca Cola", 100, 20, List.of("NAKURU", "MOMBASA", "KISUMU", "NAIROBI"));
        hq.distributeStock("D002", "Pepsi", 90, 20, List.of("NAKURU", "MOMBASA", "KISUMU", "NAIROBI"));

        // Place some orders
        Order o1 = new Order("ORD001", "Alice", "NAKURU", List.of(new Drink("D001", "Coca Cola", 100, 2)));
        nakuru.processOrder(o1);

        Order o2 = new Order("ORD002", "Bob", "MOMBASA", List.of(new Drink("D002", "Pepsi", 90, 3)));
        mombasa.processOrder(o2);

        Order o3 = new Order("ORD003", "Charlie", "NAIROBI", List.of(new Drink("D001", "Coca Cola", 100, 5)));
        nairobi.processOrder(o3);

        Order o4 = new Order("ORD004", "Diana", "KISUMU", List.of(new Drink("D001", "Coca Cola", 100, 15)));
        kisumu.processOrder(o4);

        // Print Reports
        hq.printAllReports();
    }
}
