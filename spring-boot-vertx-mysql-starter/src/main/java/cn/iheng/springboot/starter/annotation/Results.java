package cn.iheng.springboot.starter.annotation;

/**
 * @author zhangdh@jpush.cn
 * @date 7/9/18
 */
public @interface Results {
    /**unique id*/
    String id() default "";
    Result[] value() default {};
}
