/*
 * Copyright (c) 2019 - 2020 上海石指（健康）科技有限公司. All Rights Reserved
 */

package com.sdrin.lib.hospital.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 上海石指(健康)科技有限公司 sdrin.com 2020/6/27 8:18 上午
 * 根据 jackson 封装。
 *
 * @author 胡树铭
 */
public class JsonUtil {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module

        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(df);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        //if enum values are allowed to be serialized/deserialized as numbers:
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        //defines if the null values for primitive values are allowed:
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        //Through the configure method we can extend the default process to ignore the new fields:
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 解析对象为json
     *
     * @param object 任意对象
     * @return 返回string json
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json格式转为特定java对象
     *
     * @param json   参数string
     * @param tClass 转为java
     * @param <T>    java类型
     * @return 返回java对象
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json 的 数组对象，解析为 list 对象
     *
     * @param json   参数
     * @param eClass 数组java对象
     * @param <E>    数组java
     * @return 返回java数组
     */
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将map object json 转为map对象。
     *
     * @param json   参数
     * @param kClass key java 对象
     * @param vClass value java 对象
     * @param <K>    java对象
     * @param <V>    java对象
     * @return 返回map对象
     */
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 原生的解析复杂的json对象
     *
     * @param json 参数
     * @param type 复杂的java对象
     * @param <T>  对象
     * @return 返回java
     */
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
