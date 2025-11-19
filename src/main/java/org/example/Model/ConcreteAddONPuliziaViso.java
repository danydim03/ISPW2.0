package org.example.Model;

class ConcreteAddONPuliziaViso extends DecoratorAddOn {
    public ConcreteAddONPuliziaViso(Servizio servizio) {
        super(servizio);
    }

    @Override
    public double getCosto() {
        return super.getCosto() + 10.00; // + Costo Plus
    }

    @Override
    public String getDescrizione() {
        return super.getDescrizione() + ", con Pulizia Viso";
    }

    @Override
    public int getDurata() {
        return super.getDurata() + 15; // + Durata Plus
    }

    @Override
    public String getNomeAddOn() {
        return AddOnType.PULIZIA_VISO.getNome(); // Usa il nome dall'enum
    }
}
