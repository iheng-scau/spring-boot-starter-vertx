package cn.jpush.demo.handler;

import springboot.vertx.starter.annotation.VertxRequestMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

/**
 * @author iheng
 * @date 1/30/18
 */
@Slf4j
@VertxRequestMapping(value = "/exchange/imei2imsi", method = HttpMethod.GET)
public class ExchangeHandler implements Handler<RoutingContext> {
    private final static String USERNAME = "jiguang";
    private final static String PASSWORD = "jg2018";
    private final static String HOST="HTTP://43.243.130.36";
    private final static int PORT=9007;
    private final static String CTXPATH="/imeiToMsisdnMd5.htm";

    /***/
    @Override
    public void handle(RoutingContext ctx) {
        log.info("thread:{}",Thread.currentThread().getName());
        ctx.response().end("hello");
    }

    private String genSign(String timestamp, String imei) {
        String raw = USERNAME + PASSWORD + timestamp + imei;
        return DigestUtils.md5DigestAsHex(raw.getBytes());
    }
}
