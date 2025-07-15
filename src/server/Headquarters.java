package server;

import shared.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Headquarters extends UnicastRemoteObject implements OrderService {
    private Map<String, List<Order>> branchOrders;
    private List<Drink> menu;

    public Headquarters() throws RemoteException {
        branchOrders = new HashMap<>();
        menu = List.of(
                new Drink("D001", "Coca Cola", 100, 100),
                new Drink("D002", "Pepsi", 90, 100)
        );
    }

    @Override
    public synchronized boolean placeOrder(Order order) throws RemoteException {
        List<Order> orders = branchOrders.computeIfAbsent(order.getBranch(), k -> new ArrayList<>());
        orders.add(order);
        return true;
    }

    @Override
    public synchronized String getBranchReport(String branchName) throws RemoteException {
        List<Order> orders = branchOrders.getOrDefault(branchName, new ArrayList<>());
        StringBuilder report = new StringBuilder("Branch Report for " + branchName + ":\n");
        for (Order order : orders) {
            report.append("Order ID: ").append(order.getOrderId())
                    .append(", Customer: ").append(order.getCustomerName())
                    .append(", Drinks: ").append(order.getDrinks()).append("\n");
        }
        return report.toString();
    }

    @Override
    public synchronized String getFinalBusinessReport() throws RemoteException {
        StringBuilder report = new StringBuilder("Final Business Report:\n");
        for (String branch : branchOrders.keySet()) {
            report.append(getBranchReport(branch)).append("\n");
        }
        return report.toString();
    }

    @Override
    public synchronized void resetSystem() throws RemoteException {
        branchOrders.clear();
    }

    @Override
    public List<Drink> getAvailableDrinks() throws RemoteException {
        return menu;
    }
}
