package cn.jpush.demo.dao;

import io.vertx.core.Future;
import springboot.vertx.mysql.starter.annotation.Select;
import springboot.vertx.mysql.starter.annotation.VertxDao;

import java.util.Map;

/**
 * @author iheng
 * @date 3/15/18
 */
@VertxDao
public interface DemoDao {
    @Select("select * from t_result where id=1")
    Future<Map<String,Object>> select();
}
