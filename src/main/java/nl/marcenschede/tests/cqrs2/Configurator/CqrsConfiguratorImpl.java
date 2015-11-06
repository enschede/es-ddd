package nl.marcenschede.tests.cqrs2.Configurator;

import nl.marcenschede.tests.cqrs2.base.CqrsConfigurator;
import org.springframework.stereotype.Component;

/**
 * Created by marc on 06/11/15.
 */
@Component
public class CqrsConfiguratorImpl implements CqrsConfigurator {
    private static final String INDEX_NAME = "cqrs";

    @Override
    public String getIndexName() {
        return INDEX_NAME;
    }
}
