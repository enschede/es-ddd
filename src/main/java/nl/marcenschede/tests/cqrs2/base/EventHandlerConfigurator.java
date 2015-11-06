package nl.marcenschede.tests.cqrs2.base;

import java.util.List;

/**
 * Created by marc on 06/11/15.
 */
public interface EventHandlerConfigurator {
    List<? extends EventHandler> getEventHandler(Event event);
}
