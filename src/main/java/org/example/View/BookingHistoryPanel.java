package org.example.View;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BookingHistoryPanel extends JPanel {

    private MainController controller;

    public BookingHistoryPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Storico Prenotazioni");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JButton backBtn = new JButton("← Torna alla Dashboard");
        backBtn.addActionListener(e -> controller.switchScreen(MainController.DASHBOARD_VIEW));

        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // LISTA CENTRALE
        JPanel historyList = new JPanel();
        historyList.setLayout(new BoxLayout(historyList, BoxLayout.Y_AXIS));
        historyList.setBackground(Color.WHITE);
        historyList.setBorder(new EmptyBorder(20, 50, 20, 50));

        // Aggiungiamo prenotazioni fittizie (Mock Data)
        historyList.add(createHistoryCard("12 Nov 2023", "Barber Shop GattoPardo", "Taglio + Barba", "$45.00", "Completato"));
        historyList.add(Box.createVerticalStrut(15));
        historyList.add(createHistoryCard("05 Ott 2023", "Luxury Hair Style", "Taglio Premium", "$50.00", "Completato"));
        historyList.add(Box.createVerticalStrut(15));
        historyList.add(createHistoryCard("20 Set 2023", "Old School Cut", "Rasatura", "$20.00", "Cancellato"));
        historyList.add(Box.createVerticalStrut(15));
        historyList.add(createHistoryCard("15 Dic 2023", "Modern Looks", "Colore e Piega", "$80.00", "In Programma"));

        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHistoryCard(String date, String salon, String service, String price, String status) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

        // Info Sinistra: Data e Salone
        JPanel leftInfo = new JPanel(new GridLayout(2, 1));
        leftInfo.setOpaque(false);
        JLabel dateLbl = new JLabel(date);
        dateLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dateLbl.setForeground(Color.GRAY);
        JLabel salonLbl = new JLabel(salon);
        salonLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));

        leftInfo.add(dateLbl);
        leftInfo.add(salonLbl);

        // Info Centro: Servizio
        JLabel serviceLbl = new JLabel(service);
        serviceLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        serviceLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // Info Destra: Prezzo e Status
        JPanel rightInfo = new JPanel(new GridLayout(2, 1));
        rightInfo.setOpaque(false);

        JLabel priceLbl = new JLabel(price);
        priceLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel statusLbl = new JLabel(status);
        statusLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        // Colore dello status
        if (status.equals("Completato")) statusLbl.setForeground(new Color(40, 150, 40));
        else if (status.equals("Cancellato")) statusLbl.setForeground(Color.RED);
        else statusLbl.setForeground(Color.BLUE);

        rightInfo.add(priceLbl);
        rightInfo.add(statusLbl);

        card.add(leftInfo, BorderLayout.WEST);
        card.add(serviceLbl, BorderLayout.CENTER);
        card.add(rightInfo, BorderLayout.EAST);

        return card;
    }
}

