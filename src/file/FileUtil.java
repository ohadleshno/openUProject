package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    /**
     * get all words from file.
     * @param dictFilePath
     * @return all words from file split by space
     * @throws IOException
     */
    public static String[] getAllWordsFromFiles(String dictFilePath) throws IOException {
        return Files.readString(Paths.get(dictFilePath)).split(" ");
    }
}