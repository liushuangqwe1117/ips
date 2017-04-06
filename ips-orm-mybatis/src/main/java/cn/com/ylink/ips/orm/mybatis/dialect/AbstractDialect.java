package cn.com.ylink.ips.orm.mybatis.dialect;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-5
 * @description：方言抽象类，得到特殊sql
 */

public abstract class AbstractDialect {
	
	/**
	 * @description 得到分页sql语句
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-5
	 */
	public abstract String getLimitString(String sql, int offset, int limit);
}
