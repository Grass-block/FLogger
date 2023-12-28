package ink.flybird.jflogger;

import ink.flybird.jflogger.factory.StreamFactory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class LogOutputService implements ILogCollector {

    private final class QueueItem {
        public final String value;
        public final int level;

        public QueueItem(String v, int l) {
            value = v;
            level = l;
        }
    }

    public LogOutputService() {
        service = CompletableFuture.runAsync(() -> {
            try {
                while(!isEnd) {
                    var item = queue.take();
                    outputText(item);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(queue.isEmpty()) return;
            StopService();
            for(var item : queue) {
                outputText(item);
            }
        }));
    }

    private void outputText(QueueItem msg) {
        if(msg.level == 1 && LogManager.getConfig().useErrStreamWhenError) {
            eStream.println(msg.value);
            eStream.flush();
        } else {
            stream.println(msg.value);
            stream.flush();
        }
    }

    @Override
    public void addLog(int level, String result) {
        queue.add(new QueueItem(result, level));
    }

    @Override
    public void startService() {
        //NOT NEED TO START
        isEnd = false;
    }

    @Override
    public void StopService() {
        isEnd = true;
        service.cancel(true);
    }

    private final LinkedBlockingQueue<QueueItem> queue = new LinkedBlockingQueue<>();
    private final CompletableFuture<Void> service;
    private final PrintStream stream = StreamFactory.getStream();
    private final PrintStream eStream = System.err;
    private boolean isEnd = false;
}
