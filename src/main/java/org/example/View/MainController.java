package org.example.View;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainController extends JFrame {

    // Identificatori univoci per le schermate
    public static final String INITIAL_SETUP_VIEW = "INITIAL_SETUP";
    public static final String LOGIN_VIEW = "LOGIN";
    public static final String DASHBOARD_VIEW = "DASHBOARD";
    public static final String SALON_DETAIL_VIEW = "SALON_DETAIL";
    public static final String SERVICE_SELECTION_VIEW = "SERVICE_SELECTION";
    public static final String ADDON_SELECTION_VIEW = "ADDON_SELECTION";
    public static final String CART_VIEW = "CART";
    public static final String BOOKING_HISTORY_VIEW = "BOOKING_HISTORY"; // Nuova Vista

    private JPanel mainContainer;
    private CardLayout cardLayout;

    // Riferimenti ai pannelli
    private InitialSetupPanel initialSetupPanel;
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;
    private SalonDetailPanel salonDetailPanel;
    private ServiceSelectionPanel serviceSelectionPanel;
    private AddOnSelectionPanel addOnSelectionPanel;
    private CartPanel cartPanel;
    private BookingHistoryPanel bookingHistoryPanel; // Nuovo Pannello

    // --- STATO CONFIGURAZIONE ---
    private boolean persistenceEnabled = false;
    private String selectedGui = "GUI1";
    private String userType = "USER";

    // --- STATO CARRELLO (DUMMY MODEL) ---
    private List<String> cartItems = new ArrayList<>();
    private double cartTotal = 0.0;

    public MainController() {
        setTitle("BarberApp - Gestionale");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Inizializzazione Pannelli
        initialSetupPanel = new InitialSetupPanel(this);
        loginPanel = new LoginPanel(this);
        dashboardPanel = new DashboardPanel(this);
        salonDetailPanel = new SalonDetailPanel(this);
        serviceSelectionPanel = new ServiceSelectionPanel(this);
        addOnSelectionPanel = new AddOnSelectionPanel(this);
        cartPanel = new CartPanel(this);
        bookingHistoryPanel = new BookingHistoryPanel(this); // Inizializzo Storico

        // Aggiunta al container
        mainContainer.add(initialSetupPanel, INITIAL_SETUP_VIEW);
        mainContainer.add(loginPanel, LOGIN_VIEW);
        mainContainer.add(dashboardPanel, DASHBOARD_VIEW);
        mainContainer.add(salonDetailPanel, SALON_DETAIL_VIEW);
        mainContainer.add(serviceSelectionPanel, SERVICE_SELECTION_VIEW);
        mainContainer.add(addOnSelectionPanel, ADDON_SELECTION_VIEW);
        mainContainer.add(cartPanel, CART_VIEW);
        mainContainer.add(bookingHistoryPanel, BOOKING_HISTORY_VIEW); // Aggiungo Storico

        add(mainContainer);

        // AVVIO SULLA SCHERMATA DI SETUP
        cardLayout.show(mainContainer, INITIAL_SETUP_VIEW);
    }

    // --- NAVIGAZIONE ---

    public void switchScreen(String screenName) {
        if (screenName.equals(CART_VIEW)) {
            cartPanel.refreshCart(cartItems, cartTotal);
        }
        cardLayout.show(mainContainer, screenName);
    }

    public void setSystemConfig(boolean persistence, String gui, String type) {
        this.persistenceEnabled = persistence;
        this.selectedGui = gui;
        this.userType = type;
        loginPanel.updateLoginTitle(type);
        switchScreen(LOGIN_VIEW);
    }

    public void showSalonDetail(SalonData salon) {
        if (salonDetailPanel != null) salonDetailPanel.setSalonData(salon);
        switchScreen(SALON_DETAIL_VIEW);
    }

    // --- GESTIONE CARRELLO ---

    public void addToCartAndGoToCheckout(String description, double price) {
        cartItems.add(description);
        cartTotal += price;
        JOptionPane.showMessageDialog(this, "Elemento aggiunto al carrello!");
        switchScreen(CART_VIEW);
    }

    public void checkoutComplete() {
        JOptionPane.showMessageDialog(this, "Acquisto completato con successo! Grazie.");
        cartItems.clear();
        cartTotal = 0.0;
        switchScreen(DASHBOARD_VIEW);
    }

    public String getUserType() { return userType; }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) { }
        SwingUtilities.invokeLater(() -> new MainController().setVisible(true));
    }

    public static class SalonData {
        public String name;
        public String address;
        public String price;

        public SalonData(String name, String address, String price) {
            this.name = name;
            this.address = address;
            this.price = price;
        }
    }
}


