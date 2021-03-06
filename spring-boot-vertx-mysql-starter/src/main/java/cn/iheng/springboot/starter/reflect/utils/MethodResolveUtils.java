package cn.iheng.springboot.starter.reflect.utils;

import cn.iheng.springboot.starter.SqlCommand;
import cn.iheng.springboot.starter.annotation.*;
import cn.iheng.springboot.starter.autoconfig.Configuration;
import cn.iheng.springboot.starter.enums.SqlCommandType;
import cn.iheng.springboot.starter.mapping.ResultMapRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/6/18
 */
public class MethodResolveUtils {
    private final static HashSet<Class<? extends Annotation>> sqlCommandTypeSet = new HashSet() {
        {
            add(Select.class);
            add(Update.class);
            add(Delete.class);
            add(Insert.class);
        }
    };

    /**
     * @param type
     * @return
     */
    public static Type resolveType(Type type) {
        if (type instanceof Class) {
            return type;
        } else if (type instanceof TypeVariable) {

        } else if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            return resolveType(types[0]);
        } else if (type.getClass().isAssignableFrom(Map.class)) {
            return type;
        }
        return Object.class;
    }

    /**
     * resolve the specified method to get sql and sqlCommandType
     *
     * @param method
     * @return
     */
    public static SqlCommand resolveSqlCommandType(Method method) {
        // init sql and sqlCommandType
        String sql = "";
        SqlCommandType sqlCommandType = SqlCommandType.UNKNOWN;

        for (Class type : sqlCommandTypeSet) {
            Annotation annotation = method.getAnnotation(type);
            if (annotation != null) {
                Class clazz = annotation.getClass();
                if (clazz.isAssignableFrom(Select.class)) {
                    sql = ((Select) annotation).value();
                    sqlCommandType = SqlCommandType.SELECT;
                } else if (clazz.isAssignableFrom(Insert.class)) {
                    sql = ((Insert) annotation).value();
                    sqlCommandType = SqlCommandType.INSERT;
                } else if (clazz.isAssignableFrom(Delete.class)) {
                    sql = ((Delete) annotation).value();
                    sqlCommandType = SqlCommandType.DELETE;
                } else if (clazz.isAssignableFrom(Update.class)) {
                    sql = ((Update) annotation).value();
                    sqlCommandType = SqlCommandType.UPDATE;
                } else {
                    sql = "";
                    sqlCommandType = SqlCommandType.UNKNOWN;
                }
            }
        }
        return SqlCommand.builder()
                .sql(sql)
                .sqlCommandType(sqlCommandType)
                .build();
    }

    /**
     * 对指定接口的方法进行分析,并将方法注解的@Results信息注册到Configuration中用于全局使用
     * @param clazz
     * @return
     */
    public static void resolveResultMap(Class<?> clazz, Configuration configuration) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Results.class)) {
                Results results = method.getAnnotation(Results.class);
                Map<String, String> mapper = new HashMap<>();
                Result[] result = results.value();
                for (Result r : result) {
                    mapper.put(r.column(), r.property());
                }
                configuration.getResultMapRegistry().addResultMap(results.id(),
                        ResultMapRegistry.ResultMapper.builder()
                                .mapper(mapper)
                                .build());
            }
        }
    }
}
