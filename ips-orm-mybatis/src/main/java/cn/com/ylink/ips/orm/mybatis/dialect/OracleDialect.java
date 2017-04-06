package cn.com.ylink.ips.orm.mybatis.dialect;

/**
 * @author ZhangDM(Mingly)
 * @date 2012-8-6
 * @description：oracle的sql特殊处理
 */

public class OracleDialect extends AbstractDialect {
	
	private static String FOR_UPDATE = " for update";

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(FOR_UPDATE)) {
			sql = sql.substring(0, sql.length() - FOR_UPDATE.length());
			isForUpdate = true;
		}

		StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);

		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ ) where rownum_ <= " + (offset + limit)
				+ " and rownum_ > " + offset);

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}
