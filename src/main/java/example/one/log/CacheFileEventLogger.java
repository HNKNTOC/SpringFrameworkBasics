package example.one.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nikita on 07.11.2016.
 */
public class CacheFileEventLogger extends FileEventLogger {
    private static final Logger LOGGER = LogManager.getLogger(CacheFileEventLogger.class);

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
        LOGGER.debug("destroy");
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

}
