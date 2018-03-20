package springboot.vertx.mysql.starter.autoconfig;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springboot.vertx.mysql.starter.VertxMapperScannerRegistrar;

/**
 * @author iheng
 * @date 1/26/18
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(VertxMySqlProperties.class)
@Import(VertxMapperScannerRegistrar.class)
public class VertxMySqlAutoConfiguration {
    @Bean
    public SQLClient sqlClient(Vertx vertx, VertxMySqlProperties mysqlProperties) {
        JsonObject config = new JsonObject();
        config.put("host", mysqlProperties.getHost());
        config.put("port", mysqlProperties.getPort());
        config.put("database", mysqlProperties.getDatabase());
        config.put("charset", mysqlProperties.getCharset());
        config.put("username", mysqlProperties.getUsername());
        config.put("password", mysqlProperties.getPassword());
        config.put("maxPoolSize", mysqlProperties.getMaxPoolSize());
        config.put("queryTimeout", mysqlProperties.getQueryTimeout());

        SQLClient client = MySQLClient.createShared(vertx, config);
        return client;
    }

    @Bean
    @ConditionalOnMissingBean
    public Vertx vertx() {
        return Vertx.vertx();
    }
}
