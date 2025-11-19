package org.example.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Classe che gestisce l'ordine complessivo, permettendo di aggiungere
 * multipli servizi base e applicando Add-On unici a tutti i servizi.
 */
class OrdineBarbiere {
    // Lista di tutti i servizi (potenzialmente decorati)
    private List<Servizio> serviziBase;

    // Set per tracciare gli Add-On unici già applicati
    private Set<String> addOnsApplicati;

    public OrdineBarbiere() {
        this.serviziBase = new ArrayList<>();
        this.addOnsApplicati = new HashSet<>();
    }

    /**
     * Aggiunge un prodotto base all'ordine.
     */
    public void aggiungiServizioBase(Servizio servizio) {
        this.serviziBase.add(servizio);
        System.out.println("-> Servizio base aggiunto: " + servizio.getDescrizione());
    }

    /**
     * Aggiunge un Add-On (Decorator) a tutti i servizi nell'ordine,
     * garantendo che venga aggiunto una sola volta, utilizzando l'ENUM.
     * * @param addOnType La costante Enum che rappresenta l'Add-On da aggiungere.
     */
    public void aggiungiAddOnUnico(AddOnType addOnType) {
        String nomeAddOn = addOnType.getNome();
        Function<Servizio, DecoratorAddOn> decoratorCreator = addOnType.getCreator();

        if (addOnsApplicati.contains(nomeAddOn)) {
            System.out.println("-> ATTENZIONE: Add-On '" + nomeAddOn + "' già presente. Ignorato.");
            return;
        }

        if (serviziBase.isEmpty()) {
            System.out.println("ERRORE: Impossibile aggiungere l'Add-On. L'ordine è vuoto.");
            return;
        }

        // 1. Crea una nuova lista per i servizi decorati
        List<Servizio> nuoviServiziDecorati = new ArrayList<>();

        // 2. Applica il decoratore a ogni servizio esistente
        for (Servizio servizio : serviziBase) {
            // Utilizza la Function (lambda) memorizzata nell'Enum per creare l'istanza
            DecoratorAddOn servizioDecorato = decoratorCreator.apply(servizio);
            nuoviServiziDecorati.add(servizioDecorato);
        }

        // 3. Sostituisce la lista di servizi con i servizi decorati
        this.serviziBase = nuoviServiziDecorati;

        // 4. Registra l'add-on come applicato
        addOnsApplicati.add(nomeAddOn);
        System.out.println("-> Add-On '" + nomeAddOn + "' applicato a tutti i servizi.");
    }

    /**
     * Calcola il costo totale sommando il costo di tutti i servizi.
     */
    public double calcolaTotale() {
        double totale = 0.0;
        for (Servizio s : serviziBase) {
            totale += s.getCosto();
        }
        return totale;
    }

    /**
     * Calcola la durata totale sommando la durata di tutti i servizi.
     */
    public int calcolaDurataTotale() {
        int durata = 0;
        for (Servizio s : serviziBase) {
            durata += s.getDurata();
        }
        return durata;
    }

    /**
     * Stampa il riepilogo completo dell'ordine.
     */
    public void riepilogoOrdine() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         RIEPILOGO ORDINE BARBIERE");
        System.out.println("=".repeat(40));

        for (int i = 0; i < serviziBase.size(); i++) {
            Servizio servizio = serviziBase.get(i);
            System.out.printf("Servizio %d: %s%n", i + 1, servizio.getDescrizione());
            System.out.printf("  - Costo: €%.2f%n", servizio.getCosto());
            System.out.printf("  - Durata: %d min%n", servizio.getDurata());
        }

        System.out.println("\n" + "-".repeat(40));
        String addOns = addOnsApplicati.isEmpty() ? "Nessuno" : String.join(", ", addOnsApplicati);
        System.out.println("Add-On Unici Applicati: " + addOns);
        System.out.println("-".repeat(40));
        System.out.printf("TOTALE ORDINE: €%.2f%n", calcolaTotale());
        System.out.printf("DURATA TOTALE STIMATA: %d minuti%n", calcolaDurataTotale());
        System.out.println("=".repeat(40));
    }
}
