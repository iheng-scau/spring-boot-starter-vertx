package cn.iheng.springboot.starter.reflect.utils;

import io.vertx.core.json.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author zhangdh@jpush.cn
 * @date 7/9/18
 */
public class FieldResolveUtils {

    /**
     *  copy properties in a jsonObject to corresponding typeObject
     *  TODO: support result map
     */
    public static <T> void jsonObjToTypeObject(JsonObject from, T to){
        try {
            Field[] fields = to.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                Type type = field.getDeclaringClass();

                Object value = from.getValue(name);
                if(value ==null){
                    continue;
                }
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                field.set(to, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
