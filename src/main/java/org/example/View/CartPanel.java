package org.example.View;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class CartPanel extends JPanel {

    private MainController controller;

    // Elementi dinamici
    private JPanel itemsListPanel;
    private JLabel totalLabel;
    private JTextField voucherField;
    private ButtonGroup paymentGroup;

    public CartPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));
        JLabel title = new JLabel("Il tuo Carrello");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        JButton backBtn = new JButton("← Continua lo Shopping");
        backBtn.addActionListener(e -> controller.switchScreen(MainController.DASHBOARD_VIEW));
        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // CONTENUTO CENTRALE (Diviso in due colonne: LISTA | PAGAMENTO)
        JPanel content = new JPanel(new GridLayout(1, 2, 40, 0));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 40, 40, 40));

        // Colonna SX: Lista Articoli
        itemsListPanel = new JPanel();
        itemsListPanel.setLayout(new BoxLayout(itemsListPanel, BoxLayout.Y_AXIS));
        itemsListPanel.setBackground(Color.WHITE);

        JScrollPane scrollList = new JScrollPane(itemsListPanel);
        scrollList.setBorder(BorderFactory.createTitledBorder("Articoli nel carrello"));
        scrollList.setBackground(Color.WHITE);

        // Colonna DX: Checkout
        JPanel checkoutPanel = createCheckoutPanel();

        content.add(scrollList);
        content.add(checkoutPanel);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createCheckoutPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(250, 250, 250));
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        p.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        // Totale
        totalLabel = new JLabel("Totale: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Voucher Section
        JPanel voucherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        voucherPanel.setOpaque(false);
        voucherPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        voucherField = new JTextField(15);
        voucherField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Codice Sconto");

        JButton applyVoucherBtn = new JButton("Applica");
        applyVoucherBtn.addActionListener(e -> {
            String code = voucherField.getText();
            if (code.equalsIgnoreCase("SCONTO10")) {
                JOptionPane.showMessageDialog(this, "Voucher Applicato! Sconto del 10%");
                // Qui aggiorneresti il totale nel model (logica mockata)
            } else {
                JOptionPane.showMessageDialog(this, "Voucher non valido", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        voucherPanel.add(voucherField);
        voucherPanel.add(Box.createHorizontalStrut(10));
        voucherPanel.add(applyVoucherBtn);

        // Metodi di Pagamento
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Metodo di Pagamento"));
        paymentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JRadioButton card = new JRadioButton("Carta di Credito / Debito", true);
        JRadioButton paypal = new JRadioButton("PayPal");
        JRadioButton cash = new JRadioButton("Paga in negozio");

        paymentGroup = new ButtonGroup();
        paymentGroup.add(card);
        paymentGroup.add(paypal);
        paymentGroup.add(cash);

        paymentPanel.add(card);
        paymentPanel.add(paypal);
        paymentPanel.add(cash);

        // Bottone Acquista
        JButton buyBtn = new JButton("Conferma e Acquista");
        buyBtn.setBackground(new Color(40, 120, 40));
        buyBtn.setForeground(Color.WHITE);
        buyBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        buyBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        buyBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        buyBtn.addActionListener(e -> controller.checkoutComplete());

        // Assemblaggio
        p.add(totalLabel);
        p.add(Box.createVerticalStrut(20));
        p.add(new JLabel("Hai un voucher?"));
        p.add(voucherPanel);
        p.add(Box.createVerticalStrut(20));
        p.add(paymentPanel);
        p.add(Box.createVerticalGlue());
        p.add(buyBtn);

        return p;
    }

    // Metodo chiamato dal controller per popolare la vista
    public void refreshCart(List<String> items, double total) {
        itemsListPanel.removeAll();

        if (items.isEmpty()) {
            JLabel empty = new JLabel("Il carrello è vuoto.");
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemsListPanel.add(Box.createVerticalStrut(20));
            itemsListPanel.add(empty);
        } else {
            for (String item : items) {
                JPanel row = new JPanel(new BorderLayout());
                row.setBackground(Color.WHITE);
                row.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                        new EmptyBorder(15, 10, 15, 10)
                ));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                JLabel lbl = new JLabel(item);
                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                row.add(lbl, BorderLayout.CENTER);

                itemsListPanel.add(row);
            }
        }

        totalLabel.setText(String.format("Totale: $%.2f", total));

        itemsListPanel.revalidate();
        itemsListPanel.repaint();
    }
}