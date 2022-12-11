package com.lsoftware.basicworddetector.threads.consumer;

import com.lsoftware.basicworddetector.model.Candidate;
import com.lsoftware.basicworddetector.providers.SequentialProvider;
import com.lsoftware.basicworddetector.providers.impl.ChatLineProvider;
import com.lsoftware.basicworddetector.queues.CandidatesQueue;
import com.lsoftware.basicworddetector.threads.CustomRunnable;
import com.lsoftware.basicworddetector.util.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class CandidatesLoader implements CustomRunnable {
    private CandidatesQueue queue;
    private SequentialProvider provider;
    private volatile boolean running = true;

    public CandidatesLoader(CandidatesQueue queue, SequentialProvider provider){
        this.queue = queue;
        this.provider = provider;
    }

    @Override
    public void run() {
        while (running) {
            Optional<Candidate> candidate = provider.getNext();
            if (!candidate.isPresent()) {
                queue.terminate();
                log.info(Constants.LOADER_NO_MORE_CANDIDATES);
                return;
            }
            queue.add(candidate.get());
        }
    }

    @Override
    public void terminate(){
        running = false;
    }
}

