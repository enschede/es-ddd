package nl.marcenschede.tests.elastic.base.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Properties;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventClass")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DomainEntityEvent.class, name = "DomainEntityEvent"),
        @JsonSubTypes.Type(value = ApplicatonEvent.class, name = "ApplicatonEvent")})
public abstract class Event implements Serializable {

    private LocalDateTime timestamp;
    private String user;
    private String entityId;
    private String entityClass;
    private Properties properties;

    public Event() {
    }

    public Event(String entityId, String domainEntityType) {
        timestamp = LocalDateTime.now();
        user = "unknown";
        this.properties = new Properties();

        this.entityId = entityId;
        this.entityClass = domainEntityType;

    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getEntityClass() {
        return entityClass;
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                ", user='" + user + '\'' +
                ", entityId='" + entityId + '\'' +
                ", entityClass='" + entityClass + '\'' +
                ", properties=" + properties +
                '}';
    }
}
