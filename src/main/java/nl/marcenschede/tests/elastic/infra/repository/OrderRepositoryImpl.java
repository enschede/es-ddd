package nl.marcenschede.tests.elastic.infra.repository;

import nl.marcenschede.tests.elastic.base.domains.AggregateType;
import nl.marcenschede.tests.elastic.base.repository.BaseRepositoryImpl;
import nl.marcenschede.tests.elastic.domain.order.Order;
import nl.marcenschede.tests.elastic.domain.order.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImpl extends BaseRepositoryImpl<Order> implements OrderRepository {

    @Override
    protected AggregateType getType() {
        return AggregateType.ORDER;
    }
}
