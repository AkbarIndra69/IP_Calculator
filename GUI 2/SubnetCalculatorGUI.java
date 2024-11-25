/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI2;

/**
 *
 * @author AKBAR
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class SubnetCalculatorGUI extends JFrame {
    // Input fields
    private JTextField[] ipFields;
    private JTextField prefixField;
    private JTextField subnetMaskField;
    private JTextField networkAddressField;
    private JTextField broadcastAddressField;
    private JTextField numberOfSubnetsField;
    private JTextField hostsPerSubnetField;
    private JTextField kelasIPField;
    private JButton calculateButton;
    private JButton resetButton;

    public SubnetCalculatorGUI() {
        setTitle("Kalkulator Subnet");
        initializeComponents();
        setupLayout();
        addEventListeners();
        finalizeFrame();
    }

    private void initializeComponents() {
        // Initialize IP address fields
        ipFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            ipFields[i] = new JTextField(3);
        }

        prefixField = new JTextField(3);
        kelasIPField = new JTextField(3);
        subnetMaskField = new JTextField(20);
        networkAddressField = new JTextField(20);
        broadcastAddressField = new JTextField(20);
        numberOfSubnetsField = new JTextField(20);
        hostsPerSubnetField = new JTextField(20);

        // Make result fields read-only
        subnetMaskField.setEditable(false);
        networkAddressField.setEditable(false);
        broadcastAddressField.setEditable(false);
        numberOfSubnetsField.setEditable(false);
        hostsPerSubnetField.setEditable(false);
        kelasIPField.setEditable(false);

        // Initialize buttons
        calculateButton = new JButton("Hitung");
        resetButton = new JButton("Reset");
    }

    private void setupLayout() {
        // Main panel with some padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // IP Address row
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(createStyledLabel("IP Address"), gbc);

        JPanel ipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        for (int i = 0; i < 4; i++) {
            ipPanel.add(ipFields[i]);
            if (i < 3) ipPanel.add(new JLabel("."));
        }
        gbc.gridx = 1;
        mainPanel.add(ipPanel, gbc);

        // Prefix label and field
        gbc.gridx = 2;
        mainPanel.add(createStyledLabel("Prefix  /"), gbc);
        gbc.gridx = 3;
        mainPanel.add(prefixField, gbc);

        // Subnet Mask row
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(createStyledLabel("Subnet Mask"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(subnetMaskField, gbc);

        // Kelas IP
        gbc.gridx = 2; gbc.gridwidth = 1;
        mainPanel.add(createStyledLabel("Kelas IP"), gbc);
        gbc.gridx = 3;
        mainPanel.add(kelasIPField, gbc);

        // Network Address row
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        mainPanel.add(createStyledLabel("Network ID"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(networkAddressField, gbc);

        // Broadcast Address row
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        mainPanel.add(createStyledLabel("IP Broadcast"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(broadcastAddressField, gbc);

        // Number of Subnets row
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        mainPanel.add(createStyledLabel("Jumlah Subnet"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(numberOfSubnetsField, gbc);

        // Hosts Per Subnet row
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        mainPanel.add(createStyledLabel("Jumlah Host Per Subnet"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(hostsPerSubnetField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(calculateButton);
        buttonPanel.add(resetButton);
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(0, 0, 139)); // Dark blue color
        return label;
    }

    private void addEventListeners() {
        calculateButton.addActionListener(e -> calculateSubnet());
        resetButton.addActionListener(e -> resetFields());
    }

    private void calculateSubnet() {
        try {
            // Get IP address
            StringBuilder ip = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                ip.append(ipFields[i].getText());
                if (i < 3) ip.append(".");
            }
            
            IPAddress ipAddress = new IPAddress(ip.toString());
            int prefix = Integer.parseInt(prefixField.getText());
            SubnetMask mask = new SubnetMask(prefix);
            NetworkCalculator calculator = new NetworkCalculator(ipAddress, mask);
            
            // Update fields
            subnetMaskField.setText(mask.toString());
            networkAddressField.setText(calculator.getNetworkAddress());
            broadcastAddressField.setText(calculator.getBroadcastAddress());
            numberOfSubnetsField.setText(String.valueOf(calculator.getNumberOfSubnets()));
            hostsPerSubnetField.setText(String.valueOf(calculator.getNumberOfHosts()));
            kelasIPField.setText(determineIPClass(ipAddress));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String determineIPClass(IPAddress ip) {
        int firstOctet = ip.getOctets()[0];
        if (firstOctet <= 127) return "A";
        if (firstOctet <= 191) return "B";
        if (firstOctet <= 223) return "C";
        if (firstOctet <= 239) return "D";
        return "E";
    }

    private void resetFields() {
        for (JTextField field : ipFields) {
            field.setText("");
        }
        prefixField.setText("");
        subnetMaskField.setText("");
        networkAddressField.setText("");
        broadcastAddressField.setText("");
        numberOfSubnetsField.setText("");
        hostsPerSubnetField.setText("");
        kelasIPField.setText("");
    }

    private void finalizeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SubnetCalculatorGUI().setVisible(true);
        });
    }
}