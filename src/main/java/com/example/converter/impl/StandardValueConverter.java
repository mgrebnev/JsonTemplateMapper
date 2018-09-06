package com.example.converter.impl;

import com.example.converter.ValueConverter;

public class StandardValueConverter implements ValueConverter{
    @Override
    public String convert(Object value) {
        if (value instanceof String){
            return "\"" + value.toString() + "\"";
        }
        return value.toString();
    }
}
