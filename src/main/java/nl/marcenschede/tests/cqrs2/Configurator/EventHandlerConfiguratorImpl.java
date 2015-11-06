package nl.marcenschede.tests.cqrs2.Configurator;

import nl.marcenschede.tests.cqrs2.base.Event;
import nl.marcenschede.tests.cqrs2.base.EventHandler;
import nl.marcenschede.tests.cqrs2.base.EventHandlerConfigurator;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceCreatedEvent;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceCreatedEventHandler;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceNameSetEvent;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceNameSetEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marc on 06/11/15.
 */
@Component
public class EventHandlerConfiguratorImpl implements EventHandlerConfigurator {

    private Map<Class, List<? extends EventHandler>> eventHandlers;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @PostConstruct
    public void init() {

        eventHandlers = new HashMap<>();

        InvoiceCreatedEventHandler invoiceCreatedEventHandler = new InvoiceCreatedEventHandler();
        beanFactory.autowireBean(invoiceCreatedEventHandler);

        eventHandlers.put(InvoiceCreatedEvent.class, Arrays.asList(invoiceCreatedEventHandler));

        InvoiceNameSetEventHandler invoiceNameSetEventHandler = new InvoiceNameSetEventHandler();
        beanFactory.autowireBean(invoiceCreatedEventHandler);

        eventHandlers.put(InvoiceNameSetEvent.class, Arrays.asList(invoiceNameSetEventHandler));
    }

    @Override
    public List<? extends EventHandler> getEventHandler(Event event) {
        return eventHandlers.get(event.getClass());
    }
}
