package org.example.Model;

class ConcreteAddONOliEssenziali extends DecoratorAddOn {
    public ConcreteAddONOliEssenziali(Servizio servizio) {
        super(servizio);
    }

    @Override
    public double getCosto() {
        return super.getCosto() + 5.00;
    }

    @Override
    public String getDescrizione() {
        return super.getDescrizione() + ", con Oli Essenziali";
    }

    @Override
    public int getDurata() {
        return super.getDurata() + 5;
    }

    @Override
    public String getNomeAddOn() {
        return AddOnType.OLI_ESSENZIALI.getNome(); // Usa il nome dall'enum
    }
}
