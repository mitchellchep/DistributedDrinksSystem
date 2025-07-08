package server;

import shared.*;
import utils.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Headquarters extends UnicastRemoteObject implements OrderService {
    private Map<String, Branch> branches = new HashMap<>();

    public Headquarters() throws RemoteException {
        branches.put("NAKURU", new Branch("NAKURU"));
        branches.put("MOMBASA", new Branch("MOMBASA"));
        branches.put("KISUMU", new Branch("KISUMU"));
        branches.put("NAIROBI", new Branch("NAIROBI"));
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        System.out.println("➡️ Received order: " + order.orderId + " for customer " + order.customerName);
        Branch branch = branches.get(order.branch.toUpperCase());

        if (branch == null) {
            System.out.println("❌ Invalid branch: " + order.branch);
            return false;
        }

        boolean success = branch.processOrder(order);
        if (!success) {
            System.out.println("❌ Order failed due to insufficient stock or drink not found.");
            return false;
        }

        System.out.println("✅ Order processed, saving to DB...");
        saveOrderToDatabase(order);
        return true;
    }



    private void saveOrderToDatabase(Order order) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("📝 Writing to DB..."); // ← Add this here

            String sql = "INSERT INTO orders (order_id, customer_name, branch, drink_name, quantity, price_per_unit, total_price) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            for (Drink d : order.drinksOrdered) {
                double totalDrinkPrice = d.price * d.quantity;

                stmt.setString(1, order.orderId);
                stmt.setString(2, order.customerName);
                stmt.setString(3, order.branch);
                stmt.setString(4, d.name);
                stmt.setInt(5, d.quantity);
                stmt.setDouble(6, d.price);
                stmt.setDouble(7, totalDrinkPrice);

                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("✅ Order saved to DB: " + order.orderId);

        } catch (SQLException e) {
            System.err.println("❌ Error saving to DB: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public String getFinalBusinessReport() throws RemoteException {
        StringBuilder sb = new StringBuilder("=== Final HQ Report ===\n");
        double totalSales = 0;
        for (Branch b : branches.values()) {
            sb.append(b.getReportText()).append("\n");
            totalSales += b.getTotalSales();
        }
        sb.append("==== Total Company Sales: KES ").append(totalSales).append(" ====");
        return sb.toString();
    }

    @Override
    public void resetSystem() throws RemoteException {
        for (Branch b : branches.values()) {
            b.reset();
        }
    }
}
