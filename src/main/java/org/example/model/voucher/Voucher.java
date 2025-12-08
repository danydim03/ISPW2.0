package org.example.model.voucher;

import java.time.LocalDate;

/**
 * Interfaccia Strategy per i Voucher.
 * Definisce il contratto per tutti i tipi di voucher (percentuale, fisso, nessuno).
 */
public interface Voucher {

    /**
     * Calcola lo sconto da applicare al totale dell'ordine
     * @param totaleOrdine il totale dell'ordine prima dello sconto
     * @return l'importo dello sconto
     */
    double calcolaSconto(double totaleOrdine);

    /**
     * Restituisce il codice del voucher
     * @return il codice del voucher
     */
    String getCodice();

    /**
     * Restituisce la descrizione dello sconto
     * @return la descrizione
     */
    String getDescrizione();

    /**
     * Verifica se il voucher è valido (attivo e non scaduto)
     * @return true se il voucher è valido
     */
    boolean isValido();

    /**
     * Restituisce l'ID del voucher (per persistenza)
     * @return l'ID, può essere null per voucher non persistiti
     */
    Long getId();

    /**
     * Imposta l'ID del voucher
     * @param id l'ID da impostare
     */
    void setId(Long id);

    /**
     * Restituisce il tipo di voucher
     * @return "PERCENTUALE", "FISSO" o "NESSUNO"
     */
    String getTipoVoucher();

    /**
     * Restituisce la data di scadenza
     * @return la data di scadenza, può essere null
     */
    LocalDate getDataScadenza();

    /**
     * Verifica se il voucher è attivo
     * @return true se attivo
     */
    boolean isAttivo();

    /**
     * Imposta lo stato attivo del voucher
     * @param attivo true per attivare
     */
    void setAttivo(boolean attivo);
}