package com.lsoftware.basicworddetector;

import com.lsoftware.basicworddetector.analyzers.Analyzer;
import com.lsoftware.basicworddetector.model.Candidate;
import com.lsoftware.basicworddetector.providers.SequentialProvider;
import com.lsoftware.basicworddetector.queues.CandidatesQueue;
import com.lsoftware.basicworddetector.targets.Writer;
import com.lsoftware.basicworddetector.threads.CustomRunnable;
import com.lsoftware.basicworddetector.threads.business.SuspiciousDetector;
import com.lsoftware.basicworddetector.threads.producer.SuspiciousReporter;
import com.lsoftware.basicworddetector.threads.consumer.CandidatesLoader;
import com.lsoftware.basicworddetector.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class BasicWordDetectorApplication implements CommandLineRunner {
    @Value("${app.capacity.queue.candidates}")
    private String candidatesQueueCapacity;
    @Value("${app.capacity.queue.suspicious}")
    private String suspiciousQueueCapacity;
    @Value("${app.threads.analyzers.number}")
    private String analyzerThreadsNumber;
    private static final int QUEUE_1_INDEX = 0;
    private static final int QUEUE_2_INDEX = 1;

    @Autowired
    private SequentialProvider<Candidate, Scanner> chatLineProvider;

    @Autowired
    private Analyzer<String, Boolean> chatLineAnalyzer;

    @Autowired
    private Writer<Candidate> fileTarget;

    public static void main(String[] args) {
        SpringApplication.run(BasicWordDetectorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(Constants.LOADING_MESSAGE);
        Thread.sleep(2000);

        CandidatesQueue candidatesQueue = new CandidatesQueue(Integer.valueOf(candidatesQueueCapacity));
        CandidatesQueue suspiciousQueue = new CandidatesQueue(Integer.valueOf(suspiciousQueueCapacity));

        CustomRunnable loaderTemplate = new CandidatesLoader(candidatesQueue, chatLineProvider);
        CustomRunnable detectorTemplate = new SuspiciousDetector(candidatesQueue, suspiciousQueue, chatLineAnalyzer);
        CustomRunnable reporterTemplate = new SuspiciousReporter(fileTarget, suspiciousQueue);

        List<CustomRunnable> runnables = new ArrayList<>();
        runnables.add(loaderTemplate);
        runnables.add(detectorTemplate);
        runnables.add(reporterTemplate);

        List<CandidatesQueue> queues = new ArrayList<>();
        queues.add(candidatesQueue);
        queues.add(suspiciousQueue);

        long start = System.currentTimeMillis();
        startThreads(loaderTemplate, detectorTemplate, reporterTemplate);
        watchQueues(queues, runnables);
        long end = System.currentTimeMillis();
        log.info(Constants.MAIN_TOTAL_TIME, (end - start));
    }

    private void startThreads(CustomRunnable loaderTemplate,
                                      CustomRunnable detectorTemplate, CustomRunnable reporterTemplate){

        List<Thread> threads = new ArrayList<>();
        Thread loader1 = new Thread(loaderTemplate);
        threads.add(loader1);

        for (int i = 0; i < Integer.valueOf(analyzerThreadsNumber); i++) {
            Thread detector = new Thread(detectorTemplate);
            threads.add(detector);
        }

        Thread reporter1 = new Thread(reporterTemplate);
        threads.add(reporter1);

        for (Thread t: threads) {
            t.start();
        }
    }

    private void watchQueues(List<CandidatesQueue> queues, List<CustomRunnable> threads){
        try {
            Thread.sleep(2000); // Waiting two seconds, for the queues to have at least 1 value
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while(true){ // Loop for monitoring the threads
            log.debug(Constants.MONITOR_QUEUE1_STATUS, queues.get(QUEUE_1_INDEX).isTerminate());
            log.debug(Constants.MONITOR_QUEUE2_STATUS, queues.get(QUEUE_2_INDEX).isTerminate());
            if (queues.get(QUEUE_1_INDEX).isTerminate() &&
                    queues.get(QUEUE_2_INDEX).isTerminate()) {
                for (CustomRunnable thread: threads) {
                    thread.terminate();
                }
                break; // Queues emptied.
            }
        }
    }
}
