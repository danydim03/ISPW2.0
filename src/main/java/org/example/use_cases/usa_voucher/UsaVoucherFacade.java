package org.example.use_cases.usa_voucher;

import org.example.exceptions.*;
import org.example.model.ordine.Ordine;
import org.example.use_cases.crea_ordine.beans.VoucherBean;

/**
 * Facade per lo Use Case "Usa Voucher".
 * 
 * Fornisce un'interfaccia semplificata per l'applicazione di voucher agli
 * ordini.
 * Può essere usata direttamente dal controller grafico quando il voucher viene
 * applicato in modo indipendente dall'ordine.
 * 
 * Segue il pattern Facade per nascondere la complessità del sottosistema
 * voucher.
 */
public class UsaVoucherFacade {

    private final UsaVoucherController controller;

    public UsaVoucherFacade() {
        this.controller = new UsaVoucherController();
    }

    /**
     * Applica un voucher all'ordine specificato.
     * 
     * @param ordine        l'ordine a cui applicare il voucher
     * @param codiceVoucher il codice del voucher da applicare
     * @return VoucherBean con i dati del voucher applicato, null se non valido
     */
    public VoucherBean applicaVoucher(Ordine ordine, String codiceVoucher) throws DAOException,
            ObjectNotFoundException, MissingAuthorizationException, WrongListQueryIdentifierValue,
            UserNotFoundException, UnrecognizedRoleException {
        return controller.applicaVoucherAOrdine(ordine, codiceVoucher);
    }

    /**
     * Rimuove il voucher dall'ordine specificato.
     * 
     * @param ordine l'ordine da cui rimuovere il voucher
     */
    public void rimuoviVoucher(Ordine ordine) {
        controller.rimuoviVoucherDaOrdine(ordine);
    }

    /**
     * Verifica se l'ordine ha un voucher applicato.
     * 
     * @param ordine l'ordine da verificare
     * @return true se l'ordine ha un voucher applicato
     */
    public boolean hasVoucherApplicato(Ordine ordine) {
        return controller.hasVoucherApplicato(ordine);
    }

    /**
     * Ottiene il voucher attualmente applicato all'ordine.
     * 
     * @param ordine l'ordine da cui ottenere il voucher
     * @return VoucherBean del voucher applicato, null se non presente
     */
    public VoucherBean getVoucherApplicato(Ordine ordine) {
        return controller.getVoucherApplicato(ordine);
    }

    /**
     * Calcola lo sconto del voucher applicato all'ordine.
     * 
     * @param ordine l'ordine su cui calcolare lo sconto
     * @return l'importo dello sconto
     */
    public double calcolaSconto(Ordine ordine) {
        return controller.calcolaSconto(ordine);
    }

    /**
     * Ottiene il controller interno (per uso da parte di CreaOrdineController).
     * 
     * @return il controller voucher
     */
    public UsaVoucherController getController() {
        return controller;
    }
}
