package example.one;

import example.one.log.Event;
import example.one.log.EventLogger;
import example.one.log.EventType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Nikita on 04.11.2016.
 */
@Component
public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);
    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("main.xml");
    private static JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
    private Map<EventType, EventLogger> loggerMap;
    private Client client;
    private EventLogger defaultLogger;


    public static App createInstance(){
        return new App();
    }

    public void setLoggerMap(Map<EventType, EventLogger> loggerMap) {
        this.loggerMap = loggerMap;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDefaultLogger(EventLogger defaultLogger) {
        this.defaultLogger = defaultLogger;
    }

    @Autowired
    public static void main(String[] args) {

        App app = context.getBean("app", App.class);

        Client client = app.getClient();
        for (int i = 0; i < 1; i++) {
            app.logEvent(EventType.ERROR,String.format("User \"#%d\" connect to server.", client.getId()));
            app.logEvent(EventType.INFO,String.format("User \"#%d\" connect to server.", client.getId()));
            app.logEvent(EventType.DEBUG,String.format("User \"#%d\" connect to server.", client.getId()));
        }
        context.close();
    }

    public Client getClient() {
        return client;
    }

    public void logEvent(final EventType type, final String msg) {
        defaultLogger = selectLogger(type);
        Event event = createEvent(processingMsg(msg), type);
        defaultLogger.logEvent(event);
        //logInDataBase(event, jdbcTemplate);
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

    private void logInDataBase(Event event,JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
                        "INSERT INTO t_event (id,msg) VALUES (?,?)"
                        ,event.getId()
                        ,event.toString()
                );
    }

    private EventLogger selectLogger(EventType type) {
        EventLogger logger = loggerMap.get(type);
        if (logger == null) {
            logger = defaultLogger;
        }
        return logger;
    }
}
