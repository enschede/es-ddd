package nl.marcenschede.tests.elastic.base.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.marcenschede.tests.elastic.base.ESConstants;
import nl.marcenschede.tests.elastic.base.domains.DomainEntity;
import nl.marcenschede.tests.elastic.base.events.DomainEntityEvent;
import nl.marcenschede.tests.elastic.base.events.DomainEntityEventType;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ElasticRepository<T extends DomainEntity> {

    static final boolean ELASTIC_SEARCH_REFRESH = true;

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper objectMapper;

    public T create(T t) throws IdNotNullException, JsonProcessingException {
        throwExceptionIfIdNotNull(t);

        String type = t.getDomainEntityType().getType();
        String index = ESConstants.ES_INDEX_ALIAS_NAME;
        byte[] entityAsByteStream = objectMapper.writeValueAsBytes(t);

        IndexRequestBuilder result = client.prepareIndex(index, type).setSource(entityAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH);
        t.setId(result.get().getId());

        DomainEntityEvent domainEntityEvent = new DomainEntityEvent(DomainEntityEventType.CREATE, t, "");
        byte[] eventAsByteStream = objectMapper.writeValueAsBytes(domainEntityEvent);
        client.prepareIndex(index, "event").setSource(eventAsByteStream).setRefresh(ELASTIC_SEARCH_REFRESH).get().getId();

        return t;
    }

    private void throwExceptionIfIdNotNull(T t) throws IdNotNullException {
        if(t.getId()!=null)
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
        if(t.getId()==null)
            throw new IdEqualsNullException(t);
    }

}
