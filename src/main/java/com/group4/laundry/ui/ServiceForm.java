package com.group4.laundry.ui;
import com.group4.laundry.model.Service;
import com.group4.laundry.service.ServiceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;

/** Member 2: services. Commit this file together with its .form file. */
public class ServiceForm extends javax.swing.JFrame {
    public ServiceForm() {
        initComponents();
        setupServiceInterface();
        loadServices();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        titleLabel = new javax.swing.JLabel();
        contentScroll = new javax.swing.JScrollPane();
        placeholderText = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Services | Group 4");
        titleLabel.setText("Services - Member 2");
        getContentPane().add(titleLabel, java.awt.BorderLayout.NORTH);

        placeholderText.setEditable(false);
        placeholderText.setColumns(50);
        placeholderText.setRows(12);
        placeholderText.setText("Module placeholder\n\nManage service names, prices per kg and load capacity.\n\nTables: services\n\nNo records are loaded or saved yet.\nAdd form fields using the NetBeans Design tab.");
        contentScroll.setViewportView(placeholderText);
        getContentPane().add(contentScroll, java.awt.BorderLayout.CENTER);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(closeButton, java.awt.BorderLayout.SOUTH);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private final ServiceManager serviceManager = new ServiceManager();

    private JTable serviceTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextField priceField;
    private JTextField capacityField;

    private void setupServiceInterface() {

        setTitle("Service Management | Group 4");

        // Replace the placeholder content at runtime
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel heading = new JLabel("Laundry Service Management");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(heading, BorderLayout.NORTH);

        // Service table
        String[] columns = {
            "Service ID", "Service Name",
            "Price per KG", "Max KG per Load"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        serviceTable = new JTable(tableModel);
        serviceTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        serviceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillSelectedService();
            }
        });

        mainPanel.add(
            new JScrollPane(serviceTable),
            BorderLayout.CENTER
        );

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));

        nameField = new JTextField();
        priceField = new JTextField();
        capacityField = new JTextField();

        inputPanel.add(new JLabel("Service Name:"));
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Price per KG:"));
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Max KG per Load:"));
        inputPanel.add(capacityField);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        JButton newCloseButton = new JButton("Close");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(newCloseButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 10));
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addService());
        updateButton.addActionListener(e -> updateService());
        deleteButton.addActionListener(e -> deleteService());
        refreshButton.addActionListener(e -> loadServices());
        newCloseButton.addActionListener(e -> dispose());

        setContentPane(mainPanel);
        setSize(850, 500);
        setMinimumSize(new Dimension(700, 400));
    }

    private void loadServices() {
        try {
            tableModel.setRowCount(0);

            for (Service service : serviceManager.getAllServices()) {
                tableModel.addRow(new Object[]{
                    service.getServiceId(),
                    service.getServiceName(),
                    service.getPricePerKg(),
                    service.getMaxKgPerLoad()
                });
            }

        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void fillSelectedService() {
        int row = serviceTable.getSelectedRow();

        if (row >= 0) {
            int modelRow = serviceTable.convertRowIndexToModel(row);

            nameField.setText(
                tableModel.getValueAt(modelRow, 1).toString()
            );
            priceField.setText(
                tableModel.getValueAt(modelRow, 2).toString()
            );
            capacityField.setText(
                tableModel.getValueAt(modelRow, 3).toString()
            );
        }
    }

    private void addService() {
        try {
            serviceManager.addService(
                nameField.getText(),
                new BigDecimal(priceField.getText().trim()),
                new BigDecimal(capacityField.getText().trim())
            );

            JOptionPane.showMessageDialog(
                this, "Service added successfully."
            );

            loadServices();
            clearFields();

        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void updateService() {
        int row = serviceTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                this, "Please select a service to update."
            );
            return;
        }

        try {
            int modelRow = serviceTable.convertRowIndexToModel(row);
            int id = (int) tableModel.getValueAt(modelRow, 0);

            serviceManager.updateService(
                id,
                nameField.getText(),
                new BigDecimal(priceField.getText().trim()),
                new BigDecimal(capacityField.getText().trim())
            );

            JOptionPane.showMessageDialog(
                this, "Service updated successfully."
            );

            loadServices();
            clearFields();

        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void deleteService() {
        int row = serviceTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                this, "Please select a service to delete."
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this service?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int modelRow = serviceTable.convertRowIndexToModel(row);
            int id = (int) tableModel.getValueAt(modelRow, 0);

            serviceManager.deleteService(id);

            JOptionPane.showMessageDialog(
                this, "Service deleted successfully."
            );

            loadServices();
            clearFields();

        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void clearFields() {
        serviceTable.clearSelection();
        nameField.setText("");
        priceField.setText("");
        capacityField.setText("");
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(
            this,
            ex.getMessage(),
            "Service Management Error",
            JOptionPane.ERROR_MESSAGE
        );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane contentScroll;
    private javax.swing.JTextArea placeholderText;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}