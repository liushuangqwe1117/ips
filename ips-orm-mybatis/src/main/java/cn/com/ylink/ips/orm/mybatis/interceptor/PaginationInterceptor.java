package cn.com.ylink.ips.orm.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.core.util.ReflectionUtils;
import cn.com.ylink.ips.orm.mybatis.dialect.AbstractDialect;

/**
 * @author ZhangDM(Mingly)
 * @date 2012-8-5
 * @description：分页插件
 */

@Intercepts({ @org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = { java.sql.Connection.class }) })
public class PaginationInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(PaginationInterceptor.class);

	private AbstractDialect dialect;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		StatementHandler statementHandler = getStatementHandler(invocation);
		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, new DefaultObjectFactory(),
				new DefaultObjectWrapperFactory());
		RowBounds rowBounds = (RowBounds) metaStatementHandler
				.getValue("rowBounds");
		if ((rowBounds == null) || (rowBounds == RowBounds.DEFAULT)) {
			return invocation.proceed();
		}

		String originalSql = (String) metaStatementHandler
				.getValue("boundSql.sql");
		metaStatementHandler.setValue("boundSql.sql", this.dialect
				.getLimitString(originalSql, rowBounds.getOffset(),
						rowBounds.getLimit()));

		metaStatementHandler.setValue("rowBounds.offset", Integer.valueOf(0));
		metaStatementHandler.setValue("rowBounds.limit",
				Integer.valueOf(2147483647));

		return invocation.proceed();
	}

	private StatementHandler getStatementHandler(Invocation invocation) {
		StatementHandler statement = (StatementHandler) invocation.getTarget();
		if (statement instanceof RoutingStatementHandler) {
			statement = (StatementHandler) ReflectionUtils.getValueByFieldName(
					statement, "delegate");
		}
		return statement;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

	public void setDialect(AbstractDialect dialect) {
		this.dialect = dialect;
	}

}
