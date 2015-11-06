package nl.marcenschede.tests.cqrs2.base;

import nl.marcenschede.tests.cqrs2.invoice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bus {

    @Autowired
    private CreateCommandCommandHandler createCommandCommandHandler;

    @Autowired
    private InvoiceSetNameCommandHandler invoiceSetNameCommandHandler;

    @Autowired
    private InvoiceCreatedEventHandler invoiceCreatedEventHandler;

    private List<Event> scheduleEvents = new ArrayList<>();

    public void process(Command command) {

        if(command instanceof CreateInvoiceCommand) {
            createCommandCommandHandler.handle(command, this);
        }

        if(command instanceof InvoiceSetNameCommand) {
            invoiceSetNameCommandHandler.handle(command, this);
        }

        processScheduledEvents();
    }

    private void processScheduledEvents() {
        scheduleEvents.stream().forEach(event -> processScheduledEvent(event));

        scheduleEvents.clear();
    }

    private void processScheduledEvent(Event event) {
        if(event instanceof InvoiceCreatedEvent) {
            invoiceCreatedEventHandler.process(event);
        }

        if(event instanceof InvoiceNameSetEvent) {

        }

    }

    public void scheduleEventProcessing(List<Event> uncommittedChanges) {
        scheduleEvents.addAll(uncommittedChanges);
    }
}
