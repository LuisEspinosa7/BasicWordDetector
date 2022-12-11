package com.lsoftware.basicworddetector.threads.producer;

import com.lsoftware.basicworddetector.model.Candidate;
import com.lsoftware.basicworddetector.queues.CandidatesQueue;
import com.lsoftware.basicworddetector.targets.Writer;
import com.lsoftware.basicworddetector.threads.CustomRunnable;
import com.lsoftware.basicworddetector.util.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class SuspiciousReporter implements CustomRunnable {
    private Writer target;
    private CandidatesQueue suspiciousQueue;
    private volatile boolean running = true;

    public SuspiciousReporter(Writer target, CandidatesQueue suspiciousQueue) {
        this.target = target;
        this.suspiciousQueue = suspiciousQueue;
    }

    @Override
    public void run() {
        while (running) {
            Optional<Candidate> suspicious = suspiciousQueue.remove();
            if (!suspicious.isPresent()) {
                log.info(Constants.REPORTER_NO_MORE_SUSPICIOUS_WORDS);
                break;
            }

            try {
                log.info(Constants.REPORTER_WRITING_NEW_SUSPICIOUS);
                target.write(suspicious.get());
            } catch (IOException ex) {
                log.info(Constants.REPORTER_ERROR_WRITING_NEW_SUSPICIOUS);
            }
        }
        try {
            target.close();
        } catch (IOException ex) {
            log.info(Constants.REPORTER_ERROR_CLOSING_FILE);
        }
    }

    @Override
    public void terminate(){
        running = false;
    }
}

