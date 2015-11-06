package nl.marcenschede.tests.cqrs2.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bus {

    @Autowired
    private CommandHandlerConfigurator commandHandlerConfigurator;

    @Autowired
    private EventHandlerConfigurator eventHandlerConfigurator;

    private List<Event> scheduledEvents = new ArrayList<>();

    public void process(Command command) {
        CommandHandler commandHandler = commandHandlerConfigurator.getCommandHandler(command);

        commandHandler.handle(command, this);

        processScheduledEvents();
    }

    private void processScheduledEvents() {
        scheduledEvents.stream().forEach(event -> processScheduledEvent(event));
        scheduledEvents.clear();
    }

    private void processScheduledEvent(Event event) {
        List<? extends EventHandler> eventHandlers = eventHandlerConfigurator.getEventHandler(event);
        eventHandlers.stream().forEach(eventHandler -> eventHandler.process(event, this));
    }

    public void scheduleEventProcessing(List<Event> uncommittedChanges) {
        scheduledEvents.addAll(uncommittedChanges);
    }
}
