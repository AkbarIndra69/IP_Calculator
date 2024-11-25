/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author AKBAR
 */
// File: SubnetCalculatorGUI.java
import javax.swing.*;
import java.awt.*;

public class SubnetCalculatorGUI extends JFrame {
    private JTextField ipField;
    private JComboBox<String> maskCombo;
    private JTextArea resultArea;
    private JButton calculateButton;
    private JButton clearButton;
    
    public SubnetCalculatorGUI() {
        initializeComponents();
        setupLayout();
        addEventListeners();
        finalizeFrame();
    }
    
    private void initializeComponents() {
        ipField = new JTextField(15);
        
        String[] masks = new String[31];
        for (int i = 0; i < 31; i++) {
            masks[i] = "/" + (i + 1);
        }
        maskCombo = new JComboBox<>(masks);
        maskCombo.setSelectedItem("/24");
        
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        
        calculateButton = new JButton("Calculate");
        clearButton = new JButton("Clear");
    }
    
    private void setupLayout() {
        setTitle("Subnet Calculator");
        setLayout(new BorderLayout(10, 10));
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("IP Address:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        inputPanel.add(ipField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        inputPanel.add(new JLabel("Subnet Mask:"), gbc);
        
        gbc.gridx = 1;
        inputPanel.add(maskCombo, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        
        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);
    }
    
    private void addEventListeners() {
        calculateButton.addActionListener(e -> calculate());
        clearButton.addActionListener(e -> clear());
    }
    
    private void calculate() {
        try {
            IPAddress ip = new IPAddress(ipField.getText());
            int prefix = Integer.parseInt(maskCombo.getSelectedItem().toString().substring(1));
            SubnetMask mask = new SubnetMask(prefix);
            NetworkCalculator calculator = new NetworkCalculator(ip, mask);
            
            StringBuilder result = new StringBuilder();
            result.append("IP Address: ").append(ip).append("\n");
            result.append("Subnet Mask: ").append(mask).append("\n");
            result.append("Network Address: ").append(calculator.getNetworkAddress()).append("\n");
            result.append("Broadcast Address: ").append(calculator.getBroadcastAddress()).append("\n");
            result.append("Number of Usable Hosts: ").append(calculator.getNumberOfHosts()).append("\n");
            
            resultArea.setText(result.toString());
            
        } catch (InvalidIPException | InvalidSubnetException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clear() {
        ipField.setText("");
        maskCombo.setSelectedItem("/24");
        resultArea.setText("");
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