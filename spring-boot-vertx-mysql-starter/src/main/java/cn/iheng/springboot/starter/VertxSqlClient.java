package cn.iheng.springboot.starter;

import cn.iheng.springboot.starter.autoconfig.Configuration;
import io.vertx.ext.sql.SQLClient;

/**
 * @author zhangdh@jpush.cn
 * @date 7/10/18
 */
public class VertxSqlClient {
    private Configuration configuration;
    private SQLClient sqlClient;

    public VertxSqlClient(Configuration configuration, SQLClient sqlClient){
        this.configuration=configuration;
        this.sqlClient=sqlClient;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public SQLClient getSqlClient() {
        return sqlClient;
    }

    public void setSqlClient(SQLClient sqlClient) {
        this.sqlClient = sqlClient;
    }
}
