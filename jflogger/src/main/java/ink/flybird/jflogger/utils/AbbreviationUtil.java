package ink.flybird.jflogger.utils;

public class AbbreviationUtil {
    public static String abbreviatePackageName(String fullClassName) {
        if (fullClassName == null || fullClassName.isEmpty()) {
            return "";
        }

        String[] parts = fullClassName.split("\\.");

        var abbreviation = new StringBuilder();
        for(var part : parts) {
            abbreviation.append(part.charAt(0));
            abbreviation.append(".");
        }

        return abbreviation.toString();
    }
}

