package org.example.Model;

class ProductBaseTaglio implements Servizio {
    @Override
    public double getCosto() {
        return 25.00;
    }

    @Override
    public String getDescrizione() {
        return "ProductBaseTaglio Uomo Standard";
    }

    @Override
    public int getDurata() {
        return 30;
    }

    @Override
    public String getNomeAddOn() {
        return null; // Non è un add-on
    }
}
