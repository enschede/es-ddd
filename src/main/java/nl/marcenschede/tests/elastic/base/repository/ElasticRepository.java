package nl.marcenschede.tests.elastic.base.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.marcenschede.tests.elastic.ESConstants;
import nl.marcenschede.tests.elastic.base.domains.DomainEntity;
import nl.marcenschede.tests.elastic.base.domains.DomainEntityType;
import nl.marcenschede.tests.elastic.base.events.DomainEntityEvent;
import nl.marcenschede.tests.elastic.base.events.DomainEntityEventType;
import nl.marcenschede.tests.elastic.base.events.Event;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
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

public abstract class ElasticRepository<T extends DomainEntity> implements ESR<T> {

    static final boolean ELASTIC_SEARCH_REFRESH = true;

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    public T create(T t) throws IdNotNullException, JsonProcessingException {
        throwExceptionIfIdNotNull(t);

        String type = t.getDomainEntityType().getType();
        String index = ESConstants.ES_INDEX_ALIAS_NAME;
        byte[] entityAsByteStream = objectMapper.writeValueAsBytes(t);

        IndexRequestBuilder result = client.prepareIndex(index, type).setSource(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH);
        t.setId(result.get().getId());

        byte[] entityAsByteStreamForUpdate = objectMapper.writeValueAsBytes(t);
        client.prepareUpdate(index, type, t.getId()).setDoc(entityAsByteStreamForUpdate).setRefresh(ELASTIC_SEARCH_REFRESH).get();

        DomainEntityEvent domainEntityEvent = new DomainEntityEvent(DomainEntityEventType.CREATE, t, "");
        byte[] eventAsByteStream = objectMapper.writeValueAsBytes(domainEntityEvent);
        client.prepareIndex(index, "event").setSource(eventAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get().getId();

        return t;
    }

    private void throwExceptionIfIdNotNull(T t) throws IdNotNullException {
        if (t.getId() != null)
            throw new IdNotNullException(t);
    }

    public T update(T t) throws IdEqualsNullException, IdNotNullException, JsonProcessingException {
        throwExceptionIfIdEqualsNull(t);

        String type = t.getDomainEntityType().getType();
        String index = ESConstants.ES_INDEX_ALIAS_NAME;
        byte[] entityAsByteStream = objectMapper.writeValueAsBytes(t);

        client.prepareUpdate(index, type, t.getId()).setDoc(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get();

        DomainEntityEvent domainEntityEvent = new DomainEntityEvent(DomainEntityEventType.UPDATE, t, "");
        byte[] eventAsByteStream = objectMapper.writeValueAsBytes(domainEntityEvent);
        client.prepareIndex(index, "event").setSource(eventAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get().getId();

        return t;
    }

    private void throwExceptionIfIdEqualsNull(T t) throws IdNotNullException, IdEqualsNullException {
        if (t.getId() == null)
            throw new IdEqualsNullException(t);
    }

    public T findById(String id) throws IOException, IdEqualsNullException {
        throwExceptionIfNull(id);

        String index = ESConstants.ES_INDEX_ALIAS_NAME;

        GetResponse response = client.prepareGet(index, DomainEntityType.ORDER.getType(), id).get();
        byte[] responseSourceAsBytes = response.getSourceAsBytes();

        T result = objectMapper.readValue(responseSourceAsBytes, (Class<T>) getType().getClazz());
        beanFactory.autowireBean(result);

        return result;
    }

    private void throwExceptionIfNull(String id) throws IdEqualsNullException {
        if (id == null)
            throw new IdEqualsNullException(null);
    }

    public List<T> findByCriteria(Properties searchProperties) throws ExecutionException, InterruptedException {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();

        Set<Map.Entry<Object, Object>> searchElements = searchProperties.entrySet();
        for(Map.Entry<Object, Object> searchElement : searchElements) {
            queryBuilder.must(new MatchQueryBuilder((String)searchElement.getKey(), searchElement.getValue()));
        }

        SearchResponse searchResponse = client.prepareSearch()
                .setTypes(getType().getType())
                .setQuery(queryBuilder)
                .setSearchType(SearchType.SCAN).setScroll(new TimeValue(60, TimeUnit.SECONDS))
                .setSize(100)
                .execute().actionGet();

        List<T> results = new ArrayList<T>();

        while(true) {
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60, TimeUnit.SECONDS)).get();

            SearchHit[] hits = searchResponse.getHits().getHits();
            List<SearchHit> searchHits = Arrays.asList(hits);

            if(hits.length==0)
                break;

            for (SearchHit searchHit : searchHits) {
                String source = searchHit.getSourceAsString();

                T domainItem = null;
                try {
                    domainItem = (T) objectMapper.readValue(source, (Class<T>) getType().getClazz());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                beanFactory.autowireBean(domainItem);

                results.add(domainItem);
            }
        }

        return results;
    }

    public List<Event> getRelatedEvents(String domainEntityId) {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(new MatchQueryBuilder("entityClass", getType().getType()));
        queryBuilder.must(new MatchQueryBuilder("entityId", domainEntityId));

        SearchResponse searchResponse = client.prepareSearch()
                .setTypes("event")
                .setQuery(queryBuilder)
                .setSearchType(SearchType.SCAN).setScroll(new TimeValue(60, TimeUnit.SECONDS))
                .setSize(100)
                .execute().actionGet();

        List<Event> results = new ArrayList<Event>();

        while(true) {
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60, TimeUnit.SECONDS)).get();

            SearchHit[] hits = searchResponse.getHits().getHits();
            List<SearchHit> searchHits = Arrays.asList(hits);

            if(hits.length==0)
                break;

            for (SearchHit searchHit : searchHits) {
                String source = searchHit.getSourceAsString();

                Event event = null;
                try {
                    event = (Event) objectMapper.readValue(source, Event.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                results.add(event);
            }
        }

        return results;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void resetIndex() throws ExecutionException, InterruptedException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();

        deleteIndexRequest.indices(ESConstants.ES_INDEX_ALIAS_NAME);

        client.admin().indices().delete(deleteIndexRequest).get();
    }

    protected abstract DomainEntityType getType();
}
