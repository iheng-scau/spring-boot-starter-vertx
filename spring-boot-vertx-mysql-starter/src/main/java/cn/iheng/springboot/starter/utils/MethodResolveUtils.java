package cn.iheng.springboot.starter.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * @author zhangdh@jpush.cn
 * @date 7/6/18
 */
public class MethodResolveUtils {
    public static Type resolveType(Type type) {
        if(type instanceof Class){
            return type;
        }else if(type instanceof TypeVariable){

        } else if(type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            return resolveType(types[0]);
        } else if(type.getClass().isAssignableFrom(Map.class)){
            return type;
        }
        return Object.class;
    }
}
