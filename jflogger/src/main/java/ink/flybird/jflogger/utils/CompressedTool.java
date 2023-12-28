package ink.flybird.jflogger.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

public final class CompressedTool {

    public static void gzipCompress(String file, String outputFileName) {
        try (
                var inputFileStream = new FileInputStream(file);
                var compressedFileStream = new FileOutputStream(outputFileName);
                var gzipOutputStream = new GZIPOutputStream(compressedFileStream);
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputFileStream.read(buffer)) != -1) {
                gzipOutputStream.write(buffer, 0, bytesRead);
            }

        } catch (Exception ignore) {
        }
    }
}
