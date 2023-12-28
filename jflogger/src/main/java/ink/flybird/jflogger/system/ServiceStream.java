package ink.flybird.jflogger.system;

import java.io.*;
import java.nio.charset.Charset;

public class ServiceStream extends PrintStream {

    public ServiceStream(OutputStream out) {
        super(out);
    }


}
