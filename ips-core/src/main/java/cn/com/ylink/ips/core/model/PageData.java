/**
 * 封装分页查询数据.
 */
package cn.com.ylink.ips.core.model;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PageData<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1756293685402420070L;

	/**
	 * 创建分页数据对象.
	 * 
	 * @param <T>
	 *            泛型
	 * @param pageSize
	 *            每页条数
	 * @param pageNumber
	 *            页码
	 * @return 分页数据对象
	 * @author LS  
	 * @date 2013-8-1
	 */
	public static <T> PageData<T> createPagerData(int pageSize, int pageNumber) {
		PageData<T> data = new PageData<T>();
		data.setPageSize(pageSize);
		data.setPageNumber(pageNumber);
		return data;
	}

	/**
	 * 创建分页数据对象.
	 * 
	 * @param <T>
	 *            泛型
	 * @param request
	 *            HTTP请求
	 * @return 分页数据对象
	 * @author LS  
	 * @date 2013-8-1
	 */
	public static <T> PageData<T> createPagerData(HttpServletRequest request) {
		PageData<T> data = new PageData<T>();
		if (request.getParameter("pageSize") != null) {
			data.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		if (request.getParameter("pageNumber") != null) {
			data.setPageNumber(Integer.valueOf(request
					.getParameter("pageNumber")));
		}
		return data;
	}

	/**
	 * 总记录数.
	 */
	private long total = 0;
	/**
	 * 每页条数.
	 */
	private int pageSize = 15;
	/**
	 * 页码,从1开始.
	 */
	private int pageNumber = 1;
	/**
	 * 数据集合.
	 */
	private List<T> rows = null;

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 获取结束行索引.索引从1开始
	 * 
	 * @return 结束行索引
	 * @author LS 
	 * @date 2013-8-1
	 */
	public int getEndIndex() {
		return getPageNumber() * getPageSize();
	}

	/**
	 * 获取开始行索引.
	 * 
	 * @return 开始行索引
	 * @author LS 
	 * @date 2013-8-1
	 */
	public int getBeginIndex() {
		return getEndIndex() - getPageSize() + 1;
	}
}
