package ink.flybird.jflogger.utils;

public interface ISaveServiceHook {
    /**
     * Internal Save Service's Hook
     * It Will be Execute When Service Startup
     * Return True Means FLogger Will Delete The Old Logs.log File
     * @param fileName FilaName
     * @return If You Need Delete This File, Please Return True
     */
    boolean callback(String fileName);
}
