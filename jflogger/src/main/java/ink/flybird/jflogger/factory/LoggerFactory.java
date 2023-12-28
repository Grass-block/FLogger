package ink.flybird.jflogger.factory;

import ink.flybird.jflogger.*;
import ink.flybird.jflogger.formatter.IColorFormatter;
import ink.flybird.jflogger.formatter.IExceptionFormatter;
import ink.flybird.jflogger.formatter.IMessageFormatter;

import java.util.Objects;

public class LoggerFactory {

    private static final LoggerFactory instance = new LoggerFactory();
    public static LoggerFactory getInstance() {
        return instance;
    }

    public void setCustomLogger(Class<ILogger> loggerClass, Class<ILogHandler> handler) {
        this.loggerClass = loggerClass;
        this.handler = handler;
    }

    public ILogger createLogger(String name) {
        try {
            var handler = this.handler == null ?
                    new DefaultLogHandler(
                            name,
                            LogCollectorFactory.create(),
                            ColorFormatterFactory.create()
                    ) :
                    this.handler.getDeclaredConstructor(
                            String.class,
                            ILogCollector.class,
                            IColorFormatter.class
                    ).newInstance(
                            name,
                            LogCollectorFactory.create(),
                            ColorFormatterFactory.create()
                    );
            return this.loggerClass == null ?
                    new DefaultLogger(
                            handler,
                            MessageFormatterFactory.create(),
                            ExceptionFormatterFactory.create()
                    ) :
                    this.loggerClass.getDeclaredConstructor(
                            ILogHandler.class,
                            IMessageFormatter.class,
                            IExceptionFormatter.class
                    ).newInstance(
                            handler,
                            MessageFormatterFactory.create(),
                            ExceptionFormatterFactory.create()
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<ILogger> loggerClass = null;
    private Class<ILogHandler> handler = null;
}
