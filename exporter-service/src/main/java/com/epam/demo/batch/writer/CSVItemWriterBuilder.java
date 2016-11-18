package com.epam.demo.batch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by zmazula on 15/11/16.
 */
@Configuration
public class CSVItemWriterBuilder<T> {

    @Value("${output-file}")
    private String outputFile;

    @Value("${csv-delimiter}")
    private String csvDelimiter;

    @Value("${csv-columns}")
    private String csvColumns;

    private FlatFileItemWriter<T> writer;

    private void initializeCsvWriter(){
        FieldExtractor<T> fieldExtractor = initializeFieldExtractor();
        DelimitedLineAggregator<T> delimitedLineAggregator = initializeLineAggregator(fieldExtractor);
        initializeItemWriter(delimitedLineAggregator);
    }

    private void initializeItemWriter(DelimitedLineAggregator<T> delimitedLineAggregator) {
        writer = new FlatFileItemWriter<T>();
        writer.setResource(new FileSystemResource(outputFile));
        writer.setLineAggregator(delimitedLineAggregator);
    }

    private DelimitedLineAggregator<T> initializeLineAggregator(FieldExtractor<T> fieldExtractor) {
        DelimitedLineAggregator<T> delimitedLineAggregator = new DelimitedLineAggregator<>();
        delimitedLineAggregator.setDelimiter(csvDelimiter);
        delimitedLineAggregator.setFieldExtractor(fieldExtractor);
        return delimitedLineAggregator;
    }

    private FieldExtractor<T> initializeFieldExtractor() {
        BeanWrapperFieldExtractor<T> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        String[] csvColumns = getCsvColumnsFromProperties();
        beanWrapperFieldExtractor.setNames(csvColumns);
        return beanWrapperFieldExtractor;
    }

    private String[] getCsvColumnsFromProperties() {
        return csvColumns.split(",");
    }

    public ItemWriter getWriter(){
        if (writer==null) {
            initializeCsvWriter();
        }
        return writer;
    }
}
