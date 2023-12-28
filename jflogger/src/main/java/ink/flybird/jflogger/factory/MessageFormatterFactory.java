package ink.flybird.jflogger.factory;

import ink.flybird.jflogger.formatter.DefaultFormatter;
import ink.flybird.jflogger.formatter.IMessageFormatter;

public final class MessageFormatterFactory {

    private static DefaultFormatter formatter;

    public static IMessageFormatter create() {
        return formatter == null ? formatter = new DefaultFormatter() : formatter;
    }
}
