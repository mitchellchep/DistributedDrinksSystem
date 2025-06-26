package server;

import shared.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Headquarters implements OrderService {
    private Map<String, Branch> branches = new HashMap<>();

    public Headquarters() {
        initializeBranches();
    }

    public void initializeBranches() {
        Branch nakuru = new Branch("NAKURU");
        Branch mombasa = new Branch("MOMBASA");
        Branch kisumu = new Branch("KISUMU");
        Branch nairobi = new Branch("NAIROBI");

        registerBranch(nakuru);
        registerBranch(mombasa);
        registerBranch(kisumu);
        registerBranch(nairobi);

        distributeStock("D001", "Coca Cola", 100, 20, List.of("NAKURU", "MOMBASA", "KISUMU", "NAIROBI"));
        distributeStock("D002", "Pepsi", 90, 20, List.of("NAKURU", "MOMBASA", "KISUMU", "NAIROBI"));
    }

    public void registerBranch(Branch branch) {
        branches.put(branch.getName(), branch);
    }

    public void distributeStock(String id, String name, double price, int quantity, List<String> branchNames) {
        for (String branchName : branchNames) {
            Branch branch = branches.get(branchName);
            if (branch != null) {
                Drink drink = new Drink(id, name, price, quantity);
                branch.addDrink(drink);
            }
        }
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        Branch branch = branches.get(order.branch);
        if (branch != null) {
            return branch.processOrder(order);
        }
        return false;
    }

    @Override
    public String getBranchReport(String branchName) throws RemoteException {
        Branch branch = branches.get(branchName);
        if (branch != null) {
            return branch.getReportText();
        }
        return "Branch not found.";
    }

    @Override
    public String getFinalBusinessReport() throws RemoteException {
        StringBuilder report = new StringBuilder();
        double total = 0;
        for (Branch b : branches.values()) {
            report.append(b.getReportText());
            total += b.getTotalSales();
        }
        report.append("\n=== Final Business Report ===\nTotal Sales: KES ").append(total);
        return report.toString();
    }
}
