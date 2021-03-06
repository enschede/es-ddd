package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.App;
import nl.marcenschede.tests.cqrs2.base.Bus;
import nl.marcenschede.tests.cqrs2.invoice.invoicedetails.InvoiceDetails;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Optional;
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
    public void shouldCreate() throws IOException {
        UUID expectedUuid = UUID.randomUUID();

        CreateInvoiceCommand command = new CreateInvoiceCommand(expectedUuid, "Marc", "Order12345");
        bus.process(command);

        InvoiceSetNameCommand cmd2 = new InvoiceSetNameCommand(expectedUuid, "Marc Enschede");
        bus.process(cmd2);

        Invoice actualInvoice = invoiceEventRepository.loadFromHistory(expectedUuid);

        assertThat(actualInvoice.getNaam(), Matchers.is("Marc Enschede"));
        assertThat(actualInvoice.getOrderRef(), Matchers.is("Order12345"));

        Optional<InvoiceDetails> dtoInvoice = invoiceDtoRepository.findById(expectedUuid.toString());

        assertThat(dtoInvoice.get().getNaam(), Matchers.is("Marc Enschede"));

//        while(true);
    }


}