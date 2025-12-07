package org.example.model.voucher;


public class VoucherFisso implements Voucher {
    
    private String codice;
    private double importoSconto;
    private double minimoOrdine; // importo minimo per applicare lo sconto
    private boolean valido;
    
    public VoucherFisso(String codice, double importoSconto, double minimoOrdine) {
        this.codice = codice;
        this.importoSconto = importoSconto;
        this.minimoOrdine = minimoOrdine;
        this.valido = true;
    }
    
    @Override
    public double calcolaSconto(double totaleOrdine) {
        if (!valido || totaleOrdine < minimoOrdine) {
            return 0;
        }
        // Lo sconto non può superare il totale
        return Math.min(importoSconto, totaleOrdine);
    }
    
    @Override
    public String getCodice() {
        return codice;
    }
    
    @Override
    public String getDescrizione() {
        return "Sconto di €" + importoSconto + " (minimo ordine €" + minimoOrdine + ")";
    }
    
    @Override
    public boolean isValido() {
        return valido;
    }
    
    public void setValido(boolean valido) {
        this. valido = valido;
    }
}