package cn.com.ylink.ips.security.shiro;

import java.util.regex.PatternSyntaxException;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.util.RegExPatternMatcher;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import cn.com.ylink.ips.core.util.StringUtil;

public class MyPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver
{
  private boolean regexPathMatcher = false;

  PatternMatcher regexMatcher = new RegExPatternMatcher();

  public MyPathMatchingFilterChainResolver(boolean regexPathMatcher) {
    this.regexPathMatcher = regexPathMatcher;
  }

  public static String replacePattern(String pattern)
  {
    if (StringUtil.isEmpty(pattern)) {
      return pattern;
    }
    return new String(pattern).replaceAll("(\\\\)(\\\\)=", "=")
      .replaceAll("(\\\\)(\\\\)(\\.)", "\\\\.")
      .replaceFirst("(\\\\)(\\\\)(\\?)", "\\\\?");
  }

  protected boolean pathMatches(String pattern, ServletRequest request)
  {
    PatternMatcher pathMatcher = getPathMatcher();
    if (this.regexPathMatcher) {
      pathMatcher = this.regexMatcher;
    }
    String path = getPathWithinApplication(request);
    if ((request instanceof HttpServletRequest)) {
      String queryString = ((HttpServletRequest)request).getQueryString();
      if ((this.regexPathMatcher) && (!StringUtil.isEmpty(queryString))) {
        path = path + "?" + queryString;
      }
    }
    String regex = pattern;
    if (this.regexPathMatcher)
      regex = replacePattern(pattern);
    try
    {
      return pathMatcher.matches(regex, path);
    }
    catch (PatternSyntaxException e) {
      if (this.regexPathMatcher) {
        return getPathMatcher().matches(pattern, getPathWithinApplication(request));
      }
    }
    return false;
  }

  public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain)
  {
    FilterChainManager filterChainManager = getFilterChainManager();
    if (!filterChainManager.hasChains()) {
      return null;
    }

    for (String pathPattern : filterChainManager.getChainNames()) {
      if (pathMatches(pathPattern, request)) {
        return filterChainManager.proxy(originalChain, pathPattern);
      }
    }
    return null;
  }
}