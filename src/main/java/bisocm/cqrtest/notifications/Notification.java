package bisocm.cqrtest.notifications;

public class Notification {
    private final String message;

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}