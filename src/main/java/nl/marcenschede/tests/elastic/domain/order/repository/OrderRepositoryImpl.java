package nl.marcenschede.tests.elastic.domain.order.repository;

import nl.marcenschede.tests.elastic.base.domains.DomainEntityType;
import nl.marcenschede.tests.elastic.base.repository.ElasticRepository;
import nl.marcenschede.tests.elastic.domain.order.Order;
import nl.marcenschede.tests.elastic.domain.order.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImpl extends ElasticRepository<Order> implements OrderRepository {

    @Override
    protected DomainEntityType getType() {
        return DomainEntityType.ORDER;
    }
}
