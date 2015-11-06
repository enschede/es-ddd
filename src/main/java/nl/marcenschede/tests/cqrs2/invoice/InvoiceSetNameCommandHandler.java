package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.Bus;
import nl.marcenschede.tests.cqrs2.base.Command;
import nl.marcenschede.tests.cqrs2.base.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceSetNameCommandHandler implements CommandHandler {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public void handle(Command cmd, Bus bus) {
            Invoice invoice = invoiceRepository.loadFromHistory(((InvoiceSetNameCommand) cmd).getUuid());

        invoice.setName(((InvoiceSetNameCommand)cmd).getName());
        invoiceRepository.storeEvent(invoice);
    }
}
