/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-22
 */

package cn.com.ylink.ips.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZhangDM(Mingly)
 * @date 2012-8-22
 * @description：分页信息对象
 */

public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = -8729541124304675563L;

	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 总页数
	 */
	private int maxPage;
	/**
	 * 总条数
	 */
	private int maxRowCount;
	/**
	 * 每页显示条数
	 */
	private int pageSize;
	/**
	 * 当前页的记录开始行下标
	 */
	private int startRow;

	/**
	 * 当前页的记录结束行下标
	 */
	private int endRow;
	/**
	 * 最后页的条数
	 */
	private int lastPageSize;

	/**
	 * 分页数据
	 */
	private List<T> items;
	
	public PageResult() {
		super();
	}
	
	public PageResult(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getLastPageSize() {
		return lastPageSize;
	}

	public void setLastPageSize(int lastPageSize) {
		this.lastPageSize = lastPageSize;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
