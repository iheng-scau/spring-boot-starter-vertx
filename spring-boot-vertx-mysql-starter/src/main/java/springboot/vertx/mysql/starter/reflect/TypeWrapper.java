package springboot.vertx.mysql.starter.reflect;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import springboot.vertx.mysql.starter.annotation.VertxMapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author iheng
 * @date 3/20/18
 */
@Slf4j
@Data
@Builder
public class TypeWrapper<T> {
    private final static Map<String,VertxMapper> mapperCache= new ConcurrentHashMap<>(30);
    private Class<T> targetType;
    private boolean isSingle;

    /**
     *
     * @return
     */
    public T wrap(List<JsonObject>){
        if(targetType == Map.class){

        }
    }
}
