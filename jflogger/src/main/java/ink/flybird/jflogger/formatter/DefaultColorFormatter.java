package ink.flybird.jflogger.formatter;

import ink.flybird.jflogger.LogManager;
import ink.flybird.jflogger.utils.ANSIToU16;
import ink.flybird.jflogger.utils.U16ToANSI;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class DefaultColorFormatter implements IColorFormatter {

    private static boolean isColorMapSerialized = false;

    private final HashMap<String, String> colorMap = new HashMap<>();

    public DefaultColorFormatter() {

        if (!isColorMapSerialized) {
            setFormat(LogManager.getConfig().colorMap);
            isColorMapSerialized = true;
            return;
        }

        try {
            var result = this.getClass().getResourceAsStream("/color.properties");
            if(result == null) return;
            var str = new String(result.readAllBytes());
            setFormat(str);
        } catch (Exception ignored) {
            System.out.println("Can't Read Color Profile");
        }
    }

    @Override
    public void setFormat(String format) {
        var lines = format.split("\n");
        for(var line : lines) {
            if(!line.contains("=")) continue;
            var result = line.split("=");
            if(result.length != 2) continue;
            colorMap.put(result[0].toLowerCase(), result[1]);
        }
    }

    @Override
    public void setFormat(HashMap<String, String> c) {
        var map = LogManager.getConfig().colorMap;
        for(var key : map.keySet()) {
            colorMap.put(key.toLowerCase(), String.format("\u001B%s", map.get(key)));
        }
    }

    @Override
    public void readFormat(String file) {
        File fileType = new File(file);
        if(!fileType.exists())
            return;
        try {
            var io = new FileInputStream(fileType);
            var format = new String(io.readAllBytes());
            setFormat(format);
            io.close();
        } catch (Exception e) {
            throw new RuntimeException("Error To Read Format");
        }
    }

    @Override
    public String shadeLog(String log, String type) {
        var startColor = colorMap.getOrDefault(type.toLowerCase(), "\u001B[0m");
        var endColor = "\u001B[0m";
        return log
                .replace("{ColorStart}", startColor)
                .replace("{ColorEnd}", endColor);
    }
}
