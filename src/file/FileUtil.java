package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String[] createHashMapDict(String dictFilePath) throws IOException {
        return Files.readString(Paths.get(dictFilePath)).split(" ");
    }
}