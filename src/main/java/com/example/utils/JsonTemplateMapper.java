package com.example.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTemplateMapper {
    private static final String lineParseRegex = "['$']['('](\\d+|\\w+|(['*']|[')']|['(']|['-']|['_']|[';']))+[')']";

    private JsonTemplateMapper() {
    }

    // TODO refactor
    public static List<String> map(File jsonTemplate, Object flatEntity) throws Exception {
        Map<String, Object> clazzMethods = getMethodsWithReturnValuesByClass(flatEntity);
        List<String> templateLines = getLines(jsonTemplate);
        for (int i = 0; i < templateLines.size(); i++){
            String currentLine = templateLines.get(i);
            for (Map.Entry entry: clazzMethods.entrySet()){
                if (currentLine.contains("$(" + entry.getKey() + ")")){
                    String modifiedLine = currentLine.replaceAll(lineParseRegex, "\"" + entry.getValue() + "\"");
                    templateLines.set(i, modifiedLine);
                    break;
                }
            }
        }
        return templateLines;
    }


    private static Map<String, Object> getMethodsWithReturnValuesByClass(Object object) throws Exception{
        Map<String,Object> methods = new HashMap<>();
        Class currentClazz = object.getClass();
        if (currentClazz != null){
            Method[] clazzMethods = currentClazz.getMethods();
            for (Method currentMethod: clazzMethods){
                if (isPOJOGetter(currentMethod)) {
                    String adaptedMethodName = adaptMethodName(currentMethod.getName());
                    Object methodReturnValue = currentMethod.invoke(object);
                    methods.put(adaptedMethodName, methodReturnValue);
                }
            }
        }
        return methods;
    }

    private static List<String> getLines(File file){
        try {
            return Files.readAllLines(file.toPath());
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private static boolean isPOJOGetter(Method method){
        return method.getName().startsWith("get") && !method.getReturnType().equals(Class.class);
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
