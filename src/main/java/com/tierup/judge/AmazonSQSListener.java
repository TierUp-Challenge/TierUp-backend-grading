package com.tierup.judge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class AmazonSQSListener {
    private final CmdExecutor cmdExecutor;
    private final ObjectMapper objectMapper;


    public AmazonSQSListener(CmdExecutor cmdExecutor) {
        this.cmdExecutor = cmdExecutor;
        this.objectMapper = new ObjectMapper();
    }

    @SqsListener(value = "${application.amazon.sqs.queue-name}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
    public void messageListener(String message) throws InterruptedException, IOException {
        log.debug("Listener: {}", message);
        Map<String, String> submission = objectMapper.readValue(message, new TypeReference<>() {
        });
        String code = submission.get("code");
        String input = submission.get("input");
        String answer = submission.get("answer");
        log.debug("\ncode: {}", code);
        log.debug("input: {}", input);
        log.debug("answer: {}", answer);
        FileUtils.StringToFile(code, "main.py");
        FileUtils.StringToFile(input, "input.txt");
        FileUtils.StringToFile(answer, "answer.txt");

        int cpExitCode1 = cmdExecutor.executeCommand("docker cp ./input.txt python-judge1:/python");
        int cpExitCode2 = cmdExecutor.executeCommand("docker cp ./main.py python-judge1:/python");
        int startExitCode = cmdExecutor.executeCommand("docker start python-judge1");
        int outputExitCode = cmdExecutor.executeCommand("docker cp python-judge1:/python/output.txt ./output.txt");

        boolean isAnswer = FileUtils.compare("answer.txt", "output.txt");
        if (isAnswer) {
            log.debug("Accepted");
        } else {
            log.debug("Wrong answer");
        }

    }
}
