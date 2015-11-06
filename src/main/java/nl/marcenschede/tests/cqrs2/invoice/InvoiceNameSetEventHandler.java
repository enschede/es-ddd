package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.cqrs2.base.Bus;
import nl.marcenschede.tests.cqrs2.base.Event;
import nl.marcenschede.tests.cqrs2.base.EventHandler;
import nl.marcenschede.tests.cqrs2.invoice.invoicedetails.InvoiceDetails;
import nl.marcenschede.tests.elastic.base.repository.IdEqualsNullException;
import nl.marcenschede.tests.elastic.base.repository.IdNotNullException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by marc on 06/11/15.
 */
public class InvoiceNameSetEventHandler implements EventHandler {

    @Autowired
    private InvoiceDtoRepository invoiceDtoRepository;

    @Override
    public void process(Event event, Bus bus) {
        InvoiceNameSetEvent invoiceNameSetEvent = (InvoiceNameSetEvent) event;

        try {
            Optional<InvoiceDetails> invoiceDetailsOptional =
                    invoiceDtoRepository.findById(invoiceNameSetEvent.getUuid().toString());

            if(invoiceDetailsOptional.isPresent()) {

                invoiceDetailsOptional.get().setNaam(invoiceNameSetEvent.getNaam());

                invoiceDtoRepository.update(invoiceDetailsOptional.get());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IdEqualsNullException e) {
            e.printStackTrace();
        } catch (IdNotNullException e) {
            e.printStackTrace();
        }
    }
}
