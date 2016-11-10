package example.one.log;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nikita on 07.11.2016.
 */
public class FileEventLogger implements EventLogger {
    private static final Logger LOGGER = LogManager.getLogger(FileEventLogger.class);
    private String fileName;
    private File file;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    public void init() throws IOException {
        LOGGER.debug("init");
        if (!file.canWrite()) {
            throw new IOException("Can not write to file "+file.getAbsolutePath());
        }
    }

    @Override
    public void logEvent(Event event){
        LOGGER.debug("logEvent: "+event);
        try {
            FileUtils.writeStringToFile(file,event.toString()+"\n",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
