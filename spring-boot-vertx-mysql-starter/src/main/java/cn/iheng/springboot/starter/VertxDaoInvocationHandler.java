package cn.iheng.springboot.starter;

import cn.iheng.springboot.starter.enums.SqlCommandType;
import cn.iheng.springboot.starter.exception.MethodResolveException;
import cn.iheng.springboot.starter.reflect.utils.MethodResolveUtils;
import io.vertx.core.Future;
import io.vertx.ext.sql.SQLClient;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.Map;

/**
 * @author iheng
 * @date 1/26/18
 */
@Slf4j
public class VertxDaoInvocationHandler<T> implements InvocationHandler, Serializable {
    private final SQLClient client;
    private final Class<T> interfaceType;
    private final Map<Method, VertxDaoMethod> methodMap;

    public VertxDaoInvocationHandler(SQLClient client, Class<T> interfaceType, Map<Method, VertxDaoMethod> methodMap) {
        this.client = client;
        this.interfaceType = interfaceType;
        this.methodMap = methodMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
        } catch (Throwable t) {
            log.error("invoke method error,e:{}", t.toString());
            t.printStackTrace();
        }

        VertxDaoMethod daoMethod = this.cachedVertxDaoMethod(method);
        return daoMethod.execute(client, args);
    }

    private VertxDaoMethod cachedVertxDaoMethod(Method method) throws MethodResolveException {
        if (methodMap.get(method) == null) {
            methodMap.put(method, new VertxDaoMethod(this.interfaceType, method, (Class<?>) resolve(method)));
        }
        return methodMap.get(method);
    }

    private Type resolve(Method method) throws MethodResolveException {
        Class<?> returnType = method.getReturnType();
        if (!returnType.isAssignableFrom(Future.class)) {
            throw new MethodResolveException("method resolve error: method return type should be io.vertx.core.Future.");
        } else {
            Type type = method.getGenericReturnType();
            if (type instanceof TypeVariable) {

            } else if (type instanceof ParameterizedType) {
                return MethodResolveUtils.resolveType(type);
            }
        }
        return Object.class;
    }

    private SqlCommandType resolveSqlCommandType(Method method){
        return MethodResolveUtils.resolveSqlCommandType(method);
    }
}
