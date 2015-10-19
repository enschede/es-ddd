package nl.marcenschede.tests.elastic.base.events;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;

public class DomainEntityEvent extends Event {

    private DomainEntityEventType domainEntityEventType;
    private String entityId;
    private String entityClass;
    private DomainEntity domainEntity;
    private String reason;

    public DomainEntityEvent(DomainEntityEventType domainEntityEventType, DomainEntity domainEntity, String reason) {
        super(EventClass.DOMAIN_EVENT_CLASS);

        this.entityId = domainEntity.getId();
        this.entityClass = domainEntity.getDomainEntityType().getType();

        this.domainEntityEventType = domainEntityEventType;
        this.domainEntity = domainEntity;
        this.reason = reason;
    }

    public DomainEntityEventType getDomainEntityEventType() {
        return domainEntityEventType;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public DomainEntity getDomainEntity() {
        return domainEntity;
    }

    public String getReason() {
        return reason;
    }
}
