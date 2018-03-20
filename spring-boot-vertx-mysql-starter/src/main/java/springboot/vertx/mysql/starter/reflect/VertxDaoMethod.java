package springboot.vertx.mysql.starter.reflect;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import lombok.extern.slf4j.Slf4j;
import springboot.vertx.mysql.starter.annotation.*;
import springboot.vertx.mysql.starter.emuns.SqlCommandType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author iheng
 * @date 1/29/18
 */
@Slf4j
public class VertxDaoMethod<I,T>{
    private final Class<I> interfaceType;
    /**查询的实体类型*/
    private final Class<T> returnType;
    /**查询类型*/
    private final SqlCommandType type;
    /**sql*/
    private final String sql;

    public VertxDaoMethod(Class<I> interfaceType, Method method, Class<T> returnType){
        this.interfaceType=interfaceType;
        this.returnType=returnType;
        //this.isMulti=isMulti;
        Annotation[] annotations = method.getAnnotations();
        if (annotations.length != 1) {
            log.error("the method is not annotated with a proper annotation, please check the method:{}.{}", this.interfaceType.getName(), method.getName());
        }
        Class clazz=annotations[0].annotationType();
        if(clazz.equals(Insert.class)){
            this.sql=((Insert)annotations[0]).value();
            this.type=SqlCommandType.INSERT;
        }else if(clazz.equals(Delete.class)){
            this.sql=((Delete)annotations[0]).value();
            this.type=SqlCommandType.DELETE;
        }else if(clazz.equals(Select.class)){
            this.sql=((Select)annotations[0]).value();
            this.type=SqlCommandType.SELECT;
        }else if(clazz.equals(Update.class)){
            this.sql=((Update)annotations[0]).value();
            this.type=SqlCommandType.UPDATE;
        }else if(clazz.equals(Call.class)){
            this.sql=((Call)annotations[0]).value();
            this.type=SqlCommandType.CALL;
        }else{
            this.sql=null;
            type=SqlCommandType.UNKNOWN;
        }
    }

    public Future<Object> execute(SQLClient client, Object[] args){
        Future<Object> future=Future.future();
        client.getConnection(res->{
            if(res.failed()){
                log.error("get connection failed, cause:{}",res.cause().toString());
                res.cause().printStackTrace();
            }
            SQLConnection conn=res.result();
            JsonArray params=new JsonArray();
            if (args!=null&&args.length>0)
                for(Object param : args){
                    params.add(param);
                }
            /**
             * 根据指令类型进行执行
             */
            switch(this.type){
                case INSERT:
                    conn.queryWithParams(this.sql,params, result->{
                        if(result.succeeded()){
                            future.tryComplete(true);
                        }else{
                            future.tryFail(result.cause());
                        }
                    });
                    break;
                case DELETE:
                    conn.updateWithParams(this.sql,params, result->{
                        if(result.succeeded()){
                            future.tryComplete(true);
                        }else{
                            future.tryFail(result.cause());
                        }
                    });
                    break;
                case SELECT:
                    conn.queryWithParams(sql, params, result->{
                        if (result.succeeded()){
                            future.tryComplete(result.result().getRows());
                        }else {
                            future.tryFail(result.cause());
                        }
                    });
                    break;
                case UPDATE:
                    conn.updateWithParams(this.sql,params, result->{
                        if(result.succeeded()){
                            future.tryComplete(true);
                        }else{
                            future.tryFail(result.cause());
                        }
                    });
                    break;
                default:
                    log.error("unknown operation type,type:{}", this.type);
            }
        });
        return future;
    }
}
