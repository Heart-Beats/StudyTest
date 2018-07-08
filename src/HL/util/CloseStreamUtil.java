package HL.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseStreamUtil {

    public static void closeAllStream(Closeable... ioStream) {
        for (Closeable iotempStream : ioStream) {
            if (null!= iotempStream) {
                try {
                    iotempStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
