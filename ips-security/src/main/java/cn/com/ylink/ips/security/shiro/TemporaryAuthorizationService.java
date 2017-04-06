package cn.com.ylink.ips.security.shiro;

import javax.servlet.ServletRequest;

public abstract interface TemporaryAuthorizationService {
	public abstract boolean authorize(String[] paramArrayOfString,
			ServletRequest paramServletRequest);
}