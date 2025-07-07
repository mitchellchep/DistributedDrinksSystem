package admin;

import shared.OrderService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.Naming;

public class AdminUI extends JFrame {
    private JTextField ipField;
    private JTextArea output;
    private OrderService service;

    public AdminUI() {
        setTitle("Admin HQ");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ipField = new JTextField("127.0.0.1");

        JButton connectBtn = new JButton("Connect");
        JButton reportBtn = new JButton("View Report");
        JButton resetBtn = new JButton("Reset");

        topPanel.add(new JLabel("Server IP:"));
        topPanel.add(ipField);
        topPanel.add(connectBtn);
        topPanel.add(reportBtn);
        topPanel.add(new JLabel());
        topPanel.add(resetBtn);

        output = new JTextArea(); output.setEditable(false);
        output.setBorder(BorderFactory.createTitledBorder("Admin Output"));

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
        setVisible(true);

        connectBtn.addActionListener(this::connect);
        reportBtn.addActionListener(e -> showReport());
        resetBtn.addActionListener(e -> resetSystem());
    }

    private void connect(ActionEvent e) {
        try {
            service = (OrderService) Naming.lookup("rmi://" + ipField.getText().trim() + ":1099/OrderService");
            output.setText("✅ Connected to HQ.\n");
        } catch (Exception ex) {
            output.setText("❌ Connection failed: " + ex.getMessage());
        }
    }

    private void showReport() {
        try {
            output.setText(service.getFinalBusinessReport());
        } catch (Exception ex) {
            output.setText("❌ Failed to get report: " + ex.getMessage());
        }
    }

    private void resetSystem() {
        try {
            service.resetSystem();
            output.append("🔄 System reset complete.\n");
        } catch (Exception ex) {
            output.setText("❌ Reset failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminUI::new);
    }
}
