package server;

import shared.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
        Branch branch = branches.get(order.branch.toUpperCase());
        if (branch != null) {
            return branch.processOrder(order);
        }
        return false;
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
