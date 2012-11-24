package org.jan.common.utils.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * utilities for parsing Json.
 *
 * @author Jan.Wang
 * @since 1.0
 */
public class JsonUtils {

    /**
     * Converts the specified object into a string with format 'json'.
     * @param <T>
     * @param obj
     * @return
     */
    public static <T> String objToJson(T obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    /**
     * Converts the specified object into a out stream with format 'json'.
     * @param <T>
     * @param obj
     * @param out
     */
    public static <T> void objToJson(T obj, OutputStream out) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(out, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the specified json string to a specified type object.
     * @param <T>
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T jsonToObj(String jsonString, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(jsonString, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    /**
     * Converts the specified json input stream to a specified type object.
     * @param <T>
     * @param input
     * @param clazz
     * @return
     */
    public static <T> T jsonToObj(InputStream input, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(input, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
