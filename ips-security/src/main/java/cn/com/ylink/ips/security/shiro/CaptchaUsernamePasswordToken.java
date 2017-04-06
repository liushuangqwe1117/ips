package cn.com.ylink.ips.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1L;
	private String captcha;

	public String getCaptcha() {
		return this.captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken() {
	}

	public CaptchaUsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String captcha) {
		super(username, password, rememberMe);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, char[] password,
			String host, String captcha) {
		super(username, password, host);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, char[] password,
			String captcha) {
		super(username, password);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String captcha) {
		super(username, password, rememberMe);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			String host, String captcha) {
		super(username, password, host);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			String captcha) {
		super(username, password);
		this.captcha = captcha;
	}
}