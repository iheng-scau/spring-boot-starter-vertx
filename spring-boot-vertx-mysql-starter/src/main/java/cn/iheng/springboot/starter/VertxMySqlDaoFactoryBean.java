package cn.iheng.springboot.starter;

import io.vertx.ext.sql.SQLClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author iheng
 * @date 1/26/18
 */
public class VertxMySqlDaoFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware{
    private Class<T> type;
    private ApplicationContext applicationContext;
    private VertxSqlClient client;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void setClient(VertxSqlClient client) {
        this.client = client;
    }
}
