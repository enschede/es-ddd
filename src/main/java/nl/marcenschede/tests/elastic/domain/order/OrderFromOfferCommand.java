package nl.marcenschede.tests.elastic.domain.order;

import nl.marcenschede.tests.elastic.base.command.Command;
import nl.marcenschede.tests.elastic.base.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderFromOfferCommand implements Command {

    private final Offer offer;

    @Autowired
    private CommandHandler<OrderFromOfferCommand> handler;

    public OrderFromOfferCommand(Offer offer) {
        this.offer = offer;
    }

    @Override
    public void execute() {
        handler.execute(this);
    }

    public Offer getOffer() {
        return offer;
    }
}
