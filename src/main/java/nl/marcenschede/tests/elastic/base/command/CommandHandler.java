package nl.marcenschede.tests.elastic.base.command;

/**
 * Created by marc on 22/10/15.
 */
public interface CommandHandler<T extends Command> {

    void execute(T command);
}
