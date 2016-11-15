package example.one.log;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Nikita on 07.11.2016.
 */
public class Event {
    private static int idNumber = 0;
    private EventType eventType;
    private int id;
    private String msg;
    private Date date;
    private DateFormat dateFormat;

    public Event(Date date,DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        this.date = date;
        idNumber++;
        this.id = idNumber;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + eventType +
                ", id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                ", dateFormat=" + dateFormat +
                '}';
    }
}
