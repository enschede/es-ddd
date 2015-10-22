package nl.marcenschede.tests.elastic.domain.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.marcenschede.tests.elastic.base.command.CommandHandler;
import nl.marcenschede.tests.elastic.base.repository.IdNotNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferCommandHandler implements CommandHandler<OrderFromOfferCommand> {

    @Autowired
    private OrderFactory orderFactory;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void execute(OrderFromOfferCommand command) {
        Offer offer = command.getOffer();

        Order order = orderFactory.newOrder(offer.getNaam());
        try {
            orderRepository.create(order);
        } catch (IdNotNullException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
