package org.example.Model;

class ConcreteAddONMassaggio extends DecoratorAddOn {
    public ConcreteAddONMassaggio(Servizio servizio) {
        super(servizio);
    }

    @Override
    public double getCosto() {
        return super.getCosto() + 8.00; // + Costo Plus
    }

    @Override
    public String getDescrizione() {
        return super.getDescrizione() + ", con ConcreteAddONMassaggio Craniale";
    }

    @Override
    public int getDurata() {
        return super.getDurata() + 10; // + Durata Plus
    }

    @Override
    public String getNomeAddOn() {
        return AddOnType.MASSAGGIO.getNome(); // Usa il nome dall'enum
    }
}
