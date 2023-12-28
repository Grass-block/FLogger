package ink.flybird.jflogger.formatter;

import ink.flybird.jflogger.ILogger;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class DebuggableExceptionFormatter extends IExceptionFormatter {

    @Override
    public void format(int id, Exception e, ILogger logger) {


        Set<Throwable> dejaVu = Collections.newSetFromMap(new IdentityHashMap<>());
        dejaVu.add(e);
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement traceElement : trace)
            System.out.println("\tat " + traceElement);

//        synchronized (s.lock()) {
//            // Print our stack trace
//            s.println(this);
//            StackTraceElement[] trace = getOurStackTrace();
//            for (StackTraceElement traceElement : trace)
//                s.println("\tat " + traceElement);
//
//            // Print suppressed exceptions, if any
//            for (Throwable se : getSuppressed())
//                se.printEnclosedStackTrace(s, trace, SUPPRESSED_CAPTION, "\t", dejaVu);
//
//            // Print cause, if any
//            Throwable ourCause = getCause();
//            if (ourCause != null)
//                ourCause.printEnclosedStackTrace(s, trace, CAUSE_CAPTION, "", dejaVu);
//        }
    }

    @Override
    public void format(int id, Error e, ILogger logger) {

    }
}
