package nl.marcenschede.tests.cqrs2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bus {

    @Autowired
    private CreateCommandCommandHandler createCommandCommandHandler;

    @Autowired
    private InvoiceSetNameCommandHandler invoiceSetNameCommandHandler;

    public void process(Command command) {

        if(command instanceof CreateInvoiceCommand) {
            createCommandCommandHandler.handle(command);
        }

        if(command instanceof InvoiceSetNameCommand) {
            invoiceSetNameCommandHandler.handle(command);
        }

    }

}
