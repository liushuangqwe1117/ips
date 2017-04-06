/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-7
 */

package cn.com.ylink.ips.web.base.controller;

import javax.servlet.http.HttpServletRequest;

import cn.com.ylink.ips.web.base.common.Constants;
import org.apache.commons.lang.math.NumberUtils;
import cn.com.ylink.ips.core.model.PageResult;
import cn.com.ylink.ips.web.base.util.TokenUtil;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-7
 * @description：控制层基类
 */

public abstract class BaseController {
	
	/**
	 * @description 判断是否重复提交
	 * @param request
	 * @return true：重复提交
	 * 		   false：没有重复提交 
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	public boolean isDuplicateSubmit(HttpServletRequest request){
		return TokenUtil.isDuplicateSubmit(request);
	}
	
	protected PageResult getPageResult(final HttpServletRequest request) {
		return new PageResult(getPageNumber(request), getPageSize(request));
	}
	
	protected int getPageNumber(final HttpServletRequest request) {
		String pageNumber = request.getParameter(Constants.PAGE_NUMBER);
		return NumberUtils.isDigits(pageNumber) ? Integer.parseInt(pageNumber) : Constants.DEFAULT_PAGE_NUMBER;
	}
	
	/**
	 * @description 得到每页显示条数
	 * @param request
	 * @return 
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-23
	 */
	protected int getPageSize(final HttpServletRequest request) {
		String pageSize = request.getParameter(Constants.PAGE_SIZE);
		return NumberUtils.isDigits(pageSize) ? Integer.parseInt(pageSize) : Constants.DEFAULT_PAGE_SIZE;
	}
}
