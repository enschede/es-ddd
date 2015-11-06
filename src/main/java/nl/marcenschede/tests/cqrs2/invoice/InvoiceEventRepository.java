package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.EventRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InvoiceEventRepository extends EventRepository<Invoice> {
    @Override
    protected Invoice getEmptyObject(UUID uuid) {
        return new Invoice(uuid);
    }

}
