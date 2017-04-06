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
import cn.com.ylink.ips.web.base.util.TokenUtil;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-24
 * @description：防止重复提交自定义标签
 */

public final class TokenTag extends TagSupport{
	
	private static final long serialVersionUID = -4766595320791923912L;
	
	private static Logger log = LoggerFactory.getLogger(TokenTag.class);
	
	@Override
	public int doStartTag()throws JspException {
		
		try {
			String input = "<input type='hidden' name='%s' value='%s'/><input type='hidden' name='%s' value='%s'/>";
			String tokenName = TokenUtil.TOKEN_NAME;
		    String tokenKey = TokenUtil.generateGUID();
		    String tokenValue = TokenUtil.setToken(this.pageContext.getSession(), tokenKey);
		    input = String.format(input, new Object[] { tokenName, tokenKey, tokenKey, tokenValue });
	    
	    	this.pageContext.getOut().append(input);
	    } catch (IOException e) {
	    	log.error("TokenTag error");
	    }
	    
	    return super.doStartTag(); 
	}

	
}
