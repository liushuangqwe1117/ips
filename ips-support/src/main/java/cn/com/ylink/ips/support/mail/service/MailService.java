/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-9
 */

package cn.com.ylink.ips.support.mail.service;

import cn.com.ylink.ips.support.mail.exception.SendMailException;
import cn.com.ylink.ips.support.mail.model.SendMailModel;

import java.util.Map;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-9
 * @description：发送邮件服务
 */

public interface MailService {
	
	/**
	 * @description 发送邮件
	 * @param sendMailModel 发送邮件实体信息
	 * @param template 模板名称
	 * @param data 模板所需数据
	 * @author ZhangDM(Mingly)
	 *  @date 2012-8-9
	 */
	public void sendMail(SendMailModel sendMailModel, String template, Map<String, Object> data) throws SendMailException;
}
