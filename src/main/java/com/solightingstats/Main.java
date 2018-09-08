package com.solightingstats;

import com.solightingstats.mapper.TemplateMapper;
import com.solightingstats.mapper.converter.ValueConverter;
import com.solightingstats.mapper.converter.impl.StandardValueConverter;
import com.solightingstats.mapper.impl.JsonTemplateMapper;
import com.solightingstats.model.Lawsuit;

import java.io.File;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws Exception {
        Lawsuit lawsuit = new Lawsuit();
        lawsuit.setId(1);
        lawsuit.setAccountId(7476232);
        lawsuit.setContractNumber(null);
        lawsuit.setAccountUsername("vladimirmihailovich");
        File file = getFileFromResources("files/example.json");
        ValueConverter valueConverter = new StandardValueConverter();
        TemplateMapper mapper = new JsonTemplateMapper(valueConverter);
        mapper.toJson(file,lawsuit).forEach(System.out::println);
    }
    
    private static File getFileFromResources(String path) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        return new File(classLoader.getResource(path).toURI());
    }
}
