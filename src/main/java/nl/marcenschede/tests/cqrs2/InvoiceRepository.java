package nl.marcenschede.tests.cqrs2;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InvoiceRepository extends Repository<Invoice> {
    @Override
    protected Invoice getEmptyObject(UUID uuid) {
        return new Invoice(uuid);
    }
}
