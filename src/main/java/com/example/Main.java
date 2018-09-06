package com.example;

import com.example.models.Lawsuit;
import com.example.utils.JsonTemplateMapper;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Lawsuit lawsuit = new Lawsuit();
        lawsuit.setId(2);
        lawsuit.setAccountId(1923129);
        lawsuit.setAccountUsername("vladimirmihailovich");
        lawsuit.setContractNumber("4762992839283982");

        /*File file = new File("example.txt");
        JsonTemplateMapper.map(file,lawsuit).forEach(System.out::println);*/
        Integer length = "Hello, World\n Hello\n Petr".split("\n").length;
        System.out.println(length);
    }
}
