package cn.com.ylink.ips.security.shiro.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("shiroConfig")
@Lazy(false)
public class ShiroConfig {
	/**
	 * 日志
	 */
	private static Logger _log = LoggerFactory.getLogger(ShiroConfig.class);

	private Properties props = new Properties();

	public ShiroConfig() {
		// 加载配置文件
		InputStream inputStream = null;
		try {
			inputStream = ShiroConfig.class
					.getResourceAsStream("/properties/shiro.properties");
			props.load(inputStream);
		} catch (Exception e) {
			_log.warn("在classpath下没有找到properties/shiro.properties文件");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					_log.error("关闭流失败", e);
				}
			}
		}
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
}
