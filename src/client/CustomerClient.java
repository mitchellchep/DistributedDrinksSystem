package client;

import shared.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class CustomerClient {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter HQ server IP (default 127.0.0.1): ");
            String ip = sc.nextLine().trim();
            if (ip.isEmpty()) ip = "172.20.10.2";

            Registry registry = LocateRegistry.getRegistry(ip, 1099);
            OrderService service = (OrderService) registry.lookup("OrderService");

            System.out.print("Enter your name: ");
            String customerName = sc.nextLine();

            System.out.println("Select Branch:");
            String[] branches = {"NAKURU", "MOMBASA", "KISUMU", "NAIROBI"};
            for (int i = 0; i < branches.length; i++) {
                System.out.println((i + 1) + ". " + branches[i]);
            }
            int branchIndex = sc.nextInt();
            sc.nextLine(); // consume newline
            String branchName = branches[branchIndex - 1];

            System.out.println("Select Drink:");
            String[] drinks = {"D001 - Coca Cola", "D002 - Pepsi"};
            for (int i = 0; i < drinks.length; i++) {
                System.out.println((i + 1) + ". " + drinks[i]);
            }
            int drinkIndex = sc.nextInt();
            sc.nextLine(); // consume newline

            String drinkId = drinkIndex == 1 ? "D001" : "D002";
            String drinkName = drinkIndex == 1 ? "Coca Cola" : "Pepsi";
            double price = drinkIndex == 1 ? 100.0 : 90.0;

            System.out.print("Enter quantity: ");
            int quantity = sc.nextInt();

            Drink drink = new Drink(drinkId, drinkName, price, quantity);
            Order order = new Order("ORD" + System.currentTimeMillis(), customerName, branchName, List.of(drink));

            boolean success = service.placeOrder(order);
            if (success) {
                System.out.println("✅ Order placed successfully!");
                System.out.println("Branch: " + branchName);
                System.out.println("Drink: " + drinkName + " x " + quantity);
                System.out.println("Total: KES " + (price * quantity));
            } else {
                System.out.println("❌ Order failed. Either stock is insufficient or system error.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
