package ink.flybird.jflogger.factory;

import ink.flybird.jflogger.LogManager;
import ink.flybird.jflogger.system.ServiceStream;

import java.io.PrintStream;

public final class StreamFactory {

    private static ServiceStream stream;

    public static PrintStream getStream() {
        var config = LogManager.getConfig();
        if(config.handleSystemStream && stream == null) {
            stream = new ServiceStream(System.out);
        }
        return System.out;
    }
}
