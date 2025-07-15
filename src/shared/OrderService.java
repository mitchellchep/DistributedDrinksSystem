// FILE: shared/OrderService.java
package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface OrderService extends Remote {
    boolean placeOrder(Order order) throws RemoteException;
    String getBranchReport(String branchName) throws RemoteException;
    String getFinalBusinessReport() throws RemoteException;
    void resetSystem() throws RemoteException;
    List<Drink> getAvailableDrinks() throws RemoteException;
}