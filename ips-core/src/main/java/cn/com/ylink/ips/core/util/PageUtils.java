/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:Samisen 2012-8-23
 */

package cn.com.ylink.ips.core.util;

import cn.com.ylink.ips.core.model.PageResult;

/**
 * @author Samisen
 * @date 2012-8-23
 * @description：分页工具类
 */

public class PageUtils {
	/**
	 * 
	 * 初始化分页对象,调用该方法之前需要设置对象中的总记录数属性
	 * 
	 * @param pageResult 分页对象
	 * @author Samisen
	 * @date 2012-8-23
	 */
	public static void initPageParam(PageResult pageResult) {
		int maxPage = 0;
		int startRow = 0;
		int endRow = 0;
		int lastPageSize = 0;
		int pageSize = pageResult.getPageSize();
		int rowCount = pageResult.getMaxRowCount();
		int currentPage = pageResult.getCurrentPage();
		if (rowCount > 0 && pageSize > 0) {
			// 设置获取总页数
			if (rowCount % pageSize == 0) {
				maxPage = rowCount / pageSize;
				lastPageSize = pageSize;
			} else {
				maxPage = rowCount / pageSize + 1;
				lastPageSize = rowCount % pageSize;
			}
			pageResult.setMaxPage(maxPage);
			// 设置当前页
			if (currentPage <= 1) {
				currentPage = 1;
			} else if (currentPage >= maxPage) {
				currentPage = maxPage;
			}
			pageResult.setCurrentPage(currentPage);
			// 设置分页开始和结束的记录下标
			startRow = (currentPage - 1) * pageSize + 1;
			if (currentPage == maxPage) {
				endRow = startRow + lastPageSize - 1;
				;
			} else {
				endRow = startRow + pageSize - 1;
			}
			pageResult.setStartRow(startRow);
			pageResult.setEndRow(endRow);
		}
	}
}
