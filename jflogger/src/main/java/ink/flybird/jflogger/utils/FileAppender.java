package ink.flybird.jflogger.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender {

    public FileAppender(String fileName, boolean needClear) {
        try {
            this.writer = new FileWriter(fileName, !needClear);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileAppender start() {
        return this;
    }

    public FileAppender append(Object value) {
        try {
            writer.append(value.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public FileAppender appendLine(Object value) {
        try {
            writer.append(value.toString());
            writer.append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public FileAppender flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public void end() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final FileWriter writer;
}
