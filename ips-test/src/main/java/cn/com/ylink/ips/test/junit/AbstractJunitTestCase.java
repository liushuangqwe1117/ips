/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-9-20
 */

package cn.com.ylink.ips.test.junit;

//import org.junit.Before;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
import cn.com.ylink.ips.core.listener.SpringContextHolder;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-9-20
 * @description：junit测试抽象类
 */
//@ContextConfiguration(locations = {"classpath*:applicationContext.xml",
//		"classpath*:framework-spring-dependence.xml","classpath*:spring-test.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//@TransactionConfiguration(transactionManager="myTxManager",defaultRollback=true)
public abstract class AbstractJunitTestCase{
	
	@Autowired
	private ApplicationContext ctx;
	
//	@Before
	public void applicationContextSetUp(){
		SpringContextHolder.setApplicationContext(ctx);
	}
	
	
}
