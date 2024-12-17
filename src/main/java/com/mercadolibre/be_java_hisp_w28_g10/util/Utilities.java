package com.mercadolibre.be_java_hisp_w28_g10.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Utilities {

    private ObjectMapper mapper;

    public Utilities() {
        this.mapper = new ObjectMapper();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(dateFormatter));
        mapper.registerModule(javaTimeModule);
    }

    public <T, U> T convertValue(U origin, Class<T> targetClass) {
        return mapper.convertValue(origin, targetClass);
    }

    public <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) throws IllegalArgumentException {
        if (fromValue == null) {
            throw new IllegalArgumentException("fromValue must not be null");
        }
        return mapper.convertValue(fromValue, toValueTypeRef);
    }

    public <T> T convertValue(Object fromValue, JavaType toValueType) throws IllegalArgumentException {
        if (fromValue == null) {
            throw new IllegalArgumentException("fromValue must not be null");
        }
        return mapper.convertValue(fromValue, toValueType);
    }

    public <T> T readValue(InputStream src, TypeReference<T> valueTypeRef) throws IOException {
        if (src == null) {
            throw new IllegalArgumentException("InputStream must not be null");
        }
        return mapper.readValue(src, valueTypeRef);
    }

    public <T> String generateCsv(List<T> data, String[] headers) {

        try {
        StringWriter writer = new StringWriter();

        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(headers);

        StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();

        beanToCsv.write(data);

        return writer.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV");
        }
    }
}
