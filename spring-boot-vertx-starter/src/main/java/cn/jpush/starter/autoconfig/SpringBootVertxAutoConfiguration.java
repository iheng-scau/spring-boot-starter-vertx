package cn.jpush.starter.autoconfig;

import cn.jpush.starter.SpringVerticleFactory;
import cn.jpush.starter.VertxHttpServerLifecycle;
import cn.jpush.starter.VertxServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConditionalOnClass({Vertx.class})
public class SpringBootVertxAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public VertxHttpServerProperties vertxHttpServerProperties(){
        return new VertxHttpServerProperties();
    }

    @ConditionalOnMissingBean
    @Bean
    public SpringVerticleFactory springVerticleFactory(){
        return new SpringVerticleFactory();
    }

    @ConditionalOnMissingBean
    @Bean
    public VertxServerVerticle vertxServerVerticle(VertxHttpServerProperties properties){
        return new VertxServerVerticle(properties);
    }

    @Bean
    public Vertx vertx(VertxHttpServerProperties vertxHttpServerProperties,SpringVerticleFactory springVerticleFactory){
        Vertx vertx=Vertx.vertx();
        VertxOptions options=new VertxOptions();
        if(vertxHttpServerProperties.getWorkerPoolSize()>0){
            options.setWorkerPoolSize(vertxHttpServerProperties.getWorkerPoolSize());
        }
        vertx.registerVerticleFactory(springVerticleFactory);
        return vertx;
    }

    @ConditionalOnMissingBean
    @Bean
    public VertxHttpServerLifecycle vertxHttpServerLifecycle(Vertx vertx, VertxServerVerticle verticle, SpringVerticleFactory factory, VertxHttpServerProperties properties){
        return new VertxHttpServerLifecycle(vertx,verticle,factory,properties);
    }
}
