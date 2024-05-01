package com.dcat23.cli;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class ProgressCallbackImpl implements ProgressCallback {
    private final Instant start = Instant.now();

    /**
     * Recieves each line from the running process
     * @param line
     */
    @Override
    public void processLine(String line) {
        log.debug(line);
    }

    /**
     * How much time passed running the process
     *
     */
    @Override
    public String getElapsed() {
        Instant now = Instant.now();
        Duration duration = Duration.between(start, now);
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long days = absSeconds / (24 * 3600);
        long hours = (absSeconds % (24 * 3600)) / 3600;
        long minutes = ((absSeconds % (24 * 3600)) % 3600) / 60;
        long secs = ((absSeconds % (24 * 3600)) % 3600) % 60;

        StringBuilder builder = new StringBuilder();
        if (seconds < 0) {
            builder.append("-");
        }
        if (days != 0) {
            builder.append(days).append(" days ");
        }
        if (hours != 0) {
            builder.append(hours).append(" hours ");
        }
        if (minutes != 0) {
            builder.append(minutes).append(" minutes ");
        }
        if (secs != 0 || (days == 0 && hours == 0 && minutes == 0)) {
            builder.append(secs).append(" seconds");
        }
        return builder.toString();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isReady() {
        return false;
    }
}
