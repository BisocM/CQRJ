package bisocm.cqrtest.core;

import bisocm.cqrtest.annotations.CommandAttribute;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CommandRegistry {
    private final Map<Class<? extends Command>, CommandHandler<? extends Command>> handlers = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(CommandRegistry.class.getName());
    private final Set<Class<? extends Command>> commands;

    public CommandRegistry(String packageName) {
        commands = loadCommands(packageName);
        loadCommandsAndHandlers(packageName);
    }

    private Set<Class<? extends Command>> loadCommands(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(CommandAttribute.class)
                .stream()
                .filter(Command.class::isAssignableFrom)
                .map(cls -> (Class<? extends Command>) cls)
                .collect(Collectors.toSet());
    }

    private void loadCommandsAndHandlers(String packageName) {
        Reflections reflections = new Reflections(packageName);

        //Scan and register commands and their handlers
        Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(CommandAttribute.class);
        Set<Class<? extends CommandHandler<?>>> handlerClasses = (Set<Class<? extends CommandHandler<?>>>) (Set<?>) reflections.getSubTypesOf(CommandHandler.class);

        for (Class<? extends CommandHandler<?>> handlerClass : handlerClasses) {
            try {
                CommandHandler<?> handlerInstance = handlerClass.getDeclaredConstructor().newInstance();
                Class<? extends Command> commandClass = getCommandType(handlerClass);

                if (commandClass != null && commandClasses.contains(commandClass)) {
                    handlers.put(commandClass, handlerInstance);
                    LOGGER.log(Level.INFO, "Registered handler for command: {0}", commandClass.getSimpleName());
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to register handler: " + handlerClass.getName(), e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Command> getCommandType(Class<? extends CommandHandler<?>> handlerClass) {
        try {
            return (Class<? extends Command>) ((java.lang.reflect.ParameterizedType)
                    handlerClass.getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "Failed to determine command type for handler: " + handlerClass.getName(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> CommandHandler<T> getHandler(Class<T> commandClass) {
        return (CommandHandler<T>) handlers.get(commandClass);
    }

    public Set<Class<? extends Command>> getCommands() {
        return commands;
    }
}
