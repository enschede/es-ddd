package nl.marcenschede.tests.cqrs2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceSetNameCommandHandler implements CommandHandler {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public void handle(Command cmd) {
            Invoice invoice = invoiceRepository.loadFromHistory(((InvoiceSetNameCommand) cmd).getUuid());

        invoice.setName(((InvoiceSetNameCommand)cmd).getName());
        invoiceRepository.store(invoice);
    }
}
