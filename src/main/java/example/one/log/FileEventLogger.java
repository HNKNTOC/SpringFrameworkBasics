package example.one.log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nikita on 07.11.2016.
 */
public class FileEventLogger implements EventLogger {

    private String fileName;
    private File file;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    public void init() throws IOException {
        if (!file.canWrite()) {
            throw new IOException("Can not write to file "+file.getAbsolutePath());
        }
    }

    @Override
    public void logEvent(Event event){
        try {
            FileUtils.writeStringToFile(file,event.toString()+"\n",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
