package example.one.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita on 07.11.2016.
 */
public class CacheFileEventLogger extends FileEventLogger {

    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<>(cacheSize);
    }

    private void writeEventsFromCache() {
        for (Event cacheEvent : cache) {
            super.logEvent(cacheEvent);
        }
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);
        if(cache.size() >= cacheSize){
            writeEventsFromCache();
            cache.clear();
        }
    }

    public void destroy(){
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

}
