package springboot.vertx.starter.example.handler;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ErrorHandler;
import springboot.vertx.starter.annotation.VertxRequestMapping;

/**
 * @author iheng
 * @date 3/13/18
 */
@VertxRequestMapping(value = "/*", isErrorHandler = true)
public class GlobalErrorHandler implements ErrorHandler {
    @Override
    public void handle(RoutingContext event) {

    }
}
