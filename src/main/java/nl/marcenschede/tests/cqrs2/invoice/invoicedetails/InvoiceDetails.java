package nl.marcenschede.tests.cqrs2.invoice.invoicedetails;

import nl.marcenschede.tests.cqrs2.base.DtoObject;

import java.util.UUID;

/**
 * Created by marc on 06/11/15.
 */
public class InvoiceDetails implements DtoObject {
    private UUID uuid;
    private String naam;
    private String orderRef;

    private InvoiceDetails() {
    }

    public InvoiceDetails(UUID uuid, String naam, String orderRef) {
        this.uuid = uuid;
        this.naam = naam;
        this.orderRef = orderRef;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }
}
