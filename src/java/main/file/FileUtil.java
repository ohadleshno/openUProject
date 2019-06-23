package main.file;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    /**
     * get all words from java.java.file.
     *
     * @param resoureName
     * @return all words from java.java.file split by space
     * @throws IOException
     */
    public static String[] getAllWordsFromFiles(String resoureName) throws IOException, URISyntaxException {
        return Files.readString(Paths.get(ClassLoader.getSystemResource(resoureName).toURI())).split(" ");
    }
}