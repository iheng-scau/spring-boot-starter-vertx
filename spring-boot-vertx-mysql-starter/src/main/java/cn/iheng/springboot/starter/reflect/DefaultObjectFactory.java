package cn.iheng.springboot.starter.reflect;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author zhangdh@jpush.cn
 * @date 7/6/18
 */
public class DefaultObjectFactory<T> implements ObjectFactory {
    @Override
    public <T> T create(Class<T> type) {
        return create(type, null, null);
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        Class<?> targetClass = resolveInterface(type);
        try {
            Constructor<T> constructor;
            if (constructorArgTypes == null || constructorArgs == null) {
                constructor = (Constructor<T>) targetClass.getDeclaredConstructor();
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                return constructor.newInstance();
            }
            constructor = (Constructor<T>) targetClass.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
        } catch (Exception e) {

        }
        return null;
    }

    private Class<?> resolveInterface(Class<?> type) {
        Class<?> targetType;
        if (type == List.class || type == Collection.class || type == Iterator.class) {
            targetType = ArrayList.class;
        } else if (type == Map.class) {
            targetType = HashMap.class;
        } else if (type == SortedSet.class) {
            targetType = TreeSet.class;
        } else if (type == Set.class) {
            targetType = HashSet.class;
        } else {
            targetType = type;
        }
        return targetType;
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
