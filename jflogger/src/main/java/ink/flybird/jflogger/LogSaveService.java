package ink.flybird.jflogger;

import ink.flybird.jflogger.utils.FileAppender;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class LogSaveService implements ILogCollector {


    private void addHook() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(queue.isEmpty()) return;
            StopService();
            for(var item : queue) {
                writeText(item);
            }
        }));
    }

    public LogSaveService(LogOutputService service) {
        try {
            var config = LogManager.getConfig();
            var fileName = LogManager.getConfig().recordToFile;

            if(config.logSaveServiceHook != null) {
                var result = config.logSaveServiceHook.callback(fileName);
                var file = new File(fileName);
                if(result && file.exists()) {
                    var ignore = file.delete();
                }
            }

            boolean append = LogManager.getConfig().isFileRecorderAppended;
            stream = new FileAppender(fileName, !append);

            var startText = config.appendModeAppendText
                    .replace("{Time}", String.valueOf(System.nanoTime()))
                    .replace("{Logger}", "JFlogger");

            if(append)
                stream.append("\n")
                        .append(startText)
                        .append("\n")
                        .flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        saveService = CompletableFuture.runAsync(() -> {
            try {
                while(!isEnd)
                    writeText(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        this.service = service;
        addHook();
    }

    public LogSaveService() {
        try {
            var fileName = LogManager.getConfig().recordToFile;
            boolean append = LogManager.getConfig().isFileRecorderAppended;
            stream = new FileAppender(fileName, !append);
            if(append)
                stream.append("\nNew Record Start At : ")
                        .append(System.nanoTime())
                        .append("\n")
                        .flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        saveService = CompletableFuture.runAsync(() -> {
            try {
                while(!isEnd)
                    writeText(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service = new LogOutputService();
        addHook();
    }

    private void writeText(String msg) {
        var fMsg = msg.replaceAll("\u001B\\[[;\\d]*m", "");
        stream.appendLine(fMsg)
                .flush();
    }

    @Override
    public void addLog(int level, String result) {
        queue.add(result);
        service.addLog(level, result);
    }

    @Override
    public void startService() {
        isEnd = false;
    }

    @Override
    public void StopService() {
        isEnd = true;
        saveService.cancel(true);
    }


    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final CompletableFuture<Void> saveService;
    private final LogOutputService service;
    private boolean isEnd = false;
    private final FileAppender stream;
}
