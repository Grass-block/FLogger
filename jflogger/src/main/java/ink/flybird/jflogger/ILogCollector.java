package ink.flybird.jflogger;

public interface ILogCollector {
    void addLog(int level, String result);
    void startService();
    void StopService();
}
