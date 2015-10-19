package nl.marcenschede.tests.elastic.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.marcenschede.tests.elastic.base.repository.IdEqualsNullException;
import nl.marcenschede.tests.elastic.base.repository.IdNotNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping("/json")
    @ResponseBody
    public String storeOrder() throws JsonProcessingException, IdNotNullException, IdEqualsNullException {

        Order order = new Order();
        order.setNaam("Jansen");

        order = orderRepository.create(order);

        order.setNaam("Enschede");
        orderRepository.update(order);

        return order.toString();
    }


}
