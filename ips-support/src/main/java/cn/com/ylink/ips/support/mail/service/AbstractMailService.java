/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-9
 */

package cn.com.ylink.ips.support.mail.service;

import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import cn.com.ylink.ips.support.mail.exception.SendMailException;
import cn.com.ylink.ips.support.mail.model.SendMailModel;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-9
 * @description：邮件服务抽象类
 */

public abstract class AbstractMailService implements MailService {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractMailService.class);
	
	@Autowired
	@Qualifier("mailSender")
	protected JavaMailSender sender;
	
	@Value("${mail.defaultEncoding}")
	protected String encoding;
	
	@Value("${mail.from.default}")
	protected String from;

	public void sendMail(SendMailModel sendMailModel, String template,
			Map<String, Object> data) throws SendMailException {
		MimeMessage message = this.sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, this.encoding);
			setTo(sendMailModel, helper);
			setCC(sendMailModel, helper);
			helper.setFrom(this.from);
			setSubject(sendMailModel, helper);
			helper.setSentDate(new Date());
			helper.setText(getMailText(template, data), true);
			this.sender.send(message);
		} catch (MessagingException e) {
			logger.warn(e.getMessage());
			throw new SendMailException("发送邮件失败");
		} catch(SendMailException e){
			logger.warn(e.getMessage());
			throw new SendMailException(e.getMessage());
		}
	}
	
	/**
	 * @description 添加收件人
	 * @param sendMailModel
	 * @param helper
	 * @throws MessagingException 
	 * @author ZhangDM(Mingly)
	 * @throws SendMailException 
	 * @date 2012-8-10
	 */
	private void setTo(SendMailModel sendMailModel, MimeMessageHelper helper) 
			throws MessagingException, SendMailException{
		
		String[] to = sendMailModel.getTo();
	    if ((to != null) && (to.length > 0))
	    	helper.setTo(to);
	    else
	    	throw new SendMailException("收件人不能为空");
	}
	
	/**
	 * @description 设置抄送人
	 * @param sendMailModel
	 * @param helper
	 * @throws MessagingException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-10
	 */
	private void setCC(SendMailModel sendMailModel, MimeMessageHelper helper)
	    throws MessagingException {
		
		String[] cc = sendMailModel.getCc();
	    if ((cc != null) && (cc.length > 0))
	    	helper.setCc(cc);
	}
	
	/**
	 * @description 设置主题
	 * @param sendMailModel
	 * @param helper
	 * @throws MessagingException 
	 * @author ZhangDM(Mingly)
	 * @throws SendMailException 
	 * @date 2012-8-10
	 */
	private void setSubject(SendMailModel sendMailModel, MimeMessageHelper helper)
		    throws MessagingException, SendMailException {
		
	    if (StringUtils.isNotBlank(sendMailModel.getSubject())) {
	    	helper.setSubject(sendMailModel.getSubject());
	    } else {
	    	throw new SendMailException("主题不能为空");
	    }
	}
	
	/**
	 * @description 得到邮件内容
	 * @param template
	 * @param data
	 * @return 
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-10
	 */
	protected abstract String getMailText(String template, Map<String, Object> data);

}
