package pro.macchiato.cli;

public interface ProgressCallback {

    void processLine(String line);

    /**
     * Time elapsed in seconds
     * @return seconds
     */
    long getElapsed();
}
