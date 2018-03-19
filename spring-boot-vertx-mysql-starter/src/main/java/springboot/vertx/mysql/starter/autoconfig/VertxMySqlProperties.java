package springboot.vertx.mysql.starter.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * all the properties shown in this config class are default supported by vertx mysql client<br/>
 * this class is for the convenience of reading configs from spring boot configuration file.
 *
 * @author iheng
 * @date 1/26/18
 */
@Configuration
@ConfigurationProperties(VertxMySqlProperties.VERTX_MYSQL_PREFIX)
public class VertxMySqlProperties {
    public final static String VERTX_MYSQL_PREFIX = "vertx.mysql";
    private String host;
    private int port = 3306;
    private String database;
    private int maxPoolSize = 10;
    private String username;
    private String password;
    private String charset = "utf-8";
    private int queryTimeout = 6000;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }
}
