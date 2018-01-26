package cn.iheng.springboot.starter;

import io.vertx.ext.sql.SQLClient;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author iheng
 * @date 1/26/18
 */
public class VerxMySqlClientFactory implements FactoryBean<SQLClient>{
    @Override
    public SQLClient getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
