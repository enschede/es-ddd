package nl.marcenschede.tests.elastic.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderFactory {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    public Order newOrder(String naam) {
        Order order = new Order();
        order.setNaam(naam);

        beanFactory.autowireBean(order);

        return order;
    }

    public OrderLine newOrderLine(String omschrijving, Integer aantal, BigDecimal prijsPerStuk) {
        OrderLine orderLine = new OrderLine(omschrijving, aantal, prijsPerStuk);

        return orderLine;
    }

}
