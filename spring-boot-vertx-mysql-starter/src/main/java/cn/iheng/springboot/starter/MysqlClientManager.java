package cn.iheng.springboot.starter;

import io.vertx.ext.sql.SQLClient;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * class used to hold defined sql client
 *
 * @author iheng
 * @date 1/26/18
 */
public class MysqlClientManager {
    final Map<String, SQLClient> clients = new HashMap<>();

    /**
     * register client in this manager with specified id
     * @param id
     * @param client
     */
    public void register(String id, SQLClient client) {
        clients.put(id, client);
    }

    /**
     * get sql client register with specified id
     *
     * @param id
     * @return
     */
    public SQLClient getSqlClient(String id) {
        return clients.get(id);
    }

    /**
     * close all clients hold by this manager
     */
    @PreDestroy
    public void destroy() {
        clients.forEach((key, value) -> {
            value.close();
        });
    }
}
