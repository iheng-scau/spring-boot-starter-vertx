package cn.iheng.springboot.starter.annotation;

import java.lang.annotation.*;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/27/18
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResultMap {
    String value() default "";
}
