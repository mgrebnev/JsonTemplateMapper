package com.example;

import com.example.models.Lawsuit;
import com.example.utils.JsonTemplateMapper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Lawsuit lawsuit = new Lawsuit();
        lawsuit.setId(2);
        lawsuit.setAccountId(1923129);
        lawsuit.setAccountUsername("vladimirmihailovich");
        lawsuit.setContractNumber("4762992839283982");

        File file = new File("example.txt");
        JsonTemplateMapper.map(file,lawsuit).forEach(System.out::println);
    }

    //TODO remove
    private static String getTextBetweenTwoParts(String text, String startPart, String secondPart){
        if (text != null){
            Integer startSymbolPosition = text.indexOf(startPart);
            Integer endSymbolPosition = text.indexOf(secondPart);
            return text.substring(startSymbolPosition + startPart.length(), endSymbolPosition);
        }else {
            return text;
        }
    }

    private static Map<String, Object> getMethodsWithReturnValuesByClass(Object object, Class clazz) throws Exception{
        Map<String,Object> methods = new HashMap<>();
        if (clazz != null){
            Method[] clazzMethods = clazz.getMethods();
            for (Method currentMethod: clazzMethods){
                String currentMethodName = currentMethod.getName();
                if (currentMethodName.startsWith("get") && !currentMethod.getReturnType().equals(Class.class)) {
                    String adaptedMethodName = adaptMethodName(currentMethodName);
                    Object methodReturnValue = currentMethod.invoke(object);
                    methods.put(adaptedMethodName, methodReturnValue);
                }
            }
        }
        return methods;
    }

    private static String adaptMethodName(String methodName){
        String adaptedMethodName = removeExpressionFromString(methodName, "get");
        String firstSymbolInLowerCase = toLowerCase(adaptedMethodName, 1);
        return firstSymbolInLowerCase + adaptedMethodName.substring(1, adaptedMethodName.length());
    }

    private static String removeExpressionFromString(String string, String expression){
        return string.replaceAll(expression, "");
    }

    private static String toLowerCase(String string, int symbolIndex){
        return string.substring(symbolIndex - 1, symbolIndex).toLowerCase();
    }
}
