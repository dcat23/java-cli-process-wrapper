package pro.macchiato.cli;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import pro.macchiato.cli.exceptions.CliException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Getter
public class Cli {
    protected String executablePath;

    @Setter
    protected String directory;
    protected Map<String, String> options = new HashMap<>();

    public Cli(String executablePath) {
        this.directory = System.getProperty("user.dir");
        this.executablePath = executablePath;
    }


    public String buildCommand() {
        StringBuilder builder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = options.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> option = it.next();

            String name = option.getKey();
            String value = option.getValue();

            if (value == null) value = "";

            String optionFormatted = String.format("%s %s", name, value).trim();
            builder.append(optionFormatted + " ");

            it.remove();
        }

        return String.format("%s %s", executablePath, builder.toString().trim());
    }

    public void addOption(String key, String value) {
        options.put(key,value);
    }
    public void addOption(String key, int value) {
        addOption(key, String.valueOf(value));
    }
    public void addOption(String key) {
        addOption(key, null);
    }

    public void execute() throws CliException {
        execute(null);
    }
    public void execute(ProgressCallback callback) throws CliException {
        String command = buildCommand();
        Process process;
        int exitCode;
        StringBuilder outBuffer = new StringBuilder(); // stdout
        StringBuilder errBuffer = new StringBuilder(); // stderr

        String[] split = command.split(" ");

        ProcessBuilder processBuilder = new ProcessBuilder(split);

        // Define directory if one is passed
        if (directory != null) processBuilder.directory(new File(directory));

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new CliException(e.getMessage());
        }

        InputStream outStream = process.getInputStream();
        InputStream errStream = process.getErrorStream();

        ProcessExtractor stdOutProcessor = new ProcessExtractor(
                outBuffer, outStream, callback
        );
        ProcessExtractor stdErrProcessor = new ProcessExtractor(
                errBuffer, errStream, callback
        );

        try {
            stdOutProcessor.join();
            stdErrProcessor.join();
            exitCode = process.waitFor();
        } catch (InterruptedException e) {

            // process exited for some reason
            throw new CliException(e);
        }

        String out = outBuffer.toString();
        String err = errBuffer.toString();

        if (exitCode > 0) {
            throw new CliException(err);
        }

        System.out.println(out);
    }

}
