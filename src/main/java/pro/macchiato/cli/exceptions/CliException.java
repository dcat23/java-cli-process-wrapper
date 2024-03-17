package pro.macchiato.cli.exceptions;

import java.io.IOException;

public class CliException extends Exception {

    public CliException(String message) {
        super(message);
    }

    public CliException(Exception e) {
        super(e);
    }

    public CliException(InterruptedException e) {
        super(e.getMessage());
    }

    public CliException(IOException e) {
        super(e.getMessage());
    }

    public CliException(String errorBuffer, int exitCode) {
        super(errorBuffer);
    }
}
