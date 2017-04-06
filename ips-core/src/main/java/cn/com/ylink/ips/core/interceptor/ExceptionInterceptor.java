package cn.com.ylink.ips.core.interceptor;

import java.lang.reflect.Constructor;

import cn.com.ylink.ips.core.exception.BaseRuntimeException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.core.exception.AppCheckedException;
import cn.com.ylink.ips.core.model.ExceptionInfo;

public class ExceptionInterceptor implements MethodInterceptor {

	private ExceptionInfo exceptionInfo;

	public void setExceptionInfo(ExceptionInfo exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		try {
			Object obj = methodInvocation.proceed();
			return obj;
		} catch (Exception e) {
			Logger logger = LoggerFactory.getLogger(methodInvocation
					.getMethod().getDeclaringClass());
			logger.error(e.getMessage(), e);
			String exceptionClass = exceptionInfo.getExceptionClass();
			Constructor<?> construtor = Class.forName(exceptionClass)
					.getConstructor(String.class, String.class);
			AppCheckedException appException;
			if (e instanceof BaseRuntimeException) {
				BaseRuntimeException ex = (BaseRuntimeException) e;
				appException = (AppCheckedException) construtor.newInstance(
						ex.getCode(), ex.getMessage());
			} else {
				if (e instanceof AppCheckedException) {
					appException = (AppCheckedException) e;
				} else {
					appException = (AppCheckedException) construtor
							.newInstance(exceptionInfo.getCode(),
									exceptionInfo.getMessage());
				}
			}
			throw appException;
		}

	}

}
