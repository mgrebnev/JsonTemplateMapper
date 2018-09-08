package com.solightingstats.mapper.converter.impl;

import com.solightingstats.mapper.converter.ValueConverter;

public class StandardValueConverter implements ValueConverter {
    @Override
    public String convert(Object value) {
        if (value instanceof String){
            return "\"" + value.toString() + "\"";
        }
        return value == null ? "null" : value.toString();
    }
}
