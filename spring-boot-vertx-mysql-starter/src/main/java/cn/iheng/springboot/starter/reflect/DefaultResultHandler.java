package cn.iheng.springboot.starter.reflect;

import cn.iheng.springboot.starter.VertxDaoMethod;
import cn.iheng.springboot.starter.annotation.ResultMap;
import cn.iheng.springboot.starter.annotation.Results;
import cn.iheng.springboot.starter.mapping.ResultMapRegistry;
import cn.iheng.springboot.starter.reflect.utils.FieldResolveUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author iheng_scau@hotmail.com
 * @date 7/4/18
 */
public class DefaultResultHandler implements ResultHandler {
    private VertxDaoMethod vertxDaoMethod;
    private final List<Object> list;
    private ObjectFactory objectFactory = new DefaultObjectFactory<>();

    public DefaultResultHandler(VertxDaoMethod vertxDaoMethod) {
        this.vertxDaoMethod = vertxDaoMethod;
        list = new ArrayList<>();
    }

    /**
     * 进行查询结果的对象化
     *
     * @param resultSet
     * @return
     */
    @Override
    public List<Object> handle(ResultSet resultSet) {
        List<JsonObject> results = resultSet.getRows();
        Class<?> type = vertxDaoMethod.getReturnType();
        for (JsonObject result : results) {
            Object o = objectFactory.create(type);
            if (!vertxDaoMethod.hasResultMap()) {
                FieldResolveUtils.jsonObjToTypeObject(result, o, null);
            } else {
                FieldResolveUtils.jsonObjToTypeObject(result, o, getResultMap().getMapper());
            }
            list.add(o);
        }
        return list;
    }

    /**
     * @return
     */
    private ResultMapRegistry.ResultMapper getResultMap() {
        /**
         * 如果方法被@Results或ResultMap注解,说明需要将进行result map
         */
        String resultMapperId = null;
        if (vertxDaoMethod.getMethod().isAnnotationPresent(Results.class)) {
            resultMapperId = vertxDaoMethod.getMethod().getAnnotation(Results.class).id();
        } else if (vertxDaoMethod.getMethod().isAnnotationPresent(ResultMap.class)) {
            resultMapperId = vertxDaoMethod.getMethod().getAnnotation(ResultMap.class).value();
        }
        return vertxDaoMethod.getConfiguration().getResultMapRegistry().getResultMapper(resultMapperId);
    }
}
