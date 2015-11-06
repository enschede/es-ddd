package nl.marcenschede.tests.cqrs2.invoice;

import nl.marcenschede.tests.App;
import nl.marcenschede.tests.cqrs2.base.Bus;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class InvoiceIntgrationTest {

    @Autowired
    private Bus bus;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Before
    public void before() throws ExecutionException, InterruptedException {
        invoiceRepository.deleteIndex();
    }

    @Test
    public void shouldCreate() {
        UUID expectedUuid = UUID.randomUUID();

        CreateInvoiceCommand command = new CreateInvoiceCommand(expectedUuid, "Marc", "Order12345");
        bus.process(command);

        InvoiceSetNameCommand cmd2 = new InvoiceSetNameCommand(expectedUuid, "Marc Enschede");
        bus.process(cmd2);

        Invoice actualInvoice = invoiceRepository.loadFromHistory(expectedUuid);

        assertThat(actualInvoice.getNaam(), Is.is("Marc Enschede"));

        while(true);
    }


}