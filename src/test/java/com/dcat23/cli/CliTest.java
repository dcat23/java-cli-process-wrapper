package com.dcat23.cli;

import com.dcat23.cli.exceptions.CliException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CliTest {

    @Test
    void CLi_execute_shouldWork() throws CliException {
        Cli ls = new Cli("ls");
        CliResult result = ls.execute();
        assertTrue(result.out().contains("pom.xml"));
    }
}