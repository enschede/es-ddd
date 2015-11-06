package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.Event;

import java.util.UUID;

/**
 * Created by marc on 27/10/15.
 */
public class InvoiceCreatedEvent extends Event {

    private UUID uuid;
    private String naam;
    private String orderRef;

    private InvoiceCreatedEvent() {
    }

    public InvoiceCreatedEvent(UUID uuid, String naam, String orderRef) {
        this.uuid = uuid;
        this.naam = naam;
        this.orderRef = orderRef;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNaam() {
        return naam;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }
}
