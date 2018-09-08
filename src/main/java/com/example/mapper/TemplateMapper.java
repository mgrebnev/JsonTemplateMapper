package com.example.mapper;

import com.example.mapper.converter.ValueConverter;

import java.io.File;
import java.util.List;

public interface TemplateMapper {
    List<String> toJson(File jsonTemplate, Object object) throws Exception;
    List<String> toJson(String jsonTemplate, Object object) throws Exception;
    ValueConverter getValueConverter();
}
