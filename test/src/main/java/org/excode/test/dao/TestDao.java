package org.excode.test.dao;

import cn.iheng.springboot.starter.annotation.Select;
import cn.iheng.springboot.starter.annotation.VertxDao;
import io.vertx.core.Future;
import org.excode.test.Entity;

import java.util.Map;

/**
 * @author zhangdh@jpush.cn
 * @date 7/4/18
 */
@VertxDao("vertxMysqlClient")
public interface TestDao {
    @Select("select id,name,type,active from t_rule_group where id=?")
    Future<Map<String,Object>> getTestRecord(long id);

    @Select("select id,name,type,active from t_rule_group where id=?")
    Future<Entity> getTestRecordEntity(long id);
}
