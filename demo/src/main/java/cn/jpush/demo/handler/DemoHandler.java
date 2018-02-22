package cn.jpush.demo.handler;

import cn.jpush.starter.VertxRequestMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@VertxRequestMapping(value = "/demo/hello",method = HttpMethod.GET)
public class DemoHandler implements Handler<RoutingContext>{

    @Override
    public void handle(RoutingContext context) {
        context.response().end("hello");
    }
}
