import java.time.LocalDateTime;
import java.util.*;
import java.time.localDateTime;

class Drink {
    String id;
    String name;
    double price;
    int quantity;
    public Drink(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

class Order {
    String orderId;
    String customerName;
    String branch;
    List<Drink> drinksOrdered;
    double totalPrice;
    LocalDateTime orderTime;

    public Order(String orderId, String customerName, String branch, List<Drink> drinksOrdered, double totalPrice){
        this.orderId = orderId;
        this.customerName = customerName;
        this.branch = branch;
        this.drinksOrdered = drinksOrdered;
        this.totalPrice = totalPrice;
        this.orderTime = LocalDateTime;
    }

    //Branch manangement
    class Branch {
        String name;
        Map<String, Drink> stock = new HashMap<>();
        List<Order> orders = new ArrayList<>();
        final int MIN_THRESHOLD = 10;

        public Branch(String name) {
            this.name = name;
        }

        public void addDrink(Drink drink) {
            stock.put(drink.id, drink);
        }

        public boolean processOrder(Order order){
            for (Drink d: order.drinksOrdered) {
                Drink branchDrink = stock.get(d.id);
                if (branchDrink == null || branchDrink.quantity <d.quantity>)
                    return false;
            }
            //Deduct stock and store order
            for (Drink d : order.drinksOrdered){
                stock.get(d.id).quantity -= d.quantity;
            }
            orders.add(order);
            checkStockLevels();
            return true;
        }

        public void checkStockLevels() {
            for (Drink d: stock.values()){
                if(d.quantity < MIN_THRESHOLD){
                    System.out.println("[Low stock alert] Drink:"+d.name+"at"+name);
                }
            }
        }
        public double getTotalSales(){
            return orders.stream().mapToDouble(o->o.totalPrice).sum();
        }
        public void printBranchReport(){
            System.out.println("\n--- Sales report for branch:"+name+"---");
            for (Order o :orders){
                System.out.println("OrderID:"+o.orderId+", Customer: "+o.customerName+", Total: KES "+o.totalPrice);
            }
            System.out.println("Total sales: KES "+getTotalSales());
        }
    }
}