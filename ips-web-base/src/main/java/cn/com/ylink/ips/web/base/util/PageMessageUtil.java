package cn.com.ylink.ips.web.base.util;

import javax.servlet.http.HttpSession;

import cn.com.ylink.ips.web.base.common.SessionKey;

/**
 * 页面提示信息提示工具类
 * 
 * @author LS
 * 
 */
public class PageMessageUtil {

	/**
	 * 提示信息
	 * 
	 * @param session
	 * @param sessionKey
	 * @param msg
	 */
	//@SuppressWarnings("unchecked")
	public static void saveMessage(HttpSession session, String sessionKey,
			String msg) {
		/*List<String> messages = (List<String>) session.getAttribute(sessionKey);
		if (messages == null) {
			messages = new ArrayList<String>();
		}
		messages.add(msg);
		session.setAttribute(sessionKey, messages);*/
		session.setAttribute(sessionKey, msg);
	}

	/**
	 * 操作成功提示信息
	 * 
	 * @param session
	 * @param msg
	 */
	public static void saveSuccessMessage(HttpSession session, String msg) {
		saveMessage(session, SessionKey.SUCCESS_MESSAGES, msg);
	}

	/**
	 * 操作失败提示信息
	 * 
	 * @param session
	 * @param msg
	 */
	public static void saveErrorMessage(HttpSession session, String msg) {
		saveMessage(session, SessionKey.ERROR_MESSAGES, msg);
	}

}
