package springboot.vertx.starter.example;

import io.vertx.core.Future;
import io.vertx.core.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.vertx.starter.example.dao.DemoDao;
import springboot.vertx.starter.example.entity.Entity;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleApplicationTests {
	@Autowired
	DemoDao demoDao;

	@Test
	public void contextLoads() throws InterruptedException {
		Future<Entity> future=demoDao.selectEntity(1);
		future.setHandler(r->{
			if(r.succeeded()) {
				log.info("{}", Json.encode(r.result()));
			}else{
				r.cause().printStackTrace();
			}
		});
		Thread.currentThread().join();
	}
}
