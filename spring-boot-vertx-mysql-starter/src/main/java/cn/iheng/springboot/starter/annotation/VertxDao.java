package cn.iheng.springboot.starter.annotation;

import java.lang.annotation.*;

/**
 * @author iheng
 * @date 1/26/18
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface VertxDao {
    String value();
}
