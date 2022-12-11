package com.lsoftware.basicworddetector.config;

import com.lsoftware.basicworddetector.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
public class AppConfig {
    @Value("${app.input.file.forbidden}")
    private String forbbidenWordsFile;

    @Value("${app.input.file.source}")
    private String candidatesSourceFile;

    @Value("${app.output.file.target}")
    private String suspiciousTargetFile;

    @Bean
    public List<String> forbbidenWords(){
        List<String> words = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(forbbidenWordsFile))){
            String line;
            while((line = br.readLine()) != null){
                words.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(Constants.FILE_NOT_FOUND, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }

    @Bean
    public Scanner sourceCandidateWords(){
        Scanner scanner;
        try {
            File inputFile = new File(candidatesSourceFile);
            scanner = new Scanner(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(Constants.FILE_NOT_FOUND, e);
        }
        return scanner;
    }


    @Bean
    public FileWriter suspiciousWordsWriter(){
        FileWriter writer;
        try {
            writer = new FileWriter(suspiciousTargetFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(Constants.FILE_NOT_FOUND, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }
}
