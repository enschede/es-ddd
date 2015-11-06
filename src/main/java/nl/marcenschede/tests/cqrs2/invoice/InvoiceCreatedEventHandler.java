package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.Bus;
import nl.marcenschede.tests.cqrs2.base.Event;
import nl.marcenschede.tests.cqrs2.base.EventHandler;
import nl.marcenschede.tests.cqrs2.invoice.invoicedetails.InvoiceDetails;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by marc on 06/11/15.
 */
public class InvoiceCreatedEventHandler implements EventHandler {

    @Autowired
    private InvoiceDtoRepository invoiceRepository;

    @Override
    public void process(Event event, Bus bus) {
        InvoiceCreatedEvent invoiceCreatedEvent = (InvoiceCreatedEvent) event;

        InvoiceDetails invoice = new InvoiceDetails(invoiceCreatedEvent.getUuid(), invoiceCreatedEvent.getNaam(), invoiceCreatedEvent.getOrderRef());

        invoiceRepository.storeDto(invoice);
    }
}
