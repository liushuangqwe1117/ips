package cn.com.ylink.ips.core.model;

public class PageQueryParam<T> {

	T enity;
	int beginIndex;
	int endIndex;
	String orderkey;
	/**
	 * 默认升序
	 */
	boolean asc = true;

	public T getEnity() {
		return enity;
	}

	public void setEnity(T enity) {
		this.enity = enity;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getOrderkey() {
		return orderkey;
	}

	public void setOrderkey(String orderkey) {
		this.orderkey = orderkey;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
}
