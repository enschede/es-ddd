package nl.marcenschede.tests.elastic.base.repository;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;

public class IdEqualsNullException extends Throwable {

    private DomainEntity domainEntity;

    public <T extends DomainEntity> IdEqualsNullException(T t) {
        this.domainEntity = t;
    }
}
