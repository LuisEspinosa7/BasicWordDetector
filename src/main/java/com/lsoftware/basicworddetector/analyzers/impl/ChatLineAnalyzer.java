package com.lsoftware.basicworddetector.analyzers.impl;


import com.lsoftware.basicworddetector.analyzers.Analyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ChatLineAnalyzer implements Analyzer<String, Boolean> {
    @Autowired
    private List<String> forbbidenWords;

    @Override
    public Boolean analyze(String chatLine) {
        for (String target : forbbidenWords) {
            if (chatLine.contains(target)){
                return true;
            }
        }
        return false;
    }
}
