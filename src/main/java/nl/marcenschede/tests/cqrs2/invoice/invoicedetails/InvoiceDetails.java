package nl.marcenschede.tests.cqrs2.invoice.invoicedetails;

import nl.marcenschede.tests.cqrs2.base.DtoObject;

import java.util.UUID;

/**
 * Created by marc on 06/11/15.
 */
public class InvoiceDetails implements DtoObject {
    private final UUID uuid;
    private final String naam;
    private final String orderRef;

    public InvoiceDetails(UUID uuid, String naam, String orderRef) {
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

    @Override
    public String getDtoEntityType() {
        return "invoicedetails";
    }
}
