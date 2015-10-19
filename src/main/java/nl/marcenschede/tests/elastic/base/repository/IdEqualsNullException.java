package nl.marcenschede.tests.elastic.base.repository;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;

/**
 * Created by marc on 19/10/15.
 */
public class IdEqualsNullException extends Throwable {

    private DomainEntity domainEntity;

    public <T extends DomainEntity> IdEqualsNullException(T t) {
        this.domainEntity = t;
    }
}
