package nl.marcenschede.tests.elastic.base.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.marcenschede.tests.elastic.ESConstants;
import nl.marcenschede.tests.elastic.base.events.Event;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventRepository {

    static final boolean ELASTIC_SEARCH_REFRESH = true;

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper objectMapper;

    public void create(Event event) throws JsonProcessingException {
        String type = "event";
        String index = ESConstants.ES_INDEX_ALIAS_NAME;
        byte[] entityAsByteStream = objectMapper.writeValueAsBytes(event);

        client.prepareIndex(index, type).setSource(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get();
    }
}
