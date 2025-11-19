package org.example.View;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SalonDetailPanel extends JPanel {

    private MainController controller;
    private JLabel salonNameLabel;
    private JLabel addressLabel;

    // Componenti per il carosello
    private JPanel carouselCardPanel;
    private CardLayout carouselLayout;
    private int currentSlide = 1;
    private final int TOTAL_SLIDES = 3;

    public SalonDetailPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createTopSection(), BorderLayout.NORTH);

        // Layout a 2 colonne: Carosello (SX) - Info/Booking (DX)
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        contentPanel.add(createCarouselSection()); // NUOVO CAROSELLO
        contentPanel.add(createBookingFormSection());

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }

    public void setSalonData(MainController.SalonData data) {
        salonNameLabel.setText(data.name);
        addressLabel.setText(data.address);
    }

    private JPanel createTopSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Color.WHITE);
        nav.setBorder(new EmptyBorder(10, 20, 10, 20));
        JButton backBtn = new JButton("← Torna alla Dashboard");
        backBtn.addActionListener(e -> controller.switchScreen(MainController.DASHBOARD_VIEW));
        nav.add(backBtn, BorderLayout.WEST);

        panel.add(nav);
        return panel;
    }

    // --- NUOVO SEZIONE CAROSELLO ---
    private JPanel createCarouselSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Titolo
        JLabel lbl = new JLabel("Galleria Fotografica");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setBorder(new EmptyBorder(0,0,10,0));
        panel.add(lbl, BorderLayout.NORTH);

        // Pannello centrale con CardLayout per le "foto"
        carouselLayout = new CardLayout();
        carouselCardPanel = new JPanel(carouselLayout);
        carouselCardPanel.setPreferredSize(new Dimension(400, 300));

        // Aggiungo 3 slide fittizie (Pannelli colorati)
        carouselCardPanel.add(createDummySlide(new Color(200, 180, 160), "Interni Salone"), "1");
        carouselCardPanel.add(createDummySlide(new Color(160, 180, 200), "Zona Lavaggio"), "2");
        carouselCardPanel.add(createDummySlide(new Color(160, 200, 180), "Prodotti Esclusivi"), "3");

        // Controlli Navigazione (< >)
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controls.setBackground(Color.WHITE);

        JButton prevBtn = new JButton("❮ Precedente");
        JButton nextBtn = new JButton("Successiva ❯");

        prevBtn.addActionListener(e -> {
            carouselLayout.previous(carouselCardPanel);
        });

        nextBtn.addActionListener(e -> {
            carouselLayout.next(carouselCardPanel);
        });

        controls.add(prevBtn);
        controls.add(nextBtn);

        // Wrapper per bordo
        JPanel carouselWrapper = new JPanel(new BorderLayout());
        carouselWrapper.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        carouselWrapper.add(carouselCardPanel, BorderLayout.CENTER);
        carouselWrapper.add(controls, BorderLayout.SOUTH);

        panel.add(carouselWrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createDummySlide(Color color, String text) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(color);
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 24));
        l.setForeground(Color.WHITE);
        p.add(l);
        return p;
    }

    private JPanel createBookingFormSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 20, 0, 0));

        salonNameLabel = new JLabel("Nome Salone");
        salonNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JLabel tagLabel = new JLabel(" Black FRIDAY ");
        tagLabel.setOpaque(true);
        tagLabel.setBackground(new Color(200, 255, 200));
        tagLabel.setForeground(new Color(30, 100, 30));

        addressLabel = new JLabel("Indirizzo...");
        addressLabel.setForeground(Color.GRAY);

        JPanel form = new JPanel(new GridLayout(1, 1, 15, 15));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 0, 20, 0));

        form.add(createInputGroup("Seleziona Barbiere preferito", new String[]{"Marco", "Giuseppe", "Qualsiasi"}));

        JButton bookBtn = new JButton("Scegli Servizi e Aggiungi al Carrello →");
        bookBtn.setBackground(new Color(40, 40, 40));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bookBtn.setPreferredSize(new Dimension(100, 55));
        bookBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        // AZIONE: Vai alla selezione servizi
        bookBtn.addActionListener(e -> controller.switchScreen(MainController.SERVICE_SELECTION_VIEW));

        JTextArea desc = new JTextArea("Esperienza immersiva con la natura grazie ai massaggi tropicali...");
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBackground(new Color(250, 250, 250));
        desc.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(salonNameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(tagLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addressLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(form);
        panel.add(bookBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Descrizione"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(desc);

        return panel;
    }

    private JPanel createInputGroup(String title, String[] items) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setBackground(Color.WHITE);
        JLabel l = new JLabel(title);
        JComboBox<String> combo = new JComboBox<>(items);
        p.add(l, BorderLayout.NORTH);
        p.add(combo, BorderLayout.CENTER);
        return p;
    }
}