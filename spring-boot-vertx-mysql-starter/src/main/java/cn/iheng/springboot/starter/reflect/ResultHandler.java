package cn.iheng.springboot.starter.reflect;

import io.vertx.ext.sql.ResultSet;

import java.util.List;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/6/18
 */
public interface ResultHandler{
    List<Object> handle(ResultSet resultSet);
}
