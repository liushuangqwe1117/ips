package cn.com.ylink.ips.security.shiro;

import java.util.Map;

public abstract interface UrlsSectionService
{
  public abstract Map<String, String> loadUrlsSection();
}
