package ink.flybird.jflogger;

import ink.flybird.jflogger.formatter.IColorFormatter;
import ink.flybird.jflogger.formatter.IMessageFormatter;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DefaultLogHandler implements ILogHandler {

    private final DateFormat format = new SimpleDateFormat();
    private final String logName;
    private final ILogCollector output;

    private String formatString;
    private boolean isEnable = true;
    private final IColorFormatter colorFormatter;


    public DefaultLogHandler(String name, ILogCollector output, IColorFormatter formatter) {
        logName = name;
        this.output = output;
        colorFormatter = formatter;
        formatString = LogManager.getConfig().logFormat;
    }

    private String parseLogTypeName(int id) {
        return switch (id) {
            case 0 -> "Info";
            case 1 -> "Error";
            case 2 -> "Warn";
            case 3 -> "Debug";
            case 4 -> "Trace";
            default -> "UnKnown";
        };
    }

    @Override
    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    public void setLogFormat(String format) {
        formatString = format;
    }

    @Override
    public void log(int logLevel, IMessageFormatter formatter, String patten, Object... items) {
        if(!isEnable) return;

        var time = format.format(System.currentTimeMillis());
        var logMsg = formatter.format(patten, items);
        var level = parseLogTypeName(logLevel);

        var result = formatString
                .replace("{Time}", time)
                .replace("{Name}", logName)
                .replace("{Log}", logMsg)
                .replace("{TypeShort}", String.valueOf(level.charAt(0)).toUpperCase())
                .replace("{Type}", level)
                .replace("{ThreadId}", getCurrentThreadId())
                .replace("{ProcessId}", String.valueOf(pid));

        output.addLog(logLevel, colorFormatter.shadeLog(result, level));
    }

    private String getCurrentThreadId() {
        return String.valueOf(Thread.currentThread().getId());
    }

    static {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String jvmName = runtimeBean.getName();
        pid = Long.parseLong(jvmName.split("@")[0]);
    }

    private static long pid;
}
