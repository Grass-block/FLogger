package ink.flybird.jflogger.utils;

public class U16ToANSI {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_COLOR_START = "\u001B[";

    public static String convertHexToAnsi(String hexColor) {
        if (hexColor == null || (hexColor.length() != 6 && hexColor.length() != 7)) {
            throw new IllegalArgumentException("Invalid hex color code");
        }

        if (hexColor.charAt(0) == '#')
            hexColor = hexColor.substring(1);

        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);

        int ansiColor = mapRgbToAnsi(r, g, b);

        return ANSI_COLOR_START + ansiColor + "m";
    }

    private static int mapRgbToAnsi(int r, int g, int b) {
        return (r * 6 / 256) * 36 + (g * 6 / 256) * 6 + (b * 6 / 256) + 16;
    }

    public static String getAnsiResetCode() {
        return ANSI_RESET;
    }
}
