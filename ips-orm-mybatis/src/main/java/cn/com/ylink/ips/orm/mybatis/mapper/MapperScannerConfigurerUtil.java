package cn.com.ylink.ips.orm.mybatis.mapper;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.ApplicationContext;
import cn.com.ylink.ips.core.util.PackagesUtils;
import cn.com.ylink.ips.orm.mybatis.annotation.MybatisMapper;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-26
 * @description：扫描并注册mapper接口
 */

public class MapperScannerConfigurerUtil extends MapperScannerConfigurer {
	
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext){
	    this.applicationContext = applicationContext;
	    super.setApplicationContext(applicationContext);
	    setAnnotationClass(MybatisMapper.class);
	}

	public void afterPropertiesSet()throws Exception{
	    String allBasePackages = PackagesUtils.getAllBasePackages(this.applicationContext);
	    setBasePackage(allBasePackages.toString());
	    super.afterPropertiesSet();
	}
}
