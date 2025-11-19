package org.example.View; // Aggiornato

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddOnSelectionPanel extends JPanel {

    private MainController controller;

    public AddOnSelectionPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Personalizza il tuo servizio (Add-ons)", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JButton backBtn = new JButton("← Indietro");
        backBtn.addActionListener(e -> controller.switchScreen(MainController.SERVICE_SELECTION_VIEW));

        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        header.add(Box.createHorizontalStrut(100), BorderLayout.EAST);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(new EmptyBorder(20, 100, 20, 100));

        listPanel.add(createAddOnItem("Pulizia Viso", "+ $15", "Rimuove impurità e punti neri"));
        listPanel.add(Box.createVerticalStrut(15));
        listPanel.add(createAddOnItem("Olii Essenziali", "+ $5", "Aromaterapia rilassante durante il taglio"));
        listPanel.add(Box.createVerticalStrut(15));
        listPanel.add(createAddOnItem("Massaggio Cute", "+ $10", "10 minuti di massaggio rilassante"));
        listPanel.add(Box.createVerticalStrut(15));
        listPanel.add(createAddOnItem("Definizione Ciglia", "+ $12", "Ritocco estetico sopracciglia"));

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(new Color(245, 245, 245));
        footer.setBorder(new EmptyBorder(20, 20, 20, 50));

        // BOTTONE MODIFICATO: Aggiunge al carrello
        JButton addToCartBtn = new JButton("Aggiungi al Carrello e Paga");
        addToCartBtn.setBackground(new Color(40, 120, 200)); // Blu carrello
        addToCartBtn.setForeground(Color.WHITE);
        addToCartBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addToCartBtn.setPreferredSize(new Dimension(260, 50));
        addToCartBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        addToCartBtn.addActionListener(e -> {
            // Simuliamo l'aggiunta di un pacchetto completo
            // In un sistema reale qui controlleresti quali checkbox sono selezionate
            controller.addToCartAndGoToCheckout("Servizio Completo + Addons", 85.00);
        });

        footer.add(addToCartBtn);

        add(header, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createAddOnItem(String name, String price, String desc) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(250, 250, 250));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(15, 20, 15, 20)
        ));
        p.setMaximumSize(new Dimension(800, 80));
        p.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

        JCheckBox cb = new JCheckBox(name);
        cb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cb.setOpaque(false);

        JLabel descLbl = new JLabel(desc);
        descLbl.setForeground(Color.GRAY);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(cb);
        textPanel.add(descLbl);

        JLabel priceLbl = new JLabel(price);
        priceLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        priceLbl.setForeground(new Color(50, 150, 50));

        p.add(textPanel, BorderLayout.CENTER);
        p.add(priceLbl, BorderLayout.EAST);

        return p;
    }
}