package springboot.vertx.starter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VertxHttpHandlerDefinition {
    private String path;
    private HttpMethod[] method;
    private Handler<RoutingContext> handler;
    private boolean isErrorHandler;
}
