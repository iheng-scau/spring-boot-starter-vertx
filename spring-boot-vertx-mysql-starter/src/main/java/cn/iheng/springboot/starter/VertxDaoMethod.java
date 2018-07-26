package cn.iheng.springboot.starter;

import cn.iheng.springboot.starter.annotation.Results;
import cn.iheng.springboot.starter.autoconfig.Configuration;
import cn.iheng.springboot.starter.exception.ResultCastException;
import cn.iheng.springboot.starter.reflect.DefaultResultHandler;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import lombok.extern.slf4j.Slf4j;

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
     * 原始方法
     */
    private final Method method;
    /**
     * 查询的实体类型
     */
    private final Class<T> returnType;
    /**
     * sql command
     */
    private final SqlCommand sqlCommand;
    /**
     * configuration
     */
    private Configuration configuration;

    /**
     * parameterized constructor
     *
     * @param interfaceType
     * @param sqlCommand
     * @param returnType
     */
    public VertxDaoMethod(Class<I> interfaceType, Method method, SqlCommand sqlCommand, Class<T> returnType) {
        this.interfaceType = interfaceType;
        this.method=method;
        this.returnType = returnType;
        this.sqlCommand = sqlCommand;
    }

    public Future<Object> execute(SQLClient client, Object[] args) {
        Future<Object> future = Future.future();
        client.getConnection(res -> {
            SQLConnection conn = res.result();
            JsonArray params = new JsonArray();
            for (Object param : args) {
                params.add(param);
            }

            /**
             * using conn to execute the query
             * now supporting insert, delete, select, update
             */
            switch (this.sqlCommand.getSqlCommandType()) {
                case INSERT:
                    conn.queryWithParams(this.sqlCommand.getSql(), params, result -> {
                        if (result.succeeded()) {

                        } else {

                        }
                    });
                case DELETE:
                    conn.updateWithParams(this.sqlCommand.getSql(), params, result -> {
                        if (result.succeeded()) {

                        } else {

                        }
                    });
                case SELECT:
                    conn.queryWithParams(this.sqlCommand.getSql(), params, result -> {
                        if (result.succeeded()) {
                            ResultSet resultSet = result.result();
                            if (resultSet.getRows() == null || resultSet.getRows().size() == 0) {
                                future.tryComplete(null);
                            } else {
                                if (isSingle()) {
                                    if (resultSet.getRows().size() > 1)
                                        future.tryFail(new ResultCastException("the method expected to return a single item but several are found"));
                                    future.tryComplete(new DefaultResultHandler<>(returnType).handle(resultSet).get(0));
                                } else {
                                    future.tryComplete(new DefaultResultHandler<>(returnType).handle(resultSet));
                                }
                            }
                        } else {
                            future.tryFail(result.cause().getMessage());
                            result.cause().printStackTrace();
                        }
                    });
                case UPDATE:
                    conn.updateWithParams(this.sqlCommand.getSql(), params, result -> {
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

    /**
     * return that
     * @param method
     * @return
     */
    private boolean hasResultMap(Method method){
        return method.isAnnotationPresent(Results.class);
    }
}
