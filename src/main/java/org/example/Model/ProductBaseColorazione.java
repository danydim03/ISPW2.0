package org.example.Model;

class ProductBaseColorazione implements Servizio {
    @Override
    public double getCosto() {
        return 40.00;
    }

    @Override
    public String getDescrizione() {
        return "ProductBaseColorazione Capelli Completa";
    }

    @Override
    public int getDurata() {
        return 50;
    }

    @Override
    public String getNomeAddOn() {
        return null; // Non è un add-on
    }
}
