package nl.marcenschede.tests.cqrs2;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by marc on 27/10/15.
 */
public abstract class Repository<T extends AggregateRoot> {

    private Map<UUID, List<Event>> dataset = new HashMap<>();


    public void store(T t) {
        UUID uuid = t.getUuid();

        if(!dataset.containsKey(uuid)) {
            dataset.put(uuid, new LinkedList<>());
        }

        t.getUncommittedChanges().stream().forEach(new Consumer<Event>() {
            @Override
            public void accept(Event event) {
                dataset.get(uuid).add(event);
            }
        });
    }

    public T loadFromHistory(UUID uuid) {
        List<Event> events = dataset.get(uuid);

        T t = getEmptyObject(uuid);

        events.stream().forEach(new Consumer<Event>() {
            @Override
            public void accept(Event event) {
                t.applyChange(event, false);
            }
        });

        return t;
    }

    protected abstract T getEmptyObject(UUID uuid);
}
