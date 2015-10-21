package nl.marcenschede.tests.elastic.base.events;

import nl.marcenschede.tests.elastic.base.domains.AggregateBase;

public class AggregateEvent extends Event {

    private AggregateEventType aggregateEventType;
    private AggregateBase aggregateBase;
    private String reason;

    public AggregateEvent() {
        super();
    }

    public AggregateEvent(AggregateEventType aggregateEventType, AggregateBase aggregateBase, String reason) {
        super(aggregateBase.getId(), aggregateBase.getDomainEntityType().getType());

        this.aggregateEventType = aggregateEventType;
        this.aggregateBase = aggregateBase;
        this.reason = reason;
    }

    public AggregateEventType getAggregateEventType() {
        return aggregateEventType;
    }

    public AggregateBase getAggregateBase() {
        return aggregateBase;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "AggregateEvent{" +
                "aggregateEventType=" + aggregateEventType +
                ", aggregateBase=" + aggregateBase +
                ", reason='" + reason + '\'' +
                ", super='" + super.toString() + '\'' +
                '}';
    }
}
