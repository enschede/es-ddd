package nl.marcenschede.tests.elastic.base.events;

import nl.marcenschede.tests.elastic.base.domains.AggregateBase;

public class ApplicationEvent extends Event {

    private ProcessEventType processEventType;
    private String reason;

    public ApplicationEvent() {
        super();
    }

    public ProcessEventType getProcessEventType() {
        return processEventType;
    }

    public String getReason() {
        return reason;
    }

    public ApplicationEvent(AggregateBase aggregateBase, ProcessEventType processEventType, String reason) {
        super(aggregateBase.getId(), aggregateBase.getDomainEntityType().getType());
        this.processEventType = processEventType;
        this.reason = reason;
    }
}
