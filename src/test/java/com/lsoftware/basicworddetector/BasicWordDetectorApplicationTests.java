package com.lsoftware.basicworddetector;

import com.lsoftware.basicworddetector.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(properties = {"app.capacity.queue.candidates = 3",
        "app.capacity.queue.suspicious = 3", "app.threads.analyzers.number = 2",
        "app.input.file.forbidden = src/test/resources/files/forbbiden.csv",
        "app.input.file.source = src/test/resources/files/topical_chat_short.csv",
        "app.output.file.target = src/test/resources/files/result.csv"})
class BasicWordDetectorApplicationTests {

    private BasicWordDetectorApplication underTest;

    @BeforeEach
    public void setup(){
        underTest = new BasicWordDetectorApplication();
    }


    @Test
    void testContext() throws Exception {
        List<String> resultLines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/test/resources/files/result.csv"))){
            String line;
            while((line = br.readLine()) != null){
                resultLines.add(line);
            }
        } catch (FileNotFoundException e) {
            Assertions.fail(Constants.SUSPICIOUS_TARGET_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Only two forbbiden word on the result file
        Assertions.assertEquals(2, resultLines.size());
    }

}
