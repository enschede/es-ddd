package nl.marcenschede.tests.cqrs2;

import java.util.UUID;

/**
 * Created by marc on 27/10/15.
 */
public class InvoiceSetNameEvent implements Event {
    private final UUID uuid;
    private final String naam;

    public InvoiceSetNameEvent(UUID uuid, String naam) {
        this.uuid = uuid;
        this.naam = naam;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNaam() {
        return naam;
    }
}