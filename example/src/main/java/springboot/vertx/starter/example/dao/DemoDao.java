package springboot.vertx.starter.example.dao;

import cn.iheng.springboot.starter.annotation.Result;
import cn.iheng.springboot.starter.annotation.Results;
import cn.iheng.springboot.starter.annotation.Select;
import cn.iheng.springboot.starter.annotation.VertxDao;
import io.vertx.core.Future;
import springboot.vertx.starter.example.entity.Entity;

import java.util.Map;

/**
 * @author iheng
 * @date 3/15/18
 */
@VertxDao("vertx")
public interface DemoDao {

    @Results(
            id = "mapper",
            value = {
                    @Result(column = "id", property = "id"),
                    @Result(column = "name", property = "name"),
                    @Result(column = "type", property = "type")
            }
    )
    @Select("select id,name,type,tag from t_rule where id=1")
    Future<Map<String,Object>> select();

    @Select("select id,name,type,tag from t_rule where id=?")
    Future<Entity> selectEntity(int id);
}
