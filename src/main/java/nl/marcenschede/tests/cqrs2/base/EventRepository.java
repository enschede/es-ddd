package nl.marcenschede.tests.cqrs2.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by marc on 27/10/15.
 */
public abstract class EventRepository<T extends AggregateRoot> {

    static final boolean ELASTIC_SEARCH_REFRESH = true;
    static final String EVENT_TYPE_ID = "event";

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private CqrsConfigurator cqrsConfigurator;

    public void storeEvent(T t) {
        UUID uuid = t.getUuid();

        t.getUncommittedChanges().stream().forEach(event -> {
            storeSingleEvent(event);
        });

        t.markChangesAsCommitted();
    }

    private void storeSingleEvent(Event event) {
        byte[] entityAsByteStream = new byte[0];
        try {
            entityAsByteStream = objectMapper.writeValueAsBytes(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        client.prepareIndex(cqrsConfigurator.getIndexName(), EVENT_TYPE_ID).setSource(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get();

    }

    public T loadFromHistory(UUID uuid) {
        List<Event> events = loadEvents(uuid);

        T t = getEmptyObject(uuid);

        events.stream().forEach(new Consumer<Event>() {
            @Override
            public void accept(Event event) {
                t.applyChange(event, false);
            }
        });

        beanFactory.autowireBean(t);

        return t;
    }

    private List<Event> loadEvents(UUID uuid) {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(new MatchQueryBuilder("uuid", uuid));

        SearchResponse searchResponse = client.prepareSearch()
                .setTypes(EVENT_TYPE_ID)
                .setQuery(queryBuilder)
                .setSearchType(SearchType.SCAN).setScroll(new TimeValue(60, TimeUnit.SECONDS))
                .setSize(100)
                .execute().actionGet();

        GetResponse response = client.prepareGet(cqrsConfigurator.getIndexName(), EVENT_TYPE_ID, uuid.toString()).get();

        List<Event> results = new ArrayList<>();

        while(true) {
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60, TimeUnit.SECONDS)).get();

            SearchHit[] hits = searchResponse.getHits().getHits();
            List<SearchHit> searchHits = Arrays.asList(hits);

            if(hits.length==0)
                break;

            for (SearchHit searchHit : searchHits) {
                String source = searchHit.getSourceAsString();

                Event domainItem = null;
                try {
                    domainItem = (Event) objectMapper.readValue(source, Event.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                results.add(domainItem);
            }
        }

        Collections.sort(results, (o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));

        return results;
    }

    protected abstract T getEmptyObject(UUID uuid);

    public void deleteIndex() {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();

        deleteIndexRequest.indices(cqrsConfigurator.getIndexName());

        try {
            client.admin().indices().delete(deleteIndexRequest).get();
        } catch (ExecutionException | InterruptedException ee) {
        }

    }

    public void storeDto(DtoObject t) {
        String type = t.getDtoEntityType();
        byte[] entityAsByteStream = new byte[0];
        try {
            entityAsByteStream = objectMapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        client.prepareIndex(cqrsConfigurator.getIndexName(), type).setSource(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get();
    }

}
