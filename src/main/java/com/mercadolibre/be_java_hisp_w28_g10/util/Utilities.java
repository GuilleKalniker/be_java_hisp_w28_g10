package com.mercadolibre.be_java_hisp_w28_g10.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class Utilities {
    @Autowired
    private ObjectMapper mapper;

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
}
