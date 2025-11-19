package org.example.View; // Aggiornato

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private MainController controller;
    private JLabel titleLabel;

    public LoginPanel(MainController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1, true),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));

        titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField emailField = new JTextField(15);
        emailField.putClientProperty("JTextField.placeholderText", "Username / Email");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JPasswordField passField = new JPasswordField(15);
        passField.putClientProperty("JTextField.placeholderText", "Password");
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JButton loginBtn = new JButton("Accedi");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBackground(new Color(40, 120, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        loginBtn.addActionListener(e -> {
            String user = emailField.getText();
            String role = controller.getUserType();

            if (!user.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Benvenuto " + role + ": " + user);
                controller.switchScreen(MainController.DASHBOARD_VIEW);
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci credenziali", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Cambia Configurazione");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setForeground(Color.GRAY);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> controller.switchScreen(MainController.INITIAL_SETUP_VIEW));

        formPanel.add(titleLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(emailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(passField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(loginBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(backBtn);

        add(formPanel);
    }

    public void updateLoginTitle(String type) {
        if (type.equals("ADMIN")) {
            titleLabel.setText("Portale Amministratore");
            titleLabel.setForeground(new Color(200, 60, 60));
        } else {
            titleLabel.setText("Accesso Clienti");
            titleLabel.setForeground(UIManager.getColor("Label.foreground"));
        }
    }
}