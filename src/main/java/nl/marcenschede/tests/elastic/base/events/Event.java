package nl.marcenschede.tests.elastic.base.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Properties;

/**
 * Created by marc on 19/10/15.
 */
public abstract class Event implements Serializable {

    private LocalDateTime timestamp;
    private String user;
    private EventClass eventClass;
    private Properties properties;

    public Event(EventClass eventClass) {
        timestamp = LocalDateTime.now();
        user = "unknown";
        this.eventClass = eventClass;
        this.properties = new Properties();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public EventClass getEventClass() {
        return eventClass;
    }

    public Properties getProperties() {
        return properties;
    }
}
