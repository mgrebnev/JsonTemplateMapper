package com.example.mapper.converter.impl;

import com.example.mapper.converter.ValueConverter;

public class StandardValueConverter implements ValueConverter {
    @Override
    public String convert(Object value) {
        if (value instanceof String){
            return "\"" + value.toString() + "\"";
        }
        return value == null ? "null" : value.toString();
    }
}
