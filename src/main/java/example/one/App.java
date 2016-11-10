package example.one;

import example.one.log.Event;
import example.one.log.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Nikita on 04.11.2016.
 */
public class App {

    private Client client = null;
    private EventLogger consoleEventLogger = null;
    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("main.xml");

    public App(Client client, EventLogger consoleEventLogger) {
        this.client = client;
        this.consoleEventLogger = consoleEventLogger;
    }

    public Client getClient() {
        return client;
    }

    public void logEvent(final String msg) {
        String changMsg = msg.replace("#"+client.getId(),client.getFullName());
        Event event = context.getBean("event",Event.class);
        event.setMsg(changMsg);
        consoleEventLogger.logEvent(event);
    }

    public static void main(String[] args) {

        App app = context.getBean("app",App.class);

        Client client = app.getClient();
        for (int i = 0; i < 9; i++) {
            app.logEvent(String.format("User \"#%d\" connect to server.", client.getId()));
        }
        context.close();
    }
}
