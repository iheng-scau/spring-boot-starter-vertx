package springboot.vertx.starter.example.handler;

import springboot.vertx.starter.example.dao.DemoDao;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import springboot.vertx.starter.annotation.VertxRequestMapping;

@Slf4j
@VertxRequestMapping(value = "/demo/hello", method = HttpMethod.GET)
public class DemoHandler implements Handler<RoutingContext> {
    @Autowired
    DemoDao demoDao;

    @Override
    public void handle(RoutingContext context) {

        Future<Boolean> future=Future.future();
        future.tryComplete(true);
        future.setHandler(r->{
            log.info("thread:{}", Thread.currentThread().getName());
            context.response().end("hello");
        });
    }
}
