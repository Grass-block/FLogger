package ink.flybird.jflogger.utils;

public class ANSIToU16 {
    public static String convertAnsiToHex(String ansiColor) {
        if (ansiColor == null || !ansiColor.matches("\u001B\\[\\d+m")) {
            throw new IllegalArgumentException("Invalid ANSI color code");
        }

        int ansiColorValue = Integer.parseInt(ansiColor.substring(2, ansiColor.length() - 1));

        int r = ((ansiColorValue - 16) / 36) * 256 / 6;
        int g = (((ansiColorValue - 16) % 36) / 6) * 256 / 6;
        int b = ((ansiColorValue - 16) % 6) * 256 / 6;

        return String.format("#%02X%02X%02X", r, g, b);
    }
}
