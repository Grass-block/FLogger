package ink.flybird.jflogger.factory;

import ink.flybird.jflogger.ILogCollector;
import ink.flybird.jflogger.LogManager;
import ink.flybird.jflogger.LogOutputService;
import ink.flybird.jflogger.LogSaveService;

public final class LogCollectorFactory {

    private static LogSaveService saveService;
    private static LogOutputService outputService;

    public static ILogCollector create() {
        var config = LogManager.getConfig();

        if(config.useFileRecorder) {
            if(saveService == null) {
                if (outputService == null)
                    outputService = new LogOutputService();
                saveService = new LogSaveService(outputService);
            }
            return saveService;
        } else {
            if (outputService == null)
                outputService = new LogOutputService();
            return outputService;
        }
    }
}
