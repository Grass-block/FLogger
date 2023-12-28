package ink.flybird.jflogger.formatter;

import ink.flybird.jflogger.ILogger;
import ink.flybird.jflogger.LogManager;

public class DefaultExceptionFormatter extends IExceptionFormatter {

    public DefaultExceptionFormatter() {
        isDebugging = isDebugged();
    }

    @Override
    public void format(int id, Exception e, ILogger logger) {
        if(this.isDebugging && LogManager.getConfig().useSystemExceptionTraceWhenDebugging)
        {
            try {
                e.printStackTrace();
            } catch (Exception ignore) {

            }
            return;
        }

        switch (id) {
            case 0 -> {
                logger.trace("|> Exception Caught!");
                logger.trace("|- Type: {}", e.getClass().getTypeName());
                logger.trace("|- Message: {}({})", e.getMessage(), e.getLocalizedMessage());
                logger.trace("|- Cause: {}", e.getCause());
                logger.trace("|- StackTrace:");
                for (var item : e.getStackTrace()) {
                    logger.trace(
                            "|= at {}.{}({}.{}){}",
                            item.getClassName(),
                            item.getMethodName(),
                            item.getFileName(),
                            item.getLineNumber(),
                            ""//generateHyperlink(item.getFileName(), item.getLineNumber())
                    );
                }
            }
            case 1 -> {
                logger.error("|> Exception Caught!");
                logger.error("|- Type: {}", e.getClass().getTypeName());
                logger.error("|- Message: {}({})", e.getMessage(), e.getLocalizedMessage());
                logger.error("|- Cause: {}", e.getCause());
                logger.error("|- StackTrace:");
                for (var item : e.getStackTrace()) {
                    logger.error(
                            "|= at {}.{}({}.{}){}",
                            item.getClassName(),
                            item.getMethodName(),
                            item.getFileName(),
                            item.getLineNumber(),
                            ""//generateHyperlink(item.getFileName(), item.getLineNumber())
                    );
                }
            }
            default -> {
            }
        }

    }

    @Override
    public void format(int id, Error e, ILogger logger) {
        if(this.isDebugging && LogManager.getConfig().useSystemExceptionTraceWhenDebugging)
        {
            try {
                e.printStackTrace();
            } catch (Exception ignore) {

            }
            return;
        }

        switch (id) {
            case 0 -> {
                logger.trace("|> Error Caught!");
                logger.trace("|- Type: {}", e.getClass().getTypeName());
                logger.trace("|- Message: {}({})", e.getMessage(), e.getLocalizedMessage());
                logger.trace("|- Cause: {}", e.getCause());
                logger.trace("|> StackTrace:");
                for (var item : e.getStackTrace()) {
                    logger.trace(
                            "|= at {}.{}({}.{}){}",
                            item.getClassName(),
                            item.getMethodName(),
                            item.getFileName(),
                            item.getLineNumber(),
                            ""//generateHyperlink(item.getFileName(), item.getLineNumber())
                    );
                }
            }
            case 1 -> {
                logger.error("|> Error Caught!");
                logger.error("|- Type: {}", e.getClass().getTypeName());
                logger.error("|- Message: {}({})", e.getMessage(), e.getLocalizedMessage());
                logger.error("|- Cause: {}", e.getCause());
                logger.error("|- StackTrace:");
                for (var item : e.getStackTrace()) {
                    logger.error(
                            "|= at {}.{}({}.{}){}",
                            item.getClassName(),
                            item.getMethodName(),
                            item.getFileName(),
                            item.getLineNumber(),
                            ""//generateHyperlink(item.getFileName(), item.getLineNumber())
                    );
                }
            }
            default -> {
            }
        }

    }

    private static String generateHyperlink(String fileName, int lineNumber) {
        return String.format("<a href=\"file://%s:%d\">%s:%d</a>",
                fileName, lineNumber, fileName, lineNumber);
    }
}
