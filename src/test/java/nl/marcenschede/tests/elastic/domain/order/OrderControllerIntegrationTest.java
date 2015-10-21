package nl.marcenschede.tests.elastic.domain.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.marcenschede.tests.elastic.App;
import nl.marcenschede.tests.elastic.base.events.ApplicationEvent;
import nl.marcenschede.tests.elastic.base.events.Event;
import nl.marcenschede.tests.elastic.base.events.ProcessEventType;
import nl.marcenschede.tests.elastic.base.repository.EventRepository;
import nl.marcenschede.tests.elastic.base.repository.IdEqualsNullException;
import nl.marcenschede.tests.elastic.base.repository.IdNotNullException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class OrderControllerIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrderFactory orderFactory;

    @Before
    public void before() throws ExecutionException, InterruptedException {
        orderRepository.resetIndex();
    }

    @Test
    public void shouldCreateTwoEntities() throws IOException, IdNotNullException, IdEqualsNullException {

        Order kitty = orderFactory.newOrder("Kitty Enschede");
        kitty = orderRepository.create(kitty);

        assertThat(kitty.getId(), notNullValue());

        Order marc = orderFactory.newOrder("Marc Enschede");
        orderRepository.create(marc);

        assertThat(marc.getId(), notNullValue());
        assertThat(kitty.getId(), not(is(marc.getId())));

        Order actualRecord = orderRepository.findById(kitty.getId());

        assertThat(actualRecord.getNaam(), is("Kitty Enschede"));
        assertThat(actualRecord.getId(), notNullValue());
    }

    @Test
    public void shouldUpdateAggregate() throws JsonProcessingException, IdNotNullException, IdEqualsNullException {

        Order kitty = orderFactory.newOrder("Kitty de Jonge");
        kitty = orderRepository.create(kitty);

        kitty.setNaam("Kitty Enschede");
        orderRepository.update(kitty);

        List<Event> events = orderRepository.getRelatedEvents(kitty.getId());

        assertThat(events.size(), is(2));
        assertThat(events.get(0).getEntityClass(), is("order"));
        assertThat(events.get(0).getEntityId(), is(kitty.getId()));
    }

    @Test
    public void shouldCreateThreeEvents() throws JsonProcessingException, IdNotNullException, IdEqualsNullException {

        Order kitty = orderFactory.newOrder("Kitty de Jonge");
        kitty = orderRepository.create(kitty);      // Create first event

        kitty.setNaam("Kitty Enschede");
        orderRepository.update(kitty);              // Create second event

        ApplicationEvent applicationEvent = new ApplicationEvent(kitty, ProcessEventType.NO_OPS, "Test reason");
        eventRepository.create(applicationEvent);   // Create application event

        List<Event> events = orderRepository.getRelatedEvents(kitty.getId());

        assertThat(events.size(), is(3));
        assertThat(events.get(0).getEntityClass(), is("order"));
        assertThat(events.get(0).getEntityId(), is(kitty.getId()));
    }

    @Test
    public void shouldBeFoundById() throws IOException, IdNotNullException, IdEqualsNullException {

        Order kitty = orderFactory.newOrder("Kitty Enschede");
        kitty = orderRepository.create(kitty);

        Order actualOrder = orderRepository.findById(kitty.getId());

        assertThat(actualOrder, notNullValue());
        assertThat(actualOrder.getNaam(), is("Kitty Enschede"));
    }

}