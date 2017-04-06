package cn.com.ylink.ips.web.base.common;

public abstract interface SessionKey {
	public static final String EXCEPTION_OBJECT = "exception_object";
	public static final String SUCCESS_MESSAGES = "success_messages";
	public static final String ERROR_MESSAGES = "error_messages";
	public static final String LOGIN_FAILURE_COUNT = "login_failure_count";
	public static final String LOGIN_ACCOUNT = "login_account";
	public static final String KAPTCHA = "kaptcha";
	public static final String CA_RANDOM_NUMBER = "ca_random_number";
	public static final String PREFERRED_LOCALE = "preferred_locale";
	public static final String PREFERRED_THEME = "preferred_theme";
	public static final String SHOPPING_CART = "shopping_cart";
	public static final String REQUEST_PARAMETERS = "request_parameters";
}