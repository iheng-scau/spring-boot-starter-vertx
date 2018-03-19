package springboot.vertx.mysql.starter;

import io.vertx.ext.sql.SQLClient;
import lombok.extern.slf4j.Slf4j;
import springboot.vertx.mysql.starter.reflect.VertxDaoInvocationHandler;
import springboot.vertx.mysql.starter.reflect.VertxDaoMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author iheng
 * @date 1/26/18
 */
@Slf4j
public class VertxDaoProxyFactory<T> {
    private final Class<T> interfaceType;
    private final Map<Method, VertxDaoMethod> methodMap = new ConcurrentHashMap<>();

    public VertxDaoProxyFactory(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public T newInstance(SQLClient client) {
        InvocationHandler handler = new VertxDaoInvocationHandler<>(client, interfaceType, methodMap);
        return newInstance(handler);
    }

    public T newInstance(InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(this.interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
    }
}
