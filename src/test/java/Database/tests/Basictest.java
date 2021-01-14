package Database.tests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import Project.Database.SpringBoot_starter;

@RunWith (SpringRunner.class)
@SpringBootTest (classes = SpringBoot_starter.class)
@WebAppConfiguration
public class Basictest {
	@Test
	  public void contextLoads () {
	  }

}
