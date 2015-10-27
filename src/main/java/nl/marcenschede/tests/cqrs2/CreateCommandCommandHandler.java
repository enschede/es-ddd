package nl.marcenschede.tests.cqrs2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCommandCommandHandler implements CommandHandler {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public void handle(Command cmd) {
        CreateInvoiceCommand createInvoiceCommand = (CreateInvoiceCommand)cmd;
        
        Invoice invoice = new Invoice(createInvoiceCommand.getUuid(), createInvoiceCommand.getNaam());
        invoiceRepository.store(invoice);

    }
}
