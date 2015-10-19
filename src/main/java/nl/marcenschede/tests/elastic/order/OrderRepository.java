package nl.marcenschede.tests.elastic.order;

import nl.marcenschede.tests.elastic.base.repository.ElasticRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderRepository extends ElasticRepository<Order> {
}
