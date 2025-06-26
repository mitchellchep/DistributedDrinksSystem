package server;

import shared.OrderService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HQServer {
    public static void main(String[] args) {
        try {
            Headquarters hq = new Headquarters();

            OrderService stub = (OrderService) UnicastRemoteObject.exportObject(hq, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("OrderService", stub);

            System.out.println("HQ Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
