package example.one;

import example.one.log.Event;
import example.one.log.EventLogger;
import example.one.log.EventType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by Nikita on 04.11.2016.
 */
public class App {

    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("main.xml");
    private final Map<EventType, EventLogger> loggerMap;
    private Client client;
    private EventLogger defaultLogger;

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggerMap) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggerMap = loggerMap;
    }

    public static void main(String[] args) {

        App app = context.getBean("app", App.class);

        Client client = app.getClient();
        for (int i = 0; i < 9; i++) {
            app.logEvent(EventType.ERROR,String.format("User \"#%d\" connect to server.", client.getId()));
            app.logEvent(EventType.INFO,String.format("User \"#%d\" connect to server.", client.getId()));
        }
        context.close();
    }

    public Client getClient() {
        return client;
    }

    public void logEvent(final EventType type, final String msg) {
        defaultLogger = selectLogger(type);
        defaultLogger.logEvent(createEvent(processingMsg(msg),type));
    }

    private String processingMsg(String msg) {
        return msg.replace("#" + client.getId(), client.getFullName());
    }

    private Event createEvent(String changMsg, EventType type) {
        Event event = context.getBean("event", Event.class);
        event.setMsg(changMsg);
        event.setEventType(type);
        return event;
    }

    private EventLogger selectLogger(EventType type) {
        EventLogger logger = loggerMap.get(type);
        if (logger == null) {
            logger = defaultLogger;
        }
        return logger;
    }
}
