package nl.marcenschede.tests.cqrs2.base;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by marc on 27/10/15.
 */
public abstract class AggregateRoot {

    private List<Event> uncommittedChanges = new LinkedList<>();
    private UUID uuid;

    public AggregateRoot(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void applyChange(Event event, boolean isNew) {
        this.apply(event);
        if(isNew)
            uncommittedChanges.add(event);
    }

    public void markChangesAsCommitted() {
        uncommittedChanges.clear();
    }

    public List<Event> getUncommittedChanges() {
        return uncommittedChanges;
    }

    public abstract void apply(Event event);
}
