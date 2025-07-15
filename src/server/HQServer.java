package server;

import shared.*;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HQServer {
    public static void main(String[] args) {
        try {
            // ✅ DO NOT create registry here — you already started it via rmiregistry
            OrderService hq = new Headquarters(); // Implementation class
            Naming.rebind("OrderService", hq); // Registers it with local registry

            System.out.println("✅ HQ Server is ready...");
        } catch (Exception e) {
            System.out.println("❌ Server exception:");
            e.printStackTrace();
        }
    }
}
