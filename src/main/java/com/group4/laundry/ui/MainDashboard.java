package com.group4.laundry.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** Shared navigation. Member 7 owns changes to this hand-written Swing layout. */
public final class MainDashboard extends JFrame {
    public MainDashboard() {
        super("Group 4 | Laundry Services Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel(new BorderLayout(16, 16));
        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        JLabel heading = new JLabel("Laundry Services Management System", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(22f));
        content.add(heading, BorderLayout.NORTH);
        JPanel modules = new JPanel(new GridLayout(0, 2, 12, 12));
        addModule(modules, "Customers | Member 1", CustomerForm::new);
        addModule(modules, "Services | Member 2", ServiceForm::new);
        addModule(modules, "Add-ons | Member 3", AddOnForm::new);
        addModule(modules, "Orders | Member 4", OrderForm::new);
        addModule(modules, "Billing | Member 5", BillingForm::new);
        addModule(modules, "Processing & Pickup | Member 6", ProcessingForm::new);
        addModule(modules, "Shop Policies | Member 7", ShopPolicyForm::new);
        content.add(modules, BorderLayout.CENTER);
        content.add(new JLabel("Starter project - module screens are placeholders. Database setup is optional to launch.",
                SwingConstants.CENTER), BorderLayout.SOUTH);
        setContentPane(content);
        setSize(820, 480);
        setMinimumSize(new java.awt.Dimension(760, 420));
        setLocationRelativeTo(null);
    }

    private void addModule(JPanel panel, String title, java.util.function.Supplier<JFrame> factory) {
        JButton button = new JButton(title);
        button.addActionListener(event -> {
            JFrame form = factory.get();
            form.setLocationRelativeTo(this);
            form.setVisible(true);
        });
        panel.add(button);
    }
}
