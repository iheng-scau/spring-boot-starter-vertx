package springboot.vertx.starter.example.dao;

import cn.iheng.springboot.starter.annotation.Select;
import cn.iheng.springboot.starter.annotation.VertxDao;
import io.vertx.core.Future;

import java.util.Map;

/**
 * @author iheng
 * @date 3/15/18
 */
@VertxDao("vertx")
public interface DemoDao {
    @Select("select id,name,type,tag from t_rule where id=1")
    Future<Map<String,Object>> select();
}
