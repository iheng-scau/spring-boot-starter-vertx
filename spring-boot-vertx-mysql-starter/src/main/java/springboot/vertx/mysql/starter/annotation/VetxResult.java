package springboot.vertx.mysql.starter.annotation;

/**
 * @author iheng
 * @date 3/20/18
 */
public @interface VetxResult {
    boolean id() default false;
    String column() default "";
    String property() default "";
    Class<?> javaType() default void.class;
}
