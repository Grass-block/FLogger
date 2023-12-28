package ink.flybird.jflogger.formatter;

import java.io.IOException;

public class WindowsColorFormatter extends DefaultColorFormatter {
    public WindowsColorFormatter() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "reg", "add", "HKCU\\Console", "/v", "VirtualTerminalLevel", "/t", "REG_DWORD", "/d", "1", "/f");
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Can't Setup Windows Logger Color Shade Tool");
        }
    }
}
