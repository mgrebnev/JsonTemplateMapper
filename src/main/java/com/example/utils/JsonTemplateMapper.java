package com.example.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTemplateMapper {
    private static final String lineParseRegex = "['$']['('](\\d+|\\w+|(|[')']|['(']|['-']|['_']|))+[')']";
    
    private static final String lSeparator = "$(";
    private static final String rSeparator  =  ")"; 
    
    private JsonTemplateMapper() {
    }
    
    public static List<String> map(File jsonTemplate, Object flatEntity) throws Exception {
        return fillJsonTemplate(readAllLines(jsonTemplate), flatEntity);
    }

    public static List<String> map(String jsonTemplate, Object flatEntity) throws Exception {
        return fillJsonTemplate(Arrays.asList(separateString(jsonTemplate, "\n")), flatEntity);
    }
    
    //TODO filter by primitve/non-primitive values
    private static List<String> fillJsonTemplate(List<String> jsonLines, Object flatEntity) throws Exception {
        Map<String, Object> clazzMethods = getMethodsWithReturnValuesByObject(flatEntity);
        for (int i = 0; i < jsonLines.size(); i++){
            String currentLine = jsonLines.get(i);
            for (Map.Entry<String,Object> clazzMethod: clazzMethods.entrySet()){
                String wrappedField = lSeparator + clazzMethod.getKey() + rSeparator;
                if (isStringContainsWrappedField(currentLine,wrappedField)){
                    Object currentRefundValue = clazzMethod.getValue();
                    String modifiedLine = currentLine.replaceAll(
                            lineParseRegex, "\"" + currentRefundValue + "\""
                    );
                    jsonLines.set(i, modifiedLine);
                    break;
                }
            }
        }
        return jsonLines;
    }
    
    private static Map<String, Object> getMethodsWithReturnValuesByObject(Object object) throws Exception{
        Map<String,Object> objectMethodsMap = new HashMap<>();
        if (object != null){
            Class currentClazz = object.getClass();
            Method[] clazzMethods = currentClazz.getMethods();
            for (Method currentMethod: clazzMethods){
                if (isPOJOGetter(currentMethod)) {
                    String adaptedMethodName = adaptMethodName(currentMethod.getName());
                    Object refundValue = currentMethod.invoke(object);
                    objectMethodsMap.put(adaptedMethodName, refundValue);
                }
            }
        }
        return objectMethodsMap;
    }
    
    // TODO need efficient way
    private static boolean isStringContainsWrappedField(String string, String wrappedField){
        return string.contains(wrappedField);
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

    private static String[] separateString(String string, String separator){
        return string.split(separator);
    }
    
    private static List<String> readAllLines(File file){
        try {
            return Files.readAllLines(file.toPath());
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
