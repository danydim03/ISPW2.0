package org.example.Model;

import java.util.function.Function;

// --- ENUM PER GLI ADD-ON (MIGLIORAMENTO) ---
// Incapsula le informazioni necessarie per ogni Add-On,
// inclusa la funzione di creazione del Decorator.
enum AddOnType {
    PULIZIA_VISO("Pulizia Viso", ConcreteAddONPuliziaViso::new),
    MASSAGGIO("ConcreteAddONMassaggio Craniale", ConcreteAddONMassaggio::new),
    OLI_ESSENZIALI("Oli Essenziali", ConcreteAddONOliEssenziali::new);

    private final String nome;
    // La Function memorizza la referenza al costruttore (e.g., ConcreteAddONMassaggio::new)
    private final Function<Servizio, DecoratorAddOn> creator;

    AddOnType(String nome, Function<Servizio, DecoratorAddOn> creator) {
        this.nome = nome;
        this.creator = creator;
    }

    public String getNome() {
        return nome;
    }

    public Function<Servizio, DecoratorAddOn> getCreator() {
        return creator;
    }
}
