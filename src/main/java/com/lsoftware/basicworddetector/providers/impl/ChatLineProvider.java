package com.lsoftware.basicworddetector.providers.impl;

import com.lsoftware.basicworddetector.model.Candidate;
import com.lsoftware.basicworddetector.providers.SequentialProvider;
import com.lsoftware.basicworddetector.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

@Component
@Slf4j
public class ChatLineProvider extends SequentialProvider<Candidate, Scanner> {

    public ChatLineProvider(Scanner sourceCandidateWords){
        super(sourceCandidateWords);
    }

    @Override
    protected Optional<Candidate> processNext() {
        if (!source.hasNext()){
            return Optional.empty();
        }
        String[] nextLine = source.nextLine().split(Constants.COLON);
        String[] messageArray = Arrays.copyOfRange(nextLine, 1, nextLine.length);
        String message = Arrays.toString(messageArray).replace(Constants.KEY_OPENED, Constants.EMPTY).replace(Constants.KEY_CLOSED, Constants.EMPTY);
        log.info("CANDIDATE: " + message);

        Candidate candidate = new Candidate(nextLine[0], message);
        return Optional.of(candidate);
    }
}
