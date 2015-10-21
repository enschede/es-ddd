package nl.marcenschede.tests.elastic.domain.factuur;

import nl.marcenschede.tests.elastic.base.domains.AggregateBase;
import nl.marcenschede.tests.elastic.base.domains.AggregateType;

public class Factuur extends AggregateBase {

    @Override
    public AggregateType getDomainEntityType() {
        return AggregateType.FACTUUR;
    }
}
