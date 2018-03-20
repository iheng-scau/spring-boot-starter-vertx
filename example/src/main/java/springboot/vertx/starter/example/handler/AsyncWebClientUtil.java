package springboot.vertx.starter.example.handler;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

/**
 * @author iheng
 * @date 1/30/18
 */
public class AsyncWebClientUtil {
    private final static WebClient client=WebClient.create(Vertx.vertx());

    public static Future<JsonObject> executePostRequest(String host,String ctxPath,int port,JsonObject params){
        Future<JsonObject> future=Future.future();
        client.post(port,host,ctxPath)
                .timeout(5000)
                .as(BodyCodec.jsonObject())
                .sendJsonObject(params,ar->{
                    if(ar.succeeded()){
                        HttpResponse<JsonObject> response=ar.result();
                        future.tryComplete(response.body());
                    }else{
                        future.tryFail(ar.cause());
                    }
                });
        return future;
    }
}
