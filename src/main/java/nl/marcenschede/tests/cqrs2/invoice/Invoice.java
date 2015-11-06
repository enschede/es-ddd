package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.AggregateRoot;
import nl.marcenschede.tests.cqrs2.base.Event;

import java.util.UUID;

/**
 * Created by marc on 27/10/15.
 */
public class Invoice extends AggregateRoot {

    private String naam;
    private String orderRef;

    public Invoice(UUID uuid, String naam, String orderRef) {
        super(uuid);

        InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent(uuid, naam, orderRef);
        applyChange(invoiceCreatedEvent, true);
    }

    public Invoice(UUID uuid) {
        super(uuid);
    }

    @Override
    public void apply(Event event) {
        if(event instanceof InvoiceCreatedEvent) {
            this.naam = ((InvoiceCreatedEvent) event).getNaam();
            this.orderRef = ((InvoiceCreatedEvent) event).getOrderRef();
        }

        if(event instanceof InvoiceNameSetEvent) {
            this.naam = ((InvoiceNameSetEvent) event).getNaam();
        }


    }

    public String getNaam() {
        return naam;
    }

    public void setName(String naam) {
        Event event = new InvoiceNameSetEvent(getUuid(), naam);
        applyChange(event, true);
    }


}
