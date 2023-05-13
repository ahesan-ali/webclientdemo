package com.rbc.bbp0.config;

import com.rbc.bbp0.model.Student;
import com.rbc.bbp0.step.ExchangeRateItemWriter;
import com.rbc.bbp0.step.ExchangeRateReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ExchangeRateJobConfig {
    private static final String PROPERTY_REST_API_URL = "rest.api.url";

    @Bean
    public ItemReader<Student> itemReader(Environment environment) {
        return new ExchangeRateReader(environment.getRequiredProperty(PROPERTY_REST_API_URL));
    }

    @Bean
    public ItemWriter<Student> itemWriter() {
        return new ExchangeRateItemWriter();
    }


    @Bean
    public Step exampleJobStep(ItemReader<Student> reader,
                               ItemWriter<Student> writer,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("exampleJobStep")
                .<Student, Student>chunk(10)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Job exampleJob(Step exampleJobStep,
                          JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("exampleJob")
                .incrementer(new RunIdIncrementer())
                .flow(exampleJobStep)
                .end()
                .build();
    }
}
