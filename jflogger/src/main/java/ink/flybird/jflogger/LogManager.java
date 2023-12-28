package ink.flybird.jflogger;

import ink.flybird.jflogger.config.ConfigBuilder;
import ink.flybird.jflogger.factory.LoggerFactory;
import ink.flybird.jflogger.utils.AbbreviationUtil;

public class LogManager {

    static {
        loadUp(new ConfigBuilder());
    }

    public static void loadUp(ConfigBuilder builder) {
        if(builder == null) throw new RuntimeException("Builder Can't Be Null");
        config = builder;
    }

    public static ILogger getLogger() {
        if(config == null) throw new RuntimeException("Please Load Up");
        try {
            var name = "unnamed";

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            if (stackTrace.length >= 3) {
                StackTraceElement callerElement = stackTrace[2];
                name = callerElement.getClassName();
            }

            return getLogger(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error To Get Stack Trace");
        }
    }

    public static ILogger getLogger(String name) {
        if(config == null) throw new RuntimeException("Please Load Up");
        return LoggerFactory.getInstance().createLogger(name);
    }

    public static void SetCustomLogger(Class<ILogger> loggerClass, Class<ILogHandler> handler) {
        if(config == null) throw new RuntimeException("Please Load Up");
        LoggerFactory.getInstance().setCustomLogger(loggerClass, handler);
    }

    public static ConfigBuilder getConfig() {
        if(config == null) throw new RuntimeException("Please Load Up");
        return config;
    }

    private static ConfigBuilder config;
}
