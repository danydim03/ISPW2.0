package org.example.Model;

/**
 * Classe principale per la dimostrazione.
 */
public class OrdineBarbiereApp {
    public static void main(String[] args) {
        // 1. Creazione dell'ordine
        OrdineBarbiere ordine = new OrdineBarbiere();

        System.out.println("--- 1. Composizione Ordine Base ---");
        ordine.aggiungiServizioBase(new ProductBaseTaglio());
        ordine.aggiungiServizioBase(new ProductBaseBarba());
        ordine.aggiungiServizioBase(new ProductBaseColorazione());


        // 2. Aggiunta dei Decorator (Add-Ons)
        System.out.println("\n--- 2. Aggiunta Add-On (Controllo Duplicati) ---");

        // NUOVO: La chiamata è ora pulita e type-safe
        ordine.aggiungiAddOnUnico(AddOnType.MASSAGGIO);

        // Tenta di aggiungere org.example.View.Massaggio di nuovo (dovrebbe essere ignorato)
        ordine.aggiungiAddOnUnico(AddOnType.MASSAGGIO);

        ordine.aggiungiAddOnUnico(AddOnType.PULIZIA_VISO);
        // Aggiunge Pulizia Viso
        ordine.aggiungiAddOnUnico(AddOnType.PULIZIA_VISO);

        // Aggiunge un terzo servizio base DOPO alcuni decoratori
        System.out.println("\n--- 3. Aggiunta di un nuovo Servizio Base ---");
        ordine.aggiungiServizioBase(new ProductBaseColorazione());

        System.out.println("\n--- 4. Aggiunta di un Add-On applicabile a TUTTI i Servizi ---");
        // Aggiunge Oli Essenziali (applicato a ProductBaseTaglio, org.example.View.ProductBaseBarba E org.example.View.ProductBaseColorazione)
        ordine.aggiungiAddOnUnico(AddOnType.OLI_ESSENZIALI);

        // 3. Riepilogo Finale
        ordine.riepilogoOrdine();
    }
}
