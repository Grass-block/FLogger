package ink.flybird.jflogger;

import ink.flybird.jflogger.formatter.IExceptionFormatter;
import ink.flybird.jflogger.formatter.IMessageFormatter;

public class DefaultLogger extends ILogger {

    public static final int INFO = 0;
    public static final int ERROR = 1;
    public static final int WARN = 2;
    public static final int DEBUG = 3;
    public static final int TRACE = 4;

    public static final int ERROR_EXCEPTION = 1;
    public static final int TRACE_EXCEPTION = 0;

    private final ILogHandler handler;
    private final IMessageFormatter formatter;
    private final IExceptionFormatter exceptionFormatter;

    public DefaultLogger(ILogHandler handler, IMessageFormatter formatter, IExceptionFormatter exceptionFormatter) {
        this.handler = handler;
        this.formatter = formatter;
        this.exceptionFormatter = exceptionFormatter;
    }


    @Override
    public ILogHandler getHandler() {
        return handler;
    }

    @Override
    public void info(String text) {
        handler.log(INFO, formatter, "{}", text);
    }

    @Override
    public void info(String pattern, Object... items) {
        handler.log(INFO, formatter, pattern, items);
    }

    @Override
    public void warn(String text) {
        handler.log(WARN, formatter, "{}", text);
    }

    @Override
    public void warn(String pattern, Object... items) {
        handler.log(WARN, formatter, pattern, items);
    }

    @Override
    public void error(String text) {
        handler.log(ERROR, formatter, "{}", text);
    }

    @Override
    public void error(String pattern, Object... items) {
        handler.log(ERROR, formatter, pattern, items);
    }

    @Override
    public void error(Exception e) {
        exceptionFormatter.format(ERROR_EXCEPTION, e, this);
    }

    @Override
    public void debug(String text) {
        handler.log(DEBUG, formatter, "{}", text);
    }

    @Override
    public void debug(String pattern, Object... items) {
        handler.log(DEBUG, formatter, pattern, items);
    }

    @Override
    public void trace(String text) {
        handler.log(TRACE, formatter, "{}", text);
    }

    @Override
    public void trace(String pattern, Object... items) {
        handler.log(TRACE, formatter, pattern, items);
    }

    @Override
    public void trace(Exception e) {
        exceptionFormatter.format(TRACE_EXCEPTION, e, this);
    }

    @Override
    public void trace(Error e) {
        exceptionFormatter.format(TRACE_EXCEPTION, e, this);
    }

    @Override
    public void error(Error e) {
        exceptionFormatter.format(ERROR_EXCEPTION, e, this);
    }
}
