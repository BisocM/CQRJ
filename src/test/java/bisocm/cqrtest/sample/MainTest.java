package bisocm.cqrtest.sample;

import bisocm.cqrtest.core.CommandRegistry;
import bisocm.cqrtest.notifications.NotificationSystem;
import bisocm.cqrtest.core.MenuManager;
import bisocm.cqrtest.sample.commands.CreateUserCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    @Test
    public void testCommandExecution() {
        CommandRegistry registry = new CommandRegistry("bisocm.cqrtest.sample");

        MenuManager manager = new MenuManager("bisocm.cqrtest.sample");

        //Print the menu
        String authorizedMenuCommands = manager.getMenuCommands(CustomMenuState.UNAUTHORIZED);
        System.out.println(authorizedMenuCommands);

        //Initialize notification system
        NotificationSystem notificationSystem = new NotificationSystem();
        notificationSystem.subscribe(notification -> System.out.println("Notification: " + notification.getMessage()));

        //Execute commands and verify expected output
        CreateUserCommand command = new CreateUserCommand("Test", "Test2");

        manager.executeCommand(command);

        //Assertions to confirm expected behavior (add assertions as needed)
        assertTrue(true); //Example assertion placeholder
    }
}
