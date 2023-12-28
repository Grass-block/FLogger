package ink.flybird.jflogger;

public abstract class ILogger {

    public abstract ILogHandler getHandler();

    public abstract void info(String text);
    public abstract void info(String pattern, Object... items);
    public void info(Object any) {
        info(any.toString());
    }



    public abstract void warn(String text);
    public abstract void warn(String pattern, Object... items);
    public void warn(Object any) {
        warn(any.toString());
    }



    public abstract void error(String text);
    public abstract void error(String pattern, Object... items);
    public void error(Object any) {
        error(any.toString());
    }
    public abstract void error(Exception e);
    public abstract void error(Error e);



    public abstract void debug(String text);
    public abstract void debug(String pattern, Object... items);
    public void debug(Object any) {
        debug(any.toString());
    }



    public abstract void trace(String text);
    public abstract void trace(String pattern, Object... items);
    public void trace(Object any) {
        trace(any.toString());
    }
    public abstract void trace(Exception e);
    public abstract void trace(Error e);


    public void exception(Exception e) {
        error(e);
    }
}
