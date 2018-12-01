package base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 基础测试类，进行了默认的事务控制
 * 
 * @author Patrick Shen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/spring/common.xml",
		"classpath:/conf/spring/config-workflow-dao.xml",
		"classpath:/conf/spring/config-workflow-service.xml" })
public abstract class BaseTxTestCase extends
		AbstractTransactionalJUnit4SpringContextTests {
}
