package cn.iheng.springboot.starter;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author iheng
 * @date 1/26/18
 */
public class VertxMySqlDaoFactoryBean<T> implements FactoryBean<T>, InitializingBean {
    private Class<T> type;
    private VertxSqlClient client;

    public VertxMySqlDaoFactoryBean(){
        // empty constructor
    }

    @Override
    public T getObject() throws Exception {
        return new VertxDaoProxyFactory<>(type).newInstance(this.client);
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public VertxSqlClient getClient() {
        return client;
    }

    public void setClient(VertxSqlClient client) {
        this.client = client;
    }

    /**
     * 在实例化factoryBean之后需要进行的操作
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 注册可能的resultMap定义
        // MethodResolveUtils.resolveResultMap(type, client.getConfiguration());
    }
}
