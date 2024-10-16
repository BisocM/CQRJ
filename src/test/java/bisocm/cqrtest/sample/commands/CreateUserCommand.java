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