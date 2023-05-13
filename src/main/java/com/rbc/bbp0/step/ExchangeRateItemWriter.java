package com.rbc.bbp0.step;

import com.rbc.bbp0.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ExchangeRateItemWriter implements ItemWriter<Student> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateItemWriter.class);

    @Override
    public void write(List<? extends Student> list) throws Exception {
        LOGGER.info(">>>> Writing students: {}", list);
    }
}
