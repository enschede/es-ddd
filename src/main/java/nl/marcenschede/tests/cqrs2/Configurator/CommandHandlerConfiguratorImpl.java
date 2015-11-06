package nl.marcenschede.tests.cqrs2.Configurator;

import nl.marcenschede.tests.cqrs2.base.Command;
import nl.marcenschede.tests.cqrs2.base.CommandHandler;
import nl.marcenschede.tests.cqrs2.base.CommandHandlerConfigurator;
import nl.marcenschede.tests.cqrs2.invoice.CreateInvoiceCommand;
import nl.marcenschede.tests.cqrs2.invoice.CreateInvoiceCommandHandler;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceSetNameCommand;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceSetNameCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marc on 06/11/15.
 */
@Component
public class CommandHandlerConfiguratorImpl implements CommandHandlerConfigurator {

    private Map<Class, CommandHandler> commandHandles;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @PostConstruct
    public void init() {

        commandHandles = new HashMap<>();

        CreateInvoiceCommandHandler createInvoiceCommandHandler = new CreateInvoiceCommandHandler();
        beanFactory.autowireBean(createInvoiceCommandHandler);

        commandHandles.put(CreateInvoiceCommand.class, createInvoiceCommandHandler);

        InvoiceSetNameCommandHandler invoiceSetNameCommandHandler = new InvoiceSetNameCommandHandler();
        beanFactory.autowireBean(invoiceSetNameCommandHandler);

        commandHandles.put(InvoiceSetNameCommand.class, invoiceSetNameCommandHandler);

    }

    @Override
    public CommandHandler getCommandHandler(Command command) {
        return commandHandles.get(command.getClass());
    }
}
