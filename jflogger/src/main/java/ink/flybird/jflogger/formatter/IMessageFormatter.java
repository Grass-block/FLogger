package ink.flybird.jflogger.formatter;

public interface IMessageFormatter {
    String format(String pattern, Object... items);
}
