package org.example.Model;

class ProductBaseBarba implements Servizio {
    @Override
    public double getCosto() {
        return 15.00;
    }

    @Override
    public String getDescrizione() {
        return "Modellatura ProductBaseBarba";
    }

    @Override
    public int getDurata() {
        return 20;
    }

    @Override
    public String getNomeAddOn() {
        return null; // Non è un add-on
    }
}
