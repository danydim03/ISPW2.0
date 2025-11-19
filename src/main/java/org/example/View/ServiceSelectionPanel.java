package org.example.View; // Aggiornato

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServiceSelectionPanel extends JPanel {

    private MainController controller;

    public ServiceSelectionPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 20, 10, 20));

        JButton backBtn = new JButton("← Indietro");
        backBtn.addActionListener(e -> controller.switchScreen(MainController.SALON_DETAIL_VIEW));

        JLabel title = new JLabel("Seleziona il Servizio Base:", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        header.add(Box.createHorizontalStrut(100), BorderLayout.EAST);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(new EmptyBorder(40, 50, 80, 50));

        // Modificato per rispecchiare il Diagramma UML: Taglio, Barba, Colorazione

        // 1. TAGLIO (ConcreteComponent)
        cardsPanel.add(createServiceCard("Taglio", "$30", new String[]{"Consultazione stile", "Lavaggio capelli", "Taglio forbice", "Asciugatura"}, false));

        // 2. BARBA (ConcreteComponent) - Dark card per evidenziarlo
        cardsPanel.add(createServiceCard("Barba", "$25", new String[]{"Panno caldo", "Rasatura tradizionale", "Olio idratante", "Massaggio viso"}, true));

        // 3. COLORAZIONE (ConcreteComponent) - Sostituisce "Combo"
        cardsPanel.add(createServiceCard("Colorazione", "$45", new String[]{"Tinta senza ammoniaca", "Copertura capelli bianchi", "Riflessante naturale", "Shampoo post-colore"}, false));

        add(header, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
    }

    private JPanel createServiceCard(String title, String price, String[] features, boolean isDark) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        Color bgColor = isDark ? new Color(40, 40, 40) : Color.WHITE;
        Color textColor = isDark ? Color.WHITE : Color.BLACK;
        Color borderColor = new Color(230, 230, 230);

        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isDark ? bgColor : borderColor, 1),
                new EmptyBorder(30, 20, 30, 20)
        ));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLbl.setForeground(textColor);
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLbl = new JLabel(price);
        priceLbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        priceLbl.setForeground(textColor);
        priceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(bgColor);
        listPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (String feature : features) {
            JLabel item = new JLabel("• " + feature);
            item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            item.setForeground(isDark ? new Color(200, 200, 200) : Color.GRAY);
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setBackground(bgColor);
            row.add(item);
            listPanel.add(row);
        }

        JButton selectBtn = new JButton("Scegli " + title);
        selectBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        selectBtn.setMaximumSize(new Dimension(200, 45));
        selectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (isDark) {
            selectBtn.setBackground(Color.WHITE);
            selectBtn.setForeground(Color.BLACK);
        } else {
            selectBtn.setBackground(new Color(40, 40, 40));
            selectBtn.setForeground(Color.WHITE);
        }

        // Naviga verso la selezione dei Decorator (Add-on)
        selectBtn.addActionListener(e -> controller.switchScreen(MainController.ADDON_SELECTION_VIEW));

        card.add(Box.createVerticalStrut(10));
        card.add(titleLbl);
        card.add(Box.createVerticalStrut(10));
        card.add(priceLbl);
        card.add(Box.createVerticalStrut(20));
        card.add(listPanel);
        card.add(Box.createVerticalGlue());
        card.add(selectBtn);
        card.add(Box.createVerticalStrut(10));

        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        return card;
    }
}