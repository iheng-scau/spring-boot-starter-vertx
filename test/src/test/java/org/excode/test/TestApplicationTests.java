package org.excode.test;

import io.vertx.core.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.excode.test.dao.TestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {
    @Autowired
    TestDao testDao;
    boolean terminate = false;

    @Test
    public void contextLoads() {

        testDao.getTestRecordEntity(20).setHandler(res -> {
            if (res.succeeded()) {
                try {
                    Entity entity = res.result();
                    log.info("{}", Json.encode(entity));
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                res.cause().printStackTrace();
            }
        });

        while (!terminate) {

        }
    }
}
