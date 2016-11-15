package example.one.log;

import java.util.Collection;

/**
 * Нужен для вывода сообщения всем логгерам находящимся в коллекции.
 */
public class CombinedEventLogger implements EventLogger {

    private final Collection<EventLogger> eventLoggers;

    public CombinedEventLogger(Collection<EventLogger> eventLoggers) {
        this.eventLoggers = eventLoggers;
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger eventLogger : eventLoggers) {
            eventLogger.logEvent(event);
        }
    }
}
