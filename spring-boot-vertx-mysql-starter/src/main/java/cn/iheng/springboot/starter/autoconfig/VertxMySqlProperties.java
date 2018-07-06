package cn.iheng.springboot.starter.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * all the properties shown in this config class are default supported by vertx mysql client<br/>
 * this class is for the convenience of reading configs from spring boot configuration file.
 *
 * @author iheng
 * @date 1/26/18
 */
@ConfigurationProperties(VertxMySqlProperties.VERTX_MYSQL_PREFIX)
public class VertxMySqlProperties {
    public final static String VERTX_MYSQL_PREFIX = "vertx.mysql";
    /**
     * the host of Mysql instance, localhost as default
     */
    private String host = "localhost";
    /**
     * the port of Mysql instance, 3306 as default
     */
    private int port = 3306;
    /**
     * the database name
     */
    private String database;
    /**
     * maximum number of open connections, 10 as default
     */
    private Integer maxPoolSize = 10;
    /**
     * the username to connect to the database
     */
    private String username;
    /**
     * the password to connect to the database
     */
    private String password;
    /**
     * the name of charset that is used for the connection, utf-8 as default
     */
    private String charset = "utf-8";
    /**
     * the time out to wait for a query in milliseconds, defaults to 10000(10s)
     */
    private int queryTimeout = 10000;
    /**
     * If you want to enable SSL support you should enable this parameter.
     */
    private String sslMode;
    /**
     * Path to SSL root certificate file. Is used if you want to verify privately issued certificate.
     */
    private String sslRootCert;

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

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getSslMode() {
        return sslMode;
    }

    public void setSslMode(String sslMode) {
        this.sslMode = sslMode;
    }

    public String getSslRootCert() {
        return sslRootCert;
    }

    public void setSslRootCert(String sslRootCert) {
        this.sslRootCert = sslRootCert;
    }
}
