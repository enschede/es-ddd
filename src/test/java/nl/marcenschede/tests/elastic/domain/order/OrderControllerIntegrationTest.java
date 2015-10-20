package nl.marcenschede.tests.elastic.domain.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.marcenschede.tests.elastic.App;
import nl.marcenschede.tests.elastic.base.events.Event;
import nl.marcenschede.tests.elastic.base.repository.IdEqualsNullException;
import nl.marcenschede.tests.elastic.base.repository.IdNotNullException;
import org.junit.After;
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

    @Test
    public void shouldCreateTwoEntities() throws IOException, IdNotNullException, IdEqualsNullException {

        Order kitty = new Order();
        kitty.setNaam("Kitty Enschede");
        kitty = orderRepository.create(kitty);

        assertThat(kitty.getId(), notNullValue());

        Order marc = new Order();
        marc.setNaam("Marc Enschede");
        orderRepository.create(marc);

        assertThat(marc.getId(), notNullValue());
        assertThat(kitty.getId(), not(is(marc.getId())));

        Order actualRecord = orderRepository.findById(kitty.getId());

        assertThat(actualRecord.getNaam(), is("Kitty Enschede"));
        assertThat(actualRecord.getId(), notNullValue());
    }

    @Test
    public void shouldCreateEvents() throws JsonProcessingException, IdNotNullException, IdEqualsNullException {

        Order kitty = new Order();
        kitty.setNaam("Kitty Enschede");
        kitty = orderRepository.create(kitty);

        kitty.setNaam("Kitty Enschede");
        orderRepository.update(kitty);

        List<Event> events = orderRepository.getRelatedEvents(kitty.getId());

        assertThat(events.size(), is(2));
    }

    @Test
    public void shouldBeFoundById() throws IOException, IdNotNullException, IdEqualsNullException {

        Order kitty = new Order();
        kitty.setNaam("Kitty Enschede");
        kitty = orderRepository.create(kitty);

        Order actualOrder = orderRepository.findById(kitty.getId());

        assertThat(actualOrder, notNullValue());
        assertThat(actualOrder.getNaam(), is("Kitty Enschede"));
    }

    @After
    public void after() throws ExecutionException, InterruptedException {
        orderRepository.resetIndex();
    }

}