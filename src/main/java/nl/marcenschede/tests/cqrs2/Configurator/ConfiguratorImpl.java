package nl.marcenschede.tests.cqrs2.Configurator;

import nl.marcenschede.tests.cqrs2.base.Configurator;
import org.springframework.stereotype.Component;

/**
 * Created by marc on 06/11/15.
 */
@Component
public class ConfiguratorImpl implements Configurator {
    private static final String INDEX_NAME = "cqrs";

    @Override
    public String getIndexName() {
        return INDEX_NAME;
    }
}
