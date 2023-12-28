package ink.flybird.jflogger.formatter;

public class DefaultFormatter implements IMessageFormatter{
    private final StringBuilder builder = new StringBuilder();

    @Override
    public String format(String pattern, Object... items) {
        builder.delete(0, builder.length());
        pattern = pattern + " ";
        String[] parts = pattern.split("\\{\\}");
//        if(parts.length == 0 && items.length >= 1)
//            return items[0].toString();
        var index = 0;
        for(var item : parts) {
            builder.append(item);
            if(index < parts.length - 1 && index <= items.length - 1)
                builder.append(items[index]);
            index++;
        }
        for(int i = index - 1; i <= items.length - 1; i++) {
            builder.append(items[i]);
        }
        return builder.toString();
    }
}
