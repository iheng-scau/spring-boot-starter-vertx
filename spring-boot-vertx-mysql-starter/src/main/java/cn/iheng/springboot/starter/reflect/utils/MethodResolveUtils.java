package cn.iheng.springboot.starter.reflect.utils;

import cn.iheng.springboot.starter.annotation.Delete;
import cn.iheng.springboot.starter.annotation.Insert;
import cn.iheng.springboot.starter.annotation.Select;
import cn.iheng.springboot.starter.annotation.Update;
import cn.iheng.springboot.starter.enums.SqlCommandType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashSet;
import java.util.Map;

/**
 * @author zhangdh@jpush.cn
 * @date 7/6/18
 */
public class MethodResolveUtils {
    private final static HashSet<Class<? extends Annotation>> sqlCommandTypeSet=new HashSet(){
        {
            add(Select.class);
            add(Update.class);
            add(Delete.class);
            add(Insert.class);
        }
    };

    /**
     *
     * @param type
     * @return
     */
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

    /**
     *
     * @param method
     * @return
     */
    public static SqlCommandType resolveSqlCommandType(Method method){
        for (Class type : sqlCommandTypeSet){
            Annotation annotation=method.getAnnotation(type);
            if(annotation !=null){
                Class clazz=annotation.getClass();
                if(clazz.isAssignableFrom(Select.class))
                    return SqlCommandType.SELECT;
                else if(clazz.isAssignableFrom(Insert.class))
                    return SqlCommandType.INSERT;
                else if(clazz.isAssignableFrom(Delete.class))
                    return SqlCommandType.DELETE;
                else if (clazz.isAssignableFrom(Update.class))
                    return SqlCommandType.UPDATE;
                else
                    return SqlCommandType.UNKNOWN;
            }
        }
        return SqlCommandType.UNKNOWN;
    }
}
