// Component Interface
interface Notification {
    void send(String message);
}

// Concrete Component
class BasicNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending: " + message);
    }
}

// Decorator Base Class
class NotificationDecorator implements Notification {
    protected Notification wrapped;

    public NotificationDecorator(Notification wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void send(String message) {
        wrapped.send(message);
    }
}

// Concrete Decorators
class EmailNotification extends NotificationDecorator {
    public EmailNotification(Notification wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message) {
        System.out.print("Email Notification: ");
        super.send(message);
    }
}

class SMSNotification extends NotificationDecorator {
    public SMSNotification(Notification wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message) {
        System.out.print("SMS Notification: ");
        super.send(message);
    }
}

// Client
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Notification basicNotification = new BasicNotification();
        Notification emailNotification = new EmailNotification(basicNotification);
        Notification smsNotification = new SMSNotification(emailNotification);

        smsNotification.send("Hello World!"); // Output: SMS Notification -> Email Notification -> Sending: Hello World!
    }
}
