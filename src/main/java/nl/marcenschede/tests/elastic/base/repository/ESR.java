package nl.marcenschede.tests.elastic.base.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.marcenschede.tests.elastic.base.events.Event;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public interface ESR<T> {

    public T create(T t) throws IdNotNullException, JsonProcessingException;

    public T update(T t) throws IdEqualsNullException, IdNotNullException, JsonProcessingException;

    public T findById(String id) throws IOException, IdEqualsNullException;

    public List<T> findByCriteria(Properties searchProperties) throws ExecutionException, InterruptedException;

    public List<Event> getRelatedEvents(String domainEntityId);

    public Client getClient();

    public void resetIndex() throws ExecutionException, InterruptedException;
}
