package springboot.vertx.starter;

import springboot.vertx.starter.annotation.VertxRequestMapping;
import springboot.vertx.starter.config.VertxHttpServerProperties;
import springboot.vertx.starter.constant.HttpMethods;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class VertxServerVerticle extends AbstractVerticle implements ApplicationContextAware {
    private CyclicBarrier barrier;
    private ApplicationContext applicationContext;
    private HttpServer httpServer;
    private VertxHttpServerProperties vertxHttpServerProperties;
    private boolean isRunning = false;

    public VertxServerVerticle(VertxHttpServerProperties vertxHttpServerProperties, CyclicBarrier barrier) {
        this.vertxHttpServerProperties = vertxHttpServerProperties;
        this.barrier = barrier;
    }

    @Override
    public void start() throws Exception {
        log.info("verticle starting...");
        Router router = Router.router(vertx);
        // enable the capability to handle request body
        router.route().handler(BodyHandler.create());
        // cors config
        if (vertxHttpServerProperties.getCors() != null) {
            configCors(router);
        }

        getVertxHandlerDefinitions().forEach(definition -> {
            // ordinary handler
            if (!definition.isErrorHandler()) {
                for (HttpMethod method : definition.getMethod()) {
                    router.route(method, definition.getPath()).handler(definition.getHandler());
                }
            } else {
                router.route(definition.getPath()).failureHandler(definition.getHandler());
            }
        });

        httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept).listen(vertxHttpServerProperties.getPort());
        this.isRunning = true;
        barrier.await();
    }

    @Override
    public void stop() throws Exception {
        httpServer.close();
        super.stop();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private List<VertxHttpHandlerDefinition> getVertxHandlerDefinitions() {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(VertxRequestMapping.class);
        if (beanNames == null || beanNames.length == 0) {
            return new ArrayList<>();
        }
        List<VertxHttpHandlerDefinition> definitions = new ArrayList<>();
        for (String beanName : beanNames) {
            Handler handler = this.applicationContext.getBean(beanName, Handler.class);
            VertxRequestMapping annotion = this.applicationContext.findAnnotationOnBean(beanName, VertxRequestMapping.class);
            definitions.add(VertxHttpHandlerDefinition.builder()
                    .path(annotion.value())
                    .method(annotion.method())
                    .handler(handler)
                    .isErrorHandler(annotion.isErrorHandler())
                    .build());
        }
        return definitions;
    }

    /**
     * 进行跨域的相关设置, 如果配置文件添加了cors配置将会使用本方法进行跨域的相关设置
     *
     * @param router
     */
    private void configCors(Router router) {
        CorsHandler corsHandler;
        String pattern = vertxHttpServerProperties.getCors().getPattern();
        corsHandler = CorsHandler.create(pattern);
        if (vertxHttpServerProperties.getCors().isEnabled()) {
            String[] headers = vertxHttpServerProperties.getCors().getAllowHeaders();
            if (headers.length > 0) {
                Set<String> headerSet = new HashSet<>();
                for (String header : headers) {
                    headerSet.add(header);
                }
                corsHandler.allowedHeaders(headerSet);
            }
            String[] methods = vertxHttpServerProperties.getCors().getAllowMethods();
            if (methods.length > 0) {
                Set<HttpMethod> methodSet = new HashSet<>();
                for (String method : methods) {
                    HttpMethods methodEnum = HttpMethods.valueOf(method);
                    if (methodEnum == null) {
                        log.error("vertx http cors config error: method not support, method name:{}", method);
                        continue;
                    }
                    methodSet.add(methodEnum.getValue());
                }
                corsHandler.allowedMethods(methodSet);
            }
            router.route().handler(corsHandler);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
