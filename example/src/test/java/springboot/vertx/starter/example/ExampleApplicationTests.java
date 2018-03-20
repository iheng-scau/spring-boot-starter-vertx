package springboot.vertx.starter.example;

import io.vertx.core.Future;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.vertx.starter.example.dao.DemoDao;

import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleApplicationTests {
	@Autowired
	DemoDao demoDao;

	@Test
	public void contextLoads() throws InterruptedException {
		Future<Map<String,Object>> future=demoDao.select();
		future.setHandler(r->{
			log.info("");
		});
		Thread.currentThread().join();
	}
}
