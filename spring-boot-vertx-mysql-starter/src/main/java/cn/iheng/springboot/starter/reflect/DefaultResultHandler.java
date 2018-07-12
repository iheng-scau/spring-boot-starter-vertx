package cn.iheng.springboot.starter.reflect;

import cn.iheng.springboot.starter.reflect.utils.FieldResolveUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iheng_scau@hotmail.com
 * @date 7/4/18
 */
public class DefaultResultHandler<T> implements ResultHandler {
    private final List<Object> list;
    private final Class<T> type;
    private ObjectFactory objectFactory = new DefaultObjectFactory<>();

    public DefaultResultHandler(Class<T> type) {
        this.type = type;
        list = new ArrayList<>();
    }

    @Override
    public List<Object> handle(ResultSet resultSet) {
        List<JsonObject> results = resultSet.getRows();
        for (JsonObject result : results) {
            T o = objectFactory.create(type);
            FieldResolveUtils.jsonObjToTypeObject(result, o);
            list.add(o);
        }
        return list;
    }
}
