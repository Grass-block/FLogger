package ink.flybird.jflogger.factory;

import ink.flybird.jflogger.formatter.DefaultExceptionFormatter;
import ink.flybird.jflogger.formatter.IExceptionFormatter;

public final class ExceptionFormatterFactory {

    private static DefaultExceptionFormatter formatter;

    public static IExceptionFormatter create() {
        return formatter == null ? formatter = new DefaultExceptionFormatter() : formatter;
    }
}
