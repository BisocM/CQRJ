package bisocm.cqrtest.core;

/**
 * Abstract handler for a specific command type.
 * @param <T> the type of command this handler processes
 */
public abstract class CommandHandler<T extends Command> {
    public abstract void handle(T command);
}