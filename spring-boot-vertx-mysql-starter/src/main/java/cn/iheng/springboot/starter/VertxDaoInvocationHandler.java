package cn.iheng.springboot.starter;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author iheng
 * @date 1/26/18
 */
public class VertxDaoInvocationHandler implements InvocationHandler,Serializable{
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
