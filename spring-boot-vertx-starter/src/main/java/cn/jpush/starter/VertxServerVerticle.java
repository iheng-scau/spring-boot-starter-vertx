package cn.jpush.starter;

import cn.jpush.starter.autoconfig.VertxHttpServerProperties;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(SCOPE_PROTOTYPE)
public class VertxServerVerticle extends AbstractVerticle implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private HttpServer httpServer;
    @Autowired
    private VertxHttpServerProperties vertxHttpServerProperties;
    private boolean isRunning=false;

    public VertxServerVerticle(VertxHttpServerProperties vertxHttpServerProperties){
        this.vertxHttpServerProperties=vertxHttpServerProperties;
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        getVertxHandlerDefinitions().forEach(definition->{
            for(HttpMethod method:definition.getMethod()){
                router.route(method,definition.getPath()).handler(definition.getHandler());
            }
        });
        httpServer=vertx.createHttpServer().requestHandler(router::accept).listen(vertxHttpServerProperties.getPort());
        this.isRunning=true;
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
            Handler handler = this.applicationContext.getBean(beanName,Handler.class);
            VertxRequestMapping annotion=this.applicationContext.findAnnotationOnBean(beanName,VertxRequestMapping.class);
            definitions.add(VertxHttpHandlerDefinition.builder()
                    .path(annotion.value())
                    .method(annotion.method())
                    .handler(handler)
                    .build());
        }
        return definitions;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
