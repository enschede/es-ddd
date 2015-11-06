package nl.marcenschede.tests.cqrs2.base;

/**
 * Created by marc on 06/11/15.
 */
public interface EventHandler {

    public void process(Event event, Bus bus);
}
