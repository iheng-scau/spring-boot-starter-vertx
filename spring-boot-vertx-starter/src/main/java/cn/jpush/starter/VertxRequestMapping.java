package cn.jpush.starter;

import cn.jpush.starter.autoconfig.SpringBootVertxAutoConfiguration;
import io.vertx.core.http.HttpMethod;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Import(SpringBootVertxAutoConfiguration.class)
public @interface VertxRequestMapping {
    String value();
    HttpMethod[] method() default HttpMethod.GET;
}
