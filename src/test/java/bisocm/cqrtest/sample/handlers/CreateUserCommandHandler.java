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