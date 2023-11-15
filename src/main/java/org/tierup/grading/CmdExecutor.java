package org.tierup.grading;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CmdExecutor {
    public int executeCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        return process.waitFor();
    }
}
