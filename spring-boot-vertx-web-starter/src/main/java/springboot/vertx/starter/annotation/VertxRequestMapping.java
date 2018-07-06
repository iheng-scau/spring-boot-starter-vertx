package springboot.vertx.starter.annotation;

import io.vertx.core.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface VertxRequestMapping {
    String value();
    HttpMethod[] method() default HttpMethod.GET;
    boolean isErrorHandler() default false;
}
