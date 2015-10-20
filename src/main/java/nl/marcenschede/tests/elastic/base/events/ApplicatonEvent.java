package nl.marcenschede.tests.elastic.base.events;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;

public class ApplicatonEvent extends Event {

    private ProcessEventType processEventType;
    private String reason;

    public ApplicatonEvent() {
        super();
    }

    public ApplicatonEvent(DomainEntity domainEntity, ProcessEventType processEventType, String reason) {
        super(domainEntity.getId(), domainEntity.getDomainEntityType().getType());
        this.processEventType = processEventType;
        this.reason = reason;
    }
}
