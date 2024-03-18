package pro.macchiato.cli;

public record CliResult(String out, String err, String command, String elapsed, int exitCode) {
}
