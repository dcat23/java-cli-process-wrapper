package com.dcat23.cli;

public record CliResult(
        String out,
        String err,
        String command,
        String elapsed,
        int exitCode
) { }
