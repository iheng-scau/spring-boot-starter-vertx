package cn.iheng.springboot.starter.reflect.utils;

import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/9/18
 */
@Slf4j
public class FieldResolveUtils {

    /**
     * copy properties in a jsonObject to corresponding typeObject
     */
    public static <T> void jsonObjToTypeObject(JsonObject from, T to, Map<String, String> mapper) {
        try {
            if (mapper == null) {
                Field[] fields = to.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName();
                    Object value = from.getValue(name);

                    if (value == null) {
                        continue;
                    }
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.set(to, value);
                }
            } else {
                mapper.forEach((column, property) -> {
                    try {
                        Field field = to.getClass().getField(property);
                        Object value = from.getValue(column);

                        if (value == null)
                            return;
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(to, value);
                    } catch (NoSuchFieldException e) {
                        log.error("field is not declared in the type, file:{}, type:{}", property, to.getClass().getName());
                        e.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
