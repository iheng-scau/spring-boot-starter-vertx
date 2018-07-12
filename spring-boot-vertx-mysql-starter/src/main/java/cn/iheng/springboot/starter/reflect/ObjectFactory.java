package cn.iheng.springboot.starter.reflect;

import java.util.List;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/6/18
 */
public interface ObjectFactory {
    <T> T create(Class<T> type);
    <T> T create(Class<T> type, List<Class<?>> constructorArgTypes,List<Object> constructorArgs);
    <T> boolean isCollection(Class<T> type);
}
