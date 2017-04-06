package cn.com.ylink.ips.web.mvc.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import cn.com.ylink.ips.core.exception.BaseCheckedException;
import cn.com.ylink.ips.core.exception.BaseRuntimeException;
import cn.com.ylink.ips.web.base.common.Constants;
import cn.com.ylink.ips.web.base.util.ExceptionMsgUtil;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-29
 * @description：异常处理
 */

public class ExceptionHandlerResolver extends SimpleMappingExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerResolver.class);
	
	private static String ERROE_MSG = "操作失败"; 
	
	private static String MSG_KEY = "message"; 
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		logger.info(ex.getMessage());
		
		//异常信息
		String msg = null;
		if(ex instanceof BaseRuntimeException || ex instanceof BaseCheckedException)
			msg = getExceptionMsg(ex);
		
		if(StringUtils.isEmpty(msg))
			msg = ERROE_MSG;
		
		//判断是否是异步
		String async_flag = request.getParameter(Constants.ASYNC_FLAG);
		if(StringUtils.isNotEmpty(async_flag) && "true".equals(async_flag)){
			return getAsyncModelAndView(msg);
		}
		
		//同步处理
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			return getModelAndView(viewName, ex, request);
		}else {
			return null;
		}
	}
	
	/**
	 * @description 返回异常信息
	 * 				1、判断是否有异常信息代号
	 * 				2、如果没有是否有异常默认信息
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-5
	 */
	private String getExceptionMsg(Exception ex){
		String code = ex instanceof BaseRuntimeException ? ((BaseRuntimeException)ex).getCode() : 
			((BaseCheckedException)ex).getCode();
		
		String msg = null;
		
		if(StringUtils.isNotEmpty(code))
			msg = ExceptionMsgUtil.getExceptionMsg(ex.getMessage());
		
//		if(StringUtils.isEmpty(msg))
//			msg = ex.getMessage();
		
		return msg;
	}
	
	/**
	 * @description 异步返回信息处理
	 * @author ZhangDM(Mingly)
	 * @date 2012-7-29
	 */
	private ModelAndView getAsyncModelAndView(String msg){
		ModelAndView modelAndView = new ModelAndView();
		return setModelMsg(modelAndView, msg);
	}
	
	/**
	 * @description 向模型中放入提示信息
	 * @author ZhangDM(Mingly)
	 * @date 2012-7-29
	 */
	private ModelAndView setModelMsg(ModelAndView modelAndView, String msg){
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put(MSG_KEY, msg);
		modelAndView.getModelMap().addAllAttributes(msgMap);
		
		return modelAndView;
	}
}
