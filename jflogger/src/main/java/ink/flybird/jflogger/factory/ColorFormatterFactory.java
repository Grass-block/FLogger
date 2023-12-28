package ink.flybird.jflogger.factory;

import ink.flybird.jflogger.formatter.DefaultColorFormatter;
import ink.flybird.jflogger.formatter.IColorFormatter;
import ink.flybird.jflogger.formatter.WindowsColorFormatter;

public final class ColorFormatterFactory {

    private static WindowsColorFormatter windowsColorFormatter;
    private static DefaultColorFormatter defaultColorFormatter;


    public static IColorFormatter create() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("windows")) {
            return windowsColorFormatter == null ?
                    windowsColorFormatter = new WindowsColorFormatter() :
                    windowsColorFormatter;
        } else {
            return defaultColorFormatter == null ?
                    defaultColorFormatter = new DefaultColorFormatter() :
                    defaultColorFormatter;
        }
    }
}
