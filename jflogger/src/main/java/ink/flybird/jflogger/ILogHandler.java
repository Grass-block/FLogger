package ink.flybird.jflogger;

import ink.flybird.jflogger.formatter.IMessageFormatter;

public interface ILogHandler {
    void setEnable(boolean enable);
    void setLogFormat(String format);
    void log(int logLevel, IMessageFormatter formatter, String patten, Object... items);
}
