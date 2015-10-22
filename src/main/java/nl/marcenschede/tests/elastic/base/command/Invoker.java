package nl.marcenschede.tests.elastic.base.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class Invoker {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    public void executeCommand(Command command) {
        beanFactory.autowireBean(command);

        command.execute();
    }
}
