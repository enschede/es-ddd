package nl.marcenschede.tests.cqrs2;

import java.util.UUID;

/**
 * Created by marc on 27/10/15.
 */
public class InvoiceSetNameCommand implements Command {

    private UUID uuid;
    private String name;

    public InvoiceSetNameCommand(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
