package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OrderService extends Remote {
    boolean placeOrder(Order order) throws RemoteException;
    String getBranchReport(String branchName) throws RemoteException;
    String getFinalBusinessReport() throws RemoteException;
}
