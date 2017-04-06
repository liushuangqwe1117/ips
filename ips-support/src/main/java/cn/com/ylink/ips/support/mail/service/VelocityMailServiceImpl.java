/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-9
 */

package cn.com.ylink.ips.support.mail.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-9
 * @description：通过Velocity模板发送邮件
 */

@Service("velocityMailService")
public class VelocityMailServiceImpl extends AbstractMailService {
	
	private final static Logger logger = LoggerFactory.getLogger(VelocityMailServiceImpl.class);
	
	@Autowired
	@Qualifier("velocityEngineFactory")
	private VelocityEngineFactoryBean velocityEngineFactory;
	
	protected String getMailText(String template, Map<String, Object> data) {
		VelocityEngine velocityEngine = velocityEngineFactory.getObject();
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, 
			      this.encoding, (data == null) ? new HashMap<String, Object>(0) : data);
		
		return message;
	}
}
