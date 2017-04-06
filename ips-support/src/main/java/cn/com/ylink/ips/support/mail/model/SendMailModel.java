/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-9
 */

package cn.com.ylink.ips.support.mail.model;

import java.io.Serializable;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-9
 * @description：发送邮件实体
 */

public class SendMailModel implements Serializable {

	private static final long serialVersionUID = 1907515713472564425L;
	
	private String[] to;
	private String[] cc;
	private String subject;
	
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
