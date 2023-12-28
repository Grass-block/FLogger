package ink.flybird.jflogger.formatter;

import java.util.HashMap;

public interface IColorFormatter {
    void setFormat(String format);
    void setFormat(HashMap<String, String> colorMap);
    void readFormat(String file);
    String shadeLog(String log, String type);
}
