package com.rbc.bbp0.step;

import com.rbc.bbp0.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

public class ExchangeRateReader implements ItemReader<Student> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateReader.class);

    private final String apiUrl;

    private int nextStudentIndex;
    private List<Student> studentData;

    public ExchangeRateReader(String apiUrl) {
        this.apiUrl = apiUrl;
        nextStudentIndex = 0;
    }

    @Override
    public Student read() throws Exception {
        LOGGER.info("Reading the information of the next student");

        if (studentDataIsNotInitialized()) {
            studentData = fetchStudentDataFromAPI();
        }

        Student nextStudent = null;
        if (nextStudentIndex < studentData.size()) {
            nextStudent = studentData.get(nextStudentIndex);
            nextStudentIndex++;
        }
        else {
            nextStudentIndex = 0;
            studentData = null;
        }
        LOGGER.info("Found student: {}", nextStudent);
        return nextStudent;
    }

    private boolean studentDataIsNotInitialized() {
        return this.studentData == null;
    }

    private List<Student> fetchStudentDataFromAPI() {
        LOGGER.debug("Fetching student data from an external API by using the url: {}", apiUrl);
        WebClient.Builder builder = WebClient.builder();
        String url="https://dummyjson.com/products/1";
        String str = builder.build().get().uri(url).retrieve().bodyToMono(String.class).block();
        System.out.println("str output message ="+str);

        Student studentData = builder.build().get().uri(apiUrl).retrieve().bodyToMono(Student.class).block();

        LOGGER.debug("Found {} students", studentData);

        return Arrays.asList(studentData);
    }
}
