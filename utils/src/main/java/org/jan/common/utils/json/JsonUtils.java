package org.jan.common.utils.json;

import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {

    public static String objToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, obj);
            writer.flush();
            writer.close();
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static <T> T jsonToObj(String jsonString, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
