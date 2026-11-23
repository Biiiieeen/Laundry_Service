
package com.group4.laundry.ui;

/**
 * Member 4: Order Management
 * Tables: orders, order_details
 */
public class OrderForm extends javax.swing.JFrame {

    public OrderForm() {
        initComponents();
        setLocationRelativeTo(null);
        initializeOrderManagement();
    }

    private void initializeOrderManagement() {

        setTitle("Laundry Services - Order Management");

        titleLabel.setText("ORDER MANAGEMENT");
        titleLabel.setHorizontalAlignment(
            javax.swing.SwingConstants.CENTER
        );

        javax.swing.JPanel mainPanel =
            new javax.swing.JPanel(
                new java.awt.BorderLayout(10, 10)
            );

        mainPanel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(
                15, 15, 15, 15
            )
        );

        // INPUT FIELDS
        javax.swing.JPanel formPanel =
            new javax.swing.JPanel(
                new java.awt.GridLayout(4, 2, 10, 10)
            );

        javax.swing.JTextField customerField =
            new javax.swing.JTextField();

        javax.swing.JTextField policyField =
            new javax.swing.JTextField();

        javax.swing.JTextField serviceField =
            new javax.swing.JTextField();

        javax.swing.JTextField weightField =
            new javax.swing.JTextField();

        formPanel.add(new javax.swing.JLabel("Customer ID:"));
        formPanel.add(customerField);

        formPanel.add(new javax.swing.JLabel("Policy ID:"));
        formPanel.add(policyField);

        formPanel.add(new javax.swing.JLabel("Service ID:"));
        formPanel.add(serviceField);

        formPanel.add(new javax.swing.JLabel("Weight (kg):"));
        formPanel.add(weightField);

        // BUTTONS
        javax.swing.JPanel buttonPanel =
            new javax.swing.JPanel(
                new java.awt.FlowLayout()
            );

        javax.swing.JButton addButton =
            new javax.swing.JButton("Add Order");

        javax.swing.JButton updateButton =
            new javax.swing.JButton("Update Status");

        javax.swing.JButton deleteButton =
            new javax.swing.JButton("Delete Order");

        javax.swing.JButton clearButton =
            new javax.swing.JButton("Clear");

        javax.swing.JButton refreshButton =
            new javax.swing.JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        addButton.setEnabled(true);
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);
        clearButton.setEnabled(true);
        refreshButton.setEnabled(true);

        // ORDER TABLE
        String[] columns = {
            "Order ID",
            "Customer ID",
            "Policy ID",
            "Order Date",
            "Status"
        };

        javax.swing.table.DefaultTableModel tableModel =
            new javax.swing.table.DefaultTableModel(
                columns, 0
            ) {
                @Override
                public boolean isCellEditable(
                    int row, int column
                ) {
                    return false;
                }
            };

        javax.swing.JTable orderTable =
            new javax.swing.JTable(tableModel);

        orderTable.setRowHeight(25);
        orderTable.setFillsViewportHeight(true);

        orderTable.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );

        javax.swing.JScrollPane tableScroll =
            new javax.swing.JScrollPane(orderTable);

        // ADD ORDER BUTTON
        addButton.addActionListener(e -> {

            boolean success = addOrder(
                customerField.getText(),
                policyField.getText(),
                serviceField.getText(),
                weightField.getText()
            );

            if (success) {
                loadOrders(tableModel);

                customerField.setText("");
                policyField.setText("");
                serviceField.setText("");
                weightField.setText("");
            }
        });

        // UPDATE STATUS BUTTON
        updateButton.addActionListener(e -> {

            int selectedRow = orderTable.getSelectedRow();

            if (selectedRow == -1) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select an order first.",
                    "No Order Selected",
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int modelRow =
                orderTable.convertRowIndexToModel(
                    selectedRow
                );

            int orderId =
                ((Number) tableModel.getValueAt(
                    modelRow, 0
                )).intValue();

            String currentStatus =
                tableModel.getValueAt(
                    modelRow, 4
                ).toString();

            String nextStatus;

            switch (currentStatus) {

                case "Pending":
                    nextStatus = "Processing";
                    break;

                case "Processing":
                    nextStatus = "Ready";
                    break;

                case "Ready":
                    nextStatus = "Claimed";
                    break;

                case "Claimed":
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "This order has already been claimed."
                    );
                    return;

                default:
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Unknown status: " + currentStatus
                    );
                    return;
            }

            int confirmation =
                javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "Update Order ID " + orderId +
                    "\nFrom: " + currentStatus +
                    "\nTo: " + nextStatus + "?",
                    "Confirm Status Update",
                    javax.swing.JOptionPane.YES_NO_OPTION
                );

            if (confirmation ==
                    javax.swing.JOptionPane.YES_OPTION) {

                if (updateOrderStatus(
                    orderId,
                    currentStatus,
                    nextStatus
                )) {
                    loadOrders(tableModel);
                }
            }
        });

        // DELETE ORDER BUTTON
        deleteButton.addActionListener(e -> {

            int selectedRow = orderTable.getSelectedRow();

            if (selectedRow == -1) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select an order to delete.",
                    "No Order Selected",
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int modelRow =
                orderTable.convertRowIndexToModel(
                    selectedRow
                );

            int orderId =
                ((Number) tableModel.getValueAt(
                    modelRow, 0
                )).intValue();

            String status =
                tableModel.getValueAt(
                    modelRow, 4
                ).toString();

            int confirmation =
                javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to permanently delete " +
                    "Order ID " + orderId + "?" +
                    "\nCurrent Status: " + status +
                    "\n\nRelated order details and add-ons " +
                    "will also be deleted." +
                    "\nThis action cannot be undone.",
                    "Confirm Permanent Deletion",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );

            if (confirmation ==
                    javax.swing.JOptionPane.YES_OPTION) {

                if (deleteOrder(orderId)) {
                    loadOrders(tableModel);
                    orderTable.clearSelection();
                }
            }
        });

        // REFRESH BUTTON
        refreshButton.addActionListener(e -> {
            loadOrders(tableModel);
        });

        // CLEAR BUTTON
        clearButton.addActionListener(e -> {

            customerField.setText("");
            policyField.setText("");
            serviceField.setText("");
            weightField.setText("");

            orderTable.clearSelection();
        });

        // ASSEMBLE INTERFACE
        javax.swing.JPanel topPanel =
            new javax.swing.JPanel(
                new java.awt.BorderLayout(10, 10)
            );

        topPanel.add(
            formPanel,
            java.awt.BorderLayout.CENTER
        );

        topPanel.add(
            buttonPanel,
            java.awt.BorderLayout.SOUTH
        );

        mainPanel.add(
            topPanel,
            java.awt.BorderLayout.NORTH
        );

        mainPanel.add(
            tableScroll,
            java.awt.BorderLayout.CENTER
        );

        getContentPane().remove(contentScroll);

        getContentPane().add(
            mainPanel,
            java.awt.BorderLayout.CENTER
        );

        setSize(1000, 650);

        revalidate();
        repaint();

        loadOrders(tableModel);
    }

    // ADD ORDER TO MYSQL
    private boolean addOrder(
            String customerText,
            String policyText,
            String serviceText,
            String weightText) {

        int customerId;
        int policyId;
        int serviceId;

        java.math.BigDecimal weight;

        try {
            customerId =
                Integer.parseInt(customerText.trim());

            policyId =
                Integer.parseInt(policyText.trim());

            serviceId =
                Integer.parseInt(serviceText.trim());

            weight =
                new java.math.BigDecimal(
                    weightText.trim()
                );

            if (customerId <= 0 ||
                policyId <= 0 ||
                serviceId <= 0 ||
                weight.compareTo(
                    java.math.BigDecimal.ZERO
                ) <= 0 ||
                weight.scale() > 2 ||
                weight.compareTo(
                    new java.math.BigDecimal("999999.99")
                ) > 0) {

                throw new IllegalArgumentException();
            }

        } catch (RuntimeException ex) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Enter valid positive IDs and weight " +
                "(up to 2 decimal places).",
                "Invalid Input",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        try (java.sql.Connection conn =
                com.group4.laundry.config
                    .DatabaseConnection.open()) {

            conn.setAutoCommit(false);

            try {
                java.math.BigDecimal unitPrice;
                java.math.BigDecimal loadCapacity;

                String serviceSql =
                    "SELECT price_per_kg, max_kg_per_load " +
                    "FROM services WHERE service_id = ?";

                try (java.sql.PreparedStatement stmt =
                        conn.prepareStatement(serviceSql)) {

                    stmt.setInt(1, serviceId);

                    try (java.sql.ResultSet rs =
                            stmt.executeQuery()) {

                        if (!rs.next()) {
                            throw new java.sql.SQLException(
                                "Service ID does not exist."
                            );
                        }

                        unitPrice =
                            rs.getBigDecimal("price_per_kg");

                        loadCapacity =
                            rs.getBigDecimal("max_kg_per_load");
                    }
                }

                String orderSql =
                    "INSERT INTO orders " +
                    "(customer_id, policy_id, status) " +
                    "VALUES (?, ?, ?)";

                int orderId;

                try (java.sql.PreparedStatement stmt =
                        conn.prepareStatement(
                            orderSql,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                        )) {

                    stmt.setInt(1, customerId);
                    stmt.setInt(2, policyId);
                    stmt.setString(3, "Pending");

                    stmt.executeUpdate();

                    try (java.sql.ResultSet keys =
                            stmt.getGeneratedKeys()) {

                        if (!keys.next()) {
                            throw new java.sql.SQLException(
                                "Failed to retrieve Order ID."
                            );
                        }

                        orderId = keys.getInt(1);
                    }
                }

                String detailSql =
                    "INSERT INTO order_details " +
                    "(order_id, service_id, weight_kg, " +
                    "unit_price, load_capacity_kg) " +
                    "VALUES (?, ?, ?, ?, ?)";

                try (java.sql.PreparedStatement stmt =
                        conn.prepareStatement(detailSql)) {

                    stmt.setInt(1, orderId);
                    stmt.setInt(2, serviceId);
                    stmt.setBigDecimal(3, weight);
                    stmt.setBigDecimal(4, unitPrice);
                    stmt.setBigDecimal(5, loadCapacity);

                    stmt.executeUpdate();
                }

                conn.commit();

                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Order successfully added!" +
                    "\nOrder ID: " + orderId,
                    "Success",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                return true;

            } catch (java.sql.SQLException ex) {
                conn.rollback();
                throw ex;
            }

        } catch (java.sql.SQLException ex) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Unable to add order:\n" +
                ex.getMessage(),
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    // UPDATE ORDER STATUS
    private boolean updateOrderStatus(
            int orderId,
            String currentStatus,
            String nextStatus) {

        String sql;

        if ("Ready".equals(nextStatus)) {

            sql =
                "UPDATE orders o " +
                "JOIN shop_policies p " +
                "ON o.policy_id = p.policy_id " +
                "SET o.status = ?, " +
                "o.ready_at = CURRENT_TIMESTAMP, " +
                "o.free_days_at_ready = p.free_storage_days, " +
                "o.fee_per_day_at_ready = p.storage_fee_per_day " +
                "WHERE o.order_id = ? AND o.status = ?";

        } else if ("Claimed".equals(nextStatus)) {

            sql =
                "UPDATE orders SET status = ?, " +
                "claimed_at = CURRENT_TIMESTAMP " +
                "WHERE order_id = ? AND status = ?";

        } else {

            sql =
                "UPDATE orders SET status = ? " +
                "WHERE order_id = ? AND status = ?";
        }

        try (java.sql.Connection conn =
                com.group4.laundry.config
                    .DatabaseConnection.open();
             java.sql.PreparedStatement stmt =
                conn.prepareStatement(sql)) {

            stmt.setString(1, nextStatus);
            stmt.setInt(2, orderId);
            stmt.setString(3, currentStatus);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {

                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Order was not updated. " +
                    "Please refresh and try again.",
                    "Update Failed",
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );

                return false;
            }

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Order ID: " + orderId +
                "\nStatus updated successfully!" +
                "\nNew Status: " + nextStatus,
                "Update Successful",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            return true;

        } catch (java.sql.SQLException ex) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Unable to update order:\n" +
                ex.getMessage(),
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    // PERMANENTLY DELETE ORDER AND RELATED RECORDS
    private boolean deleteOrder(int orderId) {

        try (java.sql.Connection conn =
                com.group4.laundry.config
                    .DatabaseConnection.open()) {

            conn.setAutoCommit(false);

            try {
                // Lock and verify the selected order.
                String checkSql =
                    "SELECT order_id FROM orders " +
                    "WHERE order_id = ? FOR UPDATE";

                try (java.sql.PreparedStatement stmt =
                        conn.prepareStatement(checkSql)) {

                    stmt.setInt(1, orderId);

                    try (java.sql.ResultSet rs =
                            stmt.executeQuery()) {

                        if (!rs.next()) {
                            throw new java.sql.SQLException(
                                "Order no longer exists."
                            );
                        }
                    }
                }

                // 1. Delete add-ons linked to order details.
                String addOnSql =
                    "DELETE FROM order_detail_add_ons " +
                    "WHERE detail_id IN (" +
                    "SELECT detail_id FROM order_details " +
                    "WHERE order_id = ?)";

                try (java.sql.PreparedStatement stmt =
                        conn.prepareStatement(addOnSql)) {

                    stmt.setInt(1, orderId);
                    stmt.executeUpdate();
                }

                // 2. Delete the order's service details.
                String detailSql =
                    "DELETE FROM order_details " +
                    "WHERE order_id = ?";

                try (java.sql.PreparedStatement stmt =
                        conn.prepareStatement(detailSql)) {

                    stmt.setInt(1, orderId);
                    stmt.executeUpdate();
                }

                // 3. Delete the order itself.
                String orderSql =
                    "DELETE FROM orders WHERE order_id = ?";

                int rowsDeleted;

                try (java.sql.PreparedStatement stmt =
                        conn.prepareStatement(orderSql)) {

                    stmt.setInt(1, orderId);
                    rowsDeleted = stmt.executeUpdate();
                }

                if (rowsDeleted != 1) {
                    throw new java.sql.SQLException(
                        "Order could not be deleted."
                    );
                }

                // Commit only after all deletions succeed.
                conn.commit();

                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Order ID " + orderId +
                    " was permanently deleted.",
                    "Deletion Successful",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                return true;

            } catch (java.sql.SQLException ex) {

                try {
                    conn.rollback();
                } catch (java.sql.SQLException rollbackEx) {
                    ex.addSuppressed(rollbackEx);
                }

                throw ex;
            }

        } catch (java.sql.SQLException ex) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Unable to delete order:\n" +
                ex.getMessage() +
                "\n\nDeletion was not completed.",
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    // LOAD ORDERS FROM MYSQL
    private void loadOrders(
            javax.swing.table.DefaultTableModel tableModel) {

        String sql =
            "SELECT order_id, customer_id, policy_id, " +
            "order_date, status " +
            "FROM orders ORDER BY order_id DESC";

        try (java.sql.Connection conn =
                com.group4.laundry.config
                    .DatabaseConnection.open();
             java.sql.PreparedStatement stmt =
                conn.prepareStatement(sql);
             java.sql.ResultSet rs =
                stmt.executeQuery()) {

            tableModel.setRowCount(0);

            while (rs.next()) {

                tableModel.addRow(new Object[] {
                    rs.getInt("order_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("policy_id"),
                    rs.getTimestamp("order_date"),
                    rs.getString("status")
                });
            }

        } catch (java.sql.SQLException ex) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Unable to load orders:\n" +
                ex.getMessage(),
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        contentScroll = new javax.swing.JScrollPane();
        placeholderText = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE
        );

        setTitle("Orders | Group 4");

        titleLabel.setText("Orders - Member 4");

        getContentPane().add(
            titleLabel,
            java.awt.BorderLayout.NORTH
        );

        placeholderText.setEditable(false);
        placeholderText.setColumns(50);
        placeholderText.setRows(12);

        placeholderText.setText(
            "Module placeholder\n\n" +
            "Select a customer, policy and services; " +
            "record laundry weight.\n\n" +
            "Tables: orders, order_details\n\n" +
            "No records are loaded or saved yet."
        );

        contentScroll.setViewportView(placeholderText);

        getContentPane().add(
            contentScroll,
            java.awt.BorderLayout.CENTER
        );

        closeButton.setText("Close");

        closeButton.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(
                        java.awt.event.ActionEvent evt) {
                    closeButtonActionPerformed(evt);
                }
            }
        );

        getContentPane().add(
            closeButton,
            java.awt.BorderLayout.SOUTH
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(
            java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed

        dispose();

    }//GEN-LAST:event_closeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane contentScroll;
    private javax.swing.JTextArea placeholderText;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
