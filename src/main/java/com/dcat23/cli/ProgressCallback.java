package com.dcat23.cli;

public interface ProgressCallback {

    void processLine(String line);

    /**
     * Time elapsed in seconds
     *
     * @return seconds
     */
    String getElapsed();

    /**
     * Flag to stop processing
     * @return
     */
    boolean isReady();
}
