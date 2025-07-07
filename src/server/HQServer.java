package server;

import shared.OrderService;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class HQServer {
    public static void main(String[] args) {
        try {
            // 👇 Add this line to explicitly bind to your Linux IP
            System.setProperty("java.rmi.server.hostname", "192.168.100.33");

            LocateRegistry.createRegistry(1099);

            OrderService hq = new Headquarters();

            // 👇 Update this line to use your Linux IP in the RMI URL
            Naming.rebind("rmi://192.168.100.33:1099/OrderService", hq);

            System.out.println("✅ HQ Server ready at 192.168.100.33:1099.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
