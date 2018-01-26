package cn.iheng.springboot.starter;

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
    @Override
    public T getObject() throws Exception {
        return null;
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
}
