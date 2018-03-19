package springboot.vertx.starter.autoconfig;

import springboot.vertx.starter.SpringVerticleFactory;
import springboot.vertx.starter.VertxHttpServerLifecycle;
import springboot.vertx.starter.VertxServerVerticle;
import springboot.vertx.starter.config.VertxHttpServerProperties;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.CyclicBarrier;

@Slf4j
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass({Vertx.class})
public class SpringBootVertxAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public VertxHttpServerProperties vertxHttpServerProperties() {
        return new VertxHttpServerProperties();
    }

    @ConditionalOnMissingBean
    @Bean
    public SpringVerticleFactory springVerticleFactory() {
        return new SpringVerticleFactory();
    }

    @ConditionalOnMissingBean
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public VertxServerVerticle vertxServerVerticle(VertxHttpServerProperties properties, CyclicBarrier barrier) {
        return new VertxServerVerticle(properties, barrier);
    }

    @Bean
    public CyclicBarrier vertxStartupBarrier(VertxHttpServerProperties vertxHttpServerProperties) {
        return new CyclicBarrier(vertxHttpServerProperties.getInstances(), () -> {
            log.info("VertX server verticles deploy finished, instance: {}", vertxHttpServerProperties.getInstances());
            log.info("VertX started on port(s): {} (http)", vertxHttpServerProperties.getPort());
        });
    }

    @Bean
    public Vertx vertx(VertxHttpServerProperties vertxHttpServerProperties, SpringVerticleFactory springVerticleFactory) {
        Vertx vertx = Vertx.vertx();
        VertxOptions options = new VertxOptions();
        if (vertxHttpServerProperties.getWorkerPoolSize() > 0) {
            options.setWorkerPoolSize(vertxHttpServerProperties.getWorkerPoolSize());
        }
        vertx.registerVerticleFactory(springVerticleFactory);
        return vertx;
    }

    @ConditionalOnMissingBean
    @Bean
    public VertxHttpServerLifecycle vertxHttpServerLifecycle(Vertx vertx, VertxServerVerticle verticle, SpringVerticleFactory factory, VertxHttpServerProperties properties) {
        return new VertxHttpServerLifecycle(vertx, verticle, factory, properties);
    }
}
