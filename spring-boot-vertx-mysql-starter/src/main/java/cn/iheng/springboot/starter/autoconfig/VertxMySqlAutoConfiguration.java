package cn.iheng.springboot.starter.autoconfig;

import cn.iheng.springboot.starter.VertxDaoRegistrar;
import cn.iheng.springboot.starter.VertxSqlClient;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author iheng
 * @date 1/26/18
 */
@Configuration
@Import(VertxDaoRegistrar.class)
@EnableConfigurationProperties(VertxMySqlProperties.class)
public class VertxMySqlAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Vertx vertx(){
        return Vertx.vertx();
    }

    @Bean("vertxSqlClient")
    @ConditionalOnMissingBean
    public VertxSqlClient vertxSqlClient(Vertx vertx, VertxMySqlProperties sqlProperties) {
        Json.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        JsonObject config = JsonObject.mapFrom(sqlProperties);
        SQLClient client = MySQLClient.createShared(vertx, config);
        return new VertxSqlClient(sqlProperties.getConfiguration(),client);
    }
}
