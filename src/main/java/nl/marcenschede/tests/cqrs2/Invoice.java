package nl.marcenschede.tests.cqrs2;

import java.util.UUID;

/**
 * Created by marc on 27/10/15.
 */
public class Invoice extends AggregateRoot {

    private String naam;

    public Invoice(UUID uuid, String naam) {
        super(uuid);

        InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent(uuid, naam);
        applyChange(invoiceCreatedEvent, true);
    }

    public Invoice(UUID uuid) {
        super(uuid);
    }

    @Override
    public void apply(Event event) {
        if(event instanceof InvoiceCreatedEvent) {
            this.naam = ((InvoiceCreatedEvent) event).getNaam();
        }

        if(event instanceof InvoiceSetNameEvent) {
            this.naam = ((InvoiceSetNameEvent) event).getNaam();
        }


    }

    public String getNaam() {
        return naam;
    }

    public void setName(String naam) {
        Event event = new InvoiceSetNameEvent(getUuid(), naam);
        applyChange(event, true);
    }


}
