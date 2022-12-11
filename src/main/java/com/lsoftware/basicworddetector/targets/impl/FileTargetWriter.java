package com.lsoftware.basicworddetector.targets.impl;

import com.lsoftware.basicworddetector.model.Candidate;
import com.lsoftware.basicworddetector.targets.Writer;
import com.lsoftware.basicworddetector.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

@Component
@Slf4j
public class FileTargetWriter implements Writer<Candidate> {
    @Autowired
    private FileWriter suspiciousWordsWriter;

    @Override
    public void write(Candidate candidate) throws IOException {
        log.info(Constants.SENDING_SUSPICIOUS);
        StringJoiner joiner = new StringJoiner(Constants.COLON_SPACE);
        joiner.add(candidate.getConversationId());
        joiner.add(candidate.getMessage());
        suspiciousWordsWriter.write(joiner.toString());
        suspiciousWordsWriter.write(Constants.NEW_LINE);
    }

    @Override
    public void close() throws IOException {
        suspiciousWordsWriter.flush();
        suspiciousWordsWriter.close();
    }
}
