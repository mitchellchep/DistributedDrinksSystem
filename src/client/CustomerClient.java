package client;

import shared.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class CustomerClient {
    public static void main(String[] args) {
        try {
            // ================================
            // 1. Connect to HQ Server
            // ================================
            Scanner input = new Scanner(System.in);
            System.out.print("Enter HQ Server IP (e.g., 127.0.0.1 or 192.168.x.x): ");
            String host = input.nextLine();

            Registry registry = LocateRegistry.getRegistry(host, 1099);
            OrderService service = (OrderService) registry.lookup("OrderService");

            // ================================
            // 2. Get Customer Order Info
            // ================================
            System.out.print("Enter your name: ");
            String customer = input.nextLine();

            System.out.print("Enter branch (NAKURU, MOMBASA, KISUMU, NAIROBI): ");
            String branch = input.nextLine().toUpperCase();

            System.out.print("Enter drink ID (D001 for Coca Cola, D002 for Pepsi): ");
            String drinkId = input.nextLine().toUpperCase();

            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(input.nextLine());

            // Create drink (for order only)
            Drink drink = switch (drinkId) {
                case "D001" -> new Drink("D001", "Coca Cola", 100, qty);
                case "D002" -> new Drink("D002", "Pepsi", 90, qty);
                default -> null;
            };

            if (drink == null) {
                System.out.println("Invalid drink ID. Try again.");
                return;
            }

            Order order = new Order("ORD" + System.currentTimeMillis(), customer, branch, List.of(drink));

            // ================================
            // 3. Place Order
            // ================================
            boolean success = service.placeOrder(order);

            if (success) {
                System.out.println("✅ Order placed successfully.");
            } else {
                System.out.println("❌ Order failed. Possibly out of stock.");
            }

            // ================================
            // 4. Optionally View Reports
            // ================================
            System.out.print("Would you like to view your branch report? (yes/no): ");
            String view = input.nextLine();
            if (view.equalsIgnoreCase("yes")) {
                String report = service.getBranchReport(branch);
                System.out.println(report);
            }

        } catch (Exception e) {
            System.out.println("❌ Error connecting to HQ:");
            e.printStackTrace();
        }
    }
}