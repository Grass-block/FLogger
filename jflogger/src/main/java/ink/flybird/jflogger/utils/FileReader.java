package ink.flybird.jflogger.utils;

import java.io.FileInputStream;

public class FileReader {

    public FileReader(String name) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(name);
            var read = new String(stream.readAllBytes());
            lines = read.split("\n");
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String nextLine() {
        line++;
        return currentLine();
    }

    public String currentLine() {
        if(line <= lines.length - 1)
            return lines[line];
        else
            return "";
    }

    private int line = 0;
    private final String[] lines;
}
