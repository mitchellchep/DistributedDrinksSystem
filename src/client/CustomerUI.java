package client;

import shared.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class CustomerUI extends JFrame {
    private JTextField customerField;
    private JComboBox<String> branchBox;
    private JComboBox<String> drinkBox;
    private JTextField quantityField;
    private JTextArea outputArea;
    private OrderService service;

    public CustomerUI() {
        try {
            String host = JOptionPane.showInputDialog("Enter HQ Server IP:");
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            service = (OrderService) registry.lookup("OrderService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Failed to connect to server.");
            e.printStackTrace();
            System.exit(1);
        }

        setTitle("Drink Ordering System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Customer:");
        nameLabel.setBounds(10, 10, 100, 20);
        add(nameLabel);

        customerField = new JTextField();
        customerField.setBounds(120, 10, 200, 20);
        add(customerField);

        JLabel branchLabel = new JLabel("Branch:");
        branchLabel.setBounds(10, 40, 100, 20);
        add(branchLabel);

        branchBox = new JComboBox<>(new String[]{"NAKURU", "MOMBASA", "KISUMU", "NAIROBI"});
        branchBox.setBounds(120, 40, 200, 20);
        add(branchBox);

        JLabel drinkLabel = new JLabel("Drink:");
        drinkLabel.setBounds(10, 70, 100, 20);
        add(drinkLabel);

        drinkBox = new JComboBox<>();
        try {
            List<Drink> drinks = service.getAvailableDrinks();
            for (Drink d : drinks) {
                drinkBox.addItem(d.getId() + ": " + d.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        drinkBox.setBounds(120, 70, 200, 20);
        add(drinkBox);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setBounds(10, 100, 100, 20);
        add(qtyLabel);

        quantityField = new JTextField();
        quantityField.setBounds(120, 100, 200, 20);
        add(quantityField);

        JButton orderBtn = new JButton("Place Order");
        orderBtn.setBounds(120, 130, 200, 25);
        add(orderBtn);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 170, 360, 80);
        add(outputArea);

        orderBtn.addActionListener(this::placeOrder);
    }

    private void placeOrder(ActionEvent e) {
        try {
            String name = customerField.getText();
            String branch = (String) branchBox.getSelectedItem();
            String drinkId = ((String) drinkBox.getSelectedItem()).split(":")[0];
            int qty = Integer.parseInt(quantityField.getText());

            Drink drink = switch (drinkId) {
                case "D001" -> new Drink("D001", "Coca Cola", 100, qty);
                case "D002" -> new Drink("D002", "Pepsi", 90, qty);
                default -> null;
            };

            Order order = new Order("ORD" + System.currentTimeMillis(), name, branch, List.of(drink));
            boolean success = service.placeOrder(order);
            outputArea.setText(success ? "✅ Order placed." : "❌ Order failed.");
        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("❌ Error placing order.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerUI().setVisible(true));
    }
}
