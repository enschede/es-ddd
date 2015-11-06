package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.App;
import nl.marcenschede.tests.cqrs2.base.Bus;
import nl.marcenschede.tests.cqrs2.invoice.invoicedetails.InvoiceDetails;
import nl.marcenschede.tests.elastic.base.repository.IdEqualsNullException;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class InvoiceIntgrationTest {

    @Autowired
    private Bus bus;

    @Autowired
    private InvoiceEventRepository invoiceEventRepository;

    @Autowired
    private InvoiceDtoRepository invoiceDtoRepository;

    @Before
    public void before() throws ExecutionException, InterruptedException {
        invoiceEventRepository.deleteIndex();
    }

    @Test
    public void shouldCreate() throws IOException, IdEqualsNullException {
        UUID expectedUuid = UUID.randomUUID();

        CreateInvoiceCommand command = new CreateInvoiceCommand(expectedUuid, "Marc", "Order12345");
        bus.process(command);

        InvoiceSetNameCommand cmd2 = new InvoiceSetNameCommand(expectedUuid, "Marc Enschede");
        bus.process(cmd2);

        Invoice actualInvoice = invoiceEventRepository.loadFromHistory(expectedUuid);

        assertThat(actualInvoice.getNaam(), Is.is("Marc Enschede"));
        assertThat(actualInvoice.getOrderRef(), Is.is("Order12345"));

        InvoiceDetails dtoInvoice = invoiceDtoRepository.findById(expectedUuid.toString());

        assertThat(dtoInvoice.getNaam(), Matchers.is("Marc"));

        while(true);
    }


}