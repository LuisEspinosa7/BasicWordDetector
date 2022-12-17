package com.lsoftware.basicworddetector.util;


public interface Constants {
    String LOADING_MESSAGE = "Loading...";
    String COLON = ",";
    String COLON_SPACE = ", ";
    String NEW_LINE = "\n";
    String EMPTY = " ";
    String KEY_OPENED = "[";
    String KEY_CLOSED = "]";
    String FORBBIDEN_FILE_NOT_FOUND = "The forbbiden words file was not found.";
    String CANDIDATES_SOURCE_FILE_NOT_FOUND = "The candidates source file was not found.";
    String SUSPICIOUS_TARGET_FILE_NOT_FOUND = "The suspicious target file was not found.";
    String SENDING_SUSPICIOUS = "Sending suspicious record to a file";
    String LOADER_NO_MORE_CANDIDATES = "CandidatesLoader::There are no more candidates!!!";

    String DETECTOR_NO_MORE_CANDIDATES = "SuspiciousDetector::There are no more candidates FROM THE QUEUE, terminating.";
    String DETECTOR_RUNNING = "SuspiciousDetector::Running...";
    String DETECTOR_FOUND_FORBBIDEN_WORD = "***** FOUND WORD **** - ConversationId: {}";

    String REPORTER_NO_MORE_SUSPICIOUS_WORDS = "SuspiciousReporter::No more suspicious words in the QUEUE.";
    String REPORTER_WRITING_NEW_SUSPICIOUS = "Writing to file a new suspicious";
    String REPORTER_ERROR_WRITING_NEW_SUSPICIOUS = "Error writing to file a new suspicious";
    String REPORTER_ERROR_CLOSING_FILE = "Error closing results file.";

    String MAIN_TOTAL_TIME = "TOTAL TIME: {}";

    String MONITOR_QUEUE1_STATUS = "Monitor::Queue-1 status {}";
    String MONITOR_QUEUE2_STATUS = "Monitor::Queue-1 status {}";

}
