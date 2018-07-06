package cn.iheng.springboot.starter;

import cn.iheng.springboot.starter.annotation.*;
import cn.iheng.springboot.starter.enums.SqlCommandType;
import cn.iheng.springboot.starter.exception.ResultCastException;
import cn.iheng.springboot.starter.utils.DefaultResultHandler;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author iheng
 * @date 1/29/18
 */
@Slf4j
public class VertxDaoMethod<I, T> {
    private final Class<I> interfaceType;
    /**
     * 查询的实体类型
     */
    private final Class<T> returnType;
    /**
     * 查询类型
     */
    private final SqlCommandType type;
    /**
     * sql
     */
    private final String sql;

    public VertxDaoMethod(Class<I> interfaceType, Method method, Class<T> returnType) {
        this.interfaceType = interfaceType;
        this.returnType = returnType;

        Annotation[] annotations = method.getAnnotations();
        if (annotations.length != 1) {
            log.error("the method is not annotated with a proper annotation, please check the method:{}.{}", this.interfaceType.getName(), method.getName());
        }
        Class clazz = annotations[0].annotationType();
        if (clazz.equals(Insert.class)) {
            this.sql = ((Insert) annotations[0]).value();
            this.type = SqlCommandType.INSERT;
        } else if (clazz.equals(Delete.class)) {
            this.sql = ((Delete) annotations[0]).value();
            this.type = SqlCommandType.DELETE;
        } else if (clazz.equals(Select.class)) {
            this.sql = ((Select) annotations[0]).value();
            this.type = SqlCommandType.SELECT;
        } else if (clazz.equals(Update.class)) {
            this.sql = ((Update) annotations[0]).value();
            this.type = SqlCommandType.UPDATE;
        } else if (clazz.equals(Call.class)) {
            this.sql = ((Call) annotations[0]).value();
            this.type = SqlCommandType.CALL;
        } else {
            this.sql = null;
            type = SqlCommandType.UNKNOWN;
        }
    }

    public Future<Object> execute(SQLClient client, Object[] args) {
        Future<Object> future = Future.future();
        client.getConnection(res -> {
            SQLConnection conn = res.result();
            JsonArray params = new JsonArray();
            for (Object param : args) {
                params.add(param);
            }
            switch (this.type) {
                case INSERT:
                    conn.queryWithParams(this.sql, params, result -> {
                        if (result.succeeded()) {

                        } else {

                        }
                    });
                case DELETE:
                    conn.updateWithParams(this.sql, params, result -> {
                        if (result.succeeded()) {

                        } else {

                        }
                    });
                case SELECT:
                    conn.queryWithParams(sql, params, result -> {
                        if (result.succeeded()) {
                            ResultSet resultSet = result.result();
                            if (resultSet.getRows() == null || resultSet.getRows().size() == 0) {
                                future.tryComplete(null);
                            } else {
                                if(isSingle()){
                                    if(resultSet.getRows().size() > 1)
                                        future.tryFail(new ResultCastException("the method expected to return a single item but several are found"));
                                    future.tryComplete(new DefaultResultHandler<>(returnType).handle(resultSet).get(0));
                                }else{
                                    future.tryComplete(new DefaultResultHandler<>(returnType).handle(resultSet));
                                }
                            }
                        } else {
                            future.tryFail(result.cause().getMessage());
                            result.cause().printStackTrace();
                        }
                    });
                case UPDATE:
                    conn.updateWithParams(this.sql, params, result -> {
                        if (result.succeeded()) {

                        } else {

                        }
                    });
            }
        });
        return future;
    }

    /**
     * return whether the method's returnType is a collection
     *
     * @return
     */
    private boolean isSingle() {
        if (returnType.isAssignableFrom(List.class))
            return false;
        return true;
    }
}
