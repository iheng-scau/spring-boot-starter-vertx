package cn.iheng.springboot.starter;

import sun.jvm.hotspot.oops.Instance;

import java.lang.reflect.Proxy;

/**
 * @author iheng
 * @date 1/26/18
 */
public class VertxDaoProxyFactory<T> {
    private final Class<T> interfaceType;

    public VertxDaoProxyFactory(Class<T> interfaceType){
        this.interfaceType=interfaceType;
    }

    public Class<T> getInterfaceType(){
        return this.interfaceType;
    }

    public T newInstance(){
        return Proxy.newProxyInstance(this.interfaceType.getClassLoader(), this.interfaceType.getInterfaces(),)
    }
}
