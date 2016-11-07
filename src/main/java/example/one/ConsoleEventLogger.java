package example.one;

import example.one.log.Event;
import example.one.log.EventLogger;

/**
 * Created by Nikita on 04.11.2016.
 */
public class ConsoleEventLogger implements EventLogger {
    public void logEvent(Event event){
        System.out.println(event);
    }
}
