package bisocm.cqrtest.core;

import bisocm.cqrtest.annotations.CommandAttribute;
import bisocm.cqrtest.notifications.Notification;
import bisocm.cqrtest.notifications.NotificationSystem;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MenuManager {
    private final CommandRegistry commandRegistry;
    private final NotificationSystem notificationSystem = new NotificationSystem();

    public MenuManager(String commandPackage) {
        this.commandRegistry = new CommandRegistry(commandPackage);
    }

    public <T extends Command> void executeCommand(T command) {
        Class<T> commandClass = (Class<T>) command.getClass();
        Optional<CommandHandler<T>> handlerOpt = Optional.ofNullable(commandRegistry.getHandler(commandClass));

        handlerOpt.ifPresentOrElse(handler -> {
            notificationSystem.notify(new Notification("Executing command: " + command.getClass().getSimpleName()));
            handler.handle(command);
            notificationSystem.notify(new Notification("Command executed successfully: " + command.getClass().getSimpleName()));
        }, () -> notificationSystem.notify(new Notification("No handler found for command: " + command.getClass().getSimpleName())));
    }

    /**
     * Returns a formatted list of commands for the specified menu state, sorted by priority.
     */
    public String getMenuCommands(Enum<?> menuState) {
        Set<Class<? extends Command>> commands = commandRegistry.getCommands();
        AtomicInteger index = new AtomicInteger(1);  //Initialize the index starting at 1
        return commands.stream()
                .filter(cmd -> {
                    CommandAttribute annotation = cmd.getAnnotation(CommandAttribute.class);
                    return annotation != null && annotation.menu().equals(menuState.getClass());
                })
                .sorted((c1, c2) -> {
                    int p1 = c1.getAnnotation(CommandAttribute.class).priority();
                    int p2 = c2.getAnnotation(CommandAttribute.class).priority();
                    return Integer.compare(p1, p2);
                })
                .map(cmd -> String.format("[%d] %s",
                        index.getAndIncrement(),  //Increment index for each command, since count starts from 0.
                        cmd.getSimpleName()))
                .collect(Collectors.joining("\n"));
    }


    public NotificationSystem getNotificationSystem() {
        return notificationSystem;
    }
}