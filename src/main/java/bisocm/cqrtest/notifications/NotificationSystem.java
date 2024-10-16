package bisocm.cqrtest.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationSystem {
    private final List<Consumer<Notification>> subscribers = new ArrayList<>();

    //Subscribe a listener
    public void subscribe(Consumer<Notification> listener) {
        subscribers.add(listener);
    }

    //Unsubscribe a listener
    public void unsubscribe(Consumer<Notification> listener) {
        subscribers.remove(listener);
    }

    //Publish a notification
    public void notify(Notification notification) {
        for (Consumer<Notification> subscriber : subscribers) {
            subscriber.accept(notification);
        }
    }
}