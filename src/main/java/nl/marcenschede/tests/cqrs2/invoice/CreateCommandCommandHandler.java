package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.Bus;
import nl.marcenschede.tests.cqrs2.base.Command;
import nl.marcenschede.tests.cqrs2.base.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCommandCommandHandler implements CommandHandler {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public void handle(Command cmd, Bus bus) {
        CreateInvoiceCommand createInvoiceCommand = (CreateInvoiceCommand)cmd;
        
        Invoice invoice = new Invoice(createInvoiceCommand.getUuid(), createInvoiceCommand.getNaam(), createInvoiceCommand.getOrderRef());
        bus.scheduleEventProcessing(invoice.getUncommittedChanges());
        invoiceRepository.storeEvent(invoice);

    }
}
