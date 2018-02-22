package cn.jpush.demo.handler;

import cn.jpush.starter.VertxRequestMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
        String timestamp = String.valueOf(System.currentTimeMillis());
        JsonObject params = new JsonObject();
        params.put("userName", USERNAME);
        params.put("time", timestamp);
        JsonArray imeiList = new JsonArray();
        imeiList.add("00000000000000");
        params.put("imeiList", imeiList);
        params.put("sign", genSign(timestamp, imeiList.getString(0)));
        AsyncWebClientUtil.executePostRequest(HOST,CTXPATH,PORT,params)
                .setHandler(result->{
                    if(result.succeeded()){
                        log.info("result:{}", Json.encode(result.result()));
                        ctx.response()
                                .putHeader("Content-Type","application/json")
                                .end(Json.encode(result.result()));
                    }else{
                        log.error("error occur,e:{}",result.cause().getMessage());
                    }
                });
    }

    private String genSign(String timestamp, String imei) {
        String raw = USERNAME + PASSWORD + timestamp + imei;
        return DigestUtils.md5DigestAsHex(raw.getBytes());
    }
}
