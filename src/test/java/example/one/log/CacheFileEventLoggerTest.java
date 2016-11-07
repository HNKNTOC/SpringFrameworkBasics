package example.one.log;

import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Nikita on 07.11.2016.
 */
public class CacheFileEventLoggerTest {

    private static String fileName = "cacheLog";
    private static int cacheSize = 5;
    private CacheFileEventLogger logger = new CacheFileEventLogger(fileName, cacheSize);

    @Test
    public void checkCache() throws Exception {
        File file = new File(fileName);
        assertFalse(file.exists());
        assertTrue(file.createNewFile());

        logger.init();
        for (int i = 0; i < cacheSize-1; i++) {
            logger.logEvent(new Event(new Date(), DateFormat.getDateInstance()));
            assertTrue(file.length() == 0);
        }
        logger.logEvent(new Event(new Date(), DateFormat.getDateInstance()));
        assertFalse(file.length() == 0);
        assertTrue(file.delete());

    }
}