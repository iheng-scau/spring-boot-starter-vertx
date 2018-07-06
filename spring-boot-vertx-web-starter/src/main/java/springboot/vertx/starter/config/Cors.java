package springboot.vertx.starter.config;

import lombok.Data;

/**
 * @author iheng
 * @date 3/8/18
 */
@Data
public class Cors {
    private boolean enabled = false;
    private String pattern="*";
    private String[] allowMethods;
    private String[] allowHeaders;
}
