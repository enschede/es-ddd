package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.Event;
import nl.marcenschede.tests.cqrs2.base.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by marc on 06/11/15.
 */
@Component
public class InvoiceCreatedEventHandler implements EventHandler {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public void process(Event event) {
        InvoiceCreatedEvent invoiceCreatedEvent = (InvoiceCreatedEvent) event;

        InvoiceDetails invoice = new InvoiceDetails(invoiceCreatedEvent.getUuid(), invoiceCreatedEvent.getNaam(), invoiceCreatedEvent.getOrderRef());

        invoiceRepository.storeDto(invoice);
    }
}
