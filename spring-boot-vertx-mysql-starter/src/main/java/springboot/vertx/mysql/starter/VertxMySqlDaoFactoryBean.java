package springboot.vertx.mysql.starter;

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

    @Override
    public T getObject() throws Exception {
        return new VertxDaoProxyFactory<>(type).newInstance(sqlClient());
    }

    @Override
    public Class<T> getObjectType() {
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

    private SQLClient sqlClient(){
        return applicationContext.getBean(SQLClient.class);
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
}
