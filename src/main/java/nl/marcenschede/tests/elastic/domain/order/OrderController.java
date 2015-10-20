package nl.marcenschede.tests.elastic.domain.order;

import nl.marcenschede.tests.elastic.base.events.Event;
import nl.marcenschede.tests.elastic.base.repository.IdEqualsNullException;
import nl.marcenschede.tests.elastic.base.repository.IdNotNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping("/json")
    @ResponseBody
    public String storeOrder() throws IOException, IdNotNullException, IdEqualsNullException, ExecutionException, InterruptedException {

        Order kitty = new Order();
        kitty.setNaam("Kitty de Jonge");
        kitty = orderRepository.create(kitty);

        Order marc = new Order();
        marc.setNaam("Marc Enschede");
        orderRepository.create(marc);

        Order ties = new Order();
        ties.setNaam("Ties Wonink");
        orderRepository.create(ties);

        kitty.setNaam("Kitty Enschede");
        orderRepository.update(kitty);

        Order kitty1 = orderRepository.findById(kitty.getId());

        List<Event> events = orderRepository.getRelatedEvents(kitty.getId());

        Properties search = new Properties();
        search.setProperty("naam", "Marc Enschede");
        List<Order> orders = orderRepository.findByCriteria(search);

        return orders.toString();
    }

}
