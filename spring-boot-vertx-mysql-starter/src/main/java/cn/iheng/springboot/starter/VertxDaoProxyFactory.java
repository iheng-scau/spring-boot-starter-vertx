package cn.iheng.springboot.starter;

import io.vertx.ext.sql.SQLClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author iheng
 * @date 1/26/18
 */
public class VertxDaoProxyFactory<T> {
    private final Class<T> interfaceType;
    private final Map<Method, VertxDaoMethod> methodMap = new ConcurrentHashMap<>();

    public VertxDaoProxyFactory(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Class<T> getInterfaceType() {
        return this.interfaceType;
    }

    public T newInstance(SQLClient client) {
        InvocationHandler handler = new VertxDaoInvocationHandler<>(client, interfaceType, methodMap);
        return (T) Proxy.newProxyInstance(this.interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
    }

    public T newInstance(InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(this.interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
    }
}
