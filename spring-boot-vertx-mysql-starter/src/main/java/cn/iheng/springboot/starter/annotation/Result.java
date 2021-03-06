package cn.iheng.springboot.starter.annotation;

import cn.iheng.springboot.starter.enums.JdbcType;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/9/18
 */
public @interface Result {
    boolean id() default false;
    String column() default "";
    String property() default "";
    Class<?> javaType() default void.class;
    JdbcType jdbcType() default JdbcType.UNDEFINED;
}
