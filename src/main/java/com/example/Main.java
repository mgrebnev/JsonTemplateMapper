package com.example;

import com.example.model.Lawsuit;
import com.example.utils.JsonTemplateMapper;

import java.io.File;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws Exception {
        Lawsuit lawsuit = new Lawsuit();
        lawsuit.setId(1);
        lawsuit.setAccountId(7476232);
        lawsuit.setContractNumber("85384934234");
        lawsuit.setAccountUsername("vladimirmihailovich");
        File file = getFileFromResources("files/example.json");
        JsonTemplateMapper.map(file,lawsuit).forEach(System.out::println);
    }
    
    private static File getFileFromResources(String path) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        return new File(classLoader.getResource(path).toURI());
    }
}
