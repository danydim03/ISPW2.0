package org.example.Model;

abstract class DecoratorAddOn implements Servizio {
    protected Servizio servizioAvvolto;

    public DecoratorAddOn(Servizio servizio) {
        this.servizioAvvolto = servizio;
    }

    // Delega all'oggetto avvolto per i valori base
    @Override
    public double getCosto() {
        return servizioAvvolto.getCosto();
    }

    @Override
    public String getDescrizione() {
        return servizioAvvolto.getDescrizione();
    }

    @Override
    public int getDurata() {
        return servizioAvvolto.getDurata();
    }

    // getNomeAddOn() deve essere implementato da ogni Concrete Decorator
}
