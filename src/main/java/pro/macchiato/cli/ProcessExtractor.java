package pro.macchiato.cli;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ProcessExtractor extends Thread {

    private final InputStream stream;
    private final StringBuilder buffer;
    private final ProgressCallback callback;

    public ProcessExtractor(StringBuilder buffer, InputStream stream, ProgressCallback callback) {
        this.stream = stream;
        this.buffer = buffer;
        this.callback = callback;
        this.start();
    }

    @Override
    public void run() {
        try {
            StringBuilder currentLine = new StringBuilder();
            int nextChar;
            while ((nextChar = stream.read()) != -1 && !callback.isReady()) {
                buffer.append((char) nextChar);
                if (nextChar == '\r') {
                    callback.processLine(currentLine.toString());
                    currentLine.setLength(0);
                    continue;
                }
                currentLine.append((char) nextChar);
            }
        } catch (IOException ignored) {
            log.error(ignored.getMessage());
        }
    }
}
