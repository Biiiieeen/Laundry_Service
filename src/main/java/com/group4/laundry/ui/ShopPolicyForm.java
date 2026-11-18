package com.group4.laundry.ui;

/** Member 7: shop_policies. Commit this file together with its .form file. */
public class ShopPolicyForm extends javax.swing.JFrame {
    public ShopPolicyForm() {
        initComponents();
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
        setTitle("Shop Policies | Group 4");
        titleLabel.setText("Shop Policies - Member 7");
        getContentPane().add(titleLabel, java.awt.BorderLayout.NORTH);

        placeholderText.setEditable(false);
        placeholderText.setColumns(50);
        placeholderText.setRows(12);
        placeholderText.setText("Module placeholder\n\nManage storage days, reminders, storage fees and review days.\n\nTables: shop_policies\n\nNo records are loaded or saved yet.\nAdd form fields using the NetBeans Design tab.");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane contentScroll;
    private javax.swing.JTextArea placeholderText;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}