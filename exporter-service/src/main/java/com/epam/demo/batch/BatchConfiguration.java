package com.epam.demo.batch;

import com.epam.demo.DTO.MidBeanDTO;
import com.epam.demo.batch.listener.JobCompletionNotificationListener;
import com.epam.demo.batch.reader.MidServiceRestItemReader;
import com.epam.demo.batch.writer.CSVItemWriterBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zmazula on 11/11/16.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    CSVItemWriterBuilder<MidBeanDTO> csvItemWriterBuilder;

    @Bean
    public ItemReader reader() {
        return new MidServiceRestItemReader();
    }

//    @Bean
//    public ItemProcessor processor() {
//        return new ItemProcessor();
//    }

    @Bean
    public ItemWriter writer() {
        return csvItemWriterBuilder.getWriter();
    }

    @Bean
    public Job exportMidJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("exportMidJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<MidBeanDTO, MidBeanDTO> chunk(10)
                .reader(reader())
                //.processor(processor())
                .writer(writer())
                .build();
    }

}
