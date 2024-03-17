package pro.macchiato.cli.exceptions;

import java.io.IOException;

public class CliException extends Exception {

    public CliException(String message) {
        super(message);
    }

    public CliException(InterruptedException e) {
        super(e.getMessage());
    }
}
