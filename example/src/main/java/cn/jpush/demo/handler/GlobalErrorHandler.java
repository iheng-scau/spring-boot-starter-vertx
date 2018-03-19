package cn.jpush.demo.handler;

import springboot.vertx.starter.annotation.VertxRequestMapping;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ErrorHandler;

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
