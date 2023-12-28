import ink.flybird.jflogger.LogManager;
import ink.flybird.jflogger.config.ConfigBuilder;

public class Test {
    public static void main(String[] args) {
        var logger = LogManager.getLogger();

        logger.error("Hello World");
        logger.info("{} {} {}{}", "awa", "qwq", 12, Test.class);
        logger.info("awa {} qwq", "awa");

        LogManager.getConfig().saveConfigToFile("awa.xml");

        logger.error(new Exception("fuck!"));
    }
}
