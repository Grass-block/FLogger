package ink.flybird.jflogger.config;

import ink.flybird.jflogger.utils.ISaveServiceHook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Objects;

public class ConfigBuilder {

    public ConfigBuilder() {
        try {
            var res = this.getClass().getClassLoader().getResourceAsStream("flogger.xml");
            if (res != null) {
                final ConfigDeserializer des = new ConfigDeserializer(res);
                des.deserialize(this);
            }
        } catch (Exception ignore) {
            //TODO LogError
        }
    }

    public ConfigBuilder(Object obj) {
        try {
            var res = obj.getClass().getClassLoader().getResourceAsStream("flogger.xml");
            if (res != null) {
                final ConfigDeserializer des = new ConfigDeserializer(res);
                des.deserialize(this);
            }
        } catch (Exception ignore) {
            //TODO LogError
        }
    }

    public ConfigBuilder loadConfigFromFile(String name) {
        try {
            final ConfigDeserializer des = new ConfigDeserializer(name);
            des.deserialize(this);
        } catch (Exception ignore) {
            //TODO LogError
            return this;
        }

        return this;
    }

    public ConfigBuilder saveConfigToFile(String name) {
        try {
            final var serializer = new ConfigSerializer(name);
            serializer.serialize(this);
            serializer.serialize(colorMap);
            serializer.save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }


    public ConfigBuilder setUserFileRecorder(boolean use) {
        useFileRecorder = use;
        return this;
    }

    public ConfigBuilder setRecordToFile(String name) {
        recordToFile = name;
        return this;
    }

    public ConfigBuilder setLogSaveServiceHook(ISaveServiceHook callback) {
        logSaveServiceHook = callback;
        return this;
    }

    public ConfigBuilder setFileRecorderAppended(boolean value) {
        isFileRecorderAppended = value;
        return this;
    }

    public boolean isFileRecorderAppended = false;
    public boolean useFileRecorder = false;
    public String recordToFile = "logs.log";
    public String appendModeAppendText = "New Record Start At : {Time}";
    public String logFormat = "[{Time}] {ColorStart}{Type}{ColorEnd} {Name} : {Log}";
    public boolean handleSystemStream = true;
    public boolean useErrStreamWhenError = false;
    public boolean useSystemExceptionTraceWhenDebugging = true;

    @DoNotSerialize
    public ISaveServiceHook logSaveServiceHook = null;
    @DoNotSerialize
    public HashMap<String, String> colorMap = new HashMap<>();
}
