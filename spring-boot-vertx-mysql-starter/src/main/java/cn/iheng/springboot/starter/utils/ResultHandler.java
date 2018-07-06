package cn.iheng.springboot.starter.utils;

import io.vertx.ext.sql.ResultSet;

import java.util.List;

/**
 * @author zhangdh@jpush.cn
 * @date 7/6/18
 */
public interface ResultHandler{
    List<Object> handle(ResultSet resultSet);
}
