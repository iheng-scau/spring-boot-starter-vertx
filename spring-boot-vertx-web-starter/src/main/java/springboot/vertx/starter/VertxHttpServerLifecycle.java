package springboot.vertx.starter;

import springboot.vertx.starter.config.VertxHttpServerProperties;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;

@Slf4j
public class VertxHttpServerLifecycle implements SmartLifecycle{
    private Vertx vertx;
    private VertxServerVerticle verticle;
    private SpringVerticleFactory factory;
    private VertxHttpServerProperties properties;

    public VertxHttpServerLifecycle(Vertx vertx, VertxServerVerticle verticle, SpringVerticleFactory factory, VertxHttpServerProperties properties){
        this.vertx=vertx;
        this.verticle=verticle;
        this.factory=factory;
        this.properties=properties;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void start() {
        vertx.deployVerticle(factory.prefix()+":"+verticle.getClass().getName(),new DeploymentOptions().setInstances(properties.getInstances()));
    }

    @Override
    public void stop(Runnable runnable) {
        this.stop();
        runnable.run();
    }

    @Override
    public void stop() {
        vertx.undeploy(factory.prefix()+":"+verticle.getClass().getName());
        log.info("VertX http server has stopped");
    }

    @Override
    public boolean isRunning() {
        return verticle.isRunning();
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
