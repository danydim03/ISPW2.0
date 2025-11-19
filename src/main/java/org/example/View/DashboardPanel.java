package org.example.View;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DashboardPanel extends JPanel {

    private MainController controller;
    private JPanel salonGridPanel;
    private JTextField searchField;

    public DashboardPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 245, 245));

        salonGridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        salonGridPanel.setBackground(new Color(245, 245, 245));
        salonGridPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel placeholder = new JLabel("Clicca 'Cerca' per visualizzare i saloni...", SwingConstants.CENTER);
        placeholder.setForeground(Color.GRAY);
        salonGridPanel.add(placeholder);

        JScrollPane scrollPane = new JScrollPane(salonGridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainContent.add(scrollPane, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(getWidth(), 80));
        header.setBorder(new EmptyBorder(15, 30, 15, 30));

        JLabel logo = new JLabel("BarberApp");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        // --- Pannello Ricerca ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(Color.WHITE);

        searchField = new JTextField(30);
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Cerca salone, servizio...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        searchField.setPreferredSize(new Dimension(300, 35));

        JButton searchBtn = new JButton("Cerca");
        searchBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        searchBtn.setBackground(new Color(40, 40, 40));
        searchBtn.setForeground(Color.WHITE);

        searchBtn.addActionListener(e -> performSearch());

        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        // --- Pannello Utente & Navigazione ---
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        userPanel.setBackground(Color.WHITE);

        // Bottone Storico
        JButton historyBtn = new JButton("I miei Ordini");
        historyBtn.setFocusPainted(false);
        historyBtn.setContentAreaFilled(false);
        historyBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        historyBtn.addActionListener(e -> controller.switchScreen(MainController.BOOKING_HISTORY_VIEW));

        // Bottone Carrello
        JButton cartBtn = new JButton("Carrello");
        cartBtn.setBackground(new Color(40, 120, 200));
        cartBtn.setForeground(Color.WHITE);
        cartBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        cartBtn.addActionListener(e -> controller.switchScreen(MainController.CART_VIEW));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBorder(null);
        logoutBtn.addActionListener(e -> controller.switchScreen(MainController.LOGIN_VIEW));

        userPanel.add(historyBtn);
        userPanel.add(cartBtn);
        userPanel.add(new JSeparator(SwingConstants.VERTICAL));
        userPanel.add(logoutBtn);

        header.add(logo, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.CENTER);
        header.add(userPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel filterTitle = new JLabel("Filtri");
        filterTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        filterTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox cb1 = new JCheckBox("Taglio capelli");
        JCheckBox cb2 = new JCheckBox("Rasatura lati");
        JCheckBox cb3 = new JCheckBox("Barba completa");

        JLabel priceLabel = new JLabel("Prezzo: $0 - $100");
        JSlider priceSlider = new JSlider(0, 100, 50);
        priceSlider.setBackground(Color.WHITE);

        sidebar.add(filterTitle);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(cb1);
        sidebar.add(cb2);
        sidebar.add(cb3);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(priceLabel);
        sidebar.add(priceSlider);

        return sidebar;
    }

    private void performSearch() {
        salonGridPanel.removeAll();

        List<MainController.SalonData> mockSalons = new ArrayList<>();
        mockSalons.add(new MainController.SalonData("Barber Shop GattoPardo", "Via di Torrenova, Roma", "$25"));
        mockSalons.add(new MainController.SalonData("Luxury Hair Style", "Via della Fenice, Milano", "$40"));
        mockSalons.add(new MainController.SalonData("Old School Cut", "Piazza Navona, Roma", "$30"));
        mockSalons.add(new MainController.SalonData("Modern Looks", "Via del Corso, Roma", "$35"));
        mockSalons.add(new MainController.SalonData("Il Barbiere di Siviglia", "Via Verdi, Torino", "$20"));

        for (MainController.SalonData salon : mockSalons) {
            salonGridPanel.add(new SalonCard(salon));
        }

        salonGridPanel.revalidate();
        salonGridPanel.repaint();
    }

    private class SalonCard extends JPanel {
        public SalonCard(MainController.SalonData data) {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(220, 280));

            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(10, 10, 10, 10)
            ));

            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    controller.showSalonDetail(data);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                            new EmptyBorder(10, 10, 10, 10)
                    ));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                            new EmptyBorder(10, 10, 10, 10)
                    ));
                }
            });

            JPanel imagePlaceholder = new JPanel();
            imagePlaceholder.setBackground(new Color(200, 180, 160));
            imagePlaceholder.setPreferredSize(new Dimension(200, 140));
            JLabel imgText = new JLabel("FOTO SALONE");
            imgText.setForeground(Color.WHITE);
            imagePlaceholder.add(imgText);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

            JLabel nameLbl = new JLabel(data.name);
            nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

            JLabel addrLbl = new JLabel(data.address);
            addrLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            addrLbl.setForeground(Color.GRAY);

            JLabel priceLbl = new JLabel(data.price);
            priceLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
            priceLbl.setForeground(new Color(50, 150, 50));

            infoPanel.add(nameLbl);
            infoPanel.add(Box.createVerticalStrut(5));
            infoPanel.add(addrLbl);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(priceLbl);

            add(imagePlaceholder, BorderLayout.NORTH);
            add(infoPanel, BorderLayout.CENTER);

            putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        }
    }
}


