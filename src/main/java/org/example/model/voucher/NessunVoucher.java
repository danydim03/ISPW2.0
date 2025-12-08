package org.example.model.voucher;

import java.time.LocalDate;

/**
 * Null Object Pattern per i voucher.
 * Rappresenta l'assenza di un voucher.
 */
public class NessunVoucher implements Voucher {

    private Long id;

    public NessunVoucher() {
        // Costruttore vuoto
    }

    @Override
    public double calcolaSconto(double totaleOrdine) {
        return 0;
    }

    @Override
    public String getCodice() {
        return "";
    }

    @Override
    public String getDescrizione() {
        return "Nessuno sconto applicato";
    }

    @Override
    public boolean isValido() {
        return true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTipoVoucher() {
        return "NESSUNO";
    }

    @Override
    public LocalDate getDataScadenza() {
        return null;
    }

    @Override
    public boolean isAttivo() {
        return true;
    }

    @Override
    public void setAttivo(boolean attivo) {
        // Nessuna operazione per NessunVoucher
    }
}