package nl.marcenschede.tests.elastic.domain.factuur;

import nl.marcenschede.tests.elastic.base.domains.DomainEntity;
import nl.marcenschede.tests.elastic.base.domains.DomainEntityType;

public class Factuur extends DomainEntity {



    @Override
    public DomainEntityType getDomainEntityType() {
        return DomainEntityType.FACTUUR;
    }
}
