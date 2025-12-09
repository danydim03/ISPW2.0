package org.example.use_cases.crea_ordine.graphic_controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.BaseGraphicControl;
import org.example.PageNavigationController;
import org.example.exceptions.*;
import org.example.exceptions.CreaOrdineException;
import org.example.use_cases.crea_ordine.CreaOrdineFacade;
import org.example.use_cases.crea_ordine.beans.FoodBean;
import org.example.use_cases.crea_ordine.beans.OrdineBean;
import org.example.use_cases.crea_ordine.beans.RiepilogoOrdineBean;
import org.example.use_cases.crea_ordine.beans.RiepilogoOrdineBean.RigaOrdineBean;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CreaOrdineGUIController extends BaseGraphicControl implements Initializable {

    private static final Logger logger = Logger.getLogger(CreaOrdineGUIController.class.getName());

    @FXML private RadioButton radioPanino;
    @FXML private RadioButton radioPiadina;
    @FXML private RadioButton radioPiatto;
    private ToggleGroup baseGroup;

    @FXML private CheckBox checkCipolla;
    @FXML private CheckBox checkSalsaYogurt;
    @FXML private CheckBox checkPatatine;
    @FXML private CheckBox checkMixVerdure;

    @FXML private TableView<RigaOrdineBean> tabellaOrdine;
    @FXML private TableColumn<RigaOrdineBean, String> colonnaDescrizione;
    @FXML private TableColumn<RigaOrdineBean, String> colonnaPrezzo;
    @FXML private TableColumn<RigaOrdineBean, String> colonnaDurata;

    @FXML private Label labelSubtotale;
    @FXML private Label labelSconto;
    @FXML private Label labelTotale;
    @FXML private Label labelDurata;
    @FXML private Label labelVoucherInfo;
    @FXML private Label labelNumeroOrdine;

    @FXML private TextField textFieldVoucher;

    @FXML private Button btnAggiungiProdotto;
    @FXML private Button btnRimuoviProdotto;
    @FXML private Button btnApplicaVoucher;
    @FXML private Button btnRimuoviVoucher;
    @FXML private Button btnConfermaOrdine;
    @FXML private Button btnAnnullaOrdine;

    @FXML private javafx.scene.layout.HBox panelSconto;

    private CreaOrdineFacade facade;
    private List<FoodBean> prodottiBaseDisponibili;
    private List<FoodBean> addOnDisponibili;
    private ObservableList<RigaOrdineBean> righeOrdineObservable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        righeOrdineObservable = FXCollections.observableArrayList();

        try {
            setupTabella();
            setupListeners();
            caricaDatiIniziali();

            // Verifica che l'utente sia loggato
            String tokenKey = PageNavigationController.getInstance().getSessionTokenKey();
            if (tokenKey == null) {
                logger.severe("Utente non loggato - token null");
                mostraErrore("Errore di sessione", "Devi effettuare il login per creare un ordine.");
                return;
            }

            facade = new CreaOrdineFacade(tokenKey);
            iniziaNuovoOrdine();

        } catch (MissingAuthorizationException e) {
            logger.severe("Errore di autorizzazione: " + e.getMessage());
            mostraErrore("Errore di autorizzazione", e.getMessage());
        } catch (CreaOrdineException e) {
            logger.severe("Errore di inizializzazione ordine: " + e.getMessage());
            mostraErrore("Errore di inizializzazione", e.getMessage());
        } catch (NullPointerException e) {
            logger.severe("Sessione utente non valida: " + e.getMessage());
            mostraErrore("Errore di sessione", "Sessione non valida. Effettua nuovamente il login.");
        } catch (Exception e) {
            logger.severe("Errore generico in initialize: " + e.getMessage());
            mostraErrore("Errore", "Errore durante l'inizializzazione: " + e.getMessage());
        }
    }



    private void setupTabella() {
        colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colonnaPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzoFormattato"));
        if (colonnaDurata != null) {
            colonnaDurata.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getDurata() + " min"));
        }
        tabellaOrdine.setItems(righeOrdineObservable);
        tabellaOrdine.setPlaceholder(new Label("Nessun prodotto nell'ordine"));
    }

    private void setupListeners() {
        baseGroup = new ToggleGroup();
        radioPanino.setToggleGroup(baseGroup);
        radioPiadina.setToggleGroup(baseGroup);
        radioPiatto.setToggleGroup(baseGroup);
        radioPanino.setSelected(true);

        tabellaOrdine.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> btnRimuoviProdotto.setDisable(newSelection == null)
        );

        textFieldVoucher.textProperty().addListener(
                (obs, oldText, newText) -> btnApplicaVoucher.setDisable(newText == null || newText.trim().isEmpty())
        );

        btnRimuoviProdotto.setDisable(true);
        btnApplicaVoucher.setDisable(true);
        btnRimuoviVoucher.setDisable(true);

        if (panelSconto != null) {
            panelSconto.setVisible(false);
            panelSconto.setManaged(false);
        }
    }

    private void caricaDatiIniziali() {
        prodottiBaseDisponibili = List.of(
                new FoodBean(null, "Panino Doner Kebab", 5.50, 5, "BASE", "PaninoDonerKebab"),
                new FoodBean(null, "Piadina Doner Kebab", 6.00, 6, "BASE", "PiadinaDonerKebab"),
                new FoodBean(null, "Kebab al Piatto", 8.00, 8, "BASE", "KebabAlPiatto")
        );

        addOnDisponibili = List.of(
                new FoodBean(null, "Cipolla", 0.50, 1, "ADDON", "Cipolla"),
                new FoodBean(null, "Salsa Yogurt", 0.80, 0, "ADDON", "SalsaYogurt"),
                new FoodBean(null, "Patatine", 2.00, 3, "ADDON", "Patatine"),
                new FoodBean(null, "Mix Verdure Grigliate", 1.50, 2, "ADDON", "MixVerdureGrigliate")
        );
    }

    private FoodBean getProdottoBaseSelezionato() {
        Toggle selected = baseGroup.getSelectedToggle();
        if (selected == null) return null;
        if (selected == radioPanino) return prodottiBaseDisponibili.get(0);
        if (selected == radioPiadina) return prodottiBaseDisponibili.get(1);
        if (selected == radioPiatto) return prodottiBaseDisponibili.get(2);
        return null;
    }

    private void iniziaNuovoOrdine() throws CreaOrdineException {
        try {
            String clienteId = PageNavigationController.getInstance().getSessionTokenKey();
            OrdineBean ordine = facade.inizializzaNuovoOrdine(clienteId);
            if (labelNumeroOrdine != null && ordine.getNumeroOrdine() != null) {
                labelNumeroOrdine.setText("Ordine #" + ordine.getNumeroOrdine());
            }
            aggiornaRiepilogo();
        } catch (DAOException | MissingAuthorizationException e) {
            throw new CreaOrdineException("Impossibile inizializzare l'ordine: " + e.getMessage());
        }
    }


    @FXML
    private void onRimuoviProdotto() {
        int selectedIndex = tabellaOrdine.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            mostraWarning("Selezione richiesta", "Seleziona un prodotto dalla lista da rimuovere.");
            return;
        }

        RigaOrdineBean rigaSelezionata = tabellaOrdine.getSelectionModel().getSelectedItem();
        if (!mostraConferma("Conferma rimozione",
                "Vuoi rimuovere \"" + rigaSelezionata.getDescrizione() + "\" dall'ordine?")) {
            return;
        }

        boolean success = facade.rimuoviProdottoDaOrdine(selectedIndex);
        if (success) {
            aggiornaRiepilogo();
        } else {
            mostraErrore("Errore", "Impossibile rimuovere il prodotto.");
        }
    }

    @FXML
    private void onAggiungiProdotto() {
        try {
            FoodBean prodottoSelezionato = getProdottoBaseSelezionato();
            if (prodottoSelezionato == null) {
                mostraWarning("Selezione richiesta", "Seleziona un prodotto base.");
                return;
            }

            FoodBean richiesta = new FoodBean();
            richiesta.setClasse(prodottoSelezionato.getClasse());
            richiesta.setDescrizione(prodottoSelezionato.getDescrizione());
            richiesta.setCosto(prodottoSelezionato.getCosto());
            richiesta.setDurata(prodottoSelezionato.getDurata());
            richiesta.setTipo(prodottoSelezionato.getTipo());

            if (checkCipolla != null && checkCipolla.isSelected()) richiesta.aggiungiAddOn("Cipolla");
            if (checkSalsaYogurt != null && checkSalsaYogurt.isSelected()) richiesta.aggiungiAddOn("SalsaYogurt");
            if (checkPatatine != null && checkPatatine.isSelected()) richiesta.aggiungiAddOn("Patatine");
            if (checkMixVerdure != null && checkMixVerdure.isSelected()) richiesta.aggiungiAddOn("MixVerdureGrigliate");

            boolean success = facade.aggiungiProdottoAOrdine(richiesta);
            if (success) {
                aggiornaRiepilogo();
                resetSelezioniAddOn();
                mostraInfo("Prodotto aggiunto", "Prodotto aggiunto all'ordine con successo!");
            } else {
                mostraErrore("Errore", "Impossibile aggiungere il prodotto.");
            }

        } catch (DAOException e) {
            mostraErrore("Errore", e.getMessage());
        }
    }


    @FXML
    private void onApplicaVoucher() {
        try {
            String codiceVoucher = textFieldVoucher.getText();
            if (codiceVoucher == null || codiceVoucher.trim().isEmpty()) {
                mostraWarning("Voucher richiesto", "Inserisci un codice voucher.");
                return;
            }
            if (righeOrdineObservable.isEmpty()) {
                mostraWarning("Ordine vuoto", "Aggiungi almeno un prodotto prima di applicare un voucher.");
                return;
            }

            var voucherBean = facade.applicaVoucher(codiceVoucher);
            if (voucherBean != null) {
                aggiornaRiepilogo();
                textFieldVoucher.setDisable(true);
                btnApplicaVoucher.setDisable(true);
                btnRimuoviVoucher.setDisable(false);
                mostraInfo("Voucher applicato", "Voucher " + codiceVoucher.toUpperCase() + " applicato con successo!");
            } else {
                mostraErrore("Voucher non valido", "Il codice voucher inserito non è valido.");
                textFieldVoucher.selectAll();
                textFieldVoucher.requestFocus();
            }

        } catch (DAOException | ObjectNotFoundException | MissingAuthorizationException |
                 WrongListQueryIdentifierValue | UserNotFoundException | UnrecognizedRoleException e) {
            mostraErrore("Voucher non valido", e.getMessage());
            textFieldVoucher.selectAll();
            textFieldVoucher.requestFocus();
        }
    }

    @FXML
    private void onRimuoviVoucher() {

            if (!mostraConferma("Rimuovi Voucher", "Vuoi rimuovere il voucher applicato?")) {
                return;
            }

            facade.rimuoviVoucher();
            aggiornaRiepilogo();

            textFieldVoucher.setDisable(false);
            textFieldVoucher.clear();
            btnApplicaVoucher.setDisable(true);
            btnRimuoviVoucher.setDisable(true);


    }

    @FXML
    private void onConfermaOrdine() {
        try {
            if (righeOrdineObservable.isEmpty()) {
                mostraWarning("Ordine vuoto", "Aggiungi almeno un prodotto all'ordine prima di confermare.");
                return;
            }

            RiepilogoOrdineBean riepilogo = facade.getRiepilogoOrdine();
            String messaggioConferma = costruisciMessaggioConferma(riepilogo);

            if (!mostraConferma("Conferma Ordine", messaggioConferma)) {
                return;
            }

            boolean success = facade.confermaOrdine();
            if (success) {
                mostraInfo("Ordine confermato",
                        "Il tuo ordine #" + riepilogo.getNumeroOrdine() + " è stato confermato!\n\n" +
                                "Totale: " + riepilogo.getTotaleFormattato() + "\n" +
                                "Tempo di preparazione stimato: " + riepilogo.getDurataFormattata());
                navigaToPagamento();
            } else {
                mostraErrore("Errore", "Si è verificato un errore durante la conferma dell'ordine.");
            }

        } catch (DAOException | MissingAuthorizationException e) {
            mostraErrore("Errore", e.getMessage());
        }
    }

    @FXML
    private void onAnnullaOrdine() {
        if (righeOrdineObservable.isEmpty()) {
            navigaToHome();
            return;
        }

        if (mostraConferma("Annulla Ordine",
                "Sei sicuro di voler annullare l'ordine?\nTutti i prodotti selezionati verranno rimossi.")) {

            facade.annullaOrdine();

            try {
                resetVistaCompleta();
                iniziaNuovoOrdine();
            } catch (CreaOrdineException e) {
                mostraErrore("Errore", e.getMessage());
            }
        }
    }

    private void aggiornaRiepilogo()  {
        RiepilogoOrdineBean riepilogo = facade.getRiepilogoOrdine();
        aggiornaVistaConRiepilogo(riepilogo);
    }

    private void aggiornaVistaConRiepilogo(RiepilogoOrdineBean riepilogo) {
        if (riepilogo == null) return;

        righeOrdineObservable.clear();
        righeOrdineObservable.addAll(riepilogo.getRigheOrdine());

        labelSubtotale.setText(riepilogo.getSubtotaleFormattato());
        labelTotale.setText(riepilogo.getTotaleFormattato());
        labelDurata.setText(riepilogo.getDurataFormattata());

        if (riepilogo.isVoucherApplicato()) {
            if (labelSconto != null) labelSconto.setText(riepilogo.getScontoFormattato());
            if (labelVoucherInfo != null) labelVoucherInfo.setText(riepilogo.getCodiceVoucher() + " - " + riepilogo.getDescrizioneVoucher());
            if (panelSconto != null) {
                panelSconto.setVisible(true);
                panelSconto.setManaged(true);
            }
        } else {
            if (labelSconto != null) labelSconto.setText("€0.00");
            if (labelVoucherInfo != null) labelVoucherInfo.setText("");
            if (panelSconto != null) {
                panelSconto.setVisible(false);
                panelSconto.setManaged(false);
            }
        }

        btnConfermaOrdine.setDisable(righeOrdineObservable.isEmpty());
    }

    private void resetSelezioniAddOn() {
        if (checkCipolla != null) checkCipolla.setSelected(false);
        if (checkSalsaYogurt != null) checkSalsaYogurt.setSelected(false);
        if (checkPatatine != null) checkPatatine.setSelected(false);
        if (checkMixVerdure != null) checkMixVerdure.setSelected(false);
    }

    private void resetVistaCompleta() {
        resetSelezioniAddOn();
        radioPanino.setSelected(true);

        textFieldVoucher.clear();
        textFieldVoucher.setDisable(false);
        btnApplicaVoucher.setDisable(true);
        btnRimuoviVoucher.setDisable(true);

        righeOrdineObservable.clear();

        labelSubtotale.setText("€0.00");
        if (labelSconto != null) labelSconto.setText("€0.00");
        labelTotale.setText("€0.00");
        labelDurata.setText("0 min");
        if (labelVoucherInfo != null) labelVoucherInfo.setText("");

        if (panelSconto != null) {
            panelSconto.setVisible(false);
            panelSconto.setManaged(false);
        }
    }

    private String costruisciMessaggioConferma(RiepilogoOrdineBean riepilogo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Riepilogo Ordine #").append(riepilogo.getNumeroOrdine()).append("\n\n");
        sb.append("Prodotti:\n");
        for (RigaOrdineBean r : riepilogo.getRigheOrdine()) {
            sb.append("• ").append(r.getDescrizione())
                    .append(" - ").append(r.getPrezzoFormattato()).append("\n");
        }
        sb.append("\nSubtotale: ").append(riepilogo.getSubtotaleFormattato()).append("\n");
        if (riepilogo.isVoucherApplicato()) {
            sb.append("Sconto (").append(riepilogo.getCodiceVoucher()).append("): ")
                    .append(riepilogo.getScontoFormattato()).append("\n");
        }
        sb.append("TOTALE: ").append(riepilogo.getTotaleFormattato()).append("\n\n");
        sb.append("Tempo di preparazione: ").append(riepilogo.getDurataFormattata()).append("\n\n");
        sb.append("Vuoi confermare l'ordine?");
        return sb.toString();
    }

    private void navigaToPagamento() {
        // PageNavigationController.getInstance().navigateTo("PagamentoView.fxml");
    }

    private void navigaToHome() {
        // PageNavigationController.getInstance().navigateTo("HomeView.fxml");
    }

    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void mostraWarning(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void mostraInfo(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private boolean mostraConferma(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
