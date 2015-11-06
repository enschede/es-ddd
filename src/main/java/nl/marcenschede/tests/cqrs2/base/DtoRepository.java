package nl.marcenschede.tests.cqrs2.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by marc on 27/10/15.
 */
@Component
public abstract class DtoRepository<T extends DtoObject> {

    static final boolean ELASTIC_SEARCH_REFRESH = true;

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Configurator configurator;

    public void storeDto(T t) {
        String type = getDtoEntityTypeName();
        byte[] entityAsByteStream = new byte[0];
        try {
            entityAsByteStream = objectMapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        client.prepareIndex(configurator.getIndexName(), type).setSource(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get();
    }

    public void update(T t) throws JsonProcessingException {
        String index = configurator.getIndexName();

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(new MatchQueryBuilder("uuid", t.getUuid().toString()));

        SearchResponse searchResponse = client.prepareSearch()
                .setTypes(getDtoEntityTypeName())
                .setQuery(queryBuilder)
                .setSearchType(SearchType.SCAN).setScroll(new TimeValue(60, TimeUnit.SECONDS))
                .setSize(100)
                .execute().actionGet();

        searchResponse = client.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60, TimeUnit.SECONDS)).get();

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchHit> searchHits = Arrays.asList(hits);

        if(searchHits.size()==1) {
            String id = searchHits.get(0).getId();

            byte[] entityAsByteStream = objectMapper.writeValueAsBytes(t);

            client.prepareUpdate(index, getDtoEntityTypeName(), id).setDoc(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get();
        }
    }



    public Optional<T> findById(String id) throws IOException {
        Properties searchCriteria = new Properties();
        searchCriteria.setProperty("uuid", id);

        List<T> result = findByCriteria(searchCriteria);

        return result.size()>0 ? Optional.of(result.get(0)) : Optional.empty();
    }

    public List<T> findByCriteria(Properties searchCriteria) throws IOException {
        String index = configurator.getIndexName();

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        searchCriteria.stringPropertyNames().stream().forEach(name -> queryBuilder.must(new MatchQueryBuilder(name, searchCriteria.getProperty(name))));

        SearchResponse searchResponse = client.prepareSearch()
                .setTypes(getDtoEntityTypeName())
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
                    domainItem = (T) objectMapper.readValue(source, getDtoEntityTypeClass());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                results.add(domainItem);
            }
        }

        return results;
    }

    protected abstract Class getDtoEntityTypeClass();

    protected abstract String getDtoEntityTypeName();

}
