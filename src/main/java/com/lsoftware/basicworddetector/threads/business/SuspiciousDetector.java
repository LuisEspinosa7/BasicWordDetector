package com.lsoftware.basicworddetector.threads.business;

import com.lsoftware.basicworddetector.analyzers.Analyzer;
import com.lsoftware.basicworddetector.model.Candidate;
import com.lsoftware.basicworddetector.queues.CandidatesQueue;
import com.lsoftware.basicworddetector.threads.CustomRunnable;
import com.lsoftware.basicworddetector.util.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class SuspiciousDetector implements CustomRunnable {
    private CandidatesQueue candidatesQueue;
    private CandidatesQueue suspiciousQueue;
    private Analyzer<String, Boolean> analyzer;
    private volatile boolean running = true;

    public SuspiciousDetector(CandidatesQueue candidatesQueue,
                              CandidatesQueue suspiciousQueue, Analyzer analyzer){
        this.candidatesQueue = candidatesQueue;
        this.suspiciousQueue = suspiciousQueue;
        this.analyzer = analyzer;
    }

    @Override
    public void run() {
        while (running) {
            Optional<Candidate> candidate = candidatesQueue.remove();
            if (!candidate.isPresent()) {
                suspiciousQueue.terminate();
                log.info(Constants.DETECTOR_NO_MORE_CANDIDATES);
                break;
            }

            log.info(Constants.DETECTOR_RUNNING);

            if (analyzer.analyze(candidate.get().getMessage())){
                log.info(Constants.DETECTOR_FOUND_FORBBIDEN_WORD, candidate.get().getConversationId());
                suspiciousQueue.add(candidate.get());
            }
        }
    }

    @Override
    public void terminate(){
        running = false;
    }
}

