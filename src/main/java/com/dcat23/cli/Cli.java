package com.dcat23.cli;

import com.dcat23.cli.exceptions.CliException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class Cli {
    protected String executablePath;
    protected String directory;
    protected Map<String, String> options = new HashMap<>();

    public Cli(String executablePath) {
        this(executablePath, System.getProperty("user.dir"));
    }

    public Cli(String executablePath, String directory) {
        this.directory = directory;
        this.executablePath = Objects.requireNonNull(executablePath);
    }

    protected String buildCommand() {
        StringBuilder builder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = options.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> option = it.next();

            String name = option.getKey();
            String value = option.getValue();

            if (value == null) value = "";

            String optionFormatted = String.format("%s %s", name, value).trim();
            builder.append(optionFormatted).append(" ");

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
    public CliResult execute() throws CliException {
        return execute(new ProgressCallbackImpl());
    }
    public CliResult execute(ProgressCallback callback) throws CliException {
        String command = buildCommand();
        log.info(command);
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
            throw new CliException(e);
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
            log.debug("Processing");
            stdOutProcessor.join();
            stdErrProcessor.join();
            log.debug("Processed");
            if (callback.isReady()) {
                log.info("Destroying process {}", process.pid());
                process.destroy();
                exitCode = -1;
            } else {
                log.info("Waiting for process");
                exitCode = process.waitFor();
            }
        } catch (InterruptedException e) {
            // process exited for some reason
            throw new CliException(e);
        }

        String out = outBuffer.toString();
        String err = errBuffer.toString();

        if (exitCode > 0) {
            throw new CliException(err, exitCode);
        }

        return new CliResult(out, err, command, callback.getElapsed(), exitCode);
    }
}
