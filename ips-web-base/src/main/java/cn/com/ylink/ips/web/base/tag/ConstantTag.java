/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-7
 */
package cn.com.ylink.ips.web.base.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.core.annotation.handler.ClassPathConstantAnnotationHandler;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-24
 * @description：常量自定义标签
 */

public final class ConstantTag extends TagSupport{
	
	private static final long serialVersionUID = -4766595320791923912L;
	
	private static Logger log = LoggerFactory.getLogger(ConstantTag.class);
	
	private String namespace;
	private String fieldName;
	
	@Override
	public int doStartTag()throws JspException {
		
		try { 
			String key = this.namespace + "." + this.fieldName;
			String constant = (String)ClassPathConstantAnnotationHandler.getConstants().get(key);
			if (constant == null) {
		        log.warn("find a null constant [key = {}]", key);
			}
			this.pageContext.getOut().write(constant);
		}catch (IOException e){
			log.error("ConstantTag error");
		}
	    
	    return super.doStartTag(); 
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
}
