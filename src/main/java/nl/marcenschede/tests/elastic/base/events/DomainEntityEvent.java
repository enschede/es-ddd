package nl.marcenschede.tests.elastic.base.events;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;

public class DomainEntityEvent extends Event {

    private DomainEntityEventType domainEntityEventType;
    private DomainEntity domainEntity;
    private String reason;

    public DomainEntityEvent() {
        super();
    }

    public DomainEntityEvent(DomainEntityEventType domainEntityEventType, DomainEntity domainEntity, String reason) {
        super(domainEntity.getId(), domainEntity.getDomainEntityType().getType());

        this.domainEntityEventType = domainEntityEventType;
        this.domainEntity = domainEntity;
        this.reason = reason;
    }

    public DomainEntityEventType getDomainEntityEventType() {
        return domainEntityEventType;
    }

    public DomainEntity getDomainEntity() {
        return domainEntity;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "DomainEntityEvent{" +
                "domainEntityEventType=" + domainEntityEventType +
                ", domainEntity=" + domainEntity +
                ", reason='" + reason + '\'' +
                ", event='" + super.toString() + '\'' +
                '}';
    }
}
