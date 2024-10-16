# I hate Java.

This library allows you to build a very quick command-based CLI application. It uses reflection to build a registry of all commands and their respective handlers. Advise the `Test` directory for sample code.

## Command Initialization

Create the `Command` implementation first. This is technically the context of your command and transfers all the relevant data for that command.

```java
package bisocm.cqrtest.sample.commands;

import bisocm.cqrtest.annotations.CommandAttribute;
import bisocm.cqrtest.core.Command;
import bisocm.cqrtest.sample.CustomMenuState;

@CommandAttribute(menu = CustomMenuState.class, ordinal = 0, priority = 0)
public class CreateUserCommand implements Command {
    private final String username;
    private final String email;

    public CreateUserCommand(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
```

Pay attention to the attribute - it is a requirement. Without it, the registry will not be able to scan the assembly for the command, and the menu manager won't know how to handle it either. After the command has been registered, you need to write a separate class to handle it:

```java
package bisocm.cqrtest.sample.handlers;

import bisocm.cqrtest.core.CommandHandler;
import bisocm.cqrtest.sample.commands.CreateUserCommand;

public class CreateUserCommandHandler extends CommandHandler<CreateUserCommand> {
    @Override
    public void handle(CreateUserCommand command) {
        //Implement the command's processing logic here.
        System.out.println("Creating user: " + command.getUsername());
        System.out.println("User created successfully.");
    }
}
```

In your codebase, I suggest you make a singleton of the MenuManager and the CommandRegistry. You must pass the name of your package in order for the assembly scan to happen: 

```java
        CommandRegistry registry = new CommandRegistry("bisocm.cqrtest.sample");
        MenuManager manager = new MenuManager("bisocm.cqrtest.sample");

        //Print the menu of all commands that belong in the Unauthorized section - CustomMenuState is an enum (implemented by library consumer)
        String authorizedMenuCommands = manager.getMenuCommands(CustomMenuState.UNAUTHORIZED);
        System.out.println(authorizedMenuCommands);

        //Execute command
        CreateUserCommand command = new CreateUserCommand("Test", "Test2");
        manager.executeCommand(command);
```

No further set up is required. This library's purpose is to build very light code for command-based CLIs, with cross-cutting concerns separated.

If you feel like torturing yourself into coding with Java, feel free to implement further functionality, like:
- Pre and post execution attributes, with the ability for the library consumer to define custom logic.
- Command pipelines
