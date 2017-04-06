package cn.com.ylink.ips.orm.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import cn.com.ylink.ips.core.constant.BaseEnum;

public class CustomEnumTypeHandler<E extends BaseEnum<?>> extends
		BaseTypeHandler<E> {

	private Class<E> type;
	private E[] enums;

	public CustomEnumTypeHandler(Class<E> type) {
		if (type == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
		this.enums = this.type.getEnumConstants();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public E getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String s = cs.getString(columnIndex);
		if (this.enums != null) {
			for (BaseEnum e : enums) {
				if (s != null && e.getValue().equals(s)) {
					return (E) e;
				}
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public E getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String s = rs.getString(columnIndex);
		if (this.enums != null) {
			for (BaseEnum e : enums) {
				if (s != null && e.getValue().equals(s)) {
					return (E) e;
				}
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public E getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String s = rs.getString(columnName);

		if (this.enums != null) {
			for (BaseEnum e : enums) {
				if (s != null && e.getValue().equals(s)) {
					return (E) e;
				}
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings({ "rawtypes"})
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter instanceof BaseEnum) {
			BaseEnum en = (BaseEnum) parameter;
			if (jdbcType == null) {
				ps.setString(i, (String) en.getValue());
			} else {
				ps.setObject(i, (String) en.getValue(), jdbcType.TYPE_CODE); // see
																				// r3589
			}
		}
	}

}
