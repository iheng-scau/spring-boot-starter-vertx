package cn.iheng.springboot.starter.annotation;

import java.lang.annotation.*;

/**
 * @author iheng
 * @date 1/26/18
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Insert {
    String value();
}
