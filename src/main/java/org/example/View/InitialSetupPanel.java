package org.example.View;



import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InitialSetupPanel extends JPanel {

    private MainController controller;

    // Componenti di selezione
    private JRadioButton persistenceOn;
    private JRadioButton persistenceOff;
    private JRadioButton gui1;
    private JRadioButton gui2;

    public InitialSetupPanel(MainController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 30)); // Sfondo scuro per il setup

        JPanel mainBox = new JPanel();
        mainBox.setLayout(new BoxLayout(mainBox, BoxLayout.Y_AXIS));
        mainBox.setBackground(new Color(45, 45, 45));
        mainBox.setBorder(new EmptyBorder(40, 60, 40, 60));
        mainBox.putClientProperty(FlatClientProperties.STYLE, "arc: 20"); // Angoli arrotondati

        // Titolo
        JLabel title = new JLabel("System Configuration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- SEZIONE PERSISTENZA ---
        JPanel persistencePanel = createOptionPanel("Impostazioni Persistenza");
        persistenceOn = new JRadioButton("Usa Database (JDBC)", true);
        persistenceOff = new JRadioButton("File System / No Persistenza");
        ButtonGroup groupPers = new ButtonGroup();
        groupPers.add(persistenceOn);
        groupPers.add(persistenceOff);

        persistencePanel.add(persistenceOn);
        persistencePanel.add(Box.createVerticalStrut(5));
        persistencePanel.add(persistenceOff);

        // --- SEZIONE GUI ---
        JPanel guiPanel = createOptionPanel("Selezione Interfaccia");
        gui1 = new JRadioButton("GUI 1 (Swing Moderno)", true);
        gui2 = new JRadioButton("GUI 2 (JavaFX / Legacy / CLI)");
        ButtonGroup groupGui = new ButtonGroup();
        groupGui.add(gui1);
        groupGui.add(gui2);

        guiPanel.add(gui1);
        guiPanel.add(Box.createVerticalStrut(5));
        guiPanel.add(gui2);

        // --- SEZIONE LOGIN ---
        JLabel loginLabel = new JLabel("Seleziona Modalità di Accesso:");
        loginLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginLabel.setForeground(Color.LIGHT_GRAY);
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        btnPanel.setBackground(new Color(45, 45, 45));
        btnPanel.setMaximumSize(new Dimension(400, 60));

        JButton userBtn = createRoleButton("CLIENTE", new Color(0, 120, 215));
        userBtn.addActionListener(e -> confirmSelection("USER"));

        JButton adminBtn = createRoleButton("ADMIN", new Color(200, 60, 60));
        adminBtn.addActionListener(e -> confirmSelection("ADMIN"));

        btnPanel.add(userBtn);
        btnPanel.add(adminBtn);

        // Assemblaggio
        mainBox.add(title);
        mainBox.add(Box.createVerticalStrut(30));
        mainBox.add(persistencePanel);
        mainBox.add(Box.createVerticalStrut(20));
        mainBox.add(guiPanel);
        mainBox.add(Box.createVerticalStrut(40));
        mainBox.add(loginLabel);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(btnPanel);

        add(mainBox);
    }

    private JPanel createOptionPanel(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(45, 45, 45));
        p.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.PLAIN, 12),
                Color.LIGHT_GRAY
        ));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setMaximumSize(new Dimension(400, 100));
        return p;
    }

    private JButton createRoleButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        return btn;
    }

    private void confirmSelection(String type) {
        boolean persist = persistenceOn.isSelected();
        String gui = gui1.isSelected() ? "GUI1" : "GUI2";

        // Passa la configurazione al Controller
        controller.setSystemConfig(persist, gui, type);
    }
}