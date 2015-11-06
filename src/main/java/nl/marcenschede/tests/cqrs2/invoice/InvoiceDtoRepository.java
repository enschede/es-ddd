package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.DtoRepository;
import nl.marcenschede.tests.cqrs2.invoice.invoicedetails.InvoiceDetails;
import org.springframework.stereotype.Component;

/**
 * Created by marc on 06/11/15.
 */
@Component
public class InvoiceDtoRepository extends DtoRepository<InvoiceDetails> {

    @Override
    protected Class getDtoEntityTypeClass() {
        return InvoiceDetails.class;
    }

    @Override
    protected String getDtoEntityTypeName() {
        return "invoicedetails";
    }
}
