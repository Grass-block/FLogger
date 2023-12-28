package ink.flybird.jflogger.formatter;

import com.sun.jdi.Bootstrap;
import ink.flybird.jflogger.ILogger;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public abstract class IExceptionFormatter {
    public abstract void format(int id, Exception e, ILogger logger);
    public abstract void format(int id, Error e, ILogger logger);

    protected boolean isDebugged() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> inputArguments = runtimeMXBean.getInputArguments();

        for (String arg : inputArguments) {
            if (arg.startsWith("-agentlib:jdwp") || arg.startsWith("-Xdebug")) {
                return true;
            }
        }

        try {
            return Bootstrap.virtualMachineManager().connectedVirtualMachines().size() > 0;
        } catch (Throwable e) {

        }

        return false;
    }

    protected boolean isDebugging = false;
}
