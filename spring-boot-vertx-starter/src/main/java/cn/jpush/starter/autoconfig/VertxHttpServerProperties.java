package cn.jpush.starter.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("vertx.http")
@Data
public class VertxHttpServerProperties {
    private int port=9000;
    private int workerPoolSize=0;
    private int instances=Runtime.getRuntime().availableProcessors();
}
