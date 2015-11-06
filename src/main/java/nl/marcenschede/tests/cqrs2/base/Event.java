package nl.marcenschede.tests.cqrs2.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceCreatedEvent;
import nl.marcenschede.tests.cqrs2.invoice.InvoiceSetNameEvent;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Created by marc on 27/10/15.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventClass")
@JsonSubTypes({
        @JsonSubTypes.Type(value = InvoiceCreatedEvent.class, name = "InvoiceCreatedEvent"),
        @JsonSubTypes.Type(value = InvoiceSetNameEvent.class, name = "InvoiceSetNameEvent")})
public abstract class Event {

    private ZonedDateTime timestamp = ZonedDateTime.now(ZoneOffset.UTC);

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
