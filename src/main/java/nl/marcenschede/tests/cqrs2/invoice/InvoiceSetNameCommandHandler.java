package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.Bus;
import nl.marcenschede.tests.cqrs2.base.Command;
import nl.marcenschede.tests.cqrs2.base.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceSetNameCommandHandler implements CommandHandler {

    @Autowired
    private InvoiceEventRepository invoiceRepository;

    @Override
    public void handle(Command cmd, Bus bus) {
            Invoice invoice = invoiceRepository.loadFromHistory(((InvoiceSetNameCommand) cmd).getUuid());

        invoice.setName(((InvoiceSetNameCommand)cmd).getName());
        bus.scheduleEventProcessing(invoice.getUncommittedChanges());
        invoiceRepository.storeEvent(invoice);
    }
}
