package client;

import shared.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class CustomerUI extends JFrame {

    private JTextField nameField;
    private JComboBox<String> branchCombo;
    private JComboBox<String> drinkCombo;
    private JTextField qtyField;
    private JTextField ipField;
    private JTextArea output;

    public CustomerUI() {
        setTitle("Customer Order - Distributed Drinks");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Form Panel ===
        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        ipField = new JTextField("127.0.0.1"); // default to localhost
        nameField = new JTextField();
        branchCombo = new JComboBox<>(new String[]{"NAKURU", "MOMBASA", "KISUMU", "NAIROBI"});
        drinkCombo = new JComboBox<>(new String[]{"D001 - Coca Cola", "D002 - Pepsi"});
        qtyField = new JTextField();

        form.add(new JLabel("Server IP:"));
        form.add(ipField);
        form.add(new JLabel("Your Name:"));
        form.add(nameField);
        form.add(new JLabel("Select Branch:"));
        form.add(branchCombo);
        form.add(new JLabel("Select Drink:"));
        form.add(drinkCombo);
        form.add(new JLabel("Quantity:"));
        form.add(qtyField);

        // === Buttons ===
        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.addActionListener(this::placeOrder);

        JButton resetBtn = new JButton("Place Another Order");
        resetBtn.addActionListener(evt -> {
            nameField.setText("");
            qtyField.setText("");
            drinkCombo.setSelectedIndex(0);
            branchCombo.setSelectedIndex(0);
            output.setText("");
        });

        form.add(placeOrderBtn);
        form.add(resetBtn);

        // === Output area ===
        output = new JTextArea();
        output.setEditable(false);
        output.setBorder(BorderFactory.createTitledBorder("Status"));

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
        setVisible(true);
    }

    private void placeOrder(ActionEvent e) {
        try {
            String serverIP = ipField.getText().trim();
            Registry registry = LocateRegistry.getRegistry(serverIP, 1099);
            OrderService service = (OrderService) registry.lookup("OrderService");

            String customerName = nameField.getText().trim();
            String branch = (String) branchCombo.getSelectedItem();
            String drinkSelection = (String) drinkCombo.getSelectedItem();
            int quantity = Integer.parseInt(qtyField.getText().trim());

            String drinkId = drinkSelection.startsWith("D001") ? "D001" : "D002";
            String drinkName = drinkSelection.contains("Coca") ? "Coca Cola" : "Pepsi";
            double price = drinkId.equals("D001") ? 100 : 90;

            Drink drink = new Drink(drinkName, price, quantity);
            Order order = new Order("ORD" + System.currentTimeMillis(), customerName, branch, List.of(drink));

            boolean success = service.placeOrder(order);
            if (success) {
                double totalCost = price * quantity;
                output.setText("✅ Order placed successfully!\n");
                output.append("💰 Total: KES " + totalCost + "\n");
            } else {
                output.setText("❌ Order failed. Not enough stock or error.");
            }

        } catch (Exception ex) {
            output.setText("❌ Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerUI::new);
    }
}